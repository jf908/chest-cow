package com.xyfero.chestcow;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("chestcow")
public class ChestCow {
    public static final String MODID = "chestcow";
    public static final Logger LOGGER = LogManager.getLogger();

    private static final String CHEST_COW_NAME = "chest_cow";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChestCow.MODID);
    public static final RegistryObject<EntityType<ChestCowEntity>> CHEST_COW = ENTITY_TYPES.register(CHEST_COW_NAME, () -> EntityType.Builder.<ChestCowEntity>create(ChestCowEntity::new, EntityClassification.MISC).size(EntityType.COW.getWidth(), EntityType.COW.getHeight()).trackingRange(10).build(new ResourceLocation(MODID, CHEST_COW_NAME).toString()));

//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChestCow.MODID);
//    public static final RegistryObject<Item> CHEST_COW_SPAWN_EGG = ITEMS.register(CHEST_COW_NAME + "_spawn_egg", () -> new
//            SpawnEggItem(CHEST_COW.get(), 2893598, 11106091, (new Item.Properties()).group(ItemGroup.MISC)));

    public ChestCow() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::init);
        modEventBus.addListener(ChestCowClient::init);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(CHEST_COW.get(), ChestCowEntity.func_234188_eI_().create());
        });
    }
}
