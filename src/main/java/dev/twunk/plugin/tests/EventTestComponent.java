package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
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
import dev.twunk.hytale.interfaces.event.IOnWorldTick;
import dev.twunk.hytale.ref.AnyRef;
import dev.twunk.hytale.ref.BlockRef;
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
        IOnWorldTick<ChunkStore>,
        IOnScheduledTick<ChunkStore>
{

    public static final HytaleLogger logger = HytaleLogger.forEnclosingClass();

    // ////////////////////////////////
    // IOnAddRemove
    // ////////////////////////////////

    @Override
    public final void onAdd(AnyRef<ChunkStore> ref, AddReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log("onEntityAdded | " + reason);
    }

    @Override
    public final void onRemove(AnyRef<ChunkStore> ref, RemoveReason reason, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log("onEntityRemove | " + reason);
    }

    // // ////////////////////////////////
    // // IOnBlockTick
    // // ////////////////////////////////

    @Override
    public final void onBlockTick(BlockRef blockRef, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log("onBlockTick | " + blockRef.getCoords());
    }

    // ////////////////////////////////
    // IOnTick
    // ////////////////////////////////

    @Override
    public final void onTick(float dt, AnyRef<ChunkStore> ref, CommandBuffer<ChunkStore> commandBuffer) {
        logger.atWarning().log("onTick");
    }

    // ////////////////////////////////
    // IOnUniverseTick
    // ////////////////////////////////

    @Override
    public final void onUniverseTick(float dt, int index, Store<ChunkStore> store) {
        logger.atWarning().log("onUniverseTick");
    }

    // ////////////////////////////////
    // IOnWorldTick
    // ////////////////////////////////

    @Override
    public final void onWorldTick(
        float dt,
        ArchetypeChunk<ChunkStore> archetypeChunk,
        Store<ChunkStore> store,
        CommandBuffer<ChunkStore> commandBuffer
    ) {
        logger.atWarning().log("onUniverseTick");
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
    public final TickSchedule onScheduledTick(
        float dt,
        long worldTick,
        AnyRef<ChunkStore> ref,
        CommandBuffer<ChunkStore> commandBuffer
    ) {
        logger.atWarning().log("onScheduledTick | " + ref);

        // var worldChunk = ChunkUtils.WorldChunks.get(ref);
        // if (worldChunk != null) {
        //     worldChunk.markNeedsSaving();
        // }

        return new TickSchedule.Sleeping(600);
    }

    @Override
    public EventTestComponent clone() {
        var cloned = new EventTestComponent();
        cloned.tick = this.tick;
        return cloned;
    }
}
