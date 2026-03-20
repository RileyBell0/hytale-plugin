package dev.twunk.plugin.tests;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.TwunkLib;
import dev.twunk.interfaces.component.auto.IAutoBlockLifetimeComponent;
import dev.twunk.utils.BlockUtils;
import dev.twunk.utils.ChunkUtils;
import dev.twunk.utils.message.Chat;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestBlockCoordsComponent implements IAutoBlockLifetimeComponent {

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
            Chat.log("");
            Chat.log("Added TEST_BlockCoords block");
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

        BlockUtils.Coords.Local.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (8) SUCCESS: TEST_BlockCoords-local");
        BlockUtils.Coords.Index.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (9) SUCCESS: TEST_BlockCoords-index");
        BlockUtils.Coords.Global.test(ref, worldChunk, commandBuffer, coords);
        Chat.log("+ (10) SUCCESS: TEST_BlockCoords-global");
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
