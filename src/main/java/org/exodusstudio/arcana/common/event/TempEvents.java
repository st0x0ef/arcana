package org.exodusstudio.arcana.common.event;

import org.exodusstudio.arcana.Arcana;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = Arcana.MODID)
public class TempEvents {
    @SubscribeEvent
    public static void dataCheckLevelJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            //Arcana.LOGGER.debug(StateManager.STATE_MAP);
            //Arcana.LOGGER.debug(StructureManager.STRUCTURE_MAP);
        }
    }
}
