package org.exodusstudio.arcana.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WidgetData {
    private double relativeX; // Percentage of screen width (0.0 to 1.0)
    private double relativeY; // Percentage of screen height (0.0 to 1.0)
    private final UUID uuid;
    private final int textureIndex;
    private float alpha;

    public WidgetData(double relativeX, double relativeY, UUID uuid, int textureIndex, float alpha) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.uuid = uuid;
        this.textureIndex = textureIndex;
        this.alpha = alpha;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public double getRelativeX() {
        return relativeX;
    }
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
    }

    public double getRelativeY() {
        return relativeY;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setRelativeX(double relativeX) {
        this.relativeX = relativeX;
    }

    public void setRelativeY(double relativeY) {
        this.relativeY = relativeY;
    }

    public static final Codec<WidgetData> CODEC = RecordCodecBuilder.create(widgetDataInstance ->
            widgetDataInstance.group(
                    Codec.DOUBLE.fieldOf("relativeX").forGetter(WidgetData::getRelativeX),
                    Codec.DOUBLE.fieldOf("relativeY").forGetter(WidgetData::getRelativeY),
                    Codec.STRING.xmap(UUID::fromString, UUID::toString).fieldOf("uuid").forGetter(WidgetData::getUuid),
                    Codec.INT.fieldOf("textureIndex").forGetter(WidgetData::getTextureIndex),
                    Codec.FLOAT.optionalFieldOf("alpha", 1.0f).forGetter(WidgetData::getAlpha)
            ).apply(widgetDataInstance, WidgetData::new)
    );

    public void toBuffer(FriendlyByteBuf buffer) {
        buffer.writeDouble(relativeX);
        buffer.writeDouble(relativeY);
        buffer.writeUUID(uuid);
        buffer.writeInt(textureIndex);
        buffer.writeDouble(alpha);
    }

    public static WidgetData fromBuffer(FriendlyByteBuf buffer) {
        return new WidgetData(buffer.readDouble(), buffer.readDouble(), buffer.readUUID(), buffer.readInt(), buffer.readFloat());
    }

    public static String toJson(List<WidgetData> widgets) {
        return CODEC.listOf().encodeStart(com.mojang.serialization.JsonOps.INSTANCE, widgets).result().orElseThrow().toString();
    }

    public static List<WidgetData> fromJson(String json) {
        return CODEC.listOf().parse(com.mojang.serialization.JsonOps.INSTANCE, com.google.gson.JsonParser.parseString(json)).result().orElse(new ArrayList<>());
    }

}
