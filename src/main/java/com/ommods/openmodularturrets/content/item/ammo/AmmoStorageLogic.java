package com.ommods.openmodularturrets.content.item.ammo;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class AmmoStorageLogic {
    private AmmoType[] supportedAmmoTypes = new AmmoType[2];
    private int[] ammoCount = new int[2];

    private final int MAX_STORAGE_CAPACITY;

    public AmmoStorageLogic(int storageCapacity) {
        this.MAX_STORAGE_CAPACITY = storageCapacity;

    }

    public AmmoInsertResult tryInsertAmmo(ItemStack ammoStack) {
        if (!(ammoStack.getItem() instanceof AmmoItem ammoItem) || ammoStack.isEmpty()) {
            return new AmmoInsertResult(false, ammoStack);
        }

        int typeIndex = findOrCreateAmmoTypeIndex(ammoItem.getAmmoType());
        if (typeIndex == -1) {
            return new AmmoInsertResult(false, ammoStack);
        }

        int freeSpace = MAX_STORAGE_CAPACITY - ammoCount[typeIndex];
        if (freeSpace <= 0) {
            return new AmmoInsertResult(false, ammoStack);
        }

        int inserted = Math.min(freeSpace, ammoStack.getCount());
        ammoCount[typeIndex] += inserted;

        ItemStack remaining = ammoStack.copy();
        remaining.shrink(inserted);
        return new AmmoInsertResult(inserted > 0, remaining);
    }

    // Method to insert magazines
    public MagazineInsertResult tryInsertMagazines(ItemStack magazineStack, Magazine magazineType) {
        // Find the index for this ammo type
        int typeIndex = findAmmoTypeIndex(magazineType.getAmmoType());
        if (typeIndex == -1) {
            // Unsupported type, return unchanged
            return new MagazineInsertResult(false, magazineStack);
        }

        // Calculate free space
        int freeSpace = MAX_STORAGE_CAPACITY - ammoCount[typeIndex];

        // Calculate total potential ammo from magazines
        int totalMagazineAmmo = magazineType.getAmmoCapacity() * magazineStack.getCount();

        // Check if all magazines can be inserted
        if (freeSpace >= totalMagazineAmmo) {
            // Consume all magazines
            ammoCount[typeIndex] += totalMagazineAmmo;

            // Return empty stack
            return new MagazineInsertResult(true, ItemStack.EMPTY);
        } else {
            // Cannot insert all magazines
            return new MagazineInsertResult(false, magazineStack);
        }
    }

    // Try to consume ammo
    public boolean tryConsumeAmmo(AmmoType ammoType, int amount) {
        if (ammoType == AmmoType.NONE || amount <= 0) {
            return true;
        }
        int typeIndex = findAmmoTypeIndex(ammoType);

        if (typeIndex == -1 || ammoCount[typeIndex] < amount) {
            return false;
        }

        // Consume ammo
        ammoCount[typeIndex] -= amount;
        return true;
    }

    // Find the index of a supported ammo type
    private int findAmmoTypeIndex(AmmoType ammoType) {
        for (int i = 0; i < supportedAmmoTypes.length; i++) {
            if (supportedAmmoTypes[i] == ammoType) {
                return i;
            }
        }
        return -1;
    }

    private int findOrCreateAmmoTypeIndex(AmmoType ammoType) {
        int existingIndex = findAmmoTypeIndex(ammoType);
        if (existingIndex != -1) {
            return existingIndex;
        }

        for (int i = 0; i < supportedAmmoTypes.length; i++) {
            if (supportedAmmoTypes[i] == null || supportedAmmoTypes[i] == AmmoType.NONE || ammoCount[i] <= 0) {
                supportedAmmoTypes[i] = ammoType;
                ammoCount[i] = Math.max(0, ammoCount[i]);
                return i;
            }
        }

        return -1;
    }

    // Get current ammo count for a type
    public int getAmmoCount(AmmoType ammoType) {
        int index = findAmmoTypeIndex(ammoType);
        return index != -1 ? ammoCount[index] : 0;
    }

    // Get supported ammo types
    public AmmoType[] getSupportedAmmoTypes() {
        return supportedAmmoTypes;
    }

    public AmmoType getSupportedAmmoType(int slot) {
        if (slot < 0 || slot >= supportedAmmoTypes.length) {
            return AmmoType.NONE;
        }
        return supportedAmmoTypes[slot] == null ? AmmoType.NONE : supportedAmmoTypes[slot];
    }

    public int getAmmoCountForSlot(int slot) {
        if (slot < 0 || slot >= ammoCount.length) {
            return 0;
        }
        return ammoCount[slot];
    }

    public int getStorageCapacity() {
        return MAX_STORAGE_CAPACITY;
    }

    // Result class for magazine insertion
    public static class MagazineInsertResult {
        public final boolean accepted;
        public final ItemStack remainingMagazineStack;

        public MagazineInsertResult(boolean accepted, ItemStack remainingMagazineStack) {
            this.accepted = accepted;
            this.remainingMagazineStack = remainingMagazineStack;
        }
    }

    public static class AmmoInsertResult {
        public final boolean accepted;
        public final ItemStack remainingAmmoStack;

        public AmmoInsertResult(boolean accepted, ItemStack remainingAmmoStack) {
            this.accepted = accepted;
            this.remainingAmmoStack = remainingAmmoStack;
        }
    }

    public void setSupportedAmmoTypes(AmmoType type, int slot) {
        if (slot < 0 || type == null || slot >= supportedAmmoTypes.length) {
            return;
        }
        supportedAmmoTypes[slot] = type;
    }

    public void save(CompoundTag tag) {
        for (int i = 0; i < supportedAmmoTypes.length; i++) {
            AmmoType type = supportedAmmoTypes[i];
            tag.putString("ammo_type_" + i, type == null ? AmmoType.NONE.name() : type.name());
            tag.putInt("ammo_count_" + i, ammoCount[i]);
        }
    }

    public void load(CompoundTag tag) {
        for (int i = 0; i < supportedAmmoTypes.length; i++) {
            supportedAmmoTypes[i] = AmmoType.getType(tag.getString("ammo_type_" + i));
            ammoCount[i] = Math.max(0, tag.getInt("ammo_count_" + i));
            if (ammoCount[i] == 0 && supportedAmmoTypes[i] == AmmoType.NONE) {
                supportedAmmoTypes[i] = null;
            }
        }
    }
}
