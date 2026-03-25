package com.ommods.openmodularturrets.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TurretBaseConfigureScreen extends Screen {
    private final TurretBaseScreen parent;

    public TurretBaseConfigureScreen(TurretBaseScreen parent) {
        super(Component.literal("Turret Base Configure"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        int left = this.width / 2 - 90;
        int top = this.height / 2 - 80;

        this.addRenderableWidget(Button.builder(getMobsLabel(), button -> {
            this.parent.clickMenuButtonFromChild(11);
            button.setMessage(getMobsLabel());
        }).bounds(left, top, 180, 20).build());

        this.addRenderableWidget(Button.builder(getNeutralsLabel(), button -> {
            this.parent.clickMenuButtonFromChild(12);
            button.setMessage(getNeutralsLabel());
        }).bounds(left, top + 24, 180, 20).build());

        this.addRenderableWidget(Button.builder(getPlayersLabel(), button -> {
            this.parent.clickMenuButtonFromChild(13);
            button.setMessage(getPlayersLabel());
        }).bounds(left, top + 48, 180, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> this.onClose()).bounds(left, top + 90, 180, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, Component.literal("Targeting options:"), this.width / 2, this.height / 2 - 98, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parent);
        }
    }

    private Component getMobsLabel() {
        return Component.literal("Attack Mobs: " + (parent.getMenu().isAttacksMobs() ? "Yes" : "No"));
    }

    private Component getNeutralsLabel() {
        return Component.literal("Attack Neutrals: " + (parent.getMenu().isAttacksNeutrals() ? "Yes" : "No"));
    }

    private Component getPlayersLabel() {
        return Component.literal("Attack Players: " + (parent.getMenu().isAttacksPlayers() ? "Yes" : "No"));
    }
}
