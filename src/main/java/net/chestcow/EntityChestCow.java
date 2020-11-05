package net.chestcow;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntityChestCow extends CowEntity implements NamedScreenHandlerFactory {
	private SimpleInventory inventory;

	public EntityChestCow(EntityType<? extends CowEntity> entityType, World w) {
		super(entityType, w);

		initChest();
	}

	private void initChest() {
		inventory = new SimpleInventory(27);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getStackInHand(hand);

		if (this.isBreedingItem(itemstack)) {
			return super.interactMob(player, hand);
		} else if (itemstack.getItem() == Items.NAME_TAG) {
			return itemstack.useOnEntity(player, this, hand);
		} else {
			return openGUI(player);
		}
	}

	public ActionResult openGUI(PlayerEntity player) {
		if (this.world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			player.openHandledScreen(this);
			return ActionResult.CONSUME;
		}
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		ListTag nbttaglist = new ListTag();

		for (int i = 0; i < inventory.size(); i++) {
			ItemStack itemstack = inventory.getStack(i);

			if (!itemstack.isEmpty()) {
				CompoundTag nbttagcompound = new CompoundTag();
				nbttagcompound.putByte("Slot", (byte) i);
				itemstack.toTag(nbttagcompound);
				nbttaglist.add(nbttagcompound);
			}
		}
		tag.put("Items", nbttaglist);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);

		ListTag nbttaglist = tag.getList("Items", 10);

		for (int i = 0; i < nbttaglist.size(); i++) {
			CompoundTag nbttagcompound = nbttaglist.getCompound(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < inventory.size()) {
				inventory.setStack(j, ItemStack.fromTag(nbttagcompound));
			}
		}
	}

	// Drop items on death
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);

		if (!this.world.isClient) {
			for (int i = 0; i < inventory.size(); i++) {
				ItemStack itemstack = inventory.getStack(i);

				if (!itemstack.isEmpty()) {
					this.dropStack(itemstack, 0.0F);
				}
			}
		}
	}

	@Nullable
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		if (playerEntity.isSpectator()) {
			return null;
		} else {
			return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
		}
	}
}
