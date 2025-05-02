package legacybob.oebwf2ij.mixin.Sodium;

import com.google.common.collect.ImmutableList;
import legacybob.oebwf2ij.Legacybob;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.binding.compat.VanillaBooleanOptionBinding;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {
    @Shadow(remap = false)
    @Final
    private static final MinecraftOptionsStorage vanillaOpts = new MinecraftOptionsStorage();

    @Inject(method = "general", at = @At("RETURN"), remap = false, cancellable = true)
    private static void addOption(CallbackInfoReturnable<OptionPage> cir) {
        System.out.println("Injected into 'general' method!");
        List<OptionGroup> groups = new ArrayList<>(cir.getReturnValue().getGroups());

        if (groups.size() > 2) {
            OptionGroup group = groups.get(2);
            List<Option<?>> options = new ArrayList<>(group.getOptions());

            options.add(1, OptionImpl.createBuilder(Boolean.TYPE, vanillaOpts)
                    .setName(Component.translatable("legacybobbing.options.legacybob"))
                    .setTooltip(Component.translatable("legacybobbing.options.legacybob.tooltip"))
                    .setControl(TickBoxControl::new)
                    .setBinding(new VanillaBooleanOptionBinding(Legacybob.getHandBob()))
                    .build());

            OptionGroup.Builder builder = OptionGroup.createBuilder();
            options.forEach(builder::add);
            groups.set(2, builder.build());
        }

        cir.setReturnValue(new OptionPage(Component.translatable("stat.generalButton"), ImmutableList.copyOf(groups)));
    }
}
