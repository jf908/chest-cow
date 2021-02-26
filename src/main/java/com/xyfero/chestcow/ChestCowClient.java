package com.xyfero.chestcow;

import com.xyfero.chestcow.client.ChestCowRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ChestCowClient {
    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ChestCow.CHEST_COW.get(), ChestCowRenderer::new);
    }
}
