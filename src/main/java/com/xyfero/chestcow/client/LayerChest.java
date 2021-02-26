package com.xyfero.chestcow.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerChest extends LayerRenderer<CowEntity, CowModel<CowEntity>> {
    private ChestTileEntityRenderer chestRenderer = new ChestTileEntityRenderer<ChestTileEntity>(null);

    public LayerChest(IEntityRenderer<CowEntity, CowModel<CowEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, CowEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        RenderMaterial leftSprite = Atlases.getChestMaterial(null, ChestType.LEFT,
                chestRenderer.isChristmas);
        RenderMaterial rightSprite = Atlases.getChestMaterial(null, ChestType.RIGHT,
                chestRenderer.isChristmas);
        IVertexBuilder leftVertices = leftSprite.getBuffer(vertexConsumers, RenderType::getEntityCutout);
        IVertexBuilder rightVertices = rightSprite.getBuffer(vertexConsumers, RenderType::getEntityCutout);

        matrices.push();
        matrices.rotate(Vector3f.YP.rotationDegrees(90.0F));
        matrices.rotate(Vector3f.XP.rotationDegrees(180.0F));
        matrices.translate(-.02f, -.751f, -0.43f);
        matrices.scale(.7F, .72F, .86F);
        chestRenderer.renderModels(matrices, leftVertices, chestRenderer.leftLid,
                chestRenderer.leftLatch, chestRenderer.leftBottom, 0, light,
                OverlayTexture.NO_OVERLAY);
        matrices.push();
        matrices.translate(-1.0f, 0f, 0f);
        chestRenderer.renderModels(matrices, rightVertices, chestRenderer.rightLid,
                chestRenderer.rightLatch, chestRenderer.rightBottom, 0, light,
                OverlayTexture.NO_OVERLAY);

        matrices.pop();
        matrices.pop();
    }
}
