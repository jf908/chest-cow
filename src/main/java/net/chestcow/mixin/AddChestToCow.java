package net.chestcow.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.chestcow.ChestCow;
import net.chestcow.EntityChestCow;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(CowEntity.class)
public abstract class AddChestToCow extends AnimalEntity {
	public AddChestToCow(EntityType<CowEntity> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ItemStack itemstack = player.getStackInHand(hand);
		if (itemstack.getItem() != Items.CHEST)
			return;
		if (hand == Hand.OFF_HAND)
			return;

		AnimalEntity cow = this;
		if (cow.isBaby())
			return;

		World world = cow.getEntityWorld();
		cow.remove();

		if (!player.isCreative()) {
			itemstack.decrement(1);
		}

		world.playSoundFromEntity(player, cow, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
		if (!world.isClient) {
			((ServerWorld) cow.world).spawnParticles(ParticleTypes.EXPLOSION, cow.getX(), cow.getBodyY(0.5D), cow.getZ(), 1,
					0.0D, 0.0D, 0.0D, 0.0D);

			EntityChestCow chestCow = ChestCow.CHEST_COW.create(cow.world);
			chestCow.refreshPositionAndAngles(cow.getX(), cow.getY(), cow.getZ(), cow.yaw, cow.pitch);
			chestCow.setHealth(cow.getHealth());
			chestCow.bodyYaw = cow.bodyYaw;
			if (cow.hasCustomName()) {
				chestCow.setCustomName(cow.getCustomName());
				chestCow.setCustomNameVisible(cow.isCustomNameVisible());
			}

			if (cow.isPersistent()) {
				chestCow.setPersistent();
			}

			chestCow.setInvulnerable(cow.isInvulnerable());
			world.spawnEntity(chestCow);
		}

		info.setReturnValue(ActionResult.success(world.isClient));
	}
}
