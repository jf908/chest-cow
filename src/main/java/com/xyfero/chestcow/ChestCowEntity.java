package com.xyfero.chestcow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ChestCowEntity extends CowEntity implements INamedContainerProvider {
    private Inventory inventory;

//    public ChestCowEntity(World w) {
//        this(ChestCow.CHEST_COW.get(), w);
//    }

    public ChestCowEntity(EntityType<? extends CowEntity> entityType, World w) {
        super(entityType, w);

        initChest();
    }

    private void initChest() {
        inventory = new Inventory(27);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isBreedingItem(itemstack)) {
            return super.func_230254_b_(player, hand);
        } else if (itemstack.getItem() == Items.NAME_TAG) {
            return itemstack.interactWithEntity(player, this, hand);
        } else {
            return openGUI(player);
        }
    }

    public ActionResultType openGUI(PlayerEntity player) {
        if (this.world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            player.openContainer(this);
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void writeAdditional(CompoundNBT tag) {
        super.writeAdditional(tag);
        ListNBT nbttaglist = new ListNBT();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                CompoundNBT nbttagcompound = new CompoundNBT();
                nbttagcompound.putByte("Slot", (byte) i);
                itemstack.write(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }
        tag.put("Items", nbttaglist);
    }

    @Override
    public void readAdditional(CompoundNBT tag) {
        super.readAdditional(tag);

        ListNBT nbttaglist = tag.getList("Items", 10);

        for (int i = 0; i < nbttaglist.size(); i++) {
            CompoundNBT nbttagcompound = nbttaglist.getCompound(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < inventory.getSizeInventory()) {
                inventory.setInventorySlotContents(j, ItemStack.read(nbttagcompound));
            }
        }
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (!this.world.isRemote) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack itemstack = inventory.getStackInSlot(i);

                if (!itemstack.isEmpty()) {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }
    }

    @Override
    @Nullable
    public Container createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (playerEntity.isSpectator()) {
            return null;
        } else {
            return ChestContainer.createGeneric9X3(syncId, playerInventory, inventory);
        }
    }
}
