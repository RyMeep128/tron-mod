# Tron Mod Roadmap

## Mod Vision

Tron Mod is a Tron-inspired Minecraft mod built on NeoForge. Its long-term scope includes the Grid dimension, Light Cycles, Grid-themed mobs, structures, resources, and progression.

The mod must feel like Minecraft first. The Grid is an optional path alongside vanilla progression, comparable to the Nether or End: rewarding and substantial, but never a replacement for the Overworld or the player's preferred way to play.

## Design Pillars

- **Identity Disk at the center:** Combat, exploration, Programs, terminals, vehicles, and access to the Grid should connect to the Identity Disk wherever that connection is useful and natural.
- **Player freedom:** Players may rush, speedrun, grind, build, fight, or explore in any order the available resources allow.
- **Resource-driven progression:** Better capabilities require materials, mob drops, or discovered Protocols—not skill trees, achievements, checklists, or arbitrary level gates.
- **Technology, not magic:** Disk upgrades are Programs installed at an Identity Terminal rather than enchantments applied at an enchanting table.
- **Minecraft-first integration:** New systems should complement crafting, exploration, structures, combat, and multiplayer instead of replacing vanilla systems.
- **Skillful combat:** Charging, aiming, catching, and planning ricochets should reward practice.
- **Multiplayer reliability:** Ownership, stored identity, projectiles, dimensions, terminals, and vehicles must remain server-authoritative and safe across saves.

## Core Item: Identity Disk

The Identity Disk is the centerpiece of the mod: a persistent digital identity, throwable weapon, tool, and interface with the Grid.

Current and planned identity data includes:

- Owner UUID and last known player name.
- Unique disk ID and creation date.
- Throws and other usage data.
- Hits and defeated mobs or players.
- Lifetime bounce count.
- Installed Programs and their levels.
- Future permissions, cosmetics, vehicle links, and Grid affiliations where appropriate.

The exact original disk stack must survive throwing, returning, unloading, death, dimension travel, and server restarts without losing its identity, statistics, Programs, durability, or appearance.

## Programs and Protocols

Programs replace traditional enchantments completely.

- A **Program** is an installed behavior or upgrade stored on an Identity Disk.
- A **Protocol** or blueprint is an item or data source used to install or upgrade a Program.
- Programs are installed and managed through a custom computer-like **Identity Terminal**.
- Identity Disks must not receive Programs from enchanting tables or ordinary enchanted books.
- Natural sources include Grid mob drops, Grid resources, and rare Protocols found in structures.
- Installation and upgrades consume Grid materials plus other appropriate resources.
- Stronger Program levels require rarer or larger resource costs, but never achievements, skill trees, or hard progression gates.
- Players may combine and specialize Programs freely except where a clearly documented technical or balance conflict requires exclusivity.

Planned combat Programs:

- **Rebound:** Returns the disk to its owner; higher levels improve return behavior.
- **Velocity:** Increases projectile speed and effective range.
- **Impact:** Increases direct-hit damage and knockback.
- **Ricochet:** Adds one block bounce per level up to the global safety cap.
- **Recall:** Makes the disk return sooner and track its owner more aggressively.
- **Seeking:** Curves toward a nearby valid target after a ricochet.
- **Piercing:** Passes through additional entities before returning.
- **Split Circuit:** Creates temporary secondary disk paths at a significant resource or combat cost.
- **Disruption:** Applies a short digital debuff or disables compatible Grid technology.
- **Perfect Return:** Rewards a well-timed catch with a temporary combat bonus.

Programs should produce readable feedback through the disk tooltip or terminal UI, particles, sounds, and trail colors. Total ricochets, spawned projectiles, targeting work, and projectile lifetime must have server-safe caps.

## Entering the Grid

Grid access should begin with exploration, not automatic teleportation.

- A discoverable Overworld structure contains a dormant portal or activation device.
- The player chooses to insert or use a bound Identity Disk to activate it.
- Crafting or first using a disk never teleports the player automatically.
- The structure should be discoverable through normal exploration; optional clues or maps may help without becoming mandatory gates.
- The activation rules must prevent accidental travel and clearly communicate the destination.
- Entering without a disk, losing a disk inside, and multiplayer portal ownership need explicit, recoverable behavior.

Identity Terminals naturally generate only inside the Grid. After reaching the Grid and gathering its materials, players can craft a terminal for use in the Overworld or another home base.

## Phase 1: Project Foundation

- [x] Create and configure the NeoForge project.
- [x] Set up Git and the public GitHub repository.
- [x] Confirm that the project builds and runs.
- [x] Establish packages for items, entities, data, Programs, networking, and client rendering.
- [x] Add a Tron-themed creative tab, temporary assets, localization, and GameTest foundation.

## Phase 2: Identity Disk MVP

Goal: obtain a personal Identity Disk, inspect its identity, and use it as a melee weapon.

- [x] Register the Identity Disk item, temporary model, texture, and recipe.
- [x] Bind a crafted or first-used disk without allowing silent ownership replacement.
- [x] Persist owner, creation, unique ID, and basic combat/usage statistics.
- [x] Display ownership and selected statistics in the tooltip.
- [x] Give the disk melee damage, durability, and repair behavior.
- [x] Make replacement disks new identities rather than clones of lost disks.
- [ ] Test saving, loading, death, dimension travel, and multiplayer ownership comprehensively.
- [ ] Review whether placement or additional usage events should be recorded.

## Phase 3: Throwable Disk Combat

Goal: make the disk a reliable, server-authoritative projectile with satisfying charge and ricochet mechanics.

- [x] Add the projectile and remove the exact held stack while it is active.
- [x] Add bow-like windup that controls speed, range, and damage.
- [x] Use a side throw animation rather than the overhead trident animation.
- [x] Damage targets, preserve momentum, and ricochet predictably from blocks.
- [x] Give the base disk two ricochets, then embed it for owner pickup like a trident.
- [x] Preserve identity, statistics, durability, and all stack data through a throw.
- [x] Add recoverable timeout behavior, multiplayer synchronization, temporary effects, and GameTests.
- [ ] Add intentional timed catching and interception rules after return behavior is finalized.

## Phase 4: Grid Access Foundation

Goal: establish the optional route into the Grid so Program progression has an in-world source.

- [x] Design and build the discoverable Overworld access structure.
- [x] Add a dormant portal or digitization device to the structure.
- [x] Require deliberate activation with a bound Identity Disk.
- [x] Add clear activation feedback and safe multiplayer behavior.
- [x] Create the Grid dimension type and an initial test biome/terrain palette.
- [x] Implement safe two-way travel with generated arrival platforms and return devices.
- [x] Ensure the player is never teleported merely for crafting or binding a disk.

## Phase 5: Identity Terminal and Programs

Goal: replace magical disk enchantment with resource-driven software installation.

- [x] Remove Identity Disk Programs from enchanting-table and enchanted-book acquisition.
- [x] Add persistent installed-Program data to the Identity Disk.
- [x] Add the Identity Terminal block, block entity, menu, and screen.
- [x] Generate the first Identity Terminal at the Grid arrival site; add terminals to later Grid structures as those structures are built.
- [x] Add a home-terminal recipe requiring Grid-sourced materials.
- [x] Add initial craftable Protocol items using Grid Shards; mob drops and rare structure blueprints remain future sources.
- [x] Implement installation, upgrading, removal, costs, validation, and multiplayer synchronization.
- [x] Migrate Rebound, Velocity, Impact, and Ricochet from the temporary enchantment implementation.
- [x] Implement Recall, Seeking, Piercing, Split Circuit, Disruption, and Perfect Return as Programs.
- [x] Add readable terminal UI, disk tooltips, audiovisual feedback, and GameTests.
- [x] Define only the compatibility restrictions that are necessary for clarity, balance, or server safety.

Implementation status: all ten initial combat Programs use persistent disk data and the server-backed Identity Terminal. Installation consumes a Protocol plus increasing Grid Shard costs; removal consumes one Grid Shard and does not return the Protocol. Seeking and Split Circuit are exclusive because both control post-impact targeting paths.

## Phase 6: Grid World and Resource Loop

Goal: make the Grid an optional but rewarding Minecraft dimension that supports its own resource economy.

- [ ] Add final terrain, sky, lighting, ambient sound, and initial biomes.
- [ ] Add Grid materials, including the resource used for Program installation and terminal crafting.
- [ ] Add structures containing terminals, resources, lore, and rare Protocols.
- [ ] Add hazards and rewards that encourage exploration without prescribing an order.
- [ ] Balance renewable mob/resource sources against rare exploration finds.
- [ ] Optimize emissive effects, structures, and high-speed travel for multiplayer.

## Phase 7: Identity and Player-Facing Systems

- [ ] Add a disk inspection screen.
- [ ] Track expanded lifetime statistics and notable records without using them as progression gates.
- [ ] Add cosmetic circuits, colors, and trails acquired through resources or discovery.
- [ ] Add privacy controls for stored player information.
- [ ] Explore disk-based permissions for Grid doors, vehicles, terminals, and factions.
- [ ] Add safe rebinding/inheritance behavior and administrator recovery tools.

## Phase 8: Programs, Mobs, Structures, and Factions

- [ ] Add a hostile security Program as the first Grid mob.
- [ ] Add friendly, neutral, corrupted, and derezzed variants.
- [ ] Create ranged and disk-wielding enemies.
- [ ] Give mobs meaningful resource and Protocol drops.
- [ ] Add settlements, terminals, patrols, arenas, and other Grid structures.
- [ ] Add optional factions or reputation without turning them into mandatory progression gates.
- [ ] Add elite Programs and bosses with unique rewards.

## Phase 9: Light Cycles and Vehicles

- [ ] Add a responsive rideable Light Cycle with acceleration, braking, turning, mounting, and dismounting.
- [ ] Tie deployment and ownership naturally to the Identity Disk.
- [ ] Add efficient, expiring light trails and define collision/damage rules.
- [ ] Support multiplayer races and combat arenas.
- [ ] Add final models, animations, sounds, and emissive textures.
- [ ] Explore Light Jets, Recognizers, and faction-specific vehicle variants later.

## Phase 10: Polish and Release

- [ ] Replace temporary assets with final models, animations, textures, particles, and sounds.
- [ ] Add accessibility options for flashes, contrast, and screen movement.
- [ ] Add configuration for damage, ricochets, recovery, Program costs, and expensive effects.
- [ ] Continue dedicated-server and compatibility testing.
- [ ] Add optional advancements and an in-game guide for discovery—not hard progression gates.
- [ ] Document installation, configuration, world generation, and server requirements.
- [ ] Create release builds, changelogs, and a public playable release.

## Next Implementation Step

Build Phase 4's minimal Grid access slice before replacing the prototype enchantments:

1. Build the Identity Terminal block, storage, menu, and initial screen.
2. Add persistent Program data to Identity Disks.
3. Migrate Rebound, Velocity, Impact, and Ricochet away from enchantments.
4. Introduce the first Grid material and require it for a home-terminal recipe.

## Open Design Questions

- Can one player own multiple specialized disks, or should one disk remain canonical?
- Which Grid material powers terminals and Program installation, and is it renewable?
- Are Protocols consumed during installation, or are they reusable blueprints while materials pay each cost?
- Can installed Programs be freely removed and reused, or does changing a build consume resources?
- What recovery path exists when a player loses a disk inside the Grid?
- Can thrown disks be intercepted, deflected, stolen temporarily, or disabled?
- Does durability represent physical damage, energy, or both?
- Which Minecraft structure-discovery tools should point toward a Grid access structure?
- Visual direction: Tron: Legacy leads, with selective classic Tron, Tron: Ares, and original elements.
