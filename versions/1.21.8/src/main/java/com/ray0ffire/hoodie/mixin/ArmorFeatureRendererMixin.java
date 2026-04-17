package com.ray0ffire.hoodie.mixin;

import com.ray0ffire.hoodie.ModItems;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>>
        extends FeatureRenderer<S, M> {

    @Shadow @Final private A outerModel;

    @Unique
    private static final Identifier WHITE_HOOD_UP = Identifier.of("hoodie", "textures/models/armor/white_hoodie_hood_up_layer_1.png");
    @Unique
    private static final Identifier BLACK_HOOD_UP = Identifier.of("hoodie", "textures/models/armor/black_hoodie_hood_up_layer_1.png");

    public ArmorFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            at = @At("RETURN"))
    private void hoodie$renderHoodInRain(
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, S state, float limbAngle, float limbDistance,
            CallbackInfo ci
    ) {
        ItemStack chestStack = state.equippedChestStack;
        if (chestStack == null || chestStack.isEmpty()) return;

        Item chestItem = chestStack.getItem();
        Identifier hoodTexture = hoodie$getHoodTexture(chestItem);
        if (hoodTexture == null) return;

        // Render-state has no entity reference; pull rain/sky info from the
        // client world, sampled at the render-state position.
        World world = MinecraftClient.getInstance().world;
        if (world == null || !world.isRaining()) return;
        BlockPos pos = BlockPos.ofFloored(state.x, state.y, state.z);
        if (!world.isSkyVisible(pos)) return;

        ((BipedEntityModel<S>) this.getContextModel()).copyTransforms(outerModel);

        outerModel.head.visible = true;
        outerModel.hat.visible = true;
        outerModel.body.visible = false;
        outerModel.rightArm.visible = false;
        outerModel.leftArm.visible = false;
        outerModel.rightLeg.visible = false;
        outerModel.leftLeg.visible = false;

        VertexConsumer consumer = vertexConsumers.getBuffer(
                RenderLayer.getArmorCutoutNoCull(hoodTexture)
        );
        outerModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, -1);
    }

    @Unique
    private static Identifier hoodie$getHoodTexture(Item item) {
        if (item == ModItems.WHITE_HOODIE || item == ModItems.ARMORED_WHITE_HOODIE) {
            return WHITE_HOOD_UP;
        }
        if (item == ModItems.BLACK_HOODIE || item == ModItems.ARMORED_BLACK_HOODIE) {
            return BLACK_HOOD_UP;
        }
        return null;
    }
}
