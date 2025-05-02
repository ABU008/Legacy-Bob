package legacybob.oebwf2ij.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import legacybob.oebwf2ij.Bobbing.LegacyBobbing;

@Mixin(Frustum.class)
public class FrustumMixin {

    @Final
    @Shadow
    private Matrix4f matrix;

    @Unique
    private final Camera legacybob$mainCamera = new Camera();

    @ModifyExpressionValue(method = "calculateFrustum", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;mul(Lorg/joml/Matrix4fc;Lorg/joml/Matrix4f;)Lorg/joml/Matrix4f;", remap = false))
    private Matrix4f calculateFrustum(Matrix4f original, Matrix4f right, Matrix4f matrix4f2) {
        float angle = LegacyBobbing.getAngle(Minecraft.getInstance(), this.legacybob$mainCamera.getPartialTickTime());
        return angle != 0 ? legacybob$adjustByBobbing(matrix4f2, angle).mul(right, matrix) : original;
    }

    @Unique
    private Matrix4f legacybob$adjustByBobbing(Matrix4f projectionMatrix, float yBobAngle) {
        float mul = Math.max(0.65f, 1f - yBobAngle / 32);
        Matrix4f adjustedMatrix = new Matrix4f(projectionMatrix);
        adjustedMatrix.m00(adjustedMatrix.m00() * mul);
        adjustedMatrix.m11(adjustedMatrix.m11() * mul);
        return adjustedMatrix;
    }
}
