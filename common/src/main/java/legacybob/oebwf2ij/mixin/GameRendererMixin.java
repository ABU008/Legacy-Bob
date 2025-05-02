package legacybob.oebwf2ij.mixin;

import com.mojang.math.Axis;
import legacybob.oebwf2ij.Legacybob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import legacybob.oebwf2ij.Bobbing.LegacyBobbing;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements LegacyBobbing {

    @Final
    @Shadow
    private Minecraft minecraft;
    @Inject(method = "bobView", at = @At("RETURN"))
    private void bobView(PoseStack poseStack, float partialTick, CallbackInfo ci) {
        if (Legacybob.getHandBob().get()) {
            float angle = LegacyBobbing.getAngle(minecraft, partialTick);
            if (angle != 0) poseStack.mulPose(Axis.XP.rotationDegrees(angle));
        }
    }
}
