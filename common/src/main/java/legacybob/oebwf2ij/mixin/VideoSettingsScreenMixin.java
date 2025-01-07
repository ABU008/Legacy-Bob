package legacybob.oebwf2ij.mixin;

import legacybob.oebwf2ij.Legacybob;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VideoSettingsScreen.class)
public class VideoSettingsScreenMixin {
    @Inject(method = "options", at = @At("RETURN"), cancellable = true)
    private static void getOptions(Options options, CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] values = cir.getReturnValue();
        cir.setReturnValue(ArrayUtils.insert(9, values, Legacybob.legacyBob));
    }
}