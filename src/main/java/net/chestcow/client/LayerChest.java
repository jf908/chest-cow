package net.chestcow.client;

import net.chestcow.mixin.ChestBlockEntityRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.CowEntity;

@Environment(EnvType.CLIENT)
public class LayerChest extends FeatureRenderer<CowEntity, CowEntityModel<CowEntity>> {
	private final ChestBlockEntityRendererAccessor chestRenderer = (ChestBlockEntityRendererAccessor) (new ChestBlockEntityRenderer<ChestBlockEntity>(
			null));

	public LayerChest(FeatureRendererContext<CowEntity, CowEntityModel<CowEntity>> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CowEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		SpriteIdentifier leftSprite = TexturedRenderLayers.getChestTexture(null, ChestType.LEFT,
				chestRenderer.getChristmas());
		SpriteIdentifier rightSprite = TexturedRenderLayers.getChestTexture(null, ChestType.RIGHT,
				chestRenderer.getChristmas());
		VertexConsumer leftVertices = leftSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
		VertexConsumer rightVertices = rightSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

		matrices.push();
		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
		matrices.translate(-.02f, -.751f, -0.43f);
		matrices.scale(.7F, .72F, .86F);
		chestRenderer.renderChestPart(matrices, leftVertices, chestRenderer.getDoubleChestLeftLid(),
				chestRenderer.getDoubleChestLeftLatch(), chestRenderer.getDoubleChestLeftBase(), 0, light,
				OverlayTexture.DEFAULT_UV);
		matrices.push();
		matrices.translate(-1.0f, 0f, 0f);
		chestRenderer.renderChestPart(matrices, rightVertices, chestRenderer.getDoubleChestRightLid(),
				chestRenderer.getDoubleChestRightLatch(), chestRenderer.getDoubleChestRightBase(), 0, light,
				OverlayTexture.DEFAULT_UV);

		matrices.pop();
		matrices.pop();
	}
}