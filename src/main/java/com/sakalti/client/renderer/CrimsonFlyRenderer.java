package com.sakalti.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sakalti.client.model.CrimsonFlyModel;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CrimsonFlyRenderer extends EntityRenderer<CrimsonFlyEntity> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    new ResourceLocation("sakalti", "crimson_fly"),
                    "main"
            );

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(
                    "sakalti",
                    "textures/entity/crimson_fly.png"
            );

    private final CrimsonFlyModel model;

    public CrimsonFlyRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context);

        this.model = new CrimsonFlyModel(
                context.bakeLayer(LAYER_LOCATION)
        );

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

        poseStack.scale(
                1.0F,
                1.0F,
                1.0F
        );

        this.model.setupAnim(
                entity,
                entity.tickCount,
                partialTick,
                entity.tickCount + partialTick,
                entity.getYRot(),
                entity.getXRot()
        );

        this.model.renderToBuffer(
                poseStack,
                buffer.getBuffer(
                        RenderType.entityCutoutNoCull(TEXTURE)
                ),
                packedLight,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                1.0F,
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
