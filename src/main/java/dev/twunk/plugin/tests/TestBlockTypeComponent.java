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

public class TestBlockTypeComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestBlockTypeComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestBlockTypeComponent> CODEC = BuilderCodec.builder(
        TestBlockTypeComponent.class,
        TestBlockTypeComponent::new
    ).build();

    public TestBlockTypeComponent() {}

    @Nullable
    public TestBlockTypeComponent clone() {
        return new TestBlockTypeComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestBlockTypeComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestBlockTypeComponent.class);
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
        final var verbose = false;

        console.log("Added TEST_BlockType block");
        var worldChunk = Utils.Chunk.WorldChunk_.getWorldChunk(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = Utils.Coords.getGlobalCoords(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }
        var res = Utils.Block.Type.test(ref, worldChunk, commandBuffer, coords);

        console.log("Ran " + res.size() + " test(s)");
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            if (test != null) {
                success += 1;
            }
            if (verbose) {
                console.log(" - " + i + ") " + test);
            }
        }
        console.log("" + success + "/" + res.size() + " tests successful");
        for (var i = 0; i < res.size(); i++) {
            var entry = res.get(i);
            if (entry == null) {
                console.log(i + ") Failed test " + i + "!");
            }
        }
    }

    @Override
    public void onEntityRemove(
        @Nonnull Ref<ChunkStore> ref,
        @Nonnull RemoveReason reason,
        @Nonnull Store<ChunkStore> store,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer
    ) {}
}
