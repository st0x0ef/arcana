package org.exodusstudio.arcana.common.data_attachment;

import net.minecraft.world.entity.player.Player;
import org.exodusstudio.arcana.common.registry.DataAttachmentRegistry;

public class PlayerAttachmentHandler {
    private static KnowledgeProgression getKnowledgeProgression(Player player) {
        return player.getData(DataAttachmentRegistry.KNOWLEDGE_PROGRESSION);
    }

    public static void UpdateKnowledgeProgression(Player player, String researchName, int value) {
        KnowledgeProgression knowledgeProgression = getKnowledgeProgression(player);

        knowledgeProgression.UpdateKnowledge(researchName, value);
    }

    public static int GetSpecificKnowledgeProgression(Player player, String researchName) {
        return getKnowledgeProgression(player).GetKnowledge(researchName);
    }
}
