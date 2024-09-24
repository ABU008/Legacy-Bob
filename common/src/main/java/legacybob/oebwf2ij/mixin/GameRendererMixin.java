package legacybob.oebwf2ij.mixin;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
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
    Minecraft minecraft;
    @Inject(method = "bobView", at = @At("RETURN"))
    private void bobView(PoseStack poseStack, float partialTick, CallbackInfo ci) {
        if (this.minecraft.getCameraEntity() instanceof LegacyBobbing p) {
            LegacyBobbing legacyBobbing = (LegacyBobbing) p;
            float angle = Mth.lerp(partialTick, legacyBobbing.$prevYBob(), legacyBobbing.$yBob());

            poseStack.mulPose(Axis.XP.rotationDegrees(angle));
        }
    }
}
