package com.ryanm.tronmod.item;

import com.ryanm.tronmod.component.ProgramType;
import net.minecraft.world.item.Item;

public final class ProtocolItem extends Item {
    private final ProgramType program;

    public ProtocolItem(Properties properties, ProgramType program) {
        super(properties);
        this.program = program;
    }

    public ProgramType program() {
        return this.program;
    }
}
