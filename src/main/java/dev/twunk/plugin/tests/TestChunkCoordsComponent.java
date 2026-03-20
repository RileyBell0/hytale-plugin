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

public class TestChunkCoordsComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestChunkCoordsComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestChunkCoordsComponent> CODEC = BuilderCodec.builder(
        TestChunkCoordsComponent.class,
        TestChunkCoordsComponent::new
    ).build();

    public TestChunkCoordsComponent() {}

    @Override
    @Nullable
    public TestChunkCoordsComponent clone() {
        return new TestChunkCoordsComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestChunkCoordsComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestChunkCoordsComponent.class);
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
            console.log("Added TEST_ChunkCoords block");
        }

        var worldChunk = Utils.Chunk.WorldChunk_.get(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = Utils.Block.Coords.Global.get(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }

        Utils.Chunk.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        console.log("+ (11) SUCCESS: TEST_ChunkCoords-index");
        Utils.Chunk.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        console.log("+ (12) SUCCESS: TEST_ChunkCoords-global");
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
