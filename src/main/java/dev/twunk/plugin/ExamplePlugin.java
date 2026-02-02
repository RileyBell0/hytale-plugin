package dev.twunk.plugin;

import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.twunk.interfaces.ModPlugin;
import dev.twunk.ticking.component.system.TickingBlockComponent_Initialiser;
import dev.twunk.ticking.component.system.TickingBlockComponent_System;
import javax.annotation.Nonnull;

public class ExamplePlugin extends ModPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        var componentType = this.registerComponent(ExampleTickingBlockComponent.CODEC);

        var initialiser = new TickingBlockComponent_Initialiser(
                Query.and(BlockModule.BlockStateInfo.getComponentType(), componentType));
        this.getChunkStoreRegistry().registerSystem(initialiser);

        var system = new TickingBlockComponent_System<ExampleTickingBlockComponent>(componentType);
        this.getChunkStoreRegistry().registerSystem(system);
    }
}
