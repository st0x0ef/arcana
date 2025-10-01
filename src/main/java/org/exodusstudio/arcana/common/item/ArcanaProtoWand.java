package org.exodusstudio.arcana.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.item.BowItem.getPowerForTime;

public class ArcanaProtoWand extends Item {

    public ArcanaProtoWand(Item.Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand); // begin charging
        return InteractionResult.CONSUME;
    }


    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            int chargeTime = this.getUseDuration(stack, entity) - timeLeft;

            //THRESHOLDS
            int weak = 20;
            int medium = 45;
            int strong = 65;
            int overCharge = 140;

            float power = getPowerForTime(chargeTime);

            if (power > 0.2f) {
                if (!level.isClientSide()) {
                    if (chargeTime >= overCharge) {
                        level.explode(player, player.getX(), player.getY(), player.getZ(), 4.0f, Level.ExplosionInteraction.NONE);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1f, 1f);
                    } else {
                        int fireBallCount = 1;
                        if (chargeTime >= strong) {
                            fireBallCount = 3;
                        } else if (chargeTime >= medium) {
                            fireBallCount = 2;
                        }

                        Vec3 look = player.getLookAngle();
                        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();

                        for (int i = 0; i < fireBallCount; i++) {
                            double offset = (i - (fireBallCount - 1) / 2.0) * 0.15;
                            Vec3 dir = look.add(right.scale(offset)).normalize();

                            // Create a fireball
                            SmallFireball fireball = new SmallFireball(level, player, dir);
                            // Spawn in front of player
                            fireball.setPos(player.getX() + dir.x * 0.5, player.getEyeY() - 0.1, player.getZ() + dir.z + 0.5);
                            level.addFreshEntity(fireball);
                        }
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }

        }
        return false;
    }

    @Override
    public void onUseTick (Level level, LivingEntity livingEntity, ItemStack stack,int remainingUseDuration){
        int i = this.getUseDuration(stack, livingEntity) - remainingUseDuration + 1;
        boolean flag = i % 20 == 0;
        if (flag)
            livingEntity.playSound(SoundEvents.PORTAL_AMBIENT, 1, 2);
        int chargeTime = this.getUseDuration(stack, livingEntity) - remainingUseDuration;
    }
}

