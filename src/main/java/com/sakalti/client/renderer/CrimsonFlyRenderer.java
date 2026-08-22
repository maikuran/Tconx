package com.sakalti.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sakalti.client.model.CrimsonFlyModel;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class CrimsonFlyRenderer extends MobRenderer<CrimsonFlyEntity, CrimsonFlyModel> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("sakalti", "textures/entity/crimson_fly.png");

    public CrimsonFlyRenderer(EntityRendererManager renderManager) {
        // MobRenderer(RenderManager, Model, shadowSize)
        super(renderManager, new CrimsonFlyModel(), 0.35F);
    }

    
    public ResourceLocation getTextureLocation(CrimsonFlyEntity entity) {
        return TEXTURE;
    }

    // 必要に応じてサイズの微調整を行う場合（オプション）
    
    protected void scale(CrimsonFlyEntity entity, MatrixStack matrixStack, float partialTicks) {
        // 例: 1.0F で標準サイズ（必要に応じてモデルの大きさを変更可能）
        matrixStack.scale(1.0F, 1.0F, 1.0F);
        super.scale(entity, matrixStack, partialTicks);
    }
}
