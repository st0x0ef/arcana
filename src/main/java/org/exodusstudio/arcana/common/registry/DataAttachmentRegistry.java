package org.exodusstudio.arcana.common.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.data_attachment.KnowledgeProgression;
import java.util.function.Supplier;

public class DataAttachmentRegistry {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Arcana.MODID);

    public static final Supplier<AttachmentType<KnowledgeProgression>> KNOWLEDGE_PROGRESSION = ATTACHMENT_TYPES.register(
            "knowledge_progression", () -> AttachmentType.builder(() -> new KnowledgeProgression())
                    .serialize(KnowledgeProgression.CODEC.fieldOf("knowledge_progression"))
                    .build()
    );

    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
