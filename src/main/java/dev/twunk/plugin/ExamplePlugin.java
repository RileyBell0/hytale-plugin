package dev.twunk.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import javax.annotation.Nonnull;

public class ExamplePlugin extends ModPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register components
        this.registerComponent(ExampleTickingBlockComponent.CODEC);

        // Register systems
        new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
