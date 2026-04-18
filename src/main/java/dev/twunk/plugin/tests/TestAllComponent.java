package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.refs.AnyRef;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.interfaces.events.IOnAddRemove;

@Serializable
public class TestAllComponent implements Component<ChunkStore>, IOnAddRemove<ChunkStore> {

    @Override
    public void onEntityAdded(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        final Boolean verbose = null;
        Chat.log("");
        Chat.log("Added TEST_All block");
        var store = ref.getStore();

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
    public TestAllComponent clone() {
        return new TestAllComponent();
    }
}
