package net.chestcow.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

@Environment(EnvType.CLIENT)
public class RenderChestCow extends CowEntityRenderer {
	public RenderChestCow(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
		this.addFeature(new LayerChest(this));
	}
}
