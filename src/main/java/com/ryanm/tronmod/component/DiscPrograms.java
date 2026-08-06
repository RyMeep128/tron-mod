package com.ryanm.tronmod.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;

public record DiscPrograms(int rebound, int velocity, int impact, int ricochet) {
    public static final int MAX_LEVEL = 3;
    public static final DiscPrograms EMPTY = new DiscPrograms(0, 0, 0, 0);
    public static final Codec<DiscPrograms> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("rebound", 0).forGetter(DiscPrograms::rebound),
            Codec.INT.optionalFieldOf("velocity", 0).forGetter(DiscPrograms::velocity),
            Codec.INT.optionalFieldOf("impact", 0).forGetter(DiscPrograms::impact),
            Codec.INT.optionalFieldOf("ricochet", 0).forGetter(DiscPrograms::ricochet)
    ).apply(instance, DiscPrograms::new));

    public DiscPrograms {
        rebound = clamp(rebound);
        velocity = clamp(velocity);
        impact = clamp(impact);
        ricochet = clamp(ricochet);
    }

    public int level(ProgramType program) {
        return switch (program) {
            case REBOUND -> rebound;
            case VELOCITY -> velocity;
            case IMPACT -> impact;
            case RICOCHET -> ricochet;
        };
    }

    public DiscPrograms upgrade(ProgramType program) {
        int next = Math.min(MAX_LEVEL, this.level(program) + 1);
        return switch (program) {
            case REBOUND -> new DiscPrograms(next, velocity, impact, ricochet);
            case VELOCITY -> new DiscPrograms(rebound, next, impact, ricochet);
            case IMPACT -> new DiscPrograms(rebound, velocity, next, ricochet);
            case RICOCHET -> new DiscPrograms(rebound, velocity, impact, next);
        };
    }

    private static int clamp(int level) {
        return Mth.clamp(level, 0, MAX_LEVEL);
    }
}
