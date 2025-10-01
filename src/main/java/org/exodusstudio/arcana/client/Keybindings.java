package org.exodusstudio.arcana.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.exodusstudio.arcana.Arcana;

public final class Keybindings {

    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath(Arcana.MODID, Arcana.MODID));

    public final KeyMapping InteriorMemoriam = new KeyMapping(
            "key." + Arcana.MODID + ".memoriam",
            InputConstants.KEY_L,
            CATEGORY
    );

}
