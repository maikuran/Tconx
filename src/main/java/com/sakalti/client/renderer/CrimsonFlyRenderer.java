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

public class CrimsonFlyRenderer extends EntityRenderer<CrimsonFlyEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("sakalti", "textures/entity/crimson_fly.png");

    private final CrimsonFlyModel model;

    public CrimsonFlyRenderer(EntityRendererManager renderManager) {
        super(renderManager);

        // 1.16.5 では ModelLayerLocation を使わず直接インスタンス化します
        this.model = new CrimsonFlyModel();
        this.shadowSize = 0.35F; // 1.16.5 では shadowRadius ではなく shadowSize です
    }

    
    public void render(
            CrimsonFlyEntity entity,
            float entityYaw,
            float partialTicks,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffer,
            int packedLight
    ) {
        matrixStack.push();

        // 1.16.5 でのモデルアニメーション定義と描画呼び出し
        this.model.setRotationAngles(
                entity,
                entity.ticksExisted,
                0.0F,
                entity.ticksExisted + partialTicks,
                entityYaw,
                entity.rotationPitch
        );

        this.model.render(
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

    
    public ResourceLocation getEntityTexture(CrimsonFlyEntity entity) {
        return TEXTURE;
    }
}
