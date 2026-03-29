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
import javax.annotation.Nonnull;

public class Plugin extends HytalePlugin {

    public Plugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register components
        this.registerChunkComponent(TestBlockRefComponent.class);
        this.registerChunkComponent(TestBlockInfoComponent.class);
        this.registerChunkComponent(TestBlockIdComponent.class);
        this.registerChunkComponent(TestBlockTypeComponent.class);
        this.registerChunkComponent(TestBlockRefDetectionComponent.class);
        this.registerChunkComponent(TestWorldChunkComponent.class);
        this.registerChunkComponent(TestChunkRefComponent.class);
        this.registerChunkComponent(TestComponent_Component.class);
        this.registerChunkComponent(TestBlockCoordsComponent.class);
        this.registerChunkComponent(TestChunkCoordsComponent.class);
        this.registerChunkComponent(TestAllComponent.class);

        // Register interactions
        this.registerInteraction(TestAllInteraction.class);

        // Register systems
        // new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
