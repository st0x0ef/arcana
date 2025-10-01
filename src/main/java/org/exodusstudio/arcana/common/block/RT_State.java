package org.exodusstudio.arcana.common.block;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum RT_State implements StringRepresentable {
    OFF("off"),
    ON_LEFT("on_left"),
    ON_RIGHT("on_right");
    private final String name;

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    RT_State(String name) {
        this.name = name;
    }
}
