package com.ommods.openmodularturrets;

import com.ommods.openmodularturrets.menu.TurretBaseMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public final class OMTMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<TurretBaseMenu>> TURRET_BASE = MENUS.register(
        "turret_base",
        OMTMenus::createTurretBaseMenuType
    );

    private OMTMenus() {
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }

    private static MenuType<TurretBaseMenu> createTurretBaseMenuType() {
        try {
            Class<?> extensionClass = Class.forName("net.neoforged.neoforge.common.extensions.IMenuTypeExtension");
            Class<?> factoryClass = Class.forName("net.neoforged.neoforge.network.IContainerFactory");
            Object factory = java.lang.reflect.Proxy.newProxyInstance(
                factoryClass.getClassLoader(),
                new Class<?>[]{factoryClass},
                (proxy, method, args) -> {
                    if (method.getDeclaringClass() == Object.class) {
                        return switch (method.getName()) {
                            case "toString" -> "TurretBaseMenuFactoryProxy";
                            case "hashCode" -> System.identityHashCode(proxy);
                            case "equals" -> proxy == args[0];
                            default -> null;
                        };
                    }
                    if (!"create".equals(method.getName())) {
                        return null;
                    }

                    int containerId = (Integer) args[0];
                    net.minecraft.world.entity.player.Inventory inventory = (net.minecraft.world.entity.player.Inventory) args[1];
                    if (args.length >= 3) {
                        return new TurretBaseMenu(
                            containerId,
                            inventory,
                            (net.minecraft.network.RegistryFriendlyByteBuf) args[2]
                        );
                    }

                    return new TurretBaseMenu(containerId, inventory);
                }
            );
            Method createMethod = extensionClass.getMethod("create", factoryClass);
            @SuppressWarnings("unchecked")
            MenuType<TurretBaseMenu> menuType = (MenuType<TurretBaseMenu>) createMethod.invoke(null, factory);
            return menuType;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalStateException("Failed to create turret base menu type", exception);
        }
    }
}
