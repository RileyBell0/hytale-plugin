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
import dev.twunk.hytale.utils.ComponentUtils;
import dev.twunk.interfaces.component.ILifetimeComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@HytaleComponent
public class TestComponent_Component implements ILifetimeComponent<ChunkStore> {

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
            COMPONENT_TYPE = LibHytale.getChunkComponentType(TestComponent_Component.class);
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
            Chat.log("");
            Chat.log("Added TEST_Component_ block");
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
        ComponentUtils.test(ref, worldChunk, commandBuffer, coords, verbose);
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
