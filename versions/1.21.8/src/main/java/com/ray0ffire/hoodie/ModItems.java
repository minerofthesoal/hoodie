package com.ray0ffire.hoodie;

import com.ray0ffire.hoodie.item.ClickerItem;
import com.ray0ffire.hoodie.item.CoffeeCupItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    // --- Cosmetic clothing ---
    public static final Item WHITE_HOODIE = armor("white_hoodie", ModArmorMaterials.WHITE_HOODIE, EquipmentType.CHESTPLATE);
    public static final Item BLACK_HOODIE = armor("black_hoodie", ModArmorMaterials.BLACK_HOODIE, EquipmentType.CHESTPLATE);
    public static final Item TRANS_THIGH_HIGHS = armor("trans_thigh_highs", ModArmorMaterials.TRANS_THIGH_HIGHS, EquipmentType.LEGGINGS);
    public static final Item BLACK_TIGHTS = armor("black_tights", ModArmorMaterials.BLACK_TIGHTS, EquipmentType.LEGGINGS);

    // --- Armored variants ---
    public static final Item ARMORED_WHITE_HOODIE = armor("armored_white_hoodie", ModArmorMaterials.ARMORED_WHITE_HOODIE, EquipmentType.CHESTPLATE);
    public static final Item ARMORED_BLACK_HOODIE = armor("armored_black_hoodie", ModArmorMaterials.ARMORED_BLACK_HOODIE, EquipmentType.CHESTPLATE);
    public static final Item ARMORED_TRANS_THIGH_HIGHS = armor("armored_trans_thigh_highs", ModArmorMaterials.ARMORED_TRANS_THIGH_HIGHS, EquipmentType.LEGGINGS);
    public static final Item ARMORED_BLACK_TIGHTS = armor("armored_black_tights", ModArmorMaterials.ARMORED_BLACK_TIGHTS, EquipmentType.LEGGINGS);

    // --- New armor pieces ---
    public static final Item WHITE_BEANIE = armor("white_beanie", ModArmorMaterials.WHITE_BEANIE, EquipmentType.HELMET);
    public static final Item BLACK_BEANIE = armor("black_beanie", ModArmorMaterials.BLACK_BEANIE, EquipmentType.HELMET);
    public static final Item WHITE_SNEAKERS = armor("white_sneakers", ModArmorMaterials.WHITE_SNEAKERS, EquipmentType.BOOTS);
    public static final Item BLACK_SNEAKERS = armor("black_sneakers", ModArmorMaterials.BLACK_SNEAKERS, EquipmentType.BOOTS);
    public static final Item COMBAT_BOOTS = armor("combat_boots", ModArmorMaterials.COMBAT_BOOTS, EquipmentType.BOOTS);
    public static final Item CARGO_PANTS = armor("cargo_pants", ModArmorMaterials.CARGO_PANTS, EquipmentType.LEGGINGS);

    // --- New non-armor items ---
    public static final Item CLICKER = register("clicker",
            settings -> new ClickerItem(settings.maxCount(1)));
    public static final Item PHONE = register("phone",
            settings -> new Item(settings.maxCount(1)));
    public static final Item COFFEE_CUP = register("coffee_cup",
            settings -> new CoffeeCupItem(settings.maxCount(16)));

    private static Item armor(String name, ArmorMaterial material, EquipmentType type) {
        return register(name, settings -> new Item(settings.armor(material, type)));
    }

    private static Item register(String name, Function<Item.Settings, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(HoodieMod.MOD_ID, name));
        return Items.register(key, factory);
    }

    public static void register() {
        HoodieMod.LOGGER.info("Registering items for " + HoodieMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(WHITE_HOODIE);
            entries.add(BLACK_HOODIE);
            entries.add(TRANS_THIGH_HIGHS);
            entries.add(BLACK_TIGHTS);
            entries.add(ARMORED_WHITE_HOODIE);
            entries.add(ARMORED_BLACK_HOODIE);
            entries.add(ARMORED_TRANS_THIGH_HIGHS);
            entries.add(ARMORED_BLACK_TIGHTS);
            entries.add(WHITE_BEANIE);
            entries.add(BLACK_BEANIE);
            entries.add(WHITE_SNEAKERS);
            entries.add(BLACK_SNEAKERS);
            entries.add(COMBAT_BOOTS);
            entries.add(CARGO_PANTS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(CLICKER);
            entries.add(PHONE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(COFFEE_CUP);
        });
    }
}
