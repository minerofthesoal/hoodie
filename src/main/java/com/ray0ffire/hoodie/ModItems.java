package com.ray0ffire.hoodie;

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
    public static final Item WHITE_HOODIE = registerClothing(
            "white_hoodie", ModArmorMaterials.WHITE_HOODIE,
            ArmorItem.Type.CHESTPLATE, 80
    );
    public static final Item BLACK_HOODIE = registerClothing(
            "black_hoodie", ModArmorMaterials.BLACK_HOODIE,
            ArmorItem.Type.CHESTPLATE, 80
    );
    public static final Item TRANS_THIGH_HIGHS = registerClothing(
            "trans_thigh_highs", ModArmorMaterials.TRANS_THIGH_HIGHS,
            ArmorItem.Type.LEGGINGS, 75
    );
    public static final Item BLACK_TIGHTS = registerClothing(
            "black_tights", ModArmorMaterials.BLACK_TIGHTS,
            ArmorItem.Type.LEGGINGS, 75
    );

    // --- Armored variants (iron-level protection) ---
    public static final Item ARMORED_WHITE_HOODIE = registerClothing(
            "armored_white_hoodie", ModArmorMaterials.ARMORED_WHITE_HOODIE,
            ArmorItem.Type.CHESTPLATE, 240
    );
    public static final Item ARMORED_BLACK_HOODIE = registerClothing(
            "armored_black_hoodie", ModArmorMaterials.ARMORED_BLACK_HOODIE,
            ArmorItem.Type.CHESTPLATE, 240
    );
    public static final Item ARMORED_TRANS_THIGH_HIGHS = registerClothing(
            "armored_trans_thigh_highs", ModArmorMaterials.ARMORED_TRANS_THIGH_HIGHS,
            ArmorItem.Type.LEGGINGS, 225
    );
    public static final Item ARMORED_BLACK_TIGHTS = registerClothing(
            "armored_black_tights", ModArmorMaterials.ARMORED_BLACK_TIGHTS,
            ArmorItem.Type.LEGGINGS, 225
    );

    private static Item registerClothing(
            String name,
            RegistryEntry<ArmorMaterial> material,
            ArmorItem.Type type,
            int durability
    ) {
        Item item = new ArmorItem(material, type, new Item.Settings().maxDamage(durability));
        return Registry.register(Registries.ITEM, Identifier.of(HoodieMod.MOD_ID, name), item);
    }

    public static void register() {
        HoodieMod.LOGGER.info("Registering items for " + HoodieMod.MOD_ID);

        // Add all items to the combat creative tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(WHITE_HOODIE);
            entries.add(BLACK_HOODIE);
            entries.add(TRANS_THIGH_HIGHS);
            entries.add(BLACK_TIGHTS);
            entries.add(ARMORED_WHITE_HOODIE);
            entries.add(ARMORED_BLACK_HOODIE);
            entries.add(ARMORED_TRANS_THIGH_HIGHS);
            entries.add(ARMORED_BLACK_TIGHTS);
        });
    }
}
