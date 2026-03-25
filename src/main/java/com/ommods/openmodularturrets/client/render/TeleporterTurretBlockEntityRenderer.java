package com.ommods.openmodularturrets.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ommods.openmodularturrets.client.model.TeleporterTurretModel;
import com.ommods.openmodularturrets.content.block.turrets.TeleporterTurretBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class TeleporterTurretBlockEntityRenderer implements BlockEntityRenderer<TeleporterTurretBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/block/teleporter_turret.png");

    private final TeleporterTurretModel model;

    public TeleporterTurretBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new TeleporterTurretModel(context.bakeLayer(TeleporterTurretModel.LAYER_LOCATION));
    }

    @Override
    public void render(TeleporterTurretBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.isConcealed()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);

        this.model.resetPose();
        this.model.setBaseRotation(blockEntity.getBaseFacing());

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }
}
