package com.xyfero.chestcow.client;

import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestCowRenderer extends CowRenderer {
    public ChestCowRenderer(EntityRendererManager manager) {
        super(manager);
        this.addLayer(new LayerChest(this));
    }
}
