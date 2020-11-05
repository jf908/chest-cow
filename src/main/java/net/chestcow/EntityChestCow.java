package net.chestcow;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EntityChestCow extends CowEntity {
	private BasicInventory chest;
	
	public EntityChestCow(World w) {
		super(ChestCow.CHEST_COW, w);
		
		initChest();
	}
			
	private void initChest() {
		chest = new BasicInventory(this.getName(), 27);
    }
	
	@Override
	public boolean interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getStackInHand(hand);
		
		if(method_6481(itemstack)) {
			super.interactMob(player, hand);
			return true;
		} else if(itemstack.getItem() == Items.NAME_TAG) {
			return itemstack.interactWithEntity(player, this, hand);
		} else {
			chest.setCustomName(this.getName());
			openGUI(player);
			return true;
		}
    }
	
	public void openGUI(PlayerEntity player) {
        if (!this.world.isClient) {
        	player.openInventory(chest);
        }
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag compound) {
		super.writeCustomDataToTag(compound);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < chest.getInvSize(); i++) {
			ItemStack itemstack = chest.getInvStack(i);

			if (!itemstack.isEmpty()) {
				CompoundTag nbttagcompound = new CompoundTag();
				nbttagcompound.putByte("Slot", (byte)i);
				itemstack.toTag(nbttagcompound);
				nbttaglist.add(nbttagcompound);
			}
        }
        compound.put("Items", nbttaglist);
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag compound) {
		super.readCustomDataFromTag(compound);

		ListTag nbttaglist = compound.getList("Items", 10);

		for(int i = 0; i < nbttaglist.size(); i++){
			CompoundTag nbttagcompound = nbttaglist.getCompoundTag(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < chest.getInvSize()) {
				chest.setInvStack(j, ItemStack.fromTag(nbttagcompound));
			}
		}

	}
	
	//Drop items on death
	@Override
	public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if(!this.world.isClient) {
            for (int i = 0; i < chest.getInvSize(); i++) {
                ItemStack itemstack = chest.getInvStack(i);

                if (!itemstack.isEmpty()) {
                    this.dropStack(itemstack, 0.0F);
                }
            }
        }
    }
}
