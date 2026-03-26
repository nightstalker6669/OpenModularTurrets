package com.ommods.openmodularturrets.client;

import com.ommods.openmodularturrets.OMTMenus;
import com.ommods.openmodularturrets.client.screen.TurretBaseScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class OMTMenuScreenRegistrar {
    private OMTMenuScreenRegistrar() {
    }

    public static void register(RegisterMenuScreensEvent event) {
        try {
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

            Method registerMethod = RegisterMenuScreensEvent.class.getMethod("register", net.minecraft.world.inventory.MenuType.class, screenConstructorClass);
            registerMethod.invoke(event, OMTMenus.TURRET_BASE.get(), constructor);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalStateException("Failed to register turret base screen", exception);
        }
    }
}
