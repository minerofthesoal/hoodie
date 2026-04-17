package com.ray0ffire.hoodie.mixin;

import org.spongepowered.asm.mixin.Mixin;

/**
 * Stub for 1.21.8+. The vanilla ArmorFeatureRenderer.render signature changed
 * substantially in 1.21.4 (new EntityRenderState pipeline), so the old hood-up
 * injector no longer applies. This stub keeps the mixin entry compilable; the
 * 1.21.8 overlay drops it from hoodie.mixins.json so it isn't loaded.
 */
@Mixin(net.minecraft.client.render.entity.feature.ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
}
