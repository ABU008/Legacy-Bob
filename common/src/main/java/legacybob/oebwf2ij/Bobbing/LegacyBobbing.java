package legacybob.oebwf2ij.Bobbing;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public interface LegacyBobbing {
    float legacybob$yBob();
    float legacybob$prevYBob();
    void legacybob$setYBob(float bob);
    void legacybob$setPrevYBob(float bob);

    default float getAngle(float partialTicks){
        return Mth.lerp(partialTicks,legacybob$prevYBob(), legacybob$yBob());
    }

    default void handleYBobbing(){
        if (this instanceof Player p){
            legacybob$setPrevYBob(legacybob$yBob());
            legacybob$setYBob(legacybob$yBob() + ((!p.onGround() && !p.isDeadOrDying() ? (float) Math.atan(-p.getDeltaMovement().y * 0.2D) * 15.0F : 0) - legacybob$yBob()) * 0.8F);
        }
    }

    static float getAngle(Minecraft minecraft, float partialTicks){
        return minecraft.getCameraEntity() instanceof LegacyBobbing p ? p.getAngle(partialTicks) : 0;
    }
}
