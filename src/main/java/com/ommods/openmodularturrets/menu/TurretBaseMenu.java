package com.ommods.openmodularturrets.menu;

import com.ommods.openmodularturrets.OMTMenus;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
public class TurretBaseMenu extends AbstractContainerMenu {
    private static final int BASE_SLOT_COUNT = 13;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_SLOT_COUNT = PLAYER_INVENTORY_SLOT_COUNT + HOTBAR_SLOT_COUNT;
    private static final int BASE_SLOT_START = 0;
    private static final int PLAYER_SLOT_START = BASE_SLOT_START + BASE_SLOT_COUNT;
    private static final int HOTBAR_SLOT_START = PLAYER_SLOT_START + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int AMMO_SLOT_END = 9;
    private static final int ADDON_SLOT_START = 9;
    private static final int ADDON_SLOT_END = 11;
    private static final int UPGRADE_SLOT_START = 11;
    private static final int UPGRADE_SLOT_END = 13;

    private final Container container;
    private final ContainerData data;
    private final ContainerLevelAccess access;
    private final int menuTier;

    public TurretBaseMenu(int containerId, Inventory playerInventory, net.minecraft.network.RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, decodeClientState(buffer));
    }

    public TurretBaseMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(BASE_SLOT_COUNT), createClientData(1), 1);
    }

    public TurretBaseMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        this(containerId, playerInventory, container, data, resolveMenuTier(container, data));
    }

    private TurretBaseMenu(int containerId, Inventory playerInventory, ClientMenuState clientState) {
        this(containerId, playerInventory, new SimpleContainer(BASE_SLOT_COUNT), createClientData(clientState.tier()), clientState.tier());
    }

    private TurretBaseMenu(int containerId, Inventory playerInventory, Container container, ContainerData data, int menuTier) {
        super(OMTMenus.TURRET_BASE.get(), containerId);
        checkContainerSize(container, BASE_SLOT_COUNT);
        checkContainerDataCount(data, 13);
        this.container = container;
        this.data = data;
        this.menuTier = menuTier;
        this.access = createAccess(playerInventory.player.level(), container);
        container.startOpen(playerInventory.player);
        this.addTurretBaseSlots(container);
        this.addPlayerInventorySlots(playerInventory);
        this.addDataSlots(data);
    }

    private static ContainerLevelAccess createAccess(net.minecraft.world.level.Level level, Container container) {
        if (container instanceof TurretBaseBlockEntity turretBaseBlockEntity) {
            return ContainerLevelAccess.create(level, turretBaseBlockEntity.getBlockPos());
        }
        return ContainerLevelAccess.NULL;
    }

    private static ClientMenuState decodeClientState(net.minecraft.network.RegistryFriendlyByteBuf buffer) {
        buffer.readBlockPos();
        return new ClientMenuState(Math.max(1, buffer.readVarInt()));
    }

    private static ContainerData createClientData(int tier) {
        SimpleContainerData data = new SimpleContainerData(13);
        data.set(7, tier);
        data.set(8, 2);
        return data;
    }

    private static int resolveMenuTier(Container container, ContainerData data) {
        if (container instanceof TurretBaseBlockEntity turretBaseBlockEntity) {
            return turretBaseBlockEntity.getTier();
        }
        int syncedTier = data.get(7);
        return Math.max(1, syncedTier);
    }

    private void addTurretBaseSlots(Container container) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                this.addSlot(createValidatedSlot(container, column + row * 3, 8 + column * 18, 17 + row * 18, 64));
            }
        }
        this.addSlot(createValidatedSlot(container, 9, menuTier > 1 ? 72 : -1000, menuTier > 1 ? 18 : -1000, 1));
        this.addSlot(createValidatedSlot(container, 10, menuTier > 1 ? 92 : -1000, menuTier > 1 ? 18 : -1000, 1));
        this.addSlot(createValidatedSlot(container, 11, menuTier > 1 ? 72 : -1000, menuTier > 1 ? 52 : -1000, 1));
        this.addSlot(createValidatedSlot(container, 12, menuTier > 4 ? 92 : -1000, menuTier > 4 ? 52 : -1000, 1));
    }

    private Slot createValidatedSlot(Container container, int slotIndex, int x, int y, int maxStackSize) {
        return new Slot(container, slotIndex, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return container.canPlaceItem(slotIndex, stack);
            }

            @Override
            public int getMaxStackSize(ItemStack stack) {
                return maxStackSize;
            }
        };
    }

    private void addPlayerInventorySlots(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    public int getEnergyStored() {
        return this.data.get(0);
    }

    public int getEnergyCapacity() {
        return this.data.get(1);
    }

    public int getAmmoCount(int slot) {
        return this.data.get(2 + slot);
    }

    public int getAmmoTypeOrdinal(int slot) {
        return this.data.get(4 + slot);
    }

    public int getAttachedTurretCount() {
        return this.data.get(6);
    }

    public int getTier() {
        return Math.max(this.menuTier, this.data.get(7));
    }

    private record ClientMenuState(int tier) {
    }

    public int getYAxisDetect() {
        return this.data.get(8);
    }

    public boolean isMultiTargeting() {
        return this.data.get(9) != 0;
    }

    public boolean isAttacksMobs() {
        return this.data.get(10) != 0;
    }

    public boolean isAttacksNeutrals() {
        return this.data.get(11) != 0;
    }

    public boolean isAttacksPlayers() {
        return this.data.get(12) != 0;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (!(this.container instanceof TurretBaseBlockEntity base)) {
            return false;
        }

        switch (id) {
            case 1 -> base.adjustYAxisDetect(-1);
            case 2 -> base.adjustYAxisDetect(1);
            case 3 -> base.dropAttachedTurrets();
            case 4 -> base.dropBase();
            case 5 -> {
                return false;
            }
            case 6 -> base.toggleMultiTargeting();
            case 11 -> base.toggleAttacksMobs();
            case 12 -> base.toggleAttacksNeutrals();
            case 13 -> base.toggleAttacksPlayers();
            default -> {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotStack = slot.getItem();
        ItemStack original = slotStack.copy();

        if (slotIndex < BASE_SLOT_COUNT) {
            if (!this.moveItemStackTo(slotStack, PLAYER_SLOT_START, PLAYER_SLOT_START + PLAYER_SLOT_COUNT, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            boolean moved;
            if (this.container.canPlaceItem(ADDON_SLOT_START, slotStack)) {
                moved = this.moveItemStackTo(slotStack, ADDON_SLOT_START, ADDON_SLOT_END, false);
            } else if (this.container.canPlaceItem(UPGRADE_SLOT_START, slotStack)) {
                moved = this.moveItemStackTo(slotStack, UPGRADE_SLOT_START, UPGRADE_SLOT_END, false);
            } else if (this.container.canPlaceItem(0, slotStack)) {
                moved = this.moveItemStackTo(slotStack, BASE_SLOT_START, AMMO_SLOT_END, false);
            } else if (slotIndex < HOTBAR_SLOT_START) {
                moved = this.moveItemStackTo(slotStack, HOTBAR_SLOT_START, HOTBAR_SLOT_START + HOTBAR_SLOT_COUNT, false);
            } else {
                moved = this.moveItemStackTo(slotStack, PLAYER_SLOT_START, HOTBAR_SLOT_START, false);
            }

            if (!moved) {
                return ItemStack.EMPTY;
            }
        }

        if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (slotStack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, slotStack);
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
