package dev.twunk.plugin;

import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.ticking.component.system.SmartTickSystem;
import javax.annotation.Nonnull;

// Unused. Though, that's as simple as it gets
public class ExampleSmartTickingSystem extends SmartTickSystem {
    @Nonnull
    private static final Query<ChunkStore> QUERY = Query.and(ExampleTickingBlockComponent.getComponentType());

    public ExampleSmartTickingSystem(@Nonnull String id) {
        super(id);
    }

    @Override
    public Query<ChunkStore> getQuery() {
        return QUERY;
    }
}
