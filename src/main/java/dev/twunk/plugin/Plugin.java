package dev.twunk.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.twunk.plugin.tests.TestBlockIdComponent;
import dev.twunk.plugin.tests.TestBlockInfoComponent;
import dev.twunk.plugin.tests.TestBlockRefComponent;
import dev.twunk.plugin.tests.TestBlockRefDetectionComponent;
import dev.twunk.plugin.tests.TestBlockTypeComponent;
import dev.twunk.plugin.tests.TestChunkRefComponent;
import dev.twunk.plugin.tests.TestWorldChunkComponent;
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
        this.registerComponent(TestBlockRefDetectionComponent.CODEC);
        this.registerComponent(TestWorldChunkComponent.CODEC);
        this.registerComponent(TestChunkRefComponent.CODEC);

        // Register systems
        // new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
