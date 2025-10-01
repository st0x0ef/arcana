package org.exodusstudio.arcana.common.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.data.State;
import org.exodusstudio.arcana.common.data.Structure;
import org.exodusstudio.arcana.common.datamap.ArcanaDataHolder;
import org.exodusstudio.arcana.common.datamap.ArcanaDataMaps;
import org.exodusstudio.arcana.common.particle.ParticleRegistry;
import org.exodusstudio.arcana.common.registry.TagRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = Arcana.MODID)
public class ItemEvents {


    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        ArcanaDataHolder data = event.getItemStack().getItem().builtInRegistryHolder().getData(ArcanaDataMaps.DATA_HOLDER);
        if (data != null) {
            List<Holder<State>> states = data.states();
            List<String> stringList = new ArrayList<>();
            for (Holder<State> state : states) {
                stringList.add(Component.translatable(state.value().key()).getString());
            }
            event.getToolTip().add(Component.literal(String.join(", ", stringList)));

            List<Holder<Structure>> structures = data.structures();
            if (Minecraft.getInstance().hasShiftDown()) {
                for (Holder<Structure> structure : structures) {
                    event.getToolTip().add(Component.literal(" -> " + Component.translatable(structure.value().key()).getString()));
                }
            } else if (!structures.isEmpty()) {
                event.getToolTip().add(Component.translatable("tooltip.arcana.shift_to_view").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    static void spawnParticle(Level level, Zombie zombie) {
        ((ServerLevel) level).sendParticles(ParticleRegistry.RESEARCH_PARTICLE.get(),
                zombie.getX(), zombie.getY(), zombie.getZ(),
                5, 0.5, 1, 0, 1);
    }

    @SubscribeEvent
    public static void entityLeaveLevelEvent(EntityLeaveLevelEvent event) throws InterruptedException {
        if (!event.getLevel().isClientSide()) {

            Level level = event.getLevel();
            if (event.getEntity() instanceof ItemEntity itemEntity && event.getLevel().getBlockState(itemEntity.blockPosition()).is(Blocks.FIRE)) {
                if (itemEntity.getItem().is(TagRegistry.FUNDAMENTAL)) {
                    // TODO : add particles (Particles Added)
                    Zombie zombie = EntityType.ZOMBIE.create(event.getLevel(), EntitySpawnReason.EVENT);


                    if (zombie != null) {
                        zombie.setNoAi(true);
                        zombie.setNoGravity(true);
                        zombie.setInvulnerable(true);
                        zombie.setInvisible(true);
                        zombie.setPos(itemEntity.getX(), itemEntity.getY() + 1, itemEntity.getZ());
                        event.getLevel().addFreshEntity(zombie);
                        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
                        scheduler.schedule(() -> {
                            zombie.kill((ServerLevel) level);
                            scheduler.shutdown();
                        }, 10, TimeUnit.SECONDS);

                        scheduler.scheduleWithFixedDelay(() -> spawnParticle(level, zombie), 0, 1,TimeUnit.SECONDS);
                    }

                    }
                }
            }
        }

}
