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

public class TestBlockCoordsComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestBlockCoordsComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestBlockCoordsComponent> CODEC = BuilderCodec.builder(
        TestBlockCoordsComponent.class,
        TestBlockCoordsComponent::new
    ).build();

    public TestBlockCoordsComponent() {}

    @Override
    @Nullable
    public TestBlockCoordsComponent clone() {
        return new TestBlockCoordsComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestBlockCoordsComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestBlockCoordsComponent.class);
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
            console.log("Added TEST_BlockCoords block");
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

        Utils.Block.Coords.Local.test(ref, worldChunk, commandBuffer, coords);
        console.log("+ (8) SUCCESS: TEST_BlockCoords-local");
        Utils.Block.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        console.log("+ (9) SUCCESS: TEST_BlockCoords-index");
        Utils.Block.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        console.log("+ (10) SUCCESS: TEST_BlockCoords-global");
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
