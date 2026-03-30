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
public class TestWorldChunkComponent implements ILifetimeComponent<ChunkStore> {

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
            Chat.log("Added TEST_WorldChunk block");
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
        var res = ChunkUtils.WorldChunk_.test(ref, worldChunk, commandBuffer, coords);

        if (verbose != null) {
            Chat.log("Ran " + res.size() + " test(s)");
        }
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            if (test != null) {
                success += 1;
            }
            if (verbose != null && verbose) {
                Chat.log(" - " + i + ") " + test);
            }
        }
        if (verbose != null) {
            Chat.log("" + success + "/" + res.size() + " tests successful");
            for (var i = 0; i < res.size(); i++) {
                var entry = res.get(i);
                if (entry == null) {
                    Chat.log(i + ") Failed test " + i + "!");
                }
            }
        }

        var allTheSame = true;
        for (var i = 1; i < res.size(); i++) {
            if (!res.get(i).equals(res.get(i - 1))) {
                allTheSame = false;
                break;
            }
        }

        if (verbose != null) {
            if (allTheSame) {
                Chat.log("+ All tests were the same");
            } else {
                Chat.log("- WARNING: Not all tests were the same");
            }
        }
        if (verbose == null) {
            if (success == res.size() && allTheSame) {
                Chat.log("+ (7) SUCCESS: TEST_WorldChunk");
            } else {
                Chat.log("- (7) FAILED:  TEST_WorldChunk");
            }
        }
    }

    @Nullable
    public TestWorldChunkComponent clone() {
        return new TestWorldChunkComponent();
    }
}
