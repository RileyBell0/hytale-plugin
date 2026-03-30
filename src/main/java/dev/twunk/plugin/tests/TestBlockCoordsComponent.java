package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.refs.AnyRef;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.interfaces.component.ILifetimeComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Serializable
public class TestBlockCoordsComponent implements ILifetimeComponent<ChunkStore> {

    @Override
    public void onEntityAdded(
        @Nonnull AnyRef<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        final var verbose = false;
        runTests(ref, reason, ref.getStore(), commandBuffer, verbose);
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
            Chat.log("Added TEST_BlockCoords block");
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

        BlockUtils.Coords.Local.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (8) SUCCESS: TEST_BlockCoords-local");
        BlockUtils.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (9) SUCCESS: TEST_BlockCoords-index");
        BlockUtils.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (10) SUCCESS: TEST_BlockCoords-global");
    }

    @Override
    @Nullable
    public TestBlockCoordsComponent clone() {
        return new TestBlockCoordsComponent();
    }
}
