package dev.twunk.plugin.tests;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.twunk.annotations.Serializable;
import dev.twunk.hytale.utils.Chat;
import dev.twunk.hytale.utils.ItemUtils;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Serializable(inherits = SimpleBlockInteraction.class)
public class TestAllInteraction extends SimpleBlockInteraction {

    @Override
    protected void interactWithBlock(
        @Nonnull World world,
        @Nonnull CommandBuffer<EntityStore> commandBuffer,
        @Nonnull InteractionType interactionType,
        @Nonnull InteractionContext interactionContext,
        @Nullable ItemStack heldItem,
        @Nonnull Vector3i pos,
        @Nonnull CooldownHandler cooldownHandler
    ) {
        this.simulateInteractWithBlock(interactionType, interactionContext, heldItem, world, pos);
    }

    @Override
    protected void simulateInteractWithBlock(
        @Nonnull InteractionType interactionType,
        @Nonnull InteractionContext interactionContext,
        @Nullable ItemStack heldItem,
        @Nonnull World world,
        @Nonnull Vector3i pos
    ) {
        var store = world.getEntityStore().getStore();
        var player = store.getComponent(interactionContext.getOwningEntity(), Player.getComponentType());
        if (player == null) {
            throw new RuntimeException("Failed to get player!");
        }
        var playerRef = player.getReference();
        if (playerRef == null) {
            Chat.log("FAILED to get player ref!");
            return;
        }

        world.execute(() -> {
            Chat.log(
                "Spawned item (simulation): ",
                ItemUtils.spawn(playerRef, store, pos.add(0, 1, 0), new ItemStack("Soil_Grass", 1))
            );
        });
    }
}
