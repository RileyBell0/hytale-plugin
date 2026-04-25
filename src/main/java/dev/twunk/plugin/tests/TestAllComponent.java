package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.hytale.codec.auto.Serializable;
import dev.twunk.hytale.interfaces.event.IOnAddRemove;
import dev.twunk.hytale.ref.AnyRef;
import dev.twunk.hytale.utils.Chat;

@Serializable
public class TestAllComponent implements Component<ChunkStore>, IOnAddRemove<ChunkStore> {

    @Override
    public void onEntityAdded(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        final Boolean verbose = null;
        Chat.log("");
        Chat.log("Added TEST_All block");

        TestBlockIdComponent.runTests(ref, commandBuffer, verbose, "TEST_All");
        TestBlockInfoComponent.runTests(ref, commandBuffer, verbose);
        TestBlockRefComponent.runTests(ref, commandBuffer, verbose);
        TestBlockRefDetectionComponent.runTests(ref, verbose);
        TestBlockTypeComponent.runTests(ref, verbose);
        TestChunkRefComponent.runTests(ref, commandBuffer, verbose);
        TestWorldChunkComponent.runTests(ref, commandBuffer, verbose);
        TestBlockCoordsComponent.runTests(ref, commandBuffer, verbose);
        TestChunkCoordsComponent.runTests(ref, commandBuffer, verbose);
        TestComponent_Component.runTests(ref, commandBuffer, verbose);
    }

    @Override
    public TestAllComponent clone() {
        return new TestAllComponent();
    }
}
