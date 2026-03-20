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
import dev.twunk.*;
import dev.twunk.interfaces.component.auto.IAutoBlockLifetimeComponent;
import dev.twunk.utils.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestBlockRefComponent implements IAutoBlockLifetimeComponent {

    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, TestBlockRefComponent> COMPONENT_TYPE;

    @Nonnull
    public static final BuilderCodec<TestBlockRefComponent> CODEC = BuilderCodec.builder(
        TestBlockRefComponent.class,
        TestBlockRefComponent::new
    ).build();

    public TestBlockRefComponent() {}

    @Nullable
    public TestBlockRefComponent clone() {
        return new TestBlockRefComponent();
    }

    @Nonnull
    public static final ComponentType<ChunkStore, TestBlockRefComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = TwunkLib.getComponentType(TestBlockRefComponent.class);
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
            console.log("");
            console.log("Added TEST_BlockRef block");
        }
        var worldChunk = ChunkUtils.WorldChunk_.get(ref);
        if (worldChunk == null) {
            console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
            return;
        }
        var coords = BlockUtils.Coords.Global.get(ref);
        if (coords == null) {
            console.log("ERROR: coords was null!!!");
            return;
        }
        var res = BlockUtils.Ref_.test(ref, worldChunk, commandBuffer, coords);

        if (verbose != null) {
            console.log("Ran " + res.size() + " test(s)");
        }
        var success = 0;
        for (var i = 0; i < res.size(); i++) {
            final var test = res.get(i);

            if (test != null) {
                success += 1;
            }
            if (verbose != null && verbose) {
                console.log(" - " + i + ") " + test);
            }
        }
        if (verbose != null) {
            console.log("" + success + "/" + res.size() + " tests successful");
            for (var i = 0; i < res.size(); i++) {
                var entry = res.get(i);
                if (entry == null) {
                    console.log(i + ") Failed test " + i + "!");
                }
            }
        }

        var allTheSame = true;
        for (var i = 1; i < res.size(); i++) {
            if (!res.get(i).equals(res.get(i - 1))) {
                allTheSame = false;
                break;
            }
        }

        if (verbose != null) {
            if (allTheSame) {
                console.log("+ All tests were the same");
            } else {
                console.log("- WARNING: Not all tests were the same");
            }
        }

        if (verbose == null) {
            if (success == res.size() && allTheSame) {
                console.log("+ (3) SUCCESS: TEST_BlockRef");
            } else {
                console.log("- (3) FAILED:  TEST_BlockRef");
            }
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
