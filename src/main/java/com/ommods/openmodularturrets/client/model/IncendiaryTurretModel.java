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

public class IncendiaryTurretModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "incendiary_turret"), "main");

    private final ModelPart base;
    private final ModelPart holder;
    private final ModelPart weapon;

    public IncendiaryTurretModel(ModelPart root) {
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
            CubeListBuilder.create()
                .texOffs(0, 37).addBox(-6, 7, -6, 12, 1, 12)
                .texOffs(0, 28).addBox(-2, 3, -2, 4, 4, 4),
            PartPose.offset(0, 16, 0)
        );
        PartDefinition holder = base.addOrReplaceChild(
            "holder",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-4, 3, -4, 8, 1, 8)
                .texOffs(0, 0).addBox(-4, -2, 0, 8, 1, 1),
            PartPose.offset(0, 1, 0)
        );
        holder.addOrReplaceChild(
            "box_left_r1",
            CubeListBuilder.create()
                .texOffs(0, 15).addBox(-12, -5, -4, 8, 1, 8)
                .texOffs(0, 15).addBox(-12, 4, -4, 8, 1, 8),
            PartPose.offsetAndRotation(0, 8, 0, 0, 0, 1.5708F)
        );
        holder.addOrReplaceChild(
            "weapon",
            CubeListBuilder.create()
                .texOffs(0, 0).addBox(1, -1, -10, 2, 2, 11)
                .texOffs(0, 0).addBox(-3, -1, -10, 2, 2, 11)
                .texOffs(29, 0).addBox(-3, -3, -6, 6, 4, 10),
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
