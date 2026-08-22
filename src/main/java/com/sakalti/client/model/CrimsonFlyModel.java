package com.sakalti.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CrimsonFlyModel extends EntityModel<CrimsonFlyEntity> {

    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;
    private final ModelRenderer leftLeg;
    private final ModelRenderer rightLeg;

    public CrimsonFlyModel() {

        this.texWidth = 64;
        this.texHeight = 64;

        /*
         * 胴体
         */
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(
                -4.0F,
                -3.0F,
                -6.0F,
                8.0F,
                6.0F,
                12.0F
        );
        this.body.setPos(
                0.0F,
                0.0F,
                0.0F
        );

        /*
         * 頭
         */
        this.head = new ModelRenderer(this, 0, 18);
        this.head.addBox(
                -3.0F,
                -3.0F,
                -4.0F,
                6.0F,
                6.0F,
                6.0F
        );
        this.head.setPos(
                0.0F,
                -1.0F,
                -7.0F
        );

        /*
         * 左翼
         */
        this.leftWing = new ModelRenderer(this, 0, 30);
        this.leftWing.addBox(
                0.0F,
                -0.5F,
                -4.0F,
                12.0F,
                1.0F,
                8.0F
        );
        this.leftWing.setPos(
                4.0F,
                -1.0F,
                0.0F
        );

        /*
         * 右翼
         */
        this.rightWing = new ModelRenderer(this, 0, 40);
        this.rightWing.addBox(
                -12.0F,
                -0.5F,
                -4.0F,
                12.0F,
                1.0F,
                8.0F
        );
        this.rightWing.setPos(
                -4.0F,
                -1.0F,
                0.0F
        );

        /*
         * 左脚
         */
        this.leftLeg = new ModelRenderer(this, 0, 50);
        this.leftLeg.addBox(
                -1.0F,
                0.0F,
                -1.0F,
                2.0F,
                5.0F,
                2.0F
        );
        this.leftLeg.setPos(
                2.0F,
                3.0F,
                2.0F
        );

        /*
         * 右脚
         */
        this.rightLeg = new ModelRenderer(this, 8, 50);
        this.rightLeg.addBox(
                -1.0F,
                0.0F,
                -1.0F,
                2.0F,
                5.0F,
                2.0F
        );
        this.rightLeg.setPos(
                -2.0F,
                3.0F,
                2.0F
        );
    }

    public void setupAnim(
            CrimsonFlyEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {

        /*
         * 羽ばたき
         */
        float wingAngle =
                MathHelper.sin(
                        ageInTicks * 1.8F
                ) * 0.45F;

        this.leftWing.zRot = -wingAngle;
        this.rightWing.zRot = wingAngle;

        /*
         * 頭
         */
        this.head.yRot =
                netHeadYaw
                        * ((float) Math.PI / 180F);

        this.head.xRot =
                headPitch
                        * ((float) Math.PI / 180F);
    }

    
    public void renderToBuffer(
            MatrixStack matrixStack,
            IVertexBuilder buffer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {

        this.body.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );

        this.head.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );

        this.leftWing.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );

        this.rightWing.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );

        this.leftLeg.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );

        this.rightLeg.render(
                matrixStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );
    }
}
