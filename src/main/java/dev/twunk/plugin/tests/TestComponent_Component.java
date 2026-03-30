package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.refs.AnyRef;
import dev.twunk.hytale.utils.BlockUtils;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.hytale.utils.ComponentUtils;
import dev.twunk.interfaces.component.ILifetimeComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Serializable
public class TestComponent_Component implements ILifetimeComponent<ChunkStore> {

    @Override
    public void onEntityAdded(
        @Nonnull AnyRef<ChunkStore> ref,
        @Nonnull AddReason reason,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {
        runTests(ref, reason, ref.getStore(), commandBuffer, false);
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

    @Nullable
    public TestComponent_Component clone() {
        return new TestComponent_Component();
    }
}
