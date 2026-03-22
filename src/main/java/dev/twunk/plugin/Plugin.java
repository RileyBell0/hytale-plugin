package dev.twunk.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
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

public class Plugin extends ModPlugin {

    public Plugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register components
        this.registerChunkComponent(TestBlockRefComponent.CODEC);
        this.registerChunkComponent(TestBlockInfoComponent.CODEC);
        this.registerChunkComponent(TestBlockIdComponent.CODEC);
        this.registerChunkComponent(TestBlockTypeComponent.CODEC);
        this.registerChunkComponent(TestBlockRefDetectionComponent.CODEC);
        this.registerChunkComponent(TestWorldChunkComponent.CODEC);
        this.registerChunkComponent(TestChunkRefComponent.CODEC);
        this.registerChunkComponent(TestComponent_Component.CODEC);
        this.registerChunkComponent(TestBlockCoordsComponent.CODEC);
        this.registerChunkComponent(TestChunkCoordsComponent.CODEC);
        this.registerChunkComponent(TestAllComponent.CODEC);

        // Register interactions
        this.registerInteraction(TestAllInteraction.CODEC);

        // Register systems
        // new ExampleScheduledTickSystem("RileysExampleSystemID").registerTo(this);
    }
}
