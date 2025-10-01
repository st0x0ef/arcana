package org.exodusstudio.arcana.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.exodusstudio.arcana.common.component.ScribbledNoteData;
import org.exodusstudio.arcana.common.registry.DataComponentRegistry;
import org.exodusstudio.arcana.common.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArcanaScribblingToolItem extends Item {
    public ArcanaScribblingToolItem(Properties properties) {
        super(properties);
    }

    final List<Block> blocks = new ArrayList<>();

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Block clickedBlock = world.getBlockState(context.getClickedPos()).getBlock();
        Player player = context.getPlayer();
        BlockState blockState = world.getBlockState(pos);
        //Also needs to be able to collect Item data -Pyr0

        if(!world.isClientSide()) {
            Inventory playerInventory = player.getInventory();

            boolean itemFound = false;
            ItemStack itemStack = ItemStack.EMPTY;
            int itemStackIndex = 0;

            for(int i = 0; i < playerInventory.getContainerSize(); i++) {
                ItemStack currentItemStack = playerInventory.getItem(i);
                if (currentItemStack.getItem().equals(Items.PAPER)) {
                    if (!blocks.contains(clickedBlock)){
                        blocks.add(clickedBlock);
                        itemFound = true;
                        itemStack = currentItemStack;
                        itemStackIndex = i;
                    }
                    break;
                }
            }
            //Should also check if the block has already been taken notes of -Pyr0 (Update: now it does check if the blocks has already been taken notes of -Pyr0)
            if(itemFound) {
                playerInventory.removeItem(itemStackIndex, 1);
                ItemStack scribbledNote = new ItemStack(ItemRegistry.SCRIBBLED_NOTE.get());
                Random random = new Random();
                int randomID = random.nextInt(10);
                scribbledNote.set(DataComponentRegistry.SCRIBBLED_NOTE_DATA.get(), new ScribbledNoteData("research name"));
                playerInventory.add(scribbledNote);
            } else {
                //No empty map on his inventory
                if (blocks.contains(clickedBlock)){
                    player.displayClientMessage(Component.translatable("arcana.message.notes_already_taken"), true);
                }
                else {
                    player.displayClientMessage(Component.translatable("arcana.message.scribbling_tool_no_map"), true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
