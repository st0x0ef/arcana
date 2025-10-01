package org.exodusstudio.arcana;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exodusstudio.arcana.client.Keybindings;
import org.exodusstudio.arcana.client.gui.ResearchTableScreen;
import org.exodusstudio.arcana.common.block.entity.renderer.BoilerBlockEntityRenderer;
import org.exodusstudio.arcana.common.data.theorie.TheoryRegistry;
import org.exodusstudio.arcana.common.item.ArcanaCreativeModeTabs;
import org.exodusstudio.arcana.common.particle.BoilingParticle;
import org.exodusstudio.arcana.common.particle.EvaporationParticle;
import org.exodusstudio.arcana.common.particle.ParticleRegistry;
import org.exodusstudio.arcana.common.particle.ResearchParticle;
import org.exodusstudio.arcana.common.registry.*;

@Mod(Arcana.MODID)
public class Arcana {
    public static final String MODID = "arcana";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation id(String value) {
        return ResourceLocation.fromNamespaceAndPath(MODID, value);
    }


    public Arcana(IEventBus modEventBus, ModContainer modContainer) {
        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        ArcanaCreativeModeTabs.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);
        DataComponentRegistry.register(modEventBus);
        DataAttachmentRegistry.register(modEventBus);
        MenuRegistry.register(modEventBus);
        TheoryRegistry.RegisterTheories();
        ParticleRegistry.register(modEventBus);
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent event) {
            event.register(Keybindings.INSTANCE.InteriorMemoriam);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(MenuRegistry.RESEARCH_TABLE_MENU.get(), ResearchTableScreen::new);
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ParticleRegistry.EVAPORATION_PARTICLE.get(), EvaporationParticle.Provider::new);
            event.registerSpriteSet(ParticleRegistry.BOILING_PARTICLE.get(), BoilingParticle.Provider::new);
            event.registerSpriteSet(ParticleRegistry.RESEARCH_PARTICLE.get(), ResearchParticle.Provider::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityRegistry.BOILER_BE.get(), BoilerBlockEntityRenderer::new);
        }
    }
}