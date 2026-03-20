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
import dev.twunk.utils.message.Chat;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestAllComponent implements IAutoBlockLifetimeComponent {

    @Nullable
    private static ComponentType<ChunkStore, TestAllComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestAllComponent> CODEC = BuilderCodec.builder(
        TestAllComponent.class,
        TestAllComponent::new
    ).build();

    public TestAllComponent() {}

    @Override
    @Nullable
    public TestAllComponent clone() {
        return new TestAllComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestAllComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestAllComponent.class);
            return COMPONENT_TYPE;
        } else {
            return COMPONENT_TYPE;
        }
    }

    @Override
    public void onEntityAdded(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        final Boolean verbose = null;
        Chat.log("");
        Chat.log("Added TEST_All block");

        TestBlockIdComponent.runTests(ref, reason, store, commandBuffer, verbose, "TEST_All");
        TestBlockInfoComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestBlockRefComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestBlockRefDetectionComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestBlockTypeComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestChunkRefComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestWorldChunkComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestBlockCoordsComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestChunkCoordsComponent.runTests(ref, reason, store, commandBuffer, verbose);
        TestComponent_Component.runTests(ref, reason, store, commandBuffer, verbose);
    }

    @Override
    public void onEntityRemove(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull RemoveReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {}
}
