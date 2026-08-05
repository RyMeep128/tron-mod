package com.ryanm.tronmod.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryanm.tronmod.item.IdentityDiscItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public final class IdentityDiscClientExtensions implements IClientItemExtensions {
    @Override
    public boolean applyForgeHandTransform(
            PoseStack poseStack,
            LocalPlayer player,
            HumanoidArm arm,
            ItemStack itemInHand,
            float partialTick,
            float equipProcess,
            float swingProcess
    ) {
        HumanoidArm usingArm = player.getUsedItemHand() == InteractionHand.MAIN_HAND
                ? player.getMainArm()
                : player.getMainArm().getOpposite();
        if (!player.isUsingItem() || player.getUseItemRemainingTicks() <= 0 || usingArm != arm) {
            return false;
        }

        int side = arm == HumanoidArm.RIGHT ? 1 : -1;
        float charge = IdentityDiscItem.getChargeProgress(player.getTicksUsingItem() + partialTick);
        float ease = charge * charge * (3.0F - 2.0F * charge);

        // Bring the disc in from the outer hip and cock it beside the camera for a side throw.
        poseStack.translate(side * (0.72F - 0.16F * ease), -0.42F - equipProcess * 0.6F + 0.08F * ease, -0.78F + 0.12F * ease);
        poseStack.mulPose(Axis.YP.rotationDegrees(side * (72.0F - 24.0F * ease)));
        poseStack.mulPose(Axis.XP.rotationDegrees(-12.0F + 18.0F * ease));
        poseStack.mulPose(Axis.ZP.rotationDegrees(side * (-52.0F + 78.0F * ease)));
        if (charge >= 1.0F) {
            float pulse = Mth.sin((player.tickCount + partialTick) * 0.8F) * 0.015F;
            poseStack.translate(side * pulse, -pulse, 0.0F);
        }
        return true;
    }
}
