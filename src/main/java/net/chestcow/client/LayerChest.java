package net.chestcow.client;

// import net.chestcow.CowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ChestDoubleEntityModel;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

@Environment(EnvType.CLIENT)
public class LayerChest extends FeatureRenderer<CowEntity,CowEntityModel<CowEntity>>
{
    private final FeatureRendererContext<CowEntity,CowEntityModel<CowEntity>> context;
    private static final Identifier TEXTURE_NORMAL_DOUBLE = new Identifier("textures/entity/chest/normal_double.png");
    private final ChestDoubleEntityModel modelLargeChest = new ChestDoubleEntityModel();

    public LayerChest(FeatureRendererContext<CowEntity,CowEntityModel<CowEntity>> context) {
        super(context);
        this.context = context;
    }
    
    public void render(CowEntity chestCow, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(-0.45F, 0.01F, .8F);
        GlStateManager.scalef(.9F, .75F, .7F);
        int i = chestCow.getLightmapCoordinates();
        int j = i % 65536;
        int k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
        context.bindTexture(TEXTURE_NORMAL_DOUBLE);
        modelLargeChest.method_2799();
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
    }

    // shouldCombineTextures
    public boolean method_4200() {
        return false;
    }
}