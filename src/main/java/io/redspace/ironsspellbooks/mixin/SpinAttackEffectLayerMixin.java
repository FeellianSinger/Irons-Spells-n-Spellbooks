package io.redspace.ironsspellbooks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SpinAttackEffectLayer.class)
public class SpinAttackEffectLayerMixin {
    private static final ResourceLocation FIRE_TEXTURE = new ResourceLocation(IronsSpellbooks.MODID, "textures/entity/fire_riptide.png");
    private VertexConsumer texture = null;

    @ModifyVariable(method = "render", at = @At("STORE"))
    public VertexConsumer selectSpinAttackTexture(VertexConsumer original, PoseStack poseStack, MultiBufferSource buffer, int p_117528_, LivingEntity livingEntity, float f1, float f2, float f3, float f4, float f5, float f6) {
        //will need more refining if there become more ways to spin attack
        //Item usedItem = livingEntity.getItemInHand(livingEntity.getUsedItemHand()).getItem();
        switch (ClientMagicData.getSyncedSpellData(livingEntity).getSpinAttackType()) {
            case FIRE:
                return buffer.getBuffer(RenderType.entityCutoutNoCull(FIRE_TEXTURE));
            default:
                return original;
        }
    }
}