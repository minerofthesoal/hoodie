package com.ray0ffire.hoodie.item;

import com.ray0ffire.hoodie.CoffeeCrashTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CoffeeCupItem extends Item {
    public CoffeeCupItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 24;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            // Speed IV (amp 3) for 315s, Haste II (amp 1) for 354s (39s past the Speed).
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 315 * 20, 3));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 354 * 20, 1));
            // Schedule the coffee crash for when Haste ends: Slowness I 15s + Mining Fatigue I 10s.
            CoffeeCrashTracker.schedule(user.getUuid(), world.getTime() + 354L * 20L);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_GENERIC_DRINK, user.getSoundCategory(), 1.0f, 1.0f);

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        return stack.isEmpty() ? new ItemStack(this) : stack;
    }
}
