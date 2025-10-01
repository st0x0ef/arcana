package org.exodusstudio.arcana.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.exodusstudio.arcana.Arcana;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PopupWidget extends AbstractWidget {

    private ResourceLocation texture;
    private static boolean Corrupt = false;
    private final Runnable onClose;
    private final int textureIndex;

    public PopupWidget(int x, int y, int width, int height, Component message, int textureIndex, Runnable onClose) {
        super(x, y, width, height, message);
        this.textureIndex = textureIndex;
        this.onClose = onClose;
        updateTexture();
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    private void updateTexture() {
        if (!Corrupt) {
            if(texture == null){
                this.texture = ResourceLocation.fromNamespaceAndPath(Arcana.MODID,"textures/gui/interior_memoriam/widget_default.png");
            } else {
                this.texture = ResourceLocation.fromNamespaceAndPath(
                        Arcana.MODID,
                        "textures/gui/interior_memoriam/widget_" + textureIndex + ".png"
                );
            }
        } else {
            this.texture = ResourceLocation.fromNamespaceAndPath(
                    Arcana.MODID,
                    "textures/gui/interior_memoriam/corrupt_widget.png"
            );
        }
    }

    public void setCorrupt(boolean corrupt) {
        Corrupt = corrupt;
        updateTexture();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int x, int y, float v) {
        if (texture != null) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, getX(), getY(), 0, 0, width, height, width, height);
        }

        Minecraft minecraft = Minecraft.getInstance();
        int textWidth = width - 19; // 10px padding on both sides
        int textY = getY() + 25;
        int maxLines = (height - 25) / minecraft.font.lineHeight; // Calculate max visible lines

        // Split text into wrapped lines
        List<FormattedCharSequence> lines = minecraft.font.split(getMessage(), textWidth);

        float scale = 1.0f;
        if (lines.size() > maxLines) {
            scale = (float) maxLines / lines.size(); // Shrink proportionally
            scale = Math.max(scale, 0.5f); // Set a minimum readable scale
        }

        for (int i = 0; i < lines.size(); i++) {
            if (i >= maxLines) break; // Ensure we don't draw extra lines

            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(scale, scale);

            float scaledX = (getX() + 10) / scale;
            float scaledY = (textY + (i * minecraft.font.lineHeight)) / scale;

            guiGraphics.drawString(minecraft.font, lines.get(i), (int) scaledX, (int) scaledY, 0x000000, false);

            guiGraphics.pose().popMatrix();
        }


        // Define X hitbox
        int closeX = getX() + width - 15;
        int closeY = getY() + 9;
        int closeSize = 18;

        // Check if mouse is over the X (not the whole widget)
        boolean isHoveringX = x >= closeX && y <= closeX + closeSize
                && x >= closeY && y <= closeY + closeSize;

        int normalColor = 0xFFFFFF;
        int hoveringColor = 0x000000;
        int wgColor = isHoveringX ? hoveringColor : normalColor;
        String Wtext = (!Corrupt) ? "X" :"̇/";


        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(
                getX() + width - 15, // Adjust X position
                getY() + 9         // Y position
        );
        guiGraphics.pose().scale(2F, 2F); //scale
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                Wtext,
                0, 0, // Draw at translated position
                wgColor,
                false
        );
        guiGraphics.pose().popMatrix();

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        if(isMouseOver(mouseButtonEvent.x(), mouseButtonEvent.y())){
            int closeX = getX() + width - 15;
            int closeY = getY() + 9;
            int closeSize = 18;

            if (mouseButtonEvent.x() >= closeX && mouseButtonEvent.x() <= closeX + closeSize &&
                    mouseButtonEvent.y() >= closeY && mouseButtonEvent.y() <= closeY + closeSize) {
                if (onClose != null) onClose.run();
                return true;
            }
            return true;
        }
        return false;
    }

    private void close(){
        this.onClose.run();
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }
}
