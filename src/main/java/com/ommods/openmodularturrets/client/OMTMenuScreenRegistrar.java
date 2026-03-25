package com.ommods.openmodularturrets.client;

import com.ommods.openmodularturrets.OMTMenus;
import com.ommods.openmodularturrets.client.screen.TurretBaseScreen;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

public final class OMTMenuScreenRegistrar {
    private OMTMenuScreenRegistrar() {
    }

    @SuppressWarnings("unchecked")
    public static void register(RegisterMenuScreensEvent event) {
        try {
            Field field = RegisterMenuScreensEvent.class.getDeclaredField("registeredScreens");
            field.setAccessible(true);
            Map<MenuType<?>, Object> registeredScreens = (Map<MenuType<?>, Object>) field.get(event);

            Class<?> screenConstructorClass = Class.forName("net.minecraft.client.gui.screens.MenuScreens$ScreenConstructor");
            Object constructor = Proxy.newProxyInstance(
                screenConstructorClass.getClassLoader(),
                new Class<?>[]{screenConstructorClass},
                (proxy, method, args) -> {
                    if ("create".equals(method.getName())) {
                        return new TurretBaseScreen(
                            (com.ommods.openmodularturrets.menu.TurretBaseMenu) args[0],
                            (net.minecraft.world.entity.player.Inventory) args[1],
                            (net.minecraft.network.chat.Component) args[2]
                        );
                    }
                    return null;
                }
            );

            registeredScreens.put(OMTMenus.TURRET_BASE.get(), constructor);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to register turret base screen", exception);
        }
    }
}
