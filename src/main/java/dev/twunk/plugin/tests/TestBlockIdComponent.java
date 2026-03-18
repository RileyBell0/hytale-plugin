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

public class TestBlockIdComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestBlockIdComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestBlockIdComponent> CODEC = BuilderCodec.builder(
        TestBlockIdComponent.class,
        TestBlockIdComponent::new
    ).build();

    public TestBlockIdComponent() {}

    @Nullable
    public TestBlockIdComponent clone() {
        return new TestBlockIdComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestBlockIdComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestBlockIdComponent.class);
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

        console.log("");
        console.log("Added TEST_BlockId block");
        var worldChunk = Utils.Chunk.WorldChunk_.getWorldChunk(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = Utils.BlockCoords.Global.getGlobalCoords(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }
        var res = Utils.Block.Id.test(ref, worldChunk, commandBuffer, coords);

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

        var allTheSame = true;
        for (var i = 1; i < res.size(); i++) {
            if (!res.get(i).equals(res.get(i - 1))) {
                allTheSame = false;
                break;
            }
        }

        if (allTheSame) {
            console.log("+ All tests were the same");
        } else {
            console.log("- WARNING: Not all tests were the same");
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
