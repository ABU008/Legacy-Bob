package legacybob.oebwf2ij;

import legacybob.oebwf2ij.Config.Config;
import net.minecraft.client.OptionInstance;

public final class Legacybob {
    public static final String MOD_ID = "legacybob";

    public static final Config config = new Config();
    public static final OptionInstance<Boolean> legacyBob = OptionInstance.createBoolean("legacybobbing.options.legacybob", config.getLegacyBob(), (bool) -> {
        config.setLegacyBob(bool);
        config.save();
    });

    public static void init() {
        // Write common init code here.
    }

    public static OptionInstance<Boolean> getHandBob() {
        return legacyBob;
    }
}
