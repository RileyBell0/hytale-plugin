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

    @Override
    public void onEntityAdded(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        final var verbose = false;

        console.log("Added TEST_BlockRefDetection block");
        var worldChunk = Utils.Chunk.WorldChunk_.getWorldChunk(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = Utils.BlockCoords.Global.getGlobalCoords(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }

        var res = Utils.Block.testRefDetection(ref);

        console.log("Ran " + res.size() + " test(s)");
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            if (test != null) {
                success += 1;
            }
            if (verbose) {
                console.log(" - " + i + ") " + test);
            }
        }
        console.log("" + success + "/" + res.size() + " tests successful");
        for (var i = 0; i < res.size(); i++) {
            var entry = res.get(i);
            if (entry == null) {
                console.log(i + ") Failed test " + i + "!");
            }
        }

        var allTheSame = true;
        for (var i = 1; i < res.size(); i++) {
            if (!res.get(i).equals(res.get(i - 1))) {
                allTheSame = false;
                break;
            }
        }

        if (allTheSame) {
            console.log("+ All tests were the same");
        } else {
            console.log("- WARNING: Not all tests were the same");
        }
    }

    @Override
    public void onEntityRemove(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull RemoveReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {}
}
