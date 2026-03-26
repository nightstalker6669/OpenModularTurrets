package com.ommods.openmodularturrets.client.screen;

import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import com.ommods.openmodularturrets.menu.TurretBaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class TurretBaseScreen extends AbstractContainerScreen<TurretBaseMenu> {
    private static final ResourceLocation[] TIER_TEXTURES = new ResourceLocation[]{
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/turret_base_tier_one.png"),
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/turret_base_tier_two.png"),
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/turret_base_tier_three.png"),
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/turret_base_tier_four.png"),
        ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/turret_base_tier_five.png")
    };

    public TurretBaseScreen(TurretBaseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
    }

    @Override
    protected void init() {
        super.init();
        int x = this.leftPos;
        int y = this.topPos;

        this.addRenderableWidget(Button.builder(Component.literal("-"), button -> clickMenuButton(1)).bounds(x + 120, y + 15, 20, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("+"), button -> clickMenuButton(2)).bounds(x + 120, y + 50, 20, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Drop Turrets"), button -> clickMenuButton(3)).bounds(x + 180, y, 80, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Drop Base"), button -> clickMenuButton(4)).bounds(x + 180, y + 25, 80, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Configure"), button -> openConfigureScreen()).bounds(x + 180, y + 50, 80, 20).build());
        this.addRenderableWidget(Button.builder(getTargetingLabel(), button -> {
            clickMenuButton(6);
            button.setMessage(getTargetingLabel());
        }).bounds(x + 180, y + 75, 80, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ResourceLocation texture = getTextureForTier(this.menu.getTier());
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        renderEnergyBar(guiGraphics, texture);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("Ammo"), 8, 6, 0x404040, false);
        if (this.menu.getTier() > 1) {
            guiGraphics.drawString(this.font, Component.literal("Addons:"), 71, 6, 0x404040, false);
        }
        guiGraphics.drawString(this.font, Component.translatable("text.openmodularturrets.gui.inventory"), this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
        guiGraphics.drawString(this.font, Component.literal("-Y"), 123, 6, 0x404040, false);
        guiGraphics.drawString(this.font, Component.literal(Integer.toString(this.menu.getYAxisDetect())), 127, 39, 0x9C40FF, false);
        guiGraphics.drawString(this.font, getAmmoSummaryLine(0), 106, 56, 0x404040, false);
        guiGraphics.drawString(this.font, getAmmoSummaryLine(1), 106, 66, 0x404040, false);
    }

    private Component formatAmmoLine(int slot) {
        AmmoType[] types = AmmoType.values();
        int ordinal = this.menu.getAmmoTypeOrdinal(slot);
        AmmoType ammoType = ordinal >= 0 && ordinal < types.length ? types[ordinal] : AmmoType.NONE;
        if (ammoType == AmmoType.NONE || this.menu.getAmmoCount(slot) <= 0) {
            return Component.translatable("screen.openmodularturrets.turret_base.ammo_empty", slot + 1);
        }
        return Component.translatable(
            "screen.openmodularturrets.turret_base.ammo_loaded",
            slot + 1,
            ammoType.name().toLowerCase(),
            this.menu.getAmmoCount(slot)
        );
    }

    private Component getAmmoSummaryLine(int slot) {
        AmmoType[] types = AmmoType.values();
        int ordinal = this.menu.getAmmoTypeOrdinal(slot);
        AmmoType ammoType = ordinal >= 0 && ordinal < types.length ? types[ordinal] : AmmoType.NONE;
        if (ammoType == AmmoType.NONE || this.menu.getAmmoCount(slot) <= 0) {
            return Component.literal("A" + (slot + 1) + ": Empty");
        }
        return Component.literal("A" + (slot + 1) + ": " + getAmmoShortName(ammoType) + " x" + this.menu.getAmmoCount(slot));
    }

    private String getAmmoShortName(AmmoType ammoType) {
        return switch (ammoType) {
            case BULLET -> "Bullet";
            case ROCKET_SMALL -> "Rocket";
            case ROCKET_LARGE -> "L.Rocket";
            case RAILGUN_SLUG -> "Slug";
            case CROSSBOW_BOLT -> "Bolt";
            case FIRE_CHARGE -> "Fire";
            case GRENADE -> "Grenade";
            case ARTILLERY_SHELL -> "Shell";
            case POTATO -> "Potato";
            case BLAZING_CLAY -> "Clay";
            case DISPOSABLE_ITEM -> "Item";
            case NONE -> "Empty";
        };
    }

    private ResourceLocation getTextureForTier(int tier) {
        int index = Math.max(0, Math.min(TIER_TEXTURES.length - 1, tier - 1));
        return TIER_TEXTURES[index];
    }

    private void renderEnergyBar(GuiGraphics guiGraphics, ResourceLocation texture) {
        int energyCapacity = Math.max(1, this.menu.getEnergyCapacity());
        int energyStored = Math.max(0, this.menu.getEnergyStored());
        int expression = (energyStored * 51) / energyCapacity;

        guiGraphics.blit(texture, this.leftPos + 153, this.topPos + 17, 178, 17, 14, 51, 256, 256);
        if (expression > 0) {
            guiGraphics.blit(
                texture,
                this.leftPos + 153,
                this.topPos + 17 + 51 - expression,
                196,
                68 - expression,
                14,
                expression,
                256,
                256
            );
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (isHovering(153, 17, 14, 51, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("screen.openmodularturrets.turret_base.energy", this.menu.getEnergyStored(), this.menu.getEnergyCapacity()), mouseX, mouseY);
        }
        if (isHovering(104, 54, 56, 24, mouseX, mouseY)) {
            guiGraphics.renderComponentTooltip(
                this.font,
                List.of(
                    Component.translatable("text.openmodularturrets.gui.base"),
                    formatAmmoLine(0),
                    formatAmmoLine(1),
                    Component.literal("Turrets: " + this.menu.getAttachedTurretCount())
                ),
                mouseX,
                mouseY
            );
        }
        if (isHovering(8, 17, 54, 54, mouseX, mouseY)) {
            guiGraphics.renderComponentTooltip(
                this.font,
                List.of(
                    Component.literal("Stored ammo"),
                    formatAmmoLine(0),
                    formatAmmoLine(1)
                ),
                mouseX,
                mouseY
            );
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void clickMenuButton(int id) {
        if (this.minecraft == null || this.minecraft.gameMode == null) {
            return;
        }
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, id);
    }

    public void clickMenuButtonFromChild(int id) {
        clickMenuButton(id);
    }

    public TurretBaseMenu getMenu() {
        return this.menu;
    }

    private void openConfigureScreen() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(new TurretBaseConfigureScreen(this));
        }
    }

    private Component getTargetingLabel() {
        return Component.literal(this.menu.isMultiTargeting() ? "Target: Multi" : "Target: Single");
    }
}
