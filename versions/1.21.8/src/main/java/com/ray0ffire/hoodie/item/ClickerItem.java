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

public class ClickerItem extends Item {
    public ClickerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        float pitch = 1.6f + (world.getRandom().nextFloat() - 0.5f) * 0.1f;
        world.playSound(
                world.isClient ? user : null,
                user.getX(), user.getY(), user.getZ(),
                SoundEvents.UI_BUTTON_CLICK.value(),
                SoundCategory.PLAYERS,
                0.6f, pitch
        );
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5 * 20, 0, true, false, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 5 * 20, 0, true, false, true));
        }
        user.getItemCooldownManager().set(user.getStackInHand(hand), 20 * 20);
        return ActionResult.SUCCESS;
    }
}
