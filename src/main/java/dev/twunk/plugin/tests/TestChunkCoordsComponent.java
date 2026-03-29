package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.interfaces.component.ILifetimeComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Serializable
public class TestChunkCoordsComponent implements ILifetimeComponent<ChunkStore> {

    @Override
    public void onEntityAdded(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        final var verbose = false;
        runTests(ref, reason, store, commandBuffer, verbose);
    }

    public static final void runTests(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer,
        Boolean verbose
    ) {
        if (verbose != null) {
            Chat.log("");
            Chat.log("Added TEST_ChunkCoords block");
        }

        var worldChunk = ChunkUtils.WorldChunk_.get(ref);
        if (worldChunk == null) {
            Chat.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = BlockUtils.Coords.Global.get(ref);
        if (coords == null) {
            Chat.log("ERROR: coords was null!!!");
            return;
        }

        ChunkUtils.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (11) SUCCESS: TEST_ChunkCoords-index");
        ChunkUtils.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (12) SUCCESS: TEST_ChunkCoords-global");
    }

    @Override
    @Nullable
    public TestChunkCoordsComponent clone() {
        return new TestChunkCoordsComponent();
    }
}
