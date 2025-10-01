package org.exodusstudio.arcana.common.data_attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeProgression {
    private final Map<String, Integer> KnowledgeMap;

    // Constructor
    public KnowledgeProgression() {
        this.KnowledgeMap = new HashMap<>();
    }

    public KnowledgeProgression(Map<String, Integer> knowledgeMap)
    {
        this.KnowledgeMap = knowledgeMap;
    }

    public void UpdateKnowledge(String researchName, int value)
    {
        KnowledgeMap.put(researchName, value);
    }

    public int GetKnowledge(String researchName)
    {
        return KnowledgeMap.getOrDefault(researchName, 0);
    }

    // Getter
    public Map<String, Integer> getKnowledgeMap() {
        return KnowledgeMap;
    }

    public static final Codec<KnowledgeProgression> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(Codec.STRING, Codec.INT) // Map<String, Integer>
                            .optionalFieldOf("knowledge_map", new HashMap<>()) // Valeur par défaut
                            .forGetter(KnowledgeProgression::getKnowledgeMap)
            ).apply(instance, KnowledgeProgression::new)
    );
}
