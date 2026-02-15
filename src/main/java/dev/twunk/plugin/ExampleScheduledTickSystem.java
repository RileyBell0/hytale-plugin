package dev.twunk.plugin;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.subsystem.SubSystemOwner;
import dev.twunk.subsystem.composite.BlockTickSubSystem;
import dev.twunk.subsystem.composite.interfaces.IBlockTickSystem;
import javax.annotation.Nonnull;

// Unused. Though, that's as simple as it gets
public class ExampleScheduledTickSystem extends SubSystemOwner implements IBlockTickSystem {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nonnull
    private static final Query<ChunkStore> QUERY = Query.and(
        BlockModule.BlockStateInfo.getComponentType(),
        ExampleTickingBlockComponent.getComponentType()
    );

    // @Nonnull
    // private final String id;

    public ExampleScheduledTickSystem(@Nonnull String id) {
        super(QUERY);
        // this.id = id;

        this.appendSubSystem(new BlockTickSubSystem(this));
    }

    public void onBlockEntityTick(
        @Nonnull World world,
        @Nonnull WorldChunk wc,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer,
        @Nonnull Vector3i worldCoords,
        int blockId
    ) {
        console.log("Ticking entity at " + worldCoords);
    }

    // @Override
    // public String getId() {
    //     return this.id;
    // }

    public Query<ChunkStore> getQuery() {
        return ExampleScheduledTickSystem.QUERY;
    }
}
