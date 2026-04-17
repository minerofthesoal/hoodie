package com.ray0ffire.hoodie.clientonly.mixin;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 1.21.8+ port of the client-side texture replacer.
 *
 * Replaces every chestplate render with the black-hoodie texture and every
 * leggings render with the black-tights texture. The render-state pipeline
 * means we no longer need the entity itself - the slot + stack passed to
 * renderArmor is enough to make the swap.
 */
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorTextureReplacerMixin<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>>
        extends FeatureRenderer<S, M> {

    @Shadow @Final private A outerModel;
    @Shadow @Final private A innerModel;

    @Shadow protected abstract void setVisible(A model, EquipmentSlot slot);

    @Unique
    private static final Identifier BLACK_HOODIE_TEXTURE =
            Identifier.of("hoodie-clientonly", "textures/models/armor/black_hoodie_layer_1.png");
    @Unique
    private static final Identifier BLACK_TIGHTS_TEXTURE =
            Identifier.of("hoodie-clientonly", "textures/models/armor/black_tights_layer_2.png");

    public ArmorTextureReplacerMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @Inject(method = "renderArmor",
            at = @At("HEAD"),
            cancellable = true)
    private void hoodieClient$replaceArmorTexture(
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            ItemStack stack, EquipmentSlot slot, int light, A model,
            CallbackInfo ci
    ) {
        if (stack.isEmpty()) return;

        Identifier replacementTexture;
        A replacementModel;

        if (slot == EquipmentSlot.CHEST) {
            replacementTexture = BLACK_HOODIE_TEXTURE;
            replacementModel = outerModel;
        } else if (slot == EquipmentSlot.LEGS) {
            replacementTexture = BLACK_TIGHTS_TEXTURE;
            replacementModel = innerModel;
        } else {
            return;
        }

        ((BipedEntityModel<S>) this.getContextModel()).copyTransforms(replacementModel);
        this.setVisible(replacementModel, slot);

        VertexConsumer consumer = vertexConsumers.getBuffer(
                RenderLayer.getArmorCutoutNoCull(replacementTexture)
        );
        replacementModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, -1);

        ci.cancel();
    }
}
