package com.sakalti.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sakalti.client.model.CrimsonFlyModel;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CrimsonFlyRenderer extends EntityRenderer<CrimsonFlyEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("sakalti", "textures/entity/crimson_fly.png");

    private final CrimsonFlyModel model;

    public CrimsonFlyRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.model = new CrimsonFlyModel();
        this.shadowSize = 0.35F;
    }

    @Override
    public void render(
            CrimsonFlyEntity entity,
            float entityYaw,
            float partialTicks,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffer,
            int packedLight
    ) {
        matrixStack.push();

        // 1.16.5 での回転補間とアニメーション値の正しい計算
        float renderYaw = MathHelper.interpolateAngle(partialTicks, entity.prevRotationYaw, entity.rotationYaw);
        float limbSwing = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
        float limbSwingAmount = MathHelper.lerp(partialTicks, entity.prevLimbSwingAmount, entity.limbSwingAmount);
        float ageInTicks = entity.ticksExisted + partialTicks;
        float headPitch = MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch);

        this.model.setupAnim(
                entity,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                renderYaw,
                headPitch
        );

        this.model.renderToBuffer(
                matrixStack,
                buffer.getBuffer(RenderType.getEntityCutoutNoCull(TEXTURE)),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        matrixStack.pop();

        super.render(
                entity,
                entityYaw,
                partialTicks,
                matrixStack,
                buffer,
                packedLight
        );
    }

    @Override
    public ResourceLocation getTextureLocation(CrimsonFlyEntity entity) {
        return TEXTURE;
    }
}
