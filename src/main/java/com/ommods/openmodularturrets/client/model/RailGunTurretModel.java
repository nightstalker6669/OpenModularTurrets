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

public class RailGunTurretModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "rail_gun_turret"), "main");

    private final ModelPart base;
    private final ModelPart holder;
    private final ModelPart weapon;

    public RailGunTurretModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.base = root.getChild("base");
        this.holder = this.base.getChild("holder");
        this.weapon = this.holder.getChild("weapon");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition base = root.addOrReplaceChild(
            "base",
            CubeListBuilder.create().texOffs(0, 0).addBox(-6, 7, -6, 12, 1, 12),
            PartPose.offset(0, 16, 0)
        );
        PartDefinition holder = base.addOrReplaceChild(
            "holder",
            CubeListBuilder.create(),
            PartPose.offset(0, 0, 0)
        );
        holder.addOrReplaceChild(
            "weapon",
            CubeListBuilder.create()
                .texOffs(25, 27).addBox(-1, 1, -16, 2, 1, 17)
                .texOffs(25, 27).addBox(-1, -2, -16, 2, 1, 17)
                .texOffs(25, 45).addBox(-2, -2, -16, 1, 4, 17)
                .texOffs(25, 45).addBox(1, -2, -16, 1, 4, 17)
                .texOffs(0, 29).addBox(-3, 1, 0, 6, 2, 6)
                .texOffs(0, 37).addBox(-3, -4, 1, 6, 4, 6)
                .texOffs(0, 21).addBox(-1, 0, 3, 2, 1, 1)
                .texOffs(0, 47).addBox(-6.1F, -6, -3, 1, 8, 8)
                .texOffs(0, 47).addBox(5.1F, -6, -3, 1, 8, 8)
                .texOffs(0, 25).addBox(-6, -0.9F, 0, 12, 1, 1),
            PartPose.offset(0, -1, 0)
        );
        return LayerDefinition.create(mesh, 64, 64);
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
        this.holder.yRot = z;
        this.weapon.xRot = y;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int tint) {
        this.base.render(poseStack, vertexConsumer, packedLight, packedOverlay, tint);
    }
}
