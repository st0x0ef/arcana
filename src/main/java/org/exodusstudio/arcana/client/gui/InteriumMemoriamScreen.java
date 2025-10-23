package org.exodusstudio.arcana.client.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostChainConfig;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.component.WidgetData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class InteriumMemoriamScreen extends Screen  {

    private void renderTextureOverlay(GuiGraphics guiGraphics) {
        int i = ARGB.white((float) 1.5);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, InteriumMemoriamScreen.INTERIOR_MEMORIAM, 0, 0, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight(), i);
    }



    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!activePopups.isEmpty()) {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    private static final ResourceLocation INTERIOR_MEMORIAM = ResourceLocation.fromNamespaceAndPath(Arcana.MODID,"textures/misc/memoriam_overlay.png");
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "interior_memoriam_shader");

    private static final List<DragWidget> customWidgets = new ArrayList<>();
    private final List<DragWidget> orderedWidgets = new ArrayList<>();
    private static final List<PopupWidget> activePopups = new ArrayList<>();
    private static final int lastX = -1;
    private static final int lastY = -1;
    private static boolean shouldAddWidget = false;
    private boolean isDataLoaded = false;
    private static final List<WidgetData> savedWidgetData = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        customWidgets.clear();
        savedWidgetData.clear();
        activePopups.clear();
        orderedWidgets.clear();
        this.clearWidgets();

        if (!customWidgets.isEmpty()) {
            savedWidgetData.clear();
            for (DragWidget widget : customWidgets) {
                savedWidgetData.add(new WidgetData(widget.getX(), widget.getY(), widget.getUuid(),textureIndex, widget.getAlpha()));
            }
        }

        loadWidgetData();
        isDataLoaded = true;

        for (WidgetData data : savedWidgetData) {

            DragWidget dragWidget = getDragWidget(data);
            customWidgets.add(dragWidget);
            orderedWidgets.add(dragWidget);
            addRenderableWidget(dragWidget);
        }

        // Update textureIndex to the highest saved value + 1
        textureIndex = savedWidgetData.stream()
                .mapToInt(WidgetData::getTextureIndex)
                .max()
                .orElse(0) + 1;

        if (shouldAddWidget) {
            addCustomWidget();
            shouldAddWidget = false;
        }

    }

    @NotNull
    private DragWidget getDragWidget(WidgetData data) {
        int absoluteX = (int) (data.getRelativeX() * this.width);
        int absoluteY = (int) (data.getRelativeY() * this.height);

        DragWidget dragWidget = new DragWidget(
                absoluteX, absoluteY,
                68, 83,
                Component.literal("Drag me"),
                data.getUuid(),
                savedWidgetData,
                data.getTextureIndex(),
                this
        );
        dragWidget.setAlpha(data.getAlpha());
        return dragWidget;
    }


    private void loadWidgetData() {
        MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) return; // Ensure we're in a singleplayer world

        Path worldPath = server.getWorldPath(LevelResource.ROOT).resolve("data");

        Path path = worldPath.resolve("widget_data.json");
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                JsonElement jsonElement = JsonParser.parseString(json);

                // Check if the JSON is an array
                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();

                    // Parse each element in the array as a WidgetData object
                    for (JsonElement element : jsonArray) {
                        DataResult<WidgetData> result = WidgetData.CODEC.parse(JsonOps.INSTANCE, element);
                        result.result().ifPresent(savedWidgetData::add);
                    }
                } else {
                    // Handle the case where the JSON is not an array (e.g., single object)
                    DataResult<WidgetData> result = WidgetData.CODEC.parse(JsonOps.INSTANCE, jsonElement);
                    result.result().ifPresent(savedWidgetData::add);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setShouldAddWidget(boolean value) {
        shouldAddWidget = value;
    }
    private static int textureIndex = 1;

    public void openPopup(int textureIndex) {
        int popupWidth = 153;
        int popupHeight = 186;
        int x = (this.width - popupWidth) / 2;
        int y = (this.height - popupHeight) / 2;
        PopupWidget[] popupHolder = new PopupWidget[1];
        popupHolder[0] = new PopupWidget(
                x, y, popupWidth, popupHeight,
                Component.translatable("gui.widget." + textureIndex),
                textureIndex, () -> {
            removeWidget(popupHolder[0]);
            activePopups.remove(popupHolder[0]); // Remove from tracking when closed
        });
        activePopups.add(popupHolder[0]); // Track the new popup
        this.addRenderableWidget(popupHolder[0]);
    }

    public void addCustomWidget() {
        int startX = (this.width - 68) / 2;
        int startY = (this.height - 83) / 2;
        UUID uuid = UUID.randomUUID(); // Generate a new UUID

        // Calculate relative positions at creation time
        double relativeX = (double) startX / this.width;
        double relativeY = (double) startY / this.height;

        DragWidget dragWidget = new DragWidget(
                startX, // Start X position
                startY, // Start Y position
                68, // Widget width
                83, // Widget height
                Component.literal("Drag me"),
                uuid,
                savedWidgetData,
                textureIndex,
                this
        );
        customWidgets.add(dragWidget);
        orderedWidgets.add(dragWidget); // Track order
        addRenderableWidget(dragWidget);
        savedWidgetData.add(new WidgetData(
                relativeX,
                relativeY,
                uuid,
                textureIndex,
                1.0f
        ));
    }


    public InteriumMemoriamScreen() {
        super(Component.literal("InteriorMemoriam"));
        Minecraft.getInstance().gameRenderer.setPostEffect(BACKGROUND);
    }





    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void updateWidgetFading() {
        if (savedWidgetData.size() >= 6) {

            WidgetData oldest = savedWidgetData.getFirst();
            float alphaDecrement = 1.0f / (30 * 20);
            oldest.setAlpha(oldest.getAlpha() - alphaDecrement);
            for (DragWidget widget : customWidgets) {
                if (widget.getUuid().equals(oldest.getUuid())) {
                    widget.setAlpha(oldest.getAlpha()); // Sync widget alpha
                    break;
                }
            }

            if (oldest.getAlpha() <= 0.5f) {
                int targetTextureIndex = oldest.getTextureIndex();
                activePopups.stream()
                        .filter(popup -> popup.getTextureIndex() == targetTextureIndex)
                        .forEach(popup -> popup.setCorrupt(true));
            }

            if (oldest.getAlpha() <= 0) {

                savedWidgetData.removeFirst();
                customWidgets.removeIf(w -> w.getUuid().equals(oldest.getUuid()));
                saveWidgetData();
            } else {
                saveWidgetData();
            }
        }

        boolean hasActivePopups = !activePopups.isEmpty();
        for (DragWidget widget : customWidgets) {
            widget.active = !hasActivePopups;
            // Sync alpha (existing code)
            savedWidgetData.stream()
                    .filter(data -> data.getUuid().equals(widget.getUuid()))
                    .findFirst()
                    .ifPresent(data -> widget.setAlpha(data.getAlpha()));
        }

        for (DragWidget widget : customWidgets) {
            // Find matching WidgetData and sync alpha
            savedWidgetData.stream()
                    .filter(data -> data.getUuid().equals(widget.getUuid()))
                    .findFirst()
                    .ifPresent(data -> widget.setAlpha(data.getAlpha()));
        }
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderTextureOverlay(graphics);
        updateWidgetFading();
        super.render(graphics, mouseX, mouseY, partialTicks);
    }



    @Override
    public void onClose() {
        Minecraft.getInstance().gameRenderer.clearPostEffect();
        saveWidgetData();
        super.onClose();
    }

    private static void saveWidgetData() {
        MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) return;

        Path worldPath = server.getWorldPath(LevelResource.ROOT).resolve("data");

        // Save directly from savedWidgetData (already contains relative positions)
        DataResult<JsonElement> result = WidgetData.CODEC.listOf().encodeStart(JsonOps.INSTANCE, savedWidgetData);
        result.result().ifPresent(json -> {
            try {
                Files.writeString(worldPath.resolve("widget_data.json"), json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
