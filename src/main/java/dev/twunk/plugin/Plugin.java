package dev.twunk.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.twunk.hytale.HytalePlugin;
import dev.twunk.plugin.tests.TestAllComponent;
import dev.twunk.plugin.tests.TestAllInteraction;
import dev.twunk.plugin.tests.TestBlockCoordsComponent;
import dev.twunk.plugin.tests.TestBlockIdComponent;
import dev.twunk.plugin.tests.TestBlockInfoComponent;
import dev.twunk.plugin.tests.TestBlockRefComponent;
import dev.twunk.plugin.tests.TestBlockRefDetectionComponent;
import dev.twunk.plugin.tests.TestBlockTypeComponent;
import dev.twunk.plugin.tests.TestChunkCoordsComponent;
import dev.twunk.plugin.tests.TestChunkRefComponent;
import dev.twunk.plugin.tests.TestComponent_Component;
import dev.twunk.plugin.tests.TestWorldChunkComponent;

public class Plugin extends HytalePlugin {

    public Plugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register components
        this.register(TestBlockRefComponent.class);
        this.register(TestBlockInfoComponent.class);
        this.register(TestBlockIdComponent.class);
        this.register(TestBlockTypeComponent.class);
        this.register(TestBlockRefDetectionComponent.class);
        this.register(TestWorldChunkComponent.class);
        this.register(TestChunkRefComponent.class);
        this.register(TestComponent_Component.class);
        this.register(TestBlockCoordsComponent.class);
        this.register(TestChunkCoordsComponent.class);
        this.register(TestAllComponent.class);

        // Register interactions
        this.register(TestAllInteraction.class);

        // Register systems
        // new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
