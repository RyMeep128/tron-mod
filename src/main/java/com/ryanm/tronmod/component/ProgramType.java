package com.ryanm.tronmod.component;

import net.minecraft.util.StringRepresentable;

public enum ProgramType implements StringRepresentable {
    REBOUND("rebound"),
    VELOCITY("velocity"),
    IMPACT("impact"),
    RICOCHET("ricochet"),
    RECALL("recall"),
    SEEKING("seeking"),
    PIERCING("piercing"),
    SPLIT_CIRCUIT("split_circuit"),
    DISRUPTION("disruption"),
    PERFECT_RETURN("perfect_return");

    private final String serializedName;

    ProgramType(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return this.serializedName;
    }
}
