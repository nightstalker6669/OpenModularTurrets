package net.minecraftforge.common;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Deque;
import java.util.function.Function;

public class ForgeConfigSpec {
    private final Map<String, Object> values = new HashMap<>();

    public <T> T get(String key) {
        return (T) values.get(key);
    }

    public static class Builder {
        private final ForgeConfigSpec spec = new ForgeConfigSpec();
        private final Deque<String> path = new ArrayDeque<>();

        public Builder push(String name) {
            path.addLast(name);
            return this;
        }

        public Builder pop() {
            if (!path.isEmpty()) {
                path.removeLast();
            }
            return this;
        }
        public Builder comment(String comment) { return this; }

        private String qualify(String key) {
            if (path.isEmpty()) {
                return key;
            }
            return String.join(".", path) + "." + key;
        }

        public <T> ConfigValue<T> define(String key, T defaultValue) {
            spec.values.put(qualify(key), defaultValue);
            return new ConfigValue<>(defaultValue);
        }

        public <T> ConfigValue<T> defineInList(String key, T defaultValue, List<T> allowedValues) {
            spec.values.put(qualify(key), defaultValue);
            return new ConfigValue<>(defaultValue);
        }

        public <T extends Comparable<? super T>> ConfigValue<T> defineInRange(String key, T defaultValue, T min, T max) {
            spec.values.put(qualify(key), defaultValue);
            return new ConfigValue<>(defaultValue);
        }

        public <C> Pair<C, ForgeConfigSpec> configure(Function<Builder, C> factory) {
            return Pair.of(factory.apply(this), spec);
        }
    }

    public static class ConfigValue<T> {
        private final T value;

        public ConfigValue(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }
}
