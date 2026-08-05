package com.ryanm.tronmod.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record DiscIdentity(
        UUID ownerId,
        String ownerName,
        long createdAt,
        UUID discId,
        int throwsCount,
        int hits,
        int catches,
        int defeats,
        int bounces
) {
    public static final Codec<DiscIdentity> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("owner_id").forGetter(DiscIdentity::ownerId),
            Codec.STRING.fieldOf("owner_name").forGetter(DiscIdentity::ownerName),
            Codec.LONG.fieldOf("created_at").forGetter(DiscIdentity::createdAt),
            UUIDUtil.CODEC.fieldOf("disc_id").forGetter(DiscIdentity::discId),
            Codec.INT.optionalFieldOf("throws", 0).forGetter(DiscIdentity::throwsCount),
            Codec.INT.optionalFieldOf("hits", 0).forGetter(DiscIdentity::hits),
            Codec.INT.optionalFieldOf("catches", 0).forGetter(DiscIdentity::catches),
            Codec.INT.optionalFieldOf("defeats", 0).forGetter(DiscIdentity::defeats),
            Codec.INT.optionalFieldOf("bounces", 0).forGetter(DiscIdentity::bounces)
    ).apply(instance, DiscIdentity::new));

    public static DiscIdentity create(UUID ownerId, String ownerName, long createdAt, UUID discId) {
        return new DiscIdentity(ownerId, ownerName, createdAt, discId, 0, 0, 0, 0, 0);
    }

    public DiscIdentity recordHit() {
        return new DiscIdentity(
                ownerId, ownerName, createdAt, discId,
                throwsCount, hits + 1, catches, defeats, bounces
        );
    }

    public DiscIdentity recordDefeat() {
        return new DiscIdentity(
                ownerId, ownerName, createdAt, discId,
                throwsCount, hits, catches, defeats + 1, bounces
        );
    }
}
