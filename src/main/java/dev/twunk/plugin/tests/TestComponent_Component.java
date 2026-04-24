package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.hytale.codec.auto.Serializable;
import dev.twunk.hytale.interfaces.event.IOnAddRemove;
import dev.twunk.hytale.ref.AnyRef;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.hytale.utils.ComponentUtils;
import javax.annotation.Nullable;

@Serializable
public class TestComponent_Component implements Component<ChunkStore>, IOnAddRemove<ChunkStore> {

    @Override
    public void onEntityAdded(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        runTests(ref, reason, ref.getStore(), commandBuffer, false);
    }

    public static final void runTests(
        Ref<ChunkStore> ref,
        AddReason reason,
        Store<ChunkStore> store,
        CommandBuffer<ChunkStore> commandBuffer,
        @Nullable Boolean verbose
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
    public TestComponent_Component clone() {
        return new TestComponent_Component();
    }
}
