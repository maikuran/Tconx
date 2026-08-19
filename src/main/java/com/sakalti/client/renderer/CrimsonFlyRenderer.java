package com.sakalti.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CrimsonFlyRenderer extends EntityRenderer<CrimsonFlyEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("sakalti", "textures/entity/crimson_fly.png");

    public CrimsonFlyRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.shadowRadius = 0.35F;
    }

    @Override
    public void render(
            CrimsonFlyEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        poseStack.pushPose();

        // Entityの向きに合わせる
        poseStack.mulPose(
                Axis.YP.rotationDegrees(180.0F - entityYaw)
        );

        // 小型の飛行モブとして描画
        poseStack.scale(
                1.0F,
                1.0F,
                1.0F
        );

        poseStack.popPose();

        super.render(
                entity,
                entityYaw,
                partialTick,
                poseStack,
                buffer,
                packedLight
        );
    }

    @Override
    public ResourceLocation getTextureLocation(
            CrimsonFlyEntity entity
    ) {
        return TEXTURE;
    }
}
