package com.ray0ffire.hoodie.mixin;

import org.spongepowered.asm.mixin.Mixin;

/**
 * 1.21.11 stub.
 *
 * The hood-up rain effect lives in versions/1.21.8 and below; 1.21.11
 * rebuilt the armor render pipeline around an OrderedRenderCommandQueue
 * plus EquipmentModelData and removed the outerModel/innerModel/setVisible
 * fields the old injector relied on. The mixins.json overlay drops this
 * entry on 1.21.11 so nothing is loaded at runtime - this stub exists
 * only so the package compiles.
 */
@Mixin(net.minecraft.client.render.entity.feature.ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
}
