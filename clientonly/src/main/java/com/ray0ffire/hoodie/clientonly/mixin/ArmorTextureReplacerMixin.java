package com.ray0ffire.hoodie.clientonly.mixin;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
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
 * Client-side only mixin that replaces:
 * - All chestplate rendering with the black hoodie texture
 * - All legging rendering with the black tights texture
 */
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorTextureReplacerMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
        extends FeatureRenderer<T, M> {

    @Shadow @Final private A outerModel;
    @Shadow @Final private A innerModel;

    @Shadow protected abstract void setVisible(A model, EquipmentSlot slot);

    @Unique
    private static final Identifier BLACK_HOODIE_TEXTURE =
            Identifier.of("hoodie-clientonly", "textures/models/armor/black_hoodie_layer_1.png");
    @Unique
    private static final Identifier BLACK_TIGHTS_TEXTURE =
            Identifier.of("hoodie-clientonly", "textures/models/armor/black_tights_layer_2.png");

    public ArmorTextureReplacerMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmor",
            at = @At("HEAD"),
            cancellable = true)
    private void hoodieClient$replaceArmorTexture(
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            T entity, EquipmentSlot slot, int light, A model,
            CallbackInfo ci
    ) {
        ItemStack stack = entity.getEquippedStack(slot);
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armorItem)) return;
        if (armorItem.getSlotType() != slot) return;

        Identifier replacementTexture;
        A replacementModel;

        if (slot == EquipmentSlot.CHEST) {
            replacementTexture = BLACK_HOODIE_TEXTURE;
            replacementModel = outerModel;
        } else if (slot == EquipmentSlot.LEGS) {
            replacementTexture = BLACK_TIGHTS_TEXTURE;
            replacementModel = innerModel;
        } else {
            return; // Don't replace helmet or boots
        }

        // Copy pose from context model
        ((BipedEntityModel<T>) this.getContextModel()).copyBipedStateTo(replacementModel);
        this.setVisible(replacementModel, slot);

        // Render with our replacement texture
        VertexConsumer consumer = vertexConsumers.getBuffer(
                RenderLayer.getArmorCutoutNoCull(replacementTexture)
        );
        replacementModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, -1);

        ci.cancel(); // Cancel vanilla rendering for this slot
    }
}
