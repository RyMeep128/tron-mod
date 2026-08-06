package com.ryanm.tronmod.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;

public record DiscPrograms(
        int rebound, int velocity, int impact, int ricochet, int recall,
        int seeking, int piercing, int splitCircuit, int disruption, int perfectReturn
) {
    public static final int MAX_LEVEL = 3;
    public static final DiscPrograms EMPTY = new DiscPrograms(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    public static final Codec<DiscPrograms> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("rebound", 0).forGetter(DiscPrograms::rebound),
            Codec.INT.optionalFieldOf("velocity", 0).forGetter(DiscPrograms::velocity),
            Codec.INT.optionalFieldOf("impact", 0).forGetter(DiscPrograms::impact),
            Codec.INT.optionalFieldOf("ricochet", 0).forGetter(DiscPrograms::ricochet),
            Codec.INT.optionalFieldOf("recall", 0).forGetter(DiscPrograms::recall),
            Codec.INT.optionalFieldOf("seeking", 0).forGetter(DiscPrograms::seeking),
            Codec.INT.optionalFieldOf("piercing", 0).forGetter(DiscPrograms::piercing),
            Codec.INT.optionalFieldOf("split_circuit", 0).forGetter(DiscPrograms::splitCircuit),
            Codec.INT.optionalFieldOf("disruption", 0).forGetter(DiscPrograms::disruption),
            Codec.INT.optionalFieldOf("perfect_return", 0).forGetter(DiscPrograms::perfectReturn)
    ).apply(instance, DiscPrograms::new));

    public DiscPrograms {
        rebound=clamp(rebound); velocity=clamp(velocity); impact=clamp(impact); ricochet=clamp(ricochet); recall=clamp(recall);
        seeking=clamp(seeking); piercing=clamp(piercing); splitCircuit=clamp(splitCircuit); disruption=clamp(disruption); perfectReturn=clamp(perfectReturn);
        if (seeking > 0 && splitCircuit > 0) splitCircuit = 0;
    }

    public int level(ProgramType p) { return switch (p) {
        case REBOUND->rebound; case VELOCITY->velocity; case IMPACT->impact; case RICOCHET->ricochet; case RECALL->recall;
        case SEEKING->seeking; case PIERCING->piercing; case SPLIT_CIRCUIT->splitCircuit; case DISRUPTION->disruption; case PERFECT_RETURN->perfectReturn;
    }; }

    public boolean compatible(ProgramType p) {
        return !((p == ProgramType.SEEKING && splitCircuit > 0) || (p == ProgramType.SPLIT_CIRCUIT && seeking > 0));
    }

    public DiscPrograms upgrade(ProgramType p) {
        if (!compatible(p)) return this;
        int n=Math.min(MAX_LEVEL, level(p)+1);
        return new DiscPrograms(p==ProgramType.REBOUND?n:rebound,p==ProgramType.VELOCITY?n:velocity,p==ProgramType.IMPACT?n:impact,p==ProgramType.RICOCHET?n:ricochet,
                p==ProgramType.RECALL?n:recall,p==ProgramType.SEEKING?n:seeking,p==ProgramType.PIERCING?n:piercing,p==ProgramType.SPLIT_CIRCUIT?n:splitCircuit,
                p==ProgramType.DISRUPTION?n:disruption,p==ProgramType.PERFECT_RETURN?n:perfectReturn);
    }

    public DiscPrograms remove(ProgramType p) {
        return new DiscPrograms(p==ProgramType.REBOUND?0:rebound,p==ProgramType.VELOCITY?0:velocity,p==ProgramType.IMPACT?0:impact,p==ProgramType.RICOCHET?0:ricochet,
                p==ProgramType.RECALL?0:recall,p==ProgramType.SEEKING?0:seeking,p==ProgramType.PIERCING?0:piercing,p==ProgramType.SPLIT_CIRCUIT?0:splitCircuit,
                p==ProgramType.DISRUPTION?0:disruption,p==ProgramType.PERFECT_RETURN?0:perfectReturn);
    }

    private static int clamp(int n) { return Mth.clamp(n,0,MAX_LEVEL); }
}
