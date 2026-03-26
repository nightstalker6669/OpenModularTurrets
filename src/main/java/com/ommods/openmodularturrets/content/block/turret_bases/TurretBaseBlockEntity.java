package com.ommods.openmodularturrets.content.block.turret_bases;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.omlib.block.BlockEntityStorageEnergy;
import com.ommods.openmodularturrets.OMTItems;
import com.ommods.openmodularturrets.content.block.turrets.DirectedTurretBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoDropEvent;
import com.ommods.openmodularturrets.content.item.ammo.AmmoItem;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import com.ommods.openmodularturrets.content.item.upgrades.AbstractUpgradeItem;
import com.ommods.openmodularturrets.content.item.upgrades.UpgradeEffect;
import com.ommods.openmodularturrets.menu.TurretBaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ContainerHelper;
import net.minecraftforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class TurretBaseBlockEntity extends BlockEntityStorageEnergy implements Container, MenuProvider {
    public static final int AMMO_INPUT_SLOT_A = 0;
    public static final int AMMO_INPUT_SLOT_B = 1;
    public static final int AMMO_INPUT_SLOT_C = 2;
    public static final int AMMO_INPUT_SLOT_D = 3;
    public static final int AMMO_INPUT_SLOT_E = 4;
    public static final int AMMO_INPUT_SLOT_F = 5;
    public static final int AMMO_INPUT_SLOT_G = 6;
    public static final int AMMO_INPUT_SLOT_H = 7;
    public static final int AMMO_INPUT_SLOT_I = 8;
    public static final int ADDON_SLOT_A = 9;
    public static final int ADDON_SLOT_B = 10;
    public static final int UPGRADE_SLOT_A = 11;
    public static final int UPGRADE_SLOT_B = 12;

    private static final int[] SLOT_CAPACITY = new int[]{64, 64, 64, 64, 64, 64, 64, 64, 64, 1, 1, 1, 1};

    private final TurretBaseLogic logic;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_CAPACITY.length, ItemStack.EMPTY);
    private final ContainerData menuData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> energyStorage.getEnergyStored();
                case 1 -> energyStorage.getMaxEnergyStored();
                case 2 -> getVisibleAmmoCountForDisplay(0);
                case 3 -> getVisibleAmmoCountForDisplay(1);
                case 4 -> getVisibleAmmoTypeForDisplay(0).ordinal();
                case 5 -> getVisibleAmmoTypeForDisplay(1).ordinal();
                case 6 -> countAttachedTurrets();
                case 7 -> logic.getTier();
                case 8 -> yAxisDetect;
                case 9 -> multiTargeting ? 1 : 0;
                case 10 -> attacksMobs ? 1 : 0;
                case 11 -> attacksNeutrals ? 1 : 0;
                case 12 -> attacksPlayers ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> energyStorage.setEnergyStored(value);
                case 8 -> yAxisDetect = value;
                case 9 -> multiTargeting = value != 0;
                case 10 -> attacksMobs = value != 0;
                case 11 -> attacksNeutrals = value != 0;
                case 12 -> attacksPlayers = value != 0;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 13;
        }
    };

    private int attachedTurretCount;
    private int tickCounter;
    private int yAxisDetect = 2;
    private boolean attacksMobs = true;
    private boolean attacksNeutrals = true;
    private boolean attacksPlayers;
    private boolean multiTargeting;

    public TurretBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState, createEnergyStorage(resolveTier(blockState)));
        int tier = resolveTier(blockState);
        this.logic = new TurretBaseLogic(tier);
        this.energyStorage.setEnergyStored(this.energyStorage.getMaxEnergyStored());
    }

    public TurretBaseBlockEntity(BlockPos pos, BlockState blockState) {
        this(OMTBlockEntities.TURRET_BASE.get(), pos, blockState);
    }

    private static EnergyStorage createEnergyStorage(int tier) {
        int capacity = 20000 * tier * tier;
        return new EnergyStorage(capacity, capacity, capacity);
    }

    private static int resolveTier(BlockState state) {
        if (state.getBlock() instanceof TurretBaseBlock turretBaseBlock) {
            return turretBaseBlock.getTier();
        }
        return 1;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        if (!(be instanceof TurretBaseBlockEntity turretBaseBlockEntity)) {
            return;
        }
        turretBaseBlockEntity.serverTick(level, pos);
    }

    private void serverTick(Level level, BlockPos pos) {
        tickCounter++;
        int passiveEnergyGeneration = this.getPassiveEnergyGeneration(level, pos);
        if (passiveEnergyGeneration > 0) {
            int received = energyStorage.receiveEnergy(passiveEnergyGeneration, false);
            if (received > 0 && tickCounter % 20 == 0) {
                setChanged();
            }
        }
        if (tickCounter % 20 == 0) {
            updateTurrets();
        }
    }

    public void updateTurrets() {
        if (getLevel() == null || getLevel().isClientSide()) {
            return;
        }
        int count = countAttachedTurrets();
        if (count != attachedTurretCount) {
            attachedTurretCount = count;
            setChanged();
            getLevel().sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private int countAttachedTurrets() {
        if (getLevel() == null) {
            return attachedTurretCount;
        }

        int count = 0;
        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = getLevel().getBlockEntity(this.getBlockPos().relative(direction));
            if (blockEntity instanceof DirectedTurretBlockEntity) {
                count++;
            }
        }
        return count;
    }

    public boolean canConsumeShotResources(AmmoType ammoType, int ammoCost, int energyCost) {
        if (energyStorage.getEnergyStored() < energyCost) {
            return false;
        }
        return ammoType == AmmoType.NONE || getVisibleAmmoCount(ammoType) >= ammoCost;
    }

    public boolean consumeShotResources(AmmoType ammoType, int ammoCost, int energyCost) {
        if (!canConsumeShotResources(ammoType, ammoCost, energyCost)) {
            return false;
        }
        if (!consumeVisibleAmmo(ammoType, this.getAdjustedAmmoCost(ammoCost))) {
            return false;
        }
        energyStorage.extractEnergy(energyCost, false);
        setChanged();
        return true;
    }

    public boolean canConsumeSpecificItem(Item item, int amount, int energyCost) {
        if (energyStorage.getEnergyStored() < energyCost) {
            return false;
        }
        return countMatchingAmmoStacks(stack -> stack.is(item)) >= amount;
    }

    public boolean consumeSpecificItem(Item item, int amount, int energyCost) {
        if (!canConsumeSpecificItem(item, amount, energyCost)) {
            return false;
        }
        if (!consumeMatchingAmmoStacks(stack -> stack.is(item), this.getAdjustedAmmoCost(amount))) {
            return false;
        }
        energyStorage.extractEnergy(energyCost, false);
        setChanged();
        return true;
    }

    public boolean canConsumeAnyAmmoItem(int amount, int energyCost) {
        if (energyStorage.getEnergyStored() < energyCost) {
            return false;
        }
        return countMatchingAmmoStacks(stack -> !stack.isEmpty()) >= amount;
    }

    public boolean consumeAnyAmmoItem(int amount, int energyCost) {
        if (!canConsumeAnyAmmoItem(amount, energyCost)) {
            return false;
        }
        if (!consumeMatchingAmmoStacks(stack -> !stack.isEmpty(), this.getAdjustedAmmoCost(amount))) {
            return false;
        }
        energyStorage.extractEnergy(energyCost, false);
        setChanged();
        return true;
    }

    public ItemStack getFirstMatchingAmmoStack(Predicate<ItemStack> predicate) {
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
            ItemStack stack = items.get(slot);
            if (!stack.isEmpty() && predicate.test(stack)) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public int getAttachedTurretCount() {
        return attachedTurretCount;
    }

    public int getYAxisDetect() {
        return yAxisDetect;
    }

    public void adjustYAxisDetect(int delta) {
        this.setYAxisDetect(this.yAxisDetect + delta);
    }

    public void setYAxisDetect(int value) {
        this.yAxisDetect = Math.max(0, Math.min(9, value));
        setChanged();
    }

    public boolean isMultiTargeting() {
        return multiTargeting;
    }

    public Set<UUID> getClaimedTargetIds(@NotNull DirectedTurretBlockEntity requestingTurret) {
        Set<UUID> claimedTargets = new HashSet<>();
        if (this.level == null) {
            return claimedTargets;
        }

        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = this.level.getBlockEntity(this.getBlockPos().relative(direction));
            if (!(blockEntity instanceof DirectedTurretBlockEntity turret) || turret == requestingTurret) {
                continue;
            }

            UUID trackedTargetId = turret.getTrackedTargetId();
            if (trackedTargetId != null) {
                claimedTargets.add(trackedTargetId);
            }
        }
        return claimedTargets;
    }

    public boolean hasAddon(Item item) {
        return this.getAddonCount(item) > 0;
    }

    public boolean hasRecyclerAddon() {
        return this.hasAddon(OMTItems.addonRecycler.get());
    }

    public boolean hasConcealerAddon() {
        return this.hasAddon(OMTItems.addonConcealer.get());
    }

    public boolean hasFakeDropsAddon() {
        return this.hasAddon(OMTItems.addonFakeDrops.get());
    }

    public boolean hasSerialPortAddon() {
        return this.hasAddon(OMTItems.addonSerialPort.get());
    }

    public int getAddonCount(Item item) {
        int count = 0;
        for (int slot = ADDON_SLOT_A; slot <= ADDON_SLOT_B; slot++) {
            if (items.get(slot).is(item)) {
                count++;
            }
        }
        return count;
    }

    public int getUpgradeCount(UpgradeEffect effect) {
        int count = 0;
        for (int slot = UPGRADE_SLOT_A; slot <= UPGRADE_SLOT_B; slot++) {
            ItemStack stack = items.get(slot);
            if (stack.getItem() instanceof AbstractUpgradeItem upgradeItem && upgradeItem.getEffect() == effect) {
                count++;
            }
        }
        return count;
    }

    public float getRangeMultiplier() {
        return 1.0F + (float) (this.getUpgradeCount(UpgradeEffect.RANGE) * UpgradeEffect.RANGE.getValue());
    }

    public float getFireRateMultiplier() {
        return 1.0F + (float) (this.getUpgradeCount(UpgradeEffect.SPEED) * UpgradeEffect.SPEED.getValue() * 2.0D);
    }

    public float getDamageMultiplier() {
        float multiplier = 1.0F;
        if (this.hasAddon(OMTItems.addonDamageAmp.get())) {
            multiplier += 0.5F;
        }
        return multiplier;
    }

    public float getAccuracyMultiplier() {
        return 1.0F + (float) (this.getUpgradeCount(UpgradeEffect.ACCURACY) * UpgradeEffect.ACCURACY.getValue() * 2.0D);
    }

    public int getScatterShotCount() {
        return this.getUpgradeCount(UpgradeEffect.SCATTER_SHOT);
    }

    public void toggleMultiTargeting() {
        this.multiTargeting = !this.multiTargeting;
        setChanged();
    }

    public boolean isAttacksMobs() {
        return attacksMobs;
    }

    public void toggleAttacksMobs() {
        this.attacksMobs = !this.attacksMobs;
        setChanged();
    }

    public boolean isAttacksNeutrals() {
        return attacksNeutrals;
    }

    public void toggleAttacksNeutrals() {
        this.attacksNeutrals = !this.attacksNeutrals;
        setChanged();
    }

    public boolean isAttacksPlayers() {
        return attacksPlayers;
    }

    public void toggleAttacksPlayers() {
        this.attacksPlayers = !this.attacksPlayers;
        setChanged();
    }

    public void dropAttachedTurrets() {
        if (level == null || level.isClientSide()) {
            return;
        }
        for (Direction direction : Direction.values()) {
            BlockPos turretPos = this.worldPosition.relative(direction);
            if (level.getBlockEntity(turretPos) instanceof DirectedTurretBlockEntity) {
                level.destroyBlock(turretPos, true);
            }
        }
        updateTurrets();
    }

    public void dropBase() {
        if (level == null || level.isClientSide()) {
            return;
        }
        dropAttachedTurrets();
        level.destroyBlock(this.worldPosition, true);
    }

    public int getSerialPortSignal() {
        if (!this.hasSerialPortAddon()) {
            return 0;
        }

        int energySignal = Math.round((this.energyStorage.getEnergyStored() / (float) Math.max(1, this.energyStorage.getMaxEnergyStored())) * 15.0F);
        int turretSignal = Math.min(15, this.getAttachedTurretCount() * 3);
        int targetSignal = this.multiTargeting ? 2 : 1;
        return Math.max(energySignal, Math.max(turretSignal, targetSignal));
    }

    public void handleAmmoDropEvent(AmmoDropEvent event) {
        // Reserved for the legacy ammo drop pipeline during the broader port.
    }

    public AmmoType getAmmoTypeForSlot(int slot) {
        return getVisibleAmmoTypeForDisplay(slot);
    }

    public int getAmmoCountForSlot(int slot) {
        return getVisibleAmmoCountForDisplay(slot);
    }

    public ContainerData getMenuData() {
        return menuData;
    }

    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        updateTurrets();
        return new TurretBaseMenu(containerId, playerInventory, this, menuData);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack removed = ContainerHelper.removeItem(items, slot, amount);
        if (!removed.isEmpty()) {
            setChanged();
        }
        return removed;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getSlotLimit(slot)) {
            stack.setCount(getSlotLimit(slot));
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return isItemValid(slot, stack) && stack.getCount() <= getSlotLimit(slot);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("tier", logic.getTier());
        tag.putInt("energy", energyStorage.getEnergyStored());
        tag.putInt("attached_turrets", attachedTurretCount);
        tag.putInt("y_axis_detect", yAxisDetect);
        tag.putBoolean("attacks_mobs", attacksMobs);
        tag.putBoolean("attacks_neutrals", attacksNeutrals);
        tag.putBoolean("attacks_players", attacksPlayers);
        tag.putBoolean("multi_targeting", multiTargeting);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
        energyStorage.setEnergyStored(tag.getInt("energy"));
        attachedTurretCount = tag.getInt("attached_turrets");
        yAxisDetect = tag.contains("y_axis_detect") ? tag.getInt("y_axis_detect") : 2;
        attacksMobs = !tag.contains("attacks_mobs") || tag.getBoolean("attacks_mobs");
        attacksNeutrals = !tag.contains("attacks_neutrals") || tag.getBoolean("attacks_neutrals");
        attacksPlayers = tag.getBoolean("attacks_players");
        multiTargeting = tag.getBoolean("multi_targeting");
    }

    @Override
    public int getSlots() {
        return SLOT_CAPACITY.length;
    }

    @Override
    public int getSlotLimit(int slot) {
        return SLOT_CAPACITY[slot];
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case AMMO_INPUT_SLOT_A, AMMO_INPUT_SLOT_B, AMMO_INPUT_SLOT_C, AMMO_INPUT_SLOT_D, AMMO_INPUT_SLOT_E, AMMO_INPUT_SLOT_F, AMMO_INPUT_SLOT_G, AMMO_INPUT_SLOT_H, AMMO_INPUT_SLOT_I -> isAmmoSlotItem(stack);
            case ADDON_SLOT_A, ADDON_SLOT_B -> getTier() > 1 && isAddonItem(stack);
            case UPGRADE_SLOT_A -> getTier() > 1 && stack.getItem() instanceof AbstractUpgradeItem;
            case UPGRADE_SLOT_B -> getTier() > 4 && stack.getItem() instanceof AbstractUpgradeItem;
            default -> false;
        };
    }

    private boolean isAmmoSlotItem(ItemStack stack) {
        return !stack.isEmpty() && !(stack.getItem() instanceof AbstractUpgradeItem);
    }

    private boolean isAddonItem(ItemStack stack) {
        return stack.is(OMTItems.addonRedstoneReactor.get())
            || stack.is(OMTItems.addonDamageAmp.get())
            || stack.is(OMTItems.addonSolarPanel.get())
            || stack.is(OMTItems.addonPotentia.get())
            || stack.is(OMTItems.addonSerialPort.get())
            || stack.is(OMTItems.addonRecycler.get())
            || stack.is(OMTItems.addonConcealer.get())
            || stack.is(OMTItems.addonFakeDrops.get());
    }

    public int getTier() {
        return logic.getTier();
    }

    private int getVisibleAmmoCount(AmmoType ammoType) {
        if (ammoType == AmmoType.NONE) {
            return 0;
        }
        int count = 0;
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
            ItemStack stack = items.get(slot);
            if (!stack.isEmpty() && resolveAmmoType(stack) == ammoType) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private boolean consumeVisibleAmmo(AmmoType ammoType, int amount) {
        if (ammoType == AmmoType.NONE || amount <= 0) {
            return true;
        }

        ItemStack recycledTemplate = ItemStack.EMPTY;
        if (this.hasRecyclerAddon()) {
            for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
                ItemStack stack = items.get(slot);
                if (!stack.isEmpty() && resolveAmmoType(stack) == ammoType) {
                    recycledTemplate = stack.copyWithCount(1);
                    break;
                }
            }
        }

        int remaining = amount;
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I && remaining > 0; slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty() || resolveAmmoType(stack) != ammoType) {
                continue;
            }

            int consumed = Math.min(remaining, stack.getCount());
            stack.shrink(consumed);
            if (stack.isEmpty()) {
                items.set(slot, ItemStack.EMPTY);
            }
            remaining -= consumed;
        }
        boolean consumedAll = remaining == 0;
        if (consumedAll) {
            tryGenerateRecycledAmmo(recycledTemplate);
        }
        return consumedAll;
    }

    private int getAdjustedAmmoCost(int requestedAmount) {
        if (requestedAmount <= 0) {
            return 0;
        }
        double preserveChance = this.getUpgradeCount(UpgradeEffect.AMMO_CAPACITY) * UpgradeEffect.AMMO_CAPACITY.getValue();
        if (this.hasRecyclerAddon()) {
            preserveChance += 0.20D * this.getAddonCount(OMTItems.addonRecycler.get());
        }
        if (preserveChance > 0.0D && this.level != null && this.level.random.nextDouble() < Math.min(0.90D, preserveChance)) {
            return Math.max(0, requestedAmount - 1);
        }
        return requestedAmount;
    }

    private int getPassiveEnergyGeneration(Level level, BlockPos pos) {
        int generation = 0;
        if (this.hasAddon(OMTItems.addonPotentia.get())) {
            generation += 40;
        }
        if (this.hasAddon(OMTItems.addonRedstoneReactor.get()) && level.hasNeighborSignal(pos)) {
            generation += 30;
        }
        if (this.hasAddon(OMTItems.addonSolarPanel.get()) && level.isDay() && level.canSeeSky(pos.above())) {
            generation += 18;
        }
        return generation;
    }

    private AmmoType getVisibleAmmoTypeForDisplay(int displaySlot) {
        AmmoType[] visibleTypes = collectVisibleAmmoTypes();
        if (displaySlot < 0 || displaySlot >= visibleTypes.length || visibleTypes[displaySlot] == null) {
            return AmmoType.NONE;
        }
        return visibleTypes[displaySlot];
    }

    private int getVisibleAmmoCountForDisplay(int displaySlot) {
        AmmoType ammoType = getVisibleAmmoTypeForDisplay(displaySlot);
        return ammoType == AmmoType.NONE ? 0 : getVisibleAmmoCount(ammoType);
    }

    private AmmoType[] collectVisibleAmmoTypes() {
        AmmoType[] visibleTypes = new AmmoType[2];
        int index = 0;
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I && index < visibleTypes.length; slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            AmmoType ammoType = resolveAmmoType(stack);
            if (ammoType == AmmoType.NONE || ammoTypeAlreadyVisible(visibleTypes, ammoType)) {
                continue;
            }
            visibleTypes[index++] = ammoType;
        }
        return visibleTypes;
    }

    private AmmoType resolveAmmoType(ItemStack stack) {
        if (stack.getItem() instanceof AmmoItem ammoItem) {
            return ammoItem.getAmmoType();
        }
        if (stack.is(Items.POTATO)) {
            return AmmoType.POTATO;
        }
        if (stack.is(OMTItems.ammoBlazingClay.get())) {
            return AmmoType.BLAZING_CLAY;
        }
        return stack.isEmpty() ? AmmoType.NONE : AmmoType.DISPOSABLE_ITEM;
    }

    private int countMatchingAmmoStacks(Predicate<ItemStack> matcher) {
        int count = 0;
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
            ItemStack stack = items.get(slot);
            if (!stack.isEmpty() && matcher.test(stack)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private boolean consumeMatchingAmmoStacks(Predicate<ItemStack> matcher, int amount) {
        ItemStack recycledTemplate = ItemStack.EMPTY;
        if (this.hasRecyclerAddon()) {
            for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
                ItemStack stack = items.get(slot);
                if (!stack.isEmpty() && matcher.test(stack)) {
                    recycledTemplate = stack.copyWithCount(1);
                    break;
                }
            }
        }

        int remaining = amount;
        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I && remaining > 0; slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty() || !matcher.test(stack)) {
                continue;
            }
            int consumed = Math.min(remaining, stack.getCount());
            stack.shrink(consumed);
            if (stack.isEmpty()) {
                items.set(slot, ItemStack.EMPTY);
            }
            remaining -= consumed;
        }
        boolean consumedAll = remaining == 0;
        if (consumedAll) {
            tryGenerateRecycledAmmo(recycledTemplate);
        }
        return consumedAll;
    }

    private boolean ammoTypeAlreadyVisible(AmmoType[] visibleTypes, AmmoType ammoType) {
        for (AmmoType visibleType : visibleTypes) {
            if (visibleType == ammoType) {
                return true;
            }
        }
        return false;
    }

    private void tryGenerateRecycledAmmo(ItemStack template) {
        if (template.isEmpty() || !this.hasRecyclerAddon() || this.level == null) {
            return;
        }

        double generationChance = Math.min(0.40D, 0.05D * this.getAddonCount(OMTItems.addonRecycler.get()));
        if (generationChance <= 0.0D || this.level.random.nextDouble() >= generationChance) {
            return;
        }

        for (int slot = AMMO_INPUT_SLOT_A; slot <= AMMO_INPUT_SLOT_I; slot++) {
            ItemStack stack = items.get(slot);
            if (stack.isEmpty()) {
                items.set(slot, template.copyWithCount(1));
                return;
            }
            if (ItemStack.isSameItemSameComponents(stack, template) && stack.getCount() < stack.getMaxStackSize()) {
                stack.grow(1);
                return;
            }
        }
    }
}
