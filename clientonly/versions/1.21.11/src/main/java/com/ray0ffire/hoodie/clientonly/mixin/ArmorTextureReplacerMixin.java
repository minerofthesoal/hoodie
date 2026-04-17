package com.ray0ffire.hoodie.clientonly.mixin;

import org.spongepowered.asm.mixin.Mixin;

/**
 * 1.21.11 stub. Same story as the main-mod ArmorFeatureRendererMixin: the
 * outerModel/innerModel/setVisible API used to swap chest+legs textures
 * on every entity is gone in 1.21.11 in favor of EquipmentModelData and
 * an OrderedRenderCommandQueue. The 1.21.11 mixins.json overlay
 * disables this mixin so nothing loads at runtime - this stub only
 * exists so the package compiles.
 */
@Mixin(net.minecraft.client.render.entity.feature.ArmorFeatureRenderer.class)
public abstract class ArmorTextureReplacerMixin {
}
