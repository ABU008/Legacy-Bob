package legacybob.oebwf2ij.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import legacybob.oebwf2ij.Bobbing.LegacyBobbing;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements LegacyBobbing {
    float $prevYBob;
    float $yBob;

    @Shadow public abstract Abilities getAbilities();

    @Override
    public float $prevYBob() {
        return $prevYBob;
    }

    @Override
    public void $setPrevYBob(float bob) {
        $prevYBob = bob;
    }

    @Override
    public float $yBob() {
        return $yBob;
    }

    @Override
    public void $setYBob(float bob) {
        $yBob = bob;
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;bob:F", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    public void aiStep(CallbackInfo ci) {
        yBobbing();
    }
}
