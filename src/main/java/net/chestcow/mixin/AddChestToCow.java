package net.chestcow.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.chestcow.EntityChestCow;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(CowEntity.class)
public abstract class AddChestToCow extends AnimalEntity {
	public AddChestToCow(EntityType<CowEntity> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> info) {
		ItemStack itemstack = player.getStackInHand(hand);
		if(itemstack.getItem() != Item.getItemFromBlock(Blocks.CHEST)) return;
		if(hand == Hand.OFF) return;
		
		AnimalEntity cow = this;
		if(cow.isChild()) return;
		
		World world = cow.getEntityWorld();
		cow.invalidate();
		
		
		world.addParticle(ParticleTypes.EXPLOSION_EMITTER, cow.x, cow.y + (double)(cow.height / 2.0F), cow.z, 0.0D, 0.0D, 0.0D);
		
		if(!player.isCreative()) {
			itemstack.subtractAmount(1);
		}
		
		if(!world.isClient) {
			EntityChestCow chestCow = new EntityChestCow(world);
			chestCow.setPositionAndAngles(cow.x, cow.y, cow.z, cow.yaw, cow.pitch);
			chestCow.setHealth(cow.getHealth());
			// chestCow.renderYawOffset = cow.renderYawOffset;
			chestCow.field_6283 = cow.field_6283;

			if(cow.hasCustomName()) {
				chestCow.setCustomName(cow.getCustomName());
			}

			world.spawnEntity(chestCow);
		}

		info.setReturnValue(true);
	}
}
