package com.ray0ffire.hoodie;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * 1.21.8+ port of armor materials. ArmorMaterial is now a record that ties:
 *   - durability (per-type max damage = durability * EquipmentType base)
 *   - defense map keyed on EquipmentType
 *   - equip sound, repair tag, asset id (texture set)
 *
 * Texture rendering is driven by EquipmentAsset JSONs under
 * assets/hoodie/equipment/&lt;name&gt;.json (one per material).
 */
public class ModArmorMaterials {

    // --- Cosmetic (0 armor) variants ---
    public static final ArmorMaterial WHITE_HOODIE = makeCosmetic("white_hoodie", ItemTags.WOOL);
    public static final ArmorMaterial BLACK_HOODIE = makeCosmetic("black_hoodie", ItemTags.WOOL);
    public static final ArmorMaterial TRANS_THIGH_HIGHS = makeCosmetic("trans_thigh_highs", ItemTags.WOOL);
    public static final ArmorMaterial BLACK_TIGHTS = makeCosmetic("black_tights", ItemTags.WOOL);

    // --- Armored (iron-tier) variants share the texture set with their cosmetic counterpart ---
    public static final ArmorMaterial ARMORED_WHITE_HOODIE = makeArmored("armored_white_hoodie", "white_hoodie", ItemTags.REPAIRS_IRON_ARMOR);
    public static final ArmorMaterial ARMORED_BLACK_HOODIE = makeArmored("armored_black_hoodie", "black_hoodie", ItemTags.REPAIRS_IRON_ARMOR);
    public static final ArmorMaterial ARMORED_TRANS_THIGH_HIGHS = makeArmored("armored_trans_thigh_highs", "trans_thigh_highs", ItemTags.REPAIRS_IRON_ARMOR);
    public static final ArmorMaterial ARMORED_BLACK_TIGHTS = makeArmored("armored_black_tights", "black_tights", ItemTags.REPAIRS_IRON_ARMOR);

    // --- New armor pieces ---
    public static final ArmorMaterial WHITE_BEANIE = makeCosmetic("white_beanie", ItemTags.WOOL);
    public static final ArmorMaterial BLACK_BEANIE = makeCosmetic("black_beanie", ItemTags.WOOL);
    public static final ArmorMaterial WHITE_SNEAKERS = makeLight("white_sneakers", ItemTags.REPAIRS_LEATHER_ARMOR);
    public static final ArmorMaterial BLACK_SNEAKERS = makeLight("black_sneakers", ItemTags.REPAIRS_LEATHER_ARMOR);
    public static final ArmorMaterial COMBAT_BOOTS = makeHeavy("combat_boots", ItemTags.REPAIRS_IRON_ARMOR);
    public static final ArmorMaterial CARGO_PANTS = makeLight("cargo_pants", ItemTags.REPAIRS_LEATHER_ARMOR);

    // --- Helpers ---

    private static RegistryKey<EquipmentAsset> assetKey(String name) {
        return RegistryKey.of(
                RegistryKey.ofRegistry(Identifier.of("minecraft", "equipment_asset")),
                Identifier.of(HoodieMod.MOD_ID, name)
        );
    }

    private static ArmorMaterial makeCosmetic(String name, TagKey<net.minecraft.item.Item> repair) {
        return new ArmorMaterial(
                15,
                Map.of(
                        EquipmentType.HELMET, 0,
                        EquipmentType.CHESTPLATE, 0,
                        EquipmentType.LEGGINGS, 0,
                        EquipmentType.BOOTS, 0,
                        EquipmentType.BODY, 0
                ),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                0f, 0f,
                repair,
                assetKey(name)
        );
    }

    private static ArmorMaterial makeArmored(String name, String textureName, TagKey<net.minecraft.item.Item> repair) {
        return new ArmorMaterial(
                15,
                Map.of(
                        EquipmentType.HELMET, 2,
                        EquipmentType.CHESTPLATE, 4,
                        EquipmentType.LEGGINGS, 5,
                        EquipmentType.BOOTS, 2,
                        EquipmentType.BODY, 0
                ),
                12,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                0.5f, 0f,
                repair,
                assetKey(textureName)
        );
    }

    private static ArmorMaterial makeLight(String name, TagKey<net.minecraft.item.Item> repair) {
        return new ArmorMaterial(
                10,
                Map.of(
                        EquipmentType.HELMET, 1,
                        EquipmentType.CHESTPLATE, 3,
                        EquipmentType.LEGGINGS, 2,
                        EquipmentType.BOOTS, 1,
                        EquipmentType.BODY, 0
                ),
                15,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                0f, 0f,
                repair,
                assetKey(name)
        );
    }

    private static ArmorMaterial makeHeavy(String name, TagKey<net.minecraft.item.Item> repair) {
        return new ArmorMaterial(
                25,
                Map.of(
                        EquipmentType.HELMET, 2,
                        EquipmentType.CHESTPLATE, 6,
                        EquipmentType.LEGGINGS, 5,
                        EquipmentType.BOOTS, 2,
                        EquipmentType.BODY, 0
                ),
                9,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                0.5f, 0.05f,
                repair,
                assetKey(name)
        );
    }

    public static void register() {
        HoodieMod.LOGGER.info("Registering armor materials for " + HoodieMod.MOD_ID);
    }
}
