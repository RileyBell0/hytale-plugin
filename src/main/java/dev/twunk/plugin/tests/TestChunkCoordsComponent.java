package dev.twunk.plugin.tests;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.*;
import dev.twunk.interfaces.component.auto.IAutoBlockLifetimeComponent;
import dev.twunk.utils.*;
import dev.twunk.utils.message.Chat;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestChunkCoordsComponent implements IAutoBlockLifetimeComponent {

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
            COMPONENT_TYPE = TwunkLib.getChunkComponentType(TestChunkCoordsComponent.class);
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
            Chat.log("Added TEST_ChunkCoords block");
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

        ChunkUtils.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (11) SUCCESS: TEST_ChunkCoords-index");
        ChunkUtils.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (12) SUCCESS: TEST_ChunkCoords-global");
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
