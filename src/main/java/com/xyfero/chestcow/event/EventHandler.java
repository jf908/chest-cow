package com.xyfero.chestcow.event;

import com.xyfero.chestcow.ChestCow;
import com.xyfero.chestcow.ChestCowEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChestCow.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void onEntityInteract(EntityInteract event) {
        if (event.getTarget().getClass().equals(CowEntity.class) && event.getItemStack().getItem() == Items.CHEST) {

            if (event.getHand() == Hand.OFF_HAND) return;

            CowEntity cow = (CowEntity) event.getTarget();
            if (cow.isChild()) return;

            World world = event.getTarget().getEntityWorld();
            cow.remove();

            if (!event.getPlayer().abilities.isCreativeMode) {
                event.getItemStack().shrink(1);
            }

            world.playMovingSound(event.getPlayer(), cow, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!world.isRemote) {
                ((ServerWorld) world).spawnParticle(ParticleTypes.EXPLOSION, cow.getPosX(), cow.getPosYHeight(0.5D), cow.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);

                ChestCowEntity chestCow = ChestCow.CHEST_COW.get().create(world);
                chestCow.setLocationAndAngles(cow.getPosX(), cow.getPosY(), cow.getPosZ(), cow.rotationYaw, cow.rotationPitch);
                chestCow.setHealth(cow.getHealth());
                chestCow.renderYawOffset = cow.renderYawOffset;

                if (cow.hasCustomName()) {
                    chestCow.setCustomName(cow.getCustomName());
                }

                if (cow.isNoDespawnRequired()) {
                    chestCow.enablePersistence();
                }

                chestCow.setInvulnerable(cow.isInvulnerable());
                world.addEntity(chestCow);
            }
        }
    }
}
