package com.ommods.openmodularturrets.content.item.ammo;

import com.ommods.openmodularturrets.OMTItems;
import net.minecraft.world.item.Item;

public enum AmmoType {
    BULLET("bullet"),
    ROCKET_SMALL("rocket_small"),
    ROCKET_LARGE("rocket_large"),
    RAILGUN_SLUG("railgun_slug"),
    CROSSBOW_BOLT("crossbow_bolt"),
    FIRE_CHARGE("fire_charge"),
    GRENADE("grenade"),
    ARTILLERY_SHELL("artillery_shell"),
    POTATO("potato"),
    BLAZING_CLAY("blazing_clay"),
    DISPOSABLE_ITEM("disposable_item"),
    NONE("none"); // For energy based turrets

    private final String name;

    AmmoType(String name) {
        this.name = name;
    }

    public AmmoItem getAmmoItem() {
        return switch (this) {
            case BULLET -> (AmmoItem) OMTItems.ammoItemBullet.get();
            case ROCKET_SMALL -> (AmmoItem) OMTItems.ammoItemRocketSmall.get();
            case ROCKET_LARGE -> (AmmoItem) OMTItems.ammoItemRocketLarge.get();
            case RAILGUN_SLUG -> (AmmoItem) OMTItems.ammoItemRailgunSlug.get();
            case CROSSBOW_BOLT -> (AmmoItem) OMTItems.ammoItemCrossbowBolt.get();
            case FIRE_CHARGE -> (AmmoItem) OMTItems.ammoFireCharge.get();
            case GRENADE -> (AmmoItem) OMTItems.ammoItemGrenade.get();
            case ARTILLERY_SHELL -> (AmmoItem) OMTItems.ammoItemArtilleryShell.get();
            case POTATO, BLAZING_CLAY, DISPOSABLE_ITEM, NONE -> null;
        };
    }

    public static AmmoType getType(String name) {
        for (AmmoType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NONE;
    }
}
