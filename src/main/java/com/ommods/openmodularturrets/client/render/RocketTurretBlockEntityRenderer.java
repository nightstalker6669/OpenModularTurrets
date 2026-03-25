package com.ommods.openmodularturrets.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ommods.openmodularturrets.client.model.RocketTurretModel;
import com.ommods.openmodularturrets.content.block.turrets.RocketTurretBlockEntity;
import com.ommods.openmodularturrets.util.TurretAimHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class RocketTurretBlockEntityRenderer implements BlockEntityRenderer<RocketTurretBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/block/rocket_turret.png");

    private final RocketTurretModel model;

    public RocketTurretBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new RocketTurretModel(context.bakeLayer(RocketTurretModel.LAYER_LOCATION));
    }

    @Override
    public void render(RocketTurretBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.isConcealed()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);

        this.model.resetPose();
        this.model.setBaseRotation(blockEntity.getBaseFacing());
        this.model.setRotationForTarget(
            TurretAimHelper.getRotationXY(blockEntity.getRenderYaw(), blockEntity.getRenderPitch(), blockEntity.getBaseFacing()),
            TurretAimHelper.getRotationXZ(blockEntity.getRenderYaw(), blockEntity.getRenderPitch(), blockEntity.getBaseFacing())
        );

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }
}
