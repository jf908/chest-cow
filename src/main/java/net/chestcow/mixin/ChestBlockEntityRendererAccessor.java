package net.chestcow.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(ChestBlockEntityRenderer.class)
public interface ChestBlockEntityRendererAccessor {
  @Accessor("doubleChestLeftLid")
  public ModelPart getDoubleChestLeftLid();

  @Accessor("doubleChestLeftLatch")
  public ModelPart getDoubleChestLeftLatch();

  @Accessor("doubleChestLeftBase")
  public ModelPart getDoubleChestLeftBase();

  @Accessor("doubleChestRightLid")
  public ModelPart getDoubleChestRightLid();

  @Accessor("doubleChestRightLatch")
  public ModelPart getDoubleChestRightLatch();

  @Accessor("doubleChestRightBase")
  public ModelPart getDoubleChestRightBase();

  @Accessor("christmas")
  public boolean getChristmas();

  @Invoker("render")
  public void renderChestPart(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch,
      ModelPart base, float openFactor, int light, int overlay);
}
