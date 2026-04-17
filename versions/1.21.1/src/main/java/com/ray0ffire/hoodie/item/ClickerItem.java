package com.ray0ffire.hoodie.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ClickerItem extends Item {
    public ClickerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Play a snappy click sound at the player's location.
        // Server side plays for everyone except the user; client side plays for the user
        // so they hear it instantly without server round-trip.
        float pitch = 1.6f + (world.getRandom().nextFloat() - 0.5f) * 0.1f;
        world.playSound(
                world.isClient ? user : null,
                user.getX(), user.getY(), user.getZ(),
                SoundEvents.UI_BUTTON_CLICK.value(),
                SoundCategory.PLAYERS,
                0.6f, pitch
        );

        user.getItemCooldownManager().set(this, 4);
        return TypedActionResult.success(stack, world.isClient());
    }
}
