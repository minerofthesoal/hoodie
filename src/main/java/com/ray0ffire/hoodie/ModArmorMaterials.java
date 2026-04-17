package com.ray0ffire.hoodie;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    // --- Cosmetic (0 armor) variants ---
    public static final RegistryEntry<ArmorMaterial> WHITE_HOODIE = registerCosmetic(
            "white_hoodie", () -> Ingredient.ofItems(Items.WHITE_WOOL)
    );
    public static final RegistryEntry<ArmorMaterial> BLACK_HOODIE = registerCosmetic(
            "black_hoodie", () -> Ingredient.ofItems(Items.BLACK_WOOL)
    );
    public static final RegistryEntry<ArmorMaterial> TRANS_THIGH_HIGHS = registerCosmetic(
            "trans_thigh_highs", () -> Ingredient.ofItems(Items.STRING)
    );
    public static final RegistryEntry<ArmorMaterial> BLACK_TIGHTS = registerCosmetic(
            "black_tights", () -> Ingredient.ofItems(Items.STRING)
    );

    // --- Armored variants (same textures, iron-level protection) ---
    public static final RegistryEntry<ArmorMaterial> ARMORED_WHITE_HOODIE = registerArmored(
            "armored_white_hoodie", "white_hoodie", () -> Ingredient.ofItems(Items.IRON_INGOT)
    );
    public static final RegistryEntry<ArmorMaterial> ARMORED_BLACK_HOODIE = registerArmored(
            "armored_black_hoodie", "black_hoodie", () -> Ingredient.ofItems(Items.IRON_INGOT)
    );
    public static final RegistryEntry<ArmorMaterial> ARMORED_TRANS_THIGH_HIGHS = registerArmored(
            "armored_trans_thigh_highs", "trans_thigh_highs", () -> Ingredient.ofItems(Items.IRON_INGOT)
    );
    public static final RegistryEntry<ArmorMaterial> ARMORED_BLACK_TIGHTS = registerArmored(
            "armored_black_tights", "black_tights", () -> Ingredient.ofItems(Items.IRON_INGOT)
    );

    // --- New cosmetic armor pieces ---
    public static final RegistryEntry<ArmorMaterial> WHITE_BEANIE = registerCosmetic(
            "white_beanie", () -> Ingredient.ofItems(Items.WHITE_WOOL)
    );
    public static final RegistryEntry<ArmorMaterial> BLACK_BEANIE = registerCosmetic(
            "black_beanie", () -> Ingredient.ofItems(Items.BLACK_WOOL)
    );
    public static final RegistryEntry<ArmorMaterial> WHITE_SNEAKERS = registerLight(
            "white_sneakers", () -> Ingredient.ofItems(Items.LEATHER)
    );
    public static final RegistryEntry<ArmorMaterial> BLACK_SNEAKERS = registerLight(
            "black_sneakers", () -> Ingredient.ofItems(Items.LEATHER)
    );
    public static final RegistryEntry<ArmorMaterial> COMBAT_BOOTS = registerHeavy(
            "combat_boots", () -> Ingredient.ofItems(Items.IRON_INGOT)
    );
    public static final RegistryEntry<ArmorMaterial> CARGO_PANTS = registerLight(
            "cargo_pants", () -> Ingredient.ofItems(Items.LEATHER)
    );

    private static RegistryEntry<ArmorMaterial> registerCosmetic(String name, Supplier<Ingredient> repair) {
        return registerMaterial(name, name, makeDefense(0, 0, 0, 0), 15, 0f, 0f, repair);
    }

    private static RegistryEntry<ArmorMaterial> registerArmored(String name, String textureName, Supplier<Ingredient> repair) {
        return registerMaterial(name, textureName, makeDefense(2, 5, 4, 2), 12, 0.5f, 0f, repair);
    }

    // Light armor (leather-tier): low defense
    private static RegistryEntry<ArmorMaterial> registerLight(String name, Supplier<Ingredient> repair) {
        return registerMaterial(name, name, makeDefense(1, 2, 3, 1), 15, 0f, 0f, repair);
    }

    // Heavy armor (iron-tier): higher defense
    private static RegistryEntry<ArmorMaterial> registerHeavy(String name, Supplier<Ingredient> repair) {
        return registerMaterial(name, name, makeDefense(2, 5, 6, 2), 9, 0.5f, 0.05f, repair);
    }

    private static EnumMap<ArmorItem.Type, Integer> makeDefense(int boots, int leggings, int chest, int helmet) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chest);
        map.put(ArmorItem.Type.HELMET, helmet);
        map.put(ArmorItem.Type.BODY, 0);
        return map;
    }

    private static RegistryEntry<ArmorMaterial> registerMaterial(
            String name, String textureName,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantability, float toughness, float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Identifier.of(HoodieMod.MOD_ID, textureName))
        );

        ArmorMaterial material = new ArmorMaterial(
                defense, enchantability,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                repairIngredient, layers,
                toughness, knockbackResistance
        );

        return Registry.registerReference(
                Registries.ARMOR_MATERIAL,
                Identifier.of(HoodieMod.MOD_ID, name),
                material
        );
    }

    public static void register() {
        HoodieMod.LOGGER.info("Registering armor materials for " + HoodieMod.MOD_ID);
    }
}
