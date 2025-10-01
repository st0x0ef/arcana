package org.exodusstudio.arcana.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.client.menu.ResearchTableMenu;

public class ResearchTableScreen extends AbstractContainerScreen<ResearchTableMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "textures/gui/research_table/research_table_screen.png");


    public ResearchTableScreen(ResearchTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelY = 10000;
        this.inventoryLabelX = 10000;
        this.titleLabelX = -1000;
        this.titleLabelY = -1000;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);



        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                GUI_TEXTURE,
                0, 0,
                0, 0,
                guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                guiGraphics.guiWidth(), guiGraphics.guiHeight()
        );
    }
}
