package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.hytale.codec.auto.Serializable;
import dev.twunk.hytale.interfaces.event.IOnAddRemove;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import javax.annotation.Nullable;

@Serializable
public class TestChunkRefComponent implements Component<ChunkStore>, IOnAddRemove<ChunkStore> {

    @Override
    public void onEntityAdded(Ref<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        final var verbose = false;
        runTests(ref, commandBuffer, verbose);
    }

    public static final void runTests(
        Ref<ChunkStore> ref,
        CommandBuffer<ChunkStore> commandBuffer,
        @Nullable Boolean verbose
    ) {
        if (verbose != null) {
            Chat.log("");
            Chat.log("Added TEST_ChunkRef block");
        }
        var worldChunk = ChunkUtils.WorldChunks.get(ref);
        if (worldChunk == null) {
            Chat.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = BlockUtils.Coords.Global.get(ref);
        if (coords == null) {
            Chat.log("ERROR: coords was null!!!");
            return;
        }
        var res = ChunkUtils.Refs.test(commandBuffer, coords);

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
        var allAreChunkRefs = true;
        for (var i = 0; i < res.size(); i++) {
            var curr = res.get(i);
            if (curr == null || !curr.isValid() || !BlockUtils.isChunkRef(curr)) {
                allAreChunkRefs = false;
                if (verbose != null && verbose) {
                    Chat.log("  - " + i + " is not a valid chunk ref!!!");
                    if (curr == null) {
                        Chat.log("    - null");
                    } else if (!curr.isValid()) {
                        Chat.log("    - not valid");
                    }

                    if (curr != null && curr.isValid() && BlockUtils.isChunkRef(curr)) {
                        Chat.log("    + it is a chunk ref");
                    }
                    if (curr != null && curr.isValid() && BlockUtils.isBlockRef(curr)) {
                        Chat.log("    - heck, its a block ref???");
                    }
                    Chat.log("    - " + curr);
                } else {
                    break;
                }
            }
        }

        if (verbose != null) {
            if (!allAreChunkRefs) {
                Chat.log("- WARNING: not all refs are chunk refs");
            } else {
                Chat.log("+ All are chunk refs");
            }
        }

        if (verbose == null) {
            if (success == res.size() && allTheSame && allAreChunkRefs) {
                Chat.log("+ (6) SUCCESS: TEST_ChunkRef");
            } else {
                Chat.log("- (6) FAILED:  TEST_ChunkRef");
            }
        }
    }

    @Override
    public TestChunkRefComponent clone() {
        return new TestChunkRefComponent();
    }
}
