package com.ommods.openmodularturrets.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class LaserTurretModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "laser_turret"), "main");

    private final ModelPart base;
    private final ModelPart holder;
    private final ModelPart weapon;

    public LaserTurretModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.base = root.getChild("base");
        this.holder = this.base.getChild("holder");
        this.weapon = this.holder.getChild("weapon");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition base = root.addOrReplaceChild(
            "base",
            CubeListBuilder.create()
                .texOffs(0, 37).addBox(-6.0F, 7.0F, -6.0F, 12.0F, 1.0F, 12.0F)
                .texOffs(0, 28).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 4.0F, 4.0F),
            PartPose.offset(0.0F, 16.0F, 0.0F)
        );

        PartDefinition holder = base.addOrReplaceChild(
            "holder",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-4.0F, 3.0F, -4.0F, 8.0F, 1.0F, 8.0F)
                .texOffs(0, 0).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 1.0F, 1.0F),
            PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        holder.addOrReplaceChild(
            "box_left_r1",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-12.0F, -5.0F, -4.0F, 8.0F, 1.0F, 8.0F)
                .texOffs(0, 15).addBox(-12.0F, 4.0F, -4.0F, 8.0F, 1.0F, 8.0F),
            PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.0F, 1.5708F)
        );

        holder.addOrReplaceChild(
            "weapon",
            CubeListBuilder.create()
                .texOffs(20, 0).addBox(-2.0F, -7.0F, -3.0F, 4.0F, 7.0F, 4.0F)
                .texOffs(0, 28).addBox(-2.0F, -5.0F, 0.9F, 4.0F, 4.0F, 4.0F)
                .texOffs(37, 16).addBox(-1.0F, -4.0F, -8.1F, 2.0F, 1.0F, 7.0F)
                .texOffs(37, 0).addBox(-1.0F, -2.0F, -12.1F, 2.0F, 1.0F, 10.0F)
                .texOffs(39, 26).addBox(-1.0F, -6.0F, -6.1F, 2.0F, 1.0F, 7.0F),
            PartPose.offset(0.0F, 0.0F, 0.1F)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public void resetPose() {
        this.base.resetPose();
        this.holder.resetPose();
        this.weapon.resetPose();
    }

    public void setBaseRotation(Direction baseFacing) {
        float mountXRot = switch (baseFacing) {
            case EAST, WEST, NORTH, SOUTH -> 1.56F;
            case UP -> 3.145F;
            case DOWN -> 0.0F;
        };
        float mountYRot = switch (baseFacing) {
            case EAST -> 1.565F;
            case WEST -> 4.705F;
            case SOUTH -> 3.145F;
            case NORTH, UP, DOWN -> 0.0F;
        };

        this.base.xRot = mountXRot;
        this.base.yRot = mountYRot;
    }

    public void setRotationForTarget(float y, float z) {
        this.weapon.xRot = y;
        this.holder.yRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int tint) {
        this.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, tint);
    }
}
