package org.exodusstudio.arcana.common.event;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.client.Keybindings;
import org.exodusstudio.arcana.client.gui.InteriumMemoriamScreen;
import org.exodusstudio.arcana.common.registry.ItemRegistry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = Arcana.MODID)
public class ArcanaModEvents {

    static Set<String> specificAdvancements = Set.of(
            "arcana:arcana/quests/first_quest"
    );

    // Track block movement for delayed item drop
    private static final Map<ServerPlayer, PlayerWalkData> playerWalkDataMap = new HashMap<>();
    private static final int REQUIRED_BLOCKS = 10; // Change this value as needed

    private record PlayerWalkData(BlockPos lastBlockPos, int blocksWalked) {}

    @SubscribeEvent
    public static void onAdvancementGet (AdvancementEvent.AdvancementEarnEvent event){
        if(event.getEntity() instanceof ServerPlayer player){
            AdvancementHolder advancement = event.getAdvancement();
            if (advancement.id().toString().equals("arcana:arcana/root")) {
                // Start tracking block movement
                BlockPos initialPos = player.blockPosition();
                playerWalkDataMap.put(player, new PlayerWalkData(initialPos, 0));
            } else if (specificAdvancements.contains(advancement.id().toString())) {
                ItemStack itemStack = new ItemStack(ItemRegistry.OLD_NOTE.get());
                // Get player's looking direction and calculate offset
                Vec3 lookVec = player.getLookAngle().normalize(); // Get normalized look direction
                double dropX = player.getX() + lookVec.x + 1; // Offset by 1 block in X direction
                double dropY = player.getY() + 1.0; // Slightly above the player's feet
                double dropZ = player.getZ() + lookVec.z + 1; // Offset by 1 block in Z direction
                // Create the item entity at the offset position
                ItemEntity itemEntity = new ItemEntity(player.level(), dropX, dropY, dropZ, itemStack);
                itemEntity.setPickUpDelay(40);
                player.level().addFreshEntity(itemEntity);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Iterator<Map.Entry<ServerPlayer, PlayerWalkData>> iterator = playerWalkDataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ServerPlayer, PlayerWalkData> entry = iterator.next();
            ServerPlayer player = entry.getKey();
            PlayerWalkData data = entry.getValue();

            BlockPos currentPos = player.blockPosition();

            // Check if the player moved to a new block
            if (!currentPos.equals(data.lastBlockPos())) {
                int newBlocksWalked = data.blocksWalked() + 1;

                if (newBlocksWalked >= REQUIRED_BLOCKS) {
                    // Drop the item after walking required blocks
                    ItemStack itemStack = new ItemStack(ItemRegistry.OLD_NOTE.get());
                    Vec3 lookVec = player.getLookAngle().normalize();
                    double dropX = player.getX() + lookVec.x;
                    double dropY = player.getY() + 1.0;
                    double dropZ = player.getZ() + lookVec.z;

                    ItemEntity itemEntity = new ItemEntity(player.level(), dropX, dropY, dropZ, itemStack);
                    itemEntity.setPickUpDelay(40);
                    player.level().addFreshEntity(itemEntity);
                    player.sendSystemMessage(Component.translatable("message." + Arcana.MODID,
                            Component.translatable(Keybindings.INSTANCE.InteriorMemoriam.getKey().getDisplayName().getString())));

                    iterator.remove(); // Remove the player from tracking
                } else {
                    // Update with new position and block count
                    entry.setValue(new PlayerWalkData(currentPos, newBlocksWalked));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onDeathEvent(PlayerEvent.PlayerRespawnEvent event){
            Minecraft.getInstance().gameRenderer.togglePostEffect();

    }






    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        InteriumMemoriamScreen.updateWidgetFading();
        if (player != null && Keybindings.INSTANCE.InteriorMemoriam.consumeClick()) {
            player.playSound(SoundEvents.APPLY_EFFECT_BAD_OMEN);
            minecraft.setScreen(new InteriumMemoriamScreen());
        }
    }

}