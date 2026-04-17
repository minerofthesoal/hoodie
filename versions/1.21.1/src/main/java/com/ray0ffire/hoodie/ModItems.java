package com.ray0ffire.hoodie;

import com.ray0ffire.hoodie.item.ClickerItem;
import com.ray0ffire.hoodie.item.CoffeeCupItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModItems {

    // --- Cosmetic clothing (no armor protection) ---
    public static final Item WHITE_HOODIE = registerArmor(
            "white_hoodie", ModArmorMaterials.WHITE_HOODIE,
            ArmorItem.Type.CHESTPLATE, 80
    );
    public static final Item BLACK_HOODIE = registerArmor(
            "black_hoodie", ModArmorMaterials.BLACK_HOODIE,
            ArmorItem.Type.CHESTPLATE, 80
    );
    public static final Item TRANS_THIGH_HIGHS = registerArmor(
            "trans_thigh_highs", ModArmorMaterials.TRANS_THIGH_HIGHS,
            ArmorItem.Type.LEGGINGS, 75
    );
    public static final Item BLACK_TIGHTS = registerArmor(
            "black_tights", ModArmorMaterials.BLACK_TIGHTS,
            ArmorItem.Type.LEGGINGS, 75
    );

    // --- Armored variants (iron-level protection) ---
    public static final Item ARMORED_WHITE_HOODIE = registerArmor(
            "armored_white_hoodie", ModArmorMaterials.ARMORED_WHITE_HOODIE,
            ArmorItem.Type.CHESTPLATE, 240
    );
    public static final Item ARMORED_BLACK_HOODIE = registerArmor(
            "armored_black_hoodie", ModArmorMaterials.ARMORED_BLACK_HOODIE,
            ArmorItem.Type.CHESTPLATE, 240
    );
    public static final Item ARMORED_TRANS_THIGH_HIGHS = registerArmor(
            "armored_trans_thigh_highs", ModArmorMaterials.ARMORED_TRANS_THIGH_HIGHS,
            ArmorItem.Type.LEGGINGS, 225
    );
    public static final Item ARMORED_BLACK_TIGHTS = registerArmor(
            "armored_black_tights", ModArmorMaterials.ARMORED_BLACK_TIGHTS,
            ArmorItem.Type.LEGGINGS, 225
    );

    // --- New armor pieces ---
    public static final Item WHITE_BEANIE = registerArmor(
            "white_beanie", ModArmorMaterials.WHITE_BEANIE,
            ArmorItem.Type.HELMET, 70
    );
    public static final Item BLACK_BEANIE = registerArmor(
            "black_beanie", ModArmorMaterials.BLACK_BEANIE,
            ArmorItem.Type.HELMET, 70
    );
    public static final Item WHITE_SNEAKERS = registerArmor(
            "white_sneakers", ModArmorMaterials.WHITE_SNEAKERS,
            ArmorItem.Type.BOOTS, 120
    );
    public static final Item BLACK_SNEAKERS = registerArmor(
            "black_sneakers", ModArmorMaterials.BLACK_SNEAKERS,
            ArmorItem.Type.BOOTS, 120
    );
    public static final Item COMBAT_BOOTS = registerArmor(
            "combat_boots", ModArmorMaterials.COMBAT_BOOTS,
            ArmorItem.Type.BOOTS, 260
    );
    public static final Item CARGO_PANTS = registerArmor(
            "cargo_pants", ModArmorMaterials.CARGO_PANTS,
            ArmorItem.Type.LEGGINGS, 150
    );

    // --- New non-armor items ---
    public static final Item CLICKER = register(
            "clicker", new ClickerItem(new Item.Settings().maxCount(1))
    );
    public static final Item PHONE = register(
            "phone", new Item(new Item.Settings().maxCount(1))
    );
    public static final Item COFFEE_CUP = register(
            "coffee_cup", new CoffeeCupItem(new Item.Settings().maxCount(16))
    );

    private static Item registerArmor(
            String name,
            RegistryEntry<ArmorMaterial> material,
            ArmorItem.Type type,
            int durability
    ) {
        Item item = new ArmorItem(material, type, new Item.Settings().maxDamage(durability));
        return Registry.register(Registries.ITEM, Identifier.of(HoodieMod.MOD_ID, name), item);
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HoodieMod.MOD_ID, name), item);
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
