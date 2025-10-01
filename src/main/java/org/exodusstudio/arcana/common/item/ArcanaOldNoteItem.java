package org.exodusstudio.arcana.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.exodusstudio.arcana.client.gui.InteriumMemoriamScreen;

public class ArcanaOldNoteItem extends Item {
    public ArcanaOldNoteItem(Properties properties) {super(properties);}

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {

        if(level.isClientSide()){
            InteriumMemoriamScreen.setShouldAddWidget(true);
        }

        ItemStack itemStack = player.getItemInHand(hand);

        itemStack.shrink(1);

        return InteractionResult.SUCCESS;
    }
}
