package org.exodusstudio.arcana.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.registry.BlockRegistry;
import org.exodusstudio.arcana.common.registry.ItemRegistry;

import java.util.function.Supplier;

public class ArcanaCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Arcana.MODID);

    public static final Supplier<CreativeModeTab> ARCANA_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("arcana_creative_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.arcana.arcana_creative_tab"))
                    .icon(()->new ItemStack(ItemRegistry.SCRIBBLING_TOOL.get()))
                    .displayItems((pParameters, pOutput)->{
                      pOutput.accept(ItemRegistry.SCRIBBLING_TOOL);
                      pOutput.accept(ItemRegistry.SCRIBBLED_NOTE);
                      pOutput.accept(ItemRegistry.NHIL_CRYSTAL);
                      pOutput.accept(ItemRegistry.NHIL_POWDER);
                      pOutput.accept(ItemRegistry.OLD_NOTE);
                      pOutput.accept(ItemRegistry.INK_BOTTLE);
                      pOutput.accept(ItemRegistry.ANCIENT_FEATHER);
                      pOutput.accept(BlockRegistry.RESEARCH_TABLE);
                      pOutput.accept(BlockRegistry.BOILER_BLOCK);
                      pOutput.accept(ItemRegistry.PROTO_WAND);
                      pOutput.accept(ItemRegistry.MATE);
                      pOutput.accept(BlockRegistry.WEEPING_PETAL);
                      pOutput.accept(ItemRegistry.WEEPING_POWDER);
                      pOutput.accept(BlockRegistry.LILLIE_BLOCK);
                      pOutput.accept(ItemRegistry.ALCHEMICAL_STEEL);
                        for (DyeColor color : DyeColor.values()) {
                            ItemStack stack = new ItemStack(ItemRegistry.NITOR.get());
                            stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color.getTextureDiffuseColor()));
                            pOutput.accept(stack);
                        }
                      //pOutput.accept(BlockRegistry.NIMBUS_STONE);
                    }).build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
