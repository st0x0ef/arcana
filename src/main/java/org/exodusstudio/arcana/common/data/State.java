package org.exodusstudio.arcana.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record State(String key) {
    public static final Codec<State> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.STRING.fieldOf("key").forGetter(State::key)
    ).apply(func, State::new));
}
