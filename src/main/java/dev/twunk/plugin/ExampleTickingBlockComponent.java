package dev.twunk.plugin;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.component.IRegisteredComponent;
import dev.twunk.component.ITickableBlockComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExampleTickingBlockComponent implements ITickableBlockComponent {

    // @SuppressWarnings("unused")
    private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();

    @Nullable
    private static ComponentType<ChunkStore, ExampleTickingBlockComponent> COMPONENT_TYPE;

    // Your vars
    private int ticks = 0;

    // serializing/deserializing your vars
    @Nonnull
    public static final BuilderCodec<ExampleTickingBlockComponent> CODEC = BuilderCodec.builder(
        ExampleTickingBlockComponent.class,
        ExampleTickingBlockComponent::new
    )
        .append(
            new KeyedCodec<Integer>("Ticks", Codec.INTEGER),
            (data, value) -> data.ticks = value,
            data -> data.ticks
        )
        .add()
        .build();

    public ExampleTickingBlockComponent() {}

    public ExampleTickingBlockComponent(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void onBlockEntityTick(
        @Nonnull World world,
        @Nonnull WorldChunk wc,
        @Nonnull CommandBuffer<ChunkStore> commandBuffer,
        @Nonnull Vector3i worldCoords,
        int blockId
    ) {
        this.ticks++;
        console.log(
            "Ticked block at (" +
                worldCoords.x +
                ", " +
                worldCoords.y +
                ", " +
                worldCoords.z +
                " ) " +
                this.ticks +
                " times"
        );
    }

    @Nullable
    public ExampleTickingBlockComponent clone() {
        return new ExampleTickingBlockComponent(ticks);
    }

    @Nonnull
    public static final ComponentType<ChunkStore, ExampleTickingBlockComponent> getComponentType() {
        if (COMPONENT_TYPE == null) {
            COMPONENT_TYPE = IRegisteredComponent.getComponentType(ExampleTickingBlockComponent.class);
            return COMPONENT_TYPE;
        } else {
            return COMPONENT_TYPE;
        }
    }
}
