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
    // i want there to be two types for each component, so that i can designate it
    // as ticking or not
    // so like, yeah, oh i see we can do ExampleTickingBlockComponent to get just
    // example ticking block components, but then thats, yeah.. idk. means i can't
    // have multiple ticking components on the same block or they'd collide.
    // so i guess it's better to have maybe something like this as a default? or
    // maybe i make a default that
    //
    // i want it to be that the ticking variant IS the other variant but plus extars
    // so you can query just one or the other or both
    // so ideally, ok no this is fine because i can have the components yell at the
    // handler to keep themselves ticking or not
    // meaning, the ticking component shouldn't be removed, oh shit it should or you
    // can't query
    //
    // ok or i could do that method that doesnt involve ticking components? but nah
    // i still need to keep ticking data
    // i mean, there's the option of several variations of components over the top
    // of the base components?
    //
    // like, could have ExampleTickingBlockComponent.Ticking, .Sleep, .Stop etc
    // then they get applied? meaning 4 components by default per component? that
    // seems like
    // alot of components for a niche case of multi-ticking blocks
    //
    // but it would be handy?
    //
    // or maybe i make a helper for making those variants and don't put them in by
    // default?
    //
    // so you can do setupTickingComponent().withSubClasses()
    // or setupTickingComponent() which will just use TICKING vs withSubComponents
    // which will make each of them for you and register them, but then, how do you
    // make
    // your component class? heck.
    // i mean i could hide all that off to the side, use the base class name as the
    // default ID and just do a ton of lambda classes that are fetchable by the OG?
    //
    // that way you have a type safe way of knowing what you've got? kinda? cause
    // you
    // can't get it any other way? but then you can't access it with
    // ExampleComponent.Ticking
    //
    // nah fuck that. just, maybe i can make component type aliases? nah, cause
    // they'd collide, unless it fuck idk
    //
    // TLDR just one ticking per block, multiple ticking IS possible. so i should
    // support it.
    //
    // FUCK. but it makes the code so boilerplatey and gross if i do, so maybe i do
    // different inheritance? my class doesn't implement ITickingComponent it
    // implements IMultiTickingComponent which has subclasses for me to register
    // etc?
    //
    // that way i don't bloat anything up or lose inheritance
    //
    // hmm, that could work, meaning, idk. this really would only be useful for
    // cases where multiple mods collide using the same idea of ticking,
    // so maybe you'd want a mod to FREEZE any ticks that are happening? or speed
    // them up? then what? we want them to be able to both interact globally with
    // specific ones, and specifically instead of global ones
    //
    // so adding specific ones is easy, but how about interacting globally with
    // them? a giant query of them all? fuck that, that could be hundreds or
    // thousands of statements
    //
    // so, we're back to ticking
    //
    // and we're back to wondering if there's a way to store ticking inside of a
    // component, handle the onTick myself and somehow override the queries to let
    // me say "nah, ur not part of this one buddy" whilst also letting people
    // subscribe to fields and say "yeah i care about X field so when that changes
    // update me"
    //
    // ^^ doing the queries myself is prbably the only real way to do this i guess.
    // could override their classes and add the bits i need i guess?
    //
    // ok so queries are tested with exact reference matching, if you contain me
    // then we good
    //
    // SO i could add a query variant? query.and blah blah blah thats done
    // but maybe Query.field()?
    // then i can watch it?
    // and track state and registration with a ref system maybe? so i don't have a
    // field watcher on a nonexistent ref?
    //
    // yeah, ok this is java, this should be easy for me to sneak my way into their
    // query system
    //
    // yeah, id have to have a system that replicates what they do
    // and have query.field talk to that?
    // or just have query.field be smart?
    // cause i can't just do a simple and?
    // i mean i should do and, i should do you have my component AND it has field
    // set on it
    // but
    // idk, is that possible?? do i even have that data around? i can maybe hook
    // into it higher up in the tree for chunk store??? idk
    public class Ticking extends ExampleTickingBlockComponent {
        @Override
        public boolean isTicking() {
            return true;
        }
    }

    public boolean isTicking() {
        return false;
    }

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
