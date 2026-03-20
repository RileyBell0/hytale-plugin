package dev.twunk.plugin.tests;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.*;
import dev.twunk.interfaces.component.auto.IAutoBlockLifetimeComponent;
import dev.twunk.utils.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestChunkRefComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

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
            COMPONENT_TYPE = TwunkLib.getComponentType(TestChunkRefComponent.class);
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
            console.log("");
            console.log("Added TEST_ChunkRef block");
        }
        var worldChunk = ChunkUtils.WorldChunk_.get(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = BlockUtils.Coords.Global.get(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }
        var res = ChunkUtils.Ref_.test(ref, worldChunk, commandBuffer, coords);

        if (verbose != null) {
            console.log("Ran " + res.size() + " test(s)");
        }
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            if (test != null) {
                success += 1;
            }
            if (verbose != null && verbose) {
                console.log(" - " + i + ") " + test);
            }
        }
        if (verbose != null) {
            console.log("" + success + "/" + res.size() + " tests successful");
            for (var i = 0; i < res.size(); i++) {
                var entry = res.get(i);
                if (entry == null) {
                    console.log(i + ") Failed test " + i + "!");
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
                console.log("+ All tests were the same");
            } else {
                console.log("- WARNING: Not all tests were the same");
            }
        }
        var allAreChunkRefs = true;
        for (var i = 0; i < res.size(); i++) {
            var curr = res.get(i);
            if (curr == null || !curr.isValid() || !BlockUtils.isChunkRef(curr)) {
                allAreChunkRefs = false;
                if (verbose != null && verbose) {
                    console.log("  - " + i + " is not a valid chunk ref!!!");
                    if (curr == null) {
                        console.log("    - null");
                    } else if (!curr.isValid()) {
                        console.log("    - not valid");
                    }

                    if (curr != null && curr.isValid() && BlockUtils.isChunkRef(curr)) {
                        console.log("    + it is a chunk ref");
                    }
                    if (curr != null && curr.isValid() && BlockUtils.isBlockRef(curr)) {
                        console.log("    - heck, its a block ref???");
                    }
                    console.log("    - " + curr);
                } else {
                    break;
                }
            }
        }

        if (verbose != null) {
            if (!allAreChunkRefs) {
                console.log("- WARNING: not all refs are chunk refs");
            } else {
                console.log("+ All are chunk refs");
            }
        }

        if (verbose == null) {
            if (success == res.size() && allTheSame && allAreChunkRefs) {
                console.log("+ (6) SUCCESS: TEST_ChunkRef");
            } else {
                console.log("- (6) FAILED:  TEST_ChunkRef");
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
