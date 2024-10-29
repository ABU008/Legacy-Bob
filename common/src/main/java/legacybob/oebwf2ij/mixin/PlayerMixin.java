package legacybob.oebwf2ij.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import legacybob.oebwf2ij.Bobbing.LegacyBobbing;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements LegacyBobbing {
    @Unique
    float legacybob$prevYBob;
    @Unique
    float legacybob$yBob;

    @Unique
    public float legacybob$prevYBob() {
        return legacybob$prevYBob;
    }

    @Unique
    public void legacybob$setPrevYBob(float bob) {
        legacybob$prevYBob = bob;
    }

    @Unique
    public float legacybob$yBob() {
        return legacybob$yBob;
    }

    @Unique
    public void legacybob$setYBob(float bob) {
        legacybob$yBob = bob;
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;bob:F", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    public void aiStep(CallbackInfo ci) {
        this.legacybob$setPrevYBob(this.legacybob$yBob());

        double deltaY = this.getDeltaMovement().y;
        float rotation = (float) (Math.atan(-deltaY * 0.20000000298023224D) * 15.0D);
        boolean isGrounded = deltaY < -0.07 && deltaY > -0.08 && !this.getBlockStateOn().isAir();

        if (this.onGround() || this.getHealth() <= 0.0F || isGrounded)
            rotation = 0.0F;

        float current = this.legacybob$yBob();

        this.legacybob$setYBob(current + ((rotation - current) * 0.8F));
    }
}
