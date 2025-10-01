package org.exodusstudio.arcana.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record ScribbledNoteData(String researchName) {
    public static final Codec<ScribbledNoteData> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(Codec.STRING.fieldOf("research_name").forGetter(ScribbledNoteData::researchName))
                            .apply(instance, ScribbledNoteData::new));

    public String getOuputString() {
        return "Scribbled note with id : " + researchName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.researchName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else {
            return obj instanceof ScribbledNoteData(String name) &&  this.researchName.equals(name);
        }
    }
}
