package legacybob.oebwf2ij.Bobbing;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public interface LegacyBobbing {
    float $yBob();
    float $prevYBob();
    void $setYBob(float bob);
    void $setPrevYBob(float bob);
    default float getAngle(float partialTicks){
        return Mth.lerp(partialTicks, $prevYBob(), $yBob());
    }
    default void yBobbing(){
        if (this instanceof Player p){
            $setPrevYBob($yBob());
            $setYBob($yBob() + ((!p.onGround() && !p.isDeadOrDying() ? (float) Math.atan(-p.getDeltaMovement().y * 0.2D) * 15.0F : 0) - $yBob()) * 0.8F);
        }
    }
}
