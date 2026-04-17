package com.ray0ffire.hoodie.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EarbudsItem extends Item {
    public EarbudsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60 * 20, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20, 0));
        }
        world.playSound(
                world.isClient() ? user : null,
                user.getX(), user.getY(), user.getZ(),
                SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(),
                SoundCategory.PLAYERS,
                0.5f, 1.4f
        );
        user.getItemCooldownManager().set(user.getStackInHand(hand), 60 * 20);
        return ActionResult.SUCCESS;
    }
}
