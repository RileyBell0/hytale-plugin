// package dev.twunk.plugin;
// import com.hypixel.hytale.component.AddReason;
// import com.hypixel.hytale.component.CommandBuffer;
// import com.hypixel.hytale.component.Ref;
// import com.hypixel.hytale.component.RemoveReason;
// import com.hypixel.hytale.component.Store;
// import com.hypixel.hytale.component.query.Query;
// import com.hypixel.hytale.logger.HytaleLogger;
// import com.hypixel.hytale.math.vector.Vector3i;
// import com.hypixel.hytale.server.core.modules.block.BlockModule;
// import com.hypixel.hytale.server.core.universe.world.World;
// import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
// import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
// import dev.twunk.subsystem.SubSystemOwner;
// import dev.twunk.subsystem.base.EntityLifetimeSubSystem;
// import dev.twunk.subsystem.base.interfaces.IEntityLifetimeSystem;
// import dev.twunk.subsystem.composite.BlockTickSubSystem;
// import dev.twunk.subsystem.composite.interfaces.IBlockTickSystem;
// import dev.twunk.utils.world.Utils;
// import javax.annotation.Nonnull;
// // Unused. Though, that's as simple as it gets
// public class ExampleScheduledTickSystem extends SubSystemOwner implements IBlockTickSystem, IEntityLifetimeSystem {
//     private static final HytaleLogger.Api console = HytaleLogger.forEnclosingClass().atInfo();
//     @Nonnull
//     private static final Query<ChunkStore> QUERY = Query.and(
//         BlockModule.BlockStateInfo.getComponentType(),
//         ExampleTickingBlockComponent.getComponentType()
//     );
//     // @Nonnull
//     // private final String id;
//     public ExampleScheduledTickSystem(@Nonnull String id) {
//         super(QUERY);
//         // this.id = id;
//         this.appendSubSystem(new BlockTickSubSystem(this));
//         this.appendSubSystem(new EntityLifetimeSubSystem(this));
//     }
//     public void onBlockTick(
//         @Nonnull Ref<ChunkStore> blockRef,
//         @Nonnull World world,
//         @Nonnull WorldChunk wc,
//         @Nonnull CommandBuffer<ChunkStore> commandBuffer,
//         @Nonnull Vector3i worldCoords,
//         int blockId
//     ) {
//         // if (!hasRun.containsKey(worldCoords)) {
//         //     console.log("RUNNING FOR THE FIRST TIME");
//         //     try {
//         //         Block.Refs.test(blockRef, wc, commandBuffer, worldCoords);
//         //         console.log(blockRef + "");
//         //         console.log(wc + "");
//         //         console.log(commandBuffer + "");
//         //         console.log(worldCoords + "");
//         //         this.hasRun.put(worldCoords, true);
//         //     } catch (RuntimeException e) {
//         //         console.log("Failed: " + e);
//         //     }
//         // }
//         // var block = Block.Refs.getRef(commandBuffer, worldCoords.x, worldCoords.y - 1, worldCoords.z);
//         // if (block != null) {
//         //     console.log("" + block);
//         // }
//     }
//     // @Override
//     // public String getId() {
//     //     return this.id;
//     // }
//     public Query<ChunkStore> getQuery() {
//         return ExampleScheduledTickSystem.QUERY;
//     }
//     @Override
//     public void onEntityAdded(
//         @Nonnull Ref<ChunkStore> ref,
//         @Nonnull AddReason reason,
//         @Nonnull Store<ChunkStore> store,
//         @Nonnull CommandBuffer<ChunkStore> commandBuffer
//     ) {
//         var worldChunk = Chunk.WorldChunks.getWorldChunk(ref);
//         if (worldChunk == null) {
//             console.log("ERROR: WORLD CHUNK WAS NULL IN SETUp");
//             return;
//         }
//         var coords = Block.Coords.Global.getGlobalCoords(ref);
//         if (coords == null) {
//             console.log("ERROR: coords was null!!!");
//             return;
//         }
//         Block.Refs.test(ref, worldChunk, commandBuffer, coords);
//     }
//     @Override
//     public void onEntityRemove(
//         @Nonnull Ref<ChunkStore> ref,
//         @Nonnull RemoveReason reason,
//         @Nonnull Store<ChunkStore> store,
//         @Nonnull CommandBuffer<ChunkStore> commandBuffer
//     ) {}
// }
