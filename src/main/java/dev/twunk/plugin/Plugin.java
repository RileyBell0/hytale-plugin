package dev.twunk.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.twunk.plugin.tests.TestBlockIdComponent;
import dev.twunk.plugin.tests.TestBlockInfoComponent;
import dev.twunk.plugin.tests.TestBlockRefComponent;
import dev.twunk.plugin.tests.TestBlockTypeComponent;
import javax.annotation.Nonnull;

public class Plugin extends ModPlugin {

    public Plugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register components
        this.registerComponent(TestBlockRefComponent.CODEC);
        this.registerComponent(TestBlockInfoComponent.CODEC);
        this.registerComponent(TestBlockIdComponent.CODEC);
        this.registerComponent(TestBlockTypeComponent.CODEC);

        // Register systems
        // new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
