package net.chestcow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChestCow implements ModInitializer {
	private static String modid = "chestcow";
	private static String name = "chest_cow";

	public static final EntityType<EntityChestCow> CHEST_COW = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(modid, name), FabricEntityTypeBuilder.<EntityChestCow>create(SpawnGroup.MISC, EntityChestCow::new)
					.dimensions(EntityDimensions.fixed(0.9f, 1.4f)).trackRangeBlocks(10).build());

	public static Item spawnEgg = new SpawnEggItem(CHEST_COW, 2893598, 11106091,
			(new Item.Settings()).group(ItemGroup.MISC));

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(CHEST_COW, EntityChestCow.createCowAttributes());
		Registry.register(Registry.ITEM, new Identifier(modid, name + "_spawn_egg"), spawnEgg);
	}
}
