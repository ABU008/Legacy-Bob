package legacybob.oebwf2ij.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import org.joml.Matrix4f;
import org.joml.FrustumIntersection;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.*;

@Mixin(Frustum.class)
public class FrustumMixin {

    @Final
    @Shadow
    private FrustumIntersection intersection;

    @Final
    @Shadow
    private Matrix4f matrix;
    @Shadow
    private Vector4f viewVector;
    /**
     * @author ABU008
     * @reason To fix culling issues when falling
     */
    @Overwrite
    public void calculateFrustum(Matrix4f viewMatrix, Matrix4f projectionMatrix) {

        Matrix4f adjustedProjectionMatrix = adjustProjectionFOV(projectionMatrix);

        adjustedProjectionMatrix.mul(viewMatrix, this.matrix);

        this.intersection.set(this.matrix);

        this.viewVector = new Vector4f(0.0F, 0.0F, 1.0F, 0.0F);
        this.viewVector = this.matrix.transformTranspose(this.viewVector);
    }

    @Unique
    private Matrix4f adjustProjectionFOV(Matrix4f projectionMatrix) {
        Matrix4f adjustedMatrix = new Matrix4f(projectionMatrix);

        adjustedMatrix.m00(adjustedMatrix.m00() * 0.65f);
        adjustedMatrix.m11(adjustedMatrix.m11() * 0.65f);

        return adjustedMatrix;
    }
}
