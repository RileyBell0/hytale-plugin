package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import dev.twunk.hytale.LibHytale;
import dev.twunk.hytale.codec.auto.Serializable;
import dev.twunk.hytale.codec.auto.Serialize;
import dev.twunk.hytale.interfaces.event.IOnAddRemove;
import dev.twunk.hytale.interfaces.event.IOnBlockTick;
import dev.twunk.hytale.interfaces.event.IOnScheduledTick;
import dev.twunk.hytale.interfaces.event.IOnTick;
import dev.twunk.hytale.interfaces.event.IOnUniverseTick;
import dev.twunk.hytale.ref.AnyRef;
import dev.twunk.hytale.ref.BlockRef;
import dev.twunk.hytale.utils.ChunkUtils;
import dev.twunk.lib.event.scheduled.TickSchedule;
import javax.annotation.Nullable;

@Serializable
public final class EventTestComponent
    implements
        Component<ChunkStore>,
        IOnAddRemove<ChunkStore>,
        IOnBlockTick,
        IOnTick<ChunkStore>,
        IOnUniverseTick<ChunkStore>,
        IOnScheduledTick<ChunkStore>
{

    public static final HytaleLogger logger = HytaleLogger.forEnclosingClass();

    private static int nextId = 0;

    private int id = nextId++;

    // ////////////////////////////////
    // IOnAddRemove
    // ////////////////////////////////

    @Override
    public final void onEntityAdded(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log(id + ") onEntityAdded | " + reason);
    }

    @Override
    public final void onEntityRemove(
        AnyRef<ChunkStore> ref,
        RemoveReason reason,
        CommandBuffer<ChunkStore> commandBuffer
    ) {
        logger.atWarning().log(id + ") onEntityRemove | " + reason);
    }

    // ////////////////////////////////
    // IOnBlockTick
    // ////////////////////////////////

    @Override
    public final void onBlockTick(BlockRef blockRef, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log(id + ") onBlockTick | " + blockRef.getCoords());
    }

    // ////////////////////////////////
    // IOnTick
    // ////////////////////////////////

    @Override
    public final void onTick(float dt, AnyRef<ChunkStore> ref, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log(id + ") onTick");
    }

    // ////////////////////////////////
    // IOnUniverseTick
    // ////////////////////////////////

    @Override
    public final void onUniverseTick(float dt, int index, Store<ChunkStore> store) {
        logger.atWarning().log(id + ") onUniverseTick");
    }

    // ////////////////////////////////
    // IOnScheduledTick
    // ////////////////////////////////

    @Serialize
    public int tick = 0;

    @Nullable
    private static ComponentType<ChunkStore, EventTestComponent> componentType = null;

    @SuppressWarnings("null")
    public static ComponentType<ChunkStore, EventTestComponent> getComponentType() {
        if (EventTestComponent.componentType == null) {
            EventTestComponent.componentType = LibHytale.CHUNK_REGISTRY.getComponentType(EventTestComponent.class);
        }
        return EventTestComponent.componentType;
    }

    @Override
    @Nullable
    public final TickSchedule onScheduledTick(float dt, Ref<ChunkStore> ref, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log(id + ") onScheduledTick");

        var worldChunk = ChunkUtils.WorldChunks.get(ref);
        if (worldChunk != null) {
            worldChunk.markNeedsSaving();
        }

        return new TickSchedule.Sleeping(ref.getStore().getExternalData().getWorld().getTick() + 2);
    }

    @Override
    public EventTestComponent clone() {
        var cloned = new EventTestComponent();
        cloned.tick = this.tick;
        cloned.id = this.id;
        return cloned;
    }
}
