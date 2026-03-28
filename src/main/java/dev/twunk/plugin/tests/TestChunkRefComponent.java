package dev.twunk.plugin.tests;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.HytaleComponent;
import dev.twunk.hytale.LibHytale;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.interfaces.component.ILifetimeComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@HytaleComponent
public class TestChunkRefComponent implements ILifetimeComponent<ChunkStore> {

    @Nullable
    private static ComponentType<ChunkStore, TestChunkRefComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestChunkRefComponent> CODEC = BuilderCodec.builder(
        TestChunkRefComponent.class,
        TestChunkRefComponent::new
    ).build();

    public TestChunkRefComponent() {}

    @Override
    @Nullable
    public TestChunkRefComponent clone() {
        return new TestChunkRefComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestChunkRefComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = LibHytale.getChunkComponentType(TestChunkRefComponent.class);
            return COMPONENT_TYPE;
        } else {
            return COMPONENT_TYPE;
        }
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
            Chat.log("Added TEST_ChunkRef block");
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
        var res = ChunkUtils.Ref_.test(ref, worldChunk, commandBuffer, coords);

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
    public void onEntityAdded(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        final var verbose = false;
        runTests(ref, reason, store, commandBuffer, verbose);
    }

    @Override
    public void onEntityRemove(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull RemoveReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {}
}
