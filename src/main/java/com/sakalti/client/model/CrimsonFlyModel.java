package com.sakalti.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sakalti.entity.CrimsonFlyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CrimsonFlyModel extends EntityModel<CrimsonFlyEntity> {

    public static final int TEXTURE_WIDTH = 64;
    public static final int TEXTURE_HEIGHT = 64;

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public CrimsonFlyModel(ModelPart root) {
        this.root = root;

        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // 胴体
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(
                                -4.0F,
                                -3.0F,
                                -6.0F,
                                8.0F,
                                6.0F,
                                12.0F
                        ),
                PartPose.ZERO
        );

        // 頭
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(
                                -3.0F,
                                -3.0F,
                                -4.0F,
                                6.0F,
                                6.0F,
                                6.0F
                        ),
                PartPose.offset(
                        0.0F,
                        -1.0F,
                        -7.0F
                )
        );

        // 左翼
        root.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create()
                        .texOffs(0, 30)
                        .addBox(
                                0.0F,
                                -0.5F,
                                -4.0F,
                                12.0F,
                                1.0F,
                                8.0F
                        ),
                PartPose.offset(
                        4.0F,
                        -1.0F,
                        0.0F
                )
        );

        // 右翼
        root.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create()
                        .texOffs(0, 40)
                        .addBox(
                                -12.0F,
                                -0.5F,
                                -4.0F,
                                12.0F,
                                1.0F,
                                8.0F
                        ),
                PartPose.offset(
                        -4.0F,
                        -1.0F,
                        0.0F
                )
        );

        // 左脚
        root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 50)
                        .addBox(
                                -1.0F,
                                0.0F,
                                -1.0F,
                                2.0F,
                                5.0F,
                                2.0F
                        ),
                PartPose.offset(
                        2.0F,
                        3.0F,
                        2.0F
                )
        );

        // 右脚
        root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create()
                        .texOffs(8, 50)
                        .addBox(
                                -1.0F,
                                0.0F,
                                -1.0F,
                                2.0F,
                                5.0F,
                                2.0F
                        ),
                PartPose.offset(
                        -2.0F,
                        3.0F,
                        2.0F
                )
        );

        return LayerDefinition.create(
                mesh,
                TEXTURE_WIDTH,
                TEXTURE_HEIGHT
        );
    }

    @Override
    public void setupAnim(
            CrimsonFlyEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        // 羽ばたき
        float wingAngle =
                (float) Math.sin(ageInTicks * 1.8F) * 0.45F;

        this.leftWing.zRot = -wingAngle;
        this.rightWing.zRot = wingAngle;

        // 頭
        this.head.yRot =
                netHeadYaw * ((float) Math.PI / 180F);

        this.head.xRot =
                headPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer vertexConsumer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        root.render(
                poseStack,
                vertexConsumer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );
    }
}
