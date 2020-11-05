package net.chestcow;

import net.chestcow.client.RenderChestCow;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChestCow implements ModInitializer, ClientModInitializer {
	public static EntityType<EntityChestCow> CHEST_COW;

	@Override
	public void onInitialize() {
		final String modid = "chestcow";
		final String name = "chest_cow";
		Identifier id = new Identifier(modid, name);
		EntityType<EntityChestCow> entityType = Registry.register(Registry.ENTITY_TYPE, id, FabricEntityTypeBuilder.create(EntityChestCow.class, EntityChestCow::new).trackable(80, 3).build());
		Item spawnEgg = new SpawnEggItem(entityType, 2893598, 11106091, (new Item.Settings()).itemGroup(ItemGroup.MISC));
		Registry.register(Registry.ITEM, new Identifier(modid, name + "_spawn_egg"), spawnEgg);
		CHEST_COW = entityType;
	}

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(EntityChestCow.class, (dispatcher, context) -> new RenderChestCow(dispatcher));
	}
}
