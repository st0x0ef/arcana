package org.exodusstudio.arcana.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Structure(String key) {
    public static final Codec<Structure> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.STRING.fieldOf("key").forGetter(Structure::key)
    ).apply(func, Structure::new));
}
