package legacybob.oebwf2ij.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import org.joml.Matrix4f;
import org.joml.FrustumIntersection;
import org.spongepowered.asm.mixin.*;

@Mixin(Frustum.class)
public class FrustumMixin {

    @Final
    @Shadow
    private FrustumIntersection intersection;

    @Final
    @Shadow
    private Matrix4f matrix;
    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    public void calculateFrustum(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        Matrix4f adjustedProjectionMatrix = adjustProjectionFOV(projectionMatrix);

        adjustedProjectionMatrix.mul(viewMatrix, this.matrix);

        this.intersection.set(this.matrix);
    }

    private Matrix4f adjustProjectionFOV(Matrix4f projectionMatrix) {
        Matrix4f adjustedMatrix = new Matrix4f(projectionMatrix);

        // Decrease values on diagonal to simulate an increased FOV in frustum
        adjustedMatrix.m00(adjustedMatrix.m00() * 0.65f);  // Adjust horizontal FOV (X-axis)
        adjustedMatrix.m11(adjustedMatrix.m11() * 0.65f);  // Adjust vertical FOV (Y-axis)

        return adjustedMatrix;
    }
}
