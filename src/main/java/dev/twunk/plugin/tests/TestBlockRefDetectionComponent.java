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
import dev.twunk.component.IAutoBlockLifetimeComponent;
import dev.twunk.utils.TwunkLib;
import dev.twunk.utils.world.Utils;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestBlockRefDetectionComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestBlockRefDetectionComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestBlockRefDetectionComponent> CODEC = BuilderCodec.builder(
        TestBlockRefDetectionComponent.class,
        TestBlockRefDetectionComponent::new
    ).build();

    public TestBlockRefDetectionComponent() {}

    @Nullable
    public TestBlockRefDetectionComponent clone() {
        return new TestBlockRefDetectionComponent();
    }

    @Nonnull
    @SuppressWarnings("null")
    public static final ComponentType<ChunkStore, TestBlockRefDetectionComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestBlockRefDetectionComponent.class);
        }

        return COMPONENT_TYPE;
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
            console.log("Added TEST_BlockRefDetection block");
        }
        var worldChunk = Utils.Chunk.WorldChunk_.get(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = Utils.BlockCoords.Global.get(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }

        var res = Utils.Block.testRefDetection(ref);

        if (verbose != null) {
            console.log("Ran " + res.size() + " test(s)");
        }
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            // every 2nd test for this one are meant to be "true"
            if (test == (i % 2 == 0)) {
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

        if (verbose == null) {
            if (success == res.size()) {
                console.log("+ (4) SUCCESS: TEST_BlockRefDetection");
            } else {
                console.log("- (4) FAILED:  TEST_BlockRefDetection");
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
