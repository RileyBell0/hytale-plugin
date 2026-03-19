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

public class TestComponent_Component implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestComponent_Component> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestComponent_Component> CODEC = BuilderCodec.builder(
        TestComponent_Component.class,
        TestComponent_Component::new
    ).build();

    public TestComponent_Component() {}

    @Nullable
    public TestComponent_Component clone() {
        return new TestComponent_Component();
    }

    @Nonnull
    @SuppressWarnings("null")
    public static final ComponentType<ChunkStore, TestComponent_Component> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestComponent_Component.class);
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
            console.log("Added TEST_Component_ block");
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
        Utils.Component_.test(ref, worldChunk, commandBuffer, coords, verbose);
    }

    @Override
    public void onEntityAdded(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        runTests(ref, reason, store, commandBuffer, false);
    }

    @Override
    public void onEntityRemove(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull RemoveReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {}
}
