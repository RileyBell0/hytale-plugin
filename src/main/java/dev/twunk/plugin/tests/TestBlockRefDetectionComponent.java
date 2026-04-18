package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.refs.AnyRef;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.interfaces.events.IOnAddRemove;
import javax.annotation.Nullable;

@Serializable
public class TestBlockRefDetectionComponent implements Component<ChunkStore>, IOnAddRemove<ChunkStore> {

    @Override
    public void onEntityAdded(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        final var verbose = false;
        runTests(ref, reason, ref.getStore(), commandBuffer, verbose);
    }

    public static final void runTests(
        Ref<ChunkStore> ref,
        AddReason reason,
        Store<ChunkStore> store,
        CommandBuffer<ChunkStore> commandBuffer,
        @Nullable Boolean verbose
    ) {
        if (verbose != null) {
            Chat.log("");
            Chat.log("Added TEST_BlockRefDetection block");
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

        var res = BlockUtils.testRefDetection(ref);

        if (verbose != null) {
            Chat.log("Ran " + res.size() + " test(s)");
        }
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            // every 2nd test for this one are meant to be "true"
            if (test == (i % 2 == 0)) {
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

        if (verbose == null) {
            if (success == res.size()) {
                Chat.log("+ (4) SUCCESS: TEST_BlockRefDetection");
            } else {
                Chat.log("- (4) FAILED:  TEST_BlockRefDetection");
            }
        }
    }

    @Override
    public TestBlockRefDetectionComponent clone() {
        return new TestBlockRefDetectionComponent();
    }
}
