package net.chestcow;

import net.chestcow.client.RenderChestCow;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ChestCowClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    EntityRendererRegistry.INSTANCE.register(ChestCow.CHEST_COW,
        (dispatcher, context) -> new RenderChestCow(dispatcher));
  }
}
