# Tron Mod Roadmap

The Identity Disc is the heart of the mod. It begins as the player's persistent digital identity, then grows into a weapon, tool, progression system, and key to entering the Grid.

This roadmap is intentionally flexible. Features may move between milestones as the core mechanics are tested.

## Core Design Pillars

- **Identity:** Every player has a personal disc that records important information about them.
- **Skillful combat:** Throwing, catching, aiming, and ricocheting should reward practice.
- **Meaningful progression:** Enchantments and upgrades should change how a disc behaves, not only increase its damage.
- **The Grid:** Items, creatures, vehicles, and environments should feel like parts of one connected digital world.
- **Multiplayer friendly:** Ownership, stored player data, projectiles, and vehicles must behave correctly on dedicated servers.

## Phase 1: Project Foundation

- [x] Create the NeoForge project.
- [x] Configure the `tronmod` mod ID and package.
- [x] Set up Git and the public GitHub repository.
- [x] Confirm that the project builds.
- [x] Establish code packages for items, entities, data components, enchantments, networking, and client rendering.
- [x] Add a Tron-themed creative mode tab.
- [x] Add basic development assets and localization conventions.
- [x] Add a simple automated GameTest foundation.

## Phase 2: Identity Disc MVP

Goal: obtain a personal Identity Disc, inspect its stored identity, and use it as a basic melee weapon.

- [x] Register the Identity Disc item.
- [x] Create a temporary item model and texture.
- [x] Bind a disc to the first player who uses or crafts it.
- [x] Store persistent player identity data on the disc, serialized with the item stack.
- [x] Initial stored data:
  - Owner UUID.
  - Owner name at the time of the last update.
  - Disc creation timestamp or world game time.
  - A unique disc ID.
  - Basic usage statistics such as throws, hits, catches, and defeats.
- [x] Display ownership and selected statistics in the item tooltip.
- [x] Prevent another player from silently overwriting a bound disc's identity.
- [x] Decide how lost discs can be recovered without enabling easy duplication. For the first release, lost discs must be retrieved; a newly crafted replacement receives a new ID and fresh statistics rather than cloning the lost disc.
- [x] Give the disc sensible melee damage, durability, and repair rules.
- [ ] Test saving, loading, death, dimension travel, and multiplayer ownership.

Implementation note: on modern NeoForge, structured item data components should be preferred where practical. The component data will still persist in the saved item data rather than living only in runtime memory.

## Phase 3: Throwable Disc Combat

Goal: create a reliable server-authoritative projectile that returns to its owner.

- [ ] Add an Identity Disc projectile entity.
- [ ] Throw the disc using the item's use action.
- [ ] Temporarily remove or disable the held disc while its projectile is active.
- [ ] Damage valid targets on impact.
- [ ] Ricochet from blocks at least two times by default.
- [ ] Preserve momentum while making ricochet paths readable and predictable.
- [ ] Drop the base disc for manual recovery after impact, similar to a thrown trident.
- [ ] With Rebound, begin returning through additional ricochets after an impact or maximum flight time.
- [ ] Let the owner catch a returning Rebound disc automatically or with a timed input.
- [ ] Return the exact original item stack with all identity data, enchantments, durability, and statistics intact.
- [ ] Safely recover the disc if its projectile unloads, its owner dies, or the server restarts.
- [ ] Add sounds and temporary particles for throwing, impacts, ricochets, and catching.
- [ ] Synchronize projectile state and visuals in multiplayer.
- [ ] Add GameTests for ownership, ricochet limits, returning, and data preservation.

## Phase 4: Disc Enchantments and Upgrades

Goal: let players specialize their disc without making every upgrade a simple damage increase.

Potential enchantments:

- [ ] **Rebound:** Makes the disc ricochet back toward its owner, adding one guided bounce per level.
- [ ] **Velocity:** Increases projectile speed and effective range.
- [ ] **Impact:** Increases direct-hit damage and knockback.
- [ ] **Recall:** Makes the disc return sooner and track its owner more aggressively.
- [ ] **Seeking:** Curves slightly toward a nearby valid target after a ricochet.
- [ ] **Piercing:** Allows the disc to pass through additional entities before returning.
- [ ] **Split Circuit:** Temporarily creates secondary visual or damaging disc paths at high cost.
- [ ] **Disruption:** Applies a short digital debuff to targets or disables certain Grid technology.
- [ ] **Perfect Return:** Rewards a well-timed catch with a temporary combat bonus.

Balancing tasks:

- [ ] Define mutually exclusive enchantments where combinations would be overpowered or mechanically unclear.
- [ ] Cap total ricochets and projectile lifetime to protect server performance.
- [ ] Make enchantment effects clear through tooltips, particles, sound, and disc trail colors.
- [ ] Decide whether ordinary enchanting, a custom upgrade station, or both provide disc progression.

## Phase 5: Identity and Progression Systems

Goal: make the information stored by the disc matter beyond flavor text.

- [ ] Add a disc inspection screen.
- [ ] Track expanded lifetime statistics and notable achievements.
- [ ] Unlock cosmetic disc circuits, colors, or trails through play.
- [ ] Add configurable privacy controls for player information.
- [ ] Allow selected player data to be restored or referenced through the disc without turning it into unrestricted inventory duplication.
- [ ] Explore disc-based permissions for Grid doors, vehicles, terminals, and factions.
- [ ] Add a safe rebinding or inheritance mechanic.
- [ ] Create admin recovery tools for broken or lost ownership data.

## Phase 6: Light Cycles

Goal: introduce fast, responsive Grid vehicles after the projectile and networking foundations are stable.

- [ ] Add a basic rideable Light Cycle entity.
- [ ] Summon or deploy it through a dedicated item or upgraded Identity Disc.
- [ ] Implement acceleration, braking, turning, mounting, and dismounting.
- [ ] Add energy walls or light trails.
- [ ] Define collision and damage rules for trails.
- [ ] Make trails expire predictably and efficiently.
- [ ] Add vehicle ownership and access rules tied to Identity Discs.
- [ ] Support multiplayer races and combat arenas.
- [ ] Add final models, animations, sounds, and emissive textures.

Possible later vehicles include Light Jets, Recognizers, and faction-specific cycle variants.

## Phase 7: The Grid Dimension

Goal: provide a distinct digital world built around the mod's identity and vehicle systems.

- [ ] Define how players first access the Grid.
- [ ] Require or strongly encourage a bound Identity Disc for safe entry.
- [ ] Create the Grid dimension type and world-generation settings.
- [ ] Add an initial biome with a dark landscape and luminous circuit patterns.
- [ ] Add Grid materials, blocks, ores or energy resources, and structures.
- [ ] Create portals, digitization terminals, or another thematic travel mechanic.
- [ ] Decide what happens when a player enters without a disc or loses it inside the Grid.
- [ ] Add Grid-specific hazards, weather or sky effects, ambient sound, and music hooks.
- [ ] Optimize emissive effects, structures, and vehicle travel for multiplayer servers.

## Phase 8: Programs, Mobs, and Factions

Goal: populate the Grid with creatures and characters that interact with its systems.

- [ ] Add a simple hostile security program as the first mob.
- [ ] Add friendly or neutral programs.
- [ ] Add corrupted or derezzed variants.
- [ ] Create ranged and disc-wielding enemies.
- [ ] Add faction reputation or allegiance tied to the player's identity.
- [ ] Add NPC settlements, terminals, patrols, and arenas.
- [ ] Add bosses or elite programs with unique disc upgrades.
- [ ] Give mobs clear drops and purposes within the progression loop.

## Phase 9: Content, Polish, and Release

- [ ] Replace temporary art with final models, animations, textures, particles, and sounds.
- [ ] Add accessibility options for flashes, high-contrast effects, and screen movement.
- [ ] Add configuration for damage, ricochet count, recovery rules, and expensive visual effects.
- [ ] Test on a dedicated server throughout development.
- [ ] Test compatibility with common performance, recipe-viewing, and animation mods.
- [ ] Add advancements and an in-game guide or tutorial path.
- [ ] Document installation, configuration, and server requirements.
- [ ] Create release builds and changelogs.
- [ ] Publish an initial playable release.

## Suggested First Playable Release: `0.1.0`

The first playable version should stay focused:

- One bindable Identity Disc.
- Persistent owner identity and basic statistics.
- Melee use.
- Throwing and returning.
- Two block ricochets by default.
- Three initial enchantments: Rebound, Velocity, and Impact.
- Basic temporary visuals and sounds.
- Reliable single-player and dedicated-server behavior.

Light Cycles, the Grid dimension, and mobs should come after this core loop is fun and technically reliable.

## Open Design Questions

- How does a player obtain their first Identity Disc?
It should be crafted
- Is one canonical disc allowed per player, or can backup and specialized discs exist?
Open ended for now
- What player information is useful, fun, and appropriate to store?
Username, craft date, mobs killed, number of bounces, maybe more?
- Should the disc return automatically, require a timed catch, or support both modes?
To start the disc should need to be picked up, like a trident, but with a enchnemnt called rebound it can rebound back to the player via a couple bounces.
- Can a thrown disc be intercepted, stolen, or temporarily disabled by another player?
intercepted or hit through the air could be fun, but maybe too challenging
- Does durability represent physical damage, energy, or both?
Unknown yet
- How punishing should losing a disc be?
Unknown yet
- Which visual identity should lead the mod: classic Tron, Tron: Legacy, Tron: Ares, or an original blend?
Tron legacy should lead, but a blend to make everyone happy
