package dev.twunk.plugin;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.ticking.component.ITickingComponent;
import dev.twunk.ticking.response.TickResponse;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExampleTickingBlockComponent implements ITickingComponent {
    // @SuppressWarnings("unused")
    private static HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    // Your vars
    private int ticks = 0;

    // serializing/deserializing your vars
    @Nonnull
    public static final BuilderCodec<ExampleTickingBlockComponent> CODEC = BuilderCodec.builder(
            ExampleTickingBlockComponent.class,
            ExampleTickingBlockComponent::new)
            .append(
                    new KeyedCodec<Integer>("Ticks", Codec.INTEGER),
                    (data, value) -> data.ticks = value,
                    data -> data.ticks)
            .add()
            .build();

    public ExampleTickingBlockComponent() {
    }

    public ExampleTickingBlockComponent(int ticks) {
        this.ticks = ticks;
    }

    /**
     * Run actions every tick
     */
    @Override
    @Nullable
    public TickResponse onTick(
            @Nonnull World world,
            @Nonnull WorldChunk wc,
            @Nonnull CommandBuffer<ChunkStore> commandBuffer,
            int worldX,
            int worldY,
            int worldZ,
            int blockId) {
        this.ticks++;
        console.log("Ticked block at (" + worldX + ", " + worldY + ", " + worldZ + " ) " + this.ticks + " times");
        if (ticks > 100) {
            return TickResponse.SLEEP;
        }

        return TickResponse.CONTINUE;
    }

    @Nullable
    public ExampleTickingBlockComponent clone() {
        return new ExampleTickingBlockComponent(ticks);
    }
}
