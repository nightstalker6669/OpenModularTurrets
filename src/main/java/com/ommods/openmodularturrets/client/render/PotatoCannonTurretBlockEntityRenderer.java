package com.ommods.openmodularturrets.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ommods.openmodularturrets.client.model.PotatoCannonTurretModel;
import com.ommods.openmodularturrets.content.block.turrets.PotatoCannonTurretBlockEntity;
import com.ommods.openmodularturrets.util.TurretAimHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class PotatoCannonTurretBlockEntityRenderer implements BlockEntityRenderer<PotatoCannonTurretBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/block/potato_cannon_turret.png");
    private final PotatoCannonTurretModel model;
    public PotatoCannonTurretBlockEntityRenderer(BlockEntityRendererProvider.Context context){ this.model = new PotatoCannonTurretModel(context.bakeLayer(PotatoCannonTurretModel.LAYER_LOCATION)); }
    @Override public void render(PotatoCannonTurretBlockEntity be,float pt,PoseStack ps,MultiBufferSource bs,int light,int overlay){ if (be.isConcealed()) { return; } ps.pushPose(); ps.translate(0.5D,1.5D,0.5D); ps.scale(1.0F,-1.0F,-1.0F); model.resetPose(); model.setBaseRotation(be.getBaseFacing()); model.setRotationForTarget(TurretAimHelper.getRotationXY(be.getRenderYaw(),be.getRenderPitch(),be.getBaseFacing()), TurretAimHelper.getRotationXZ(be.getRenderYaw(),be.getRenderPitch(),be.getBaseFacing())); VertexConsumer vc=bs.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)); model.renderToBuffer(ps,vc,light,overlay,-1); ps.popPose(); }
}
