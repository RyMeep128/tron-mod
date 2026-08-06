# Tron Mod Roadmap

## Product Vision

Tron Mod is a Minecraft-first Tron sandbox built on NeoForge. It adds an optional path alongside vanilla progression rather than replacing it with a quest campaign, a second tool ladder, or a mandatory progression tree.

The Identity Disc is the center of the mod. A player's permanent disc learns modular Programs, while a limited active loadout determines what the disc can currently do. Combat, exploration, utility, terminals, Grid resources, and future systems should deepen that relationship without turning the disc into a universal automation item.

The defining rule is:

> The disc learns permanently, the player chooses temporarily, and Programs become powerful through combinations.

## Design Pillars

- Minecraft first: complement vanilla building, combat, farming, mining, and exploration.
- Player freedom: no quests, achievements, or arbitrary gates are required for progression.
- Identity is permanent: ownership, learned Programs, loadouts, and statistics must survive every normal failure mode.
- A few deep systems: prefer meaningful combinations over large counts of blocks, mobs, currencies, or upgrades.
- Strong world rules: the Grid is thin, physical Grid matter is unstable elsewhere, and the void restores rather than kills.
- Server authority: projectiles, ownership, targeting, travel, recovery, and terminal changes are validated by the server.
- KISS: version one must be small, reliable, testable, and unmistakably Tron.

## Version-One Scope

Version one contains:

1. A reliable, identity-bound throwable disc.
2. Explicit projectile states and skillful disc defense.
3. Permanent Program learning with a limited active loadout.
4. A small set of combinable combat and utility Programs.
5. Identity Terminals for installation, configuration, and charging.
6. A sparse, thin-surface Grid over the void.
7. Safe entry, exit, void recovery, death recovery, and disc recovery.
8. An Ancient City-associated access structure and built-in disc homing.
9. One hostile and one neutral Grid Program.
10. Grid matter instability outside its native dimension.

Light Cycles, batons, bosses, large settlements, structure schematics, advanced civilization simulation, and Create integration are deferred.

## Compatibility Policy for This Redesign

This pivot is a deliberate clean break. Existing development worlds, generated Grid chunks, old discs, installed Program data, terminal inventories, and previous world-generation layouts do not require migration or preservation.

- World generation may be replaced in place rather than versioned around old chunks.
- The old downtown, repeating city lattice, and `(8192, 8192)` arrival may be removed outright.
- Disc and Program data components may adopt a clean schema without compatibility adapters.
- Registry IDs should remain stable when keeping an existing concept is useful, but obsolete experimental content may be removed after checking compile-time references.
- Testing targets newly created worlds and newly created items.
- Public release compatibility becomes mandatory only after the redesigned foundation is declared stable.

## The Core Loop

1. Craft and bind an Identity Disc.
2. Follow its built-in homing signal to a Grid access structure.
3. Enter the Grid by choice.
4. Explore sparse sectors, small settlements, facilities, and landmarks.
5. Find Protocols and collect code fragments from hostile Programs.
6. Teach permanent Programs to the disc at an Identity Terminal.
7. Select a limited active loadout.
8. Experiment with Program combinations in combat and utility work.
9. Return safely to the original access point.
10. Bring knowledge and identity home while unstable Grid matter gradually derezzes.

The Grid's primary permanent reward is capability, not superior material stacks.

## Canonical System Rules

### Identity Disc

- One disc is bound to one owner and cannot be silently reassigned.
- Throwing uses the exact item stack; returning never creates a replacement copy.
- The disc cannot permanently break and does not use ordinary tool durability.
- Basic throwing and return remain available without Program energy.
- Ownership, identity, known Programs, active Programs, loadouts, and statistics persist through saves, dimensions, death, disconnects, and server restarts.
- Loss, invalid projectile state, a full inventory, or an unloaded owner must lead to safe recovery without duplication.

### Projectile State Model

The projectile must use explicit states rather than one universal impact response:

- Outbound
- Seeking
- Ricocheting
- Piercing
- Embedded
- Deflected
- Returning
- Recovering
- Invalid or expired

Collision behavior is selected from the current state. Piercing continues forward through valid targets and must never inherit an unconditional velocity reversal.

### Programs and Protocols

- Protocols teach Programs; the disc stores the learned knowledge permanently.
- Known Programs and active Programs are separate collections.
- Active slots create the primary build constraint.
- Programs do not have numbered levels in version one.
- Variants, if ever introduced, must be different algorithms with tradeoffs rather than larger numbers.
- Program energy pays for advanced actions, while basic throws and return remain free.
- Terminals recharge energy; passive Grid recharge may be added after tuning.
- Shared behavior stages and target validators should create combinations instead of hardcoded recipes for every combination.

### Shared Target Validation

Automated combat behavior rejects the owner, friendly players, teammates, allied/tamed entities, spectators, invulnerable targets, removed entities, and passive animals unless explicitly enabled. Block interaction uses tags, permission hooks, configurable ranges, and safety caps rather than hardcoded block lists.

### Grid Dimension

- Natural terrain is approximately one block thick above the void.
- There is no conventional underground, cave system, or complete mining dimension.
- The Grid is vast, sparse, quiet, dark, artificial, and available for player development.
- Negative space, controlled emissive accents, long sight lines, ambient hums, beams, and occasional silhouettes establish the atmosphere.
- Generated content favors open sectors, paths, small settlements, isolated terminals, ruins, patrol posts, facilities, regional towers, and rare landmarks.
- Giant procedural downtowns and cities repeated every 10,000 blocks are removed.
- The player's constructed Grid should eventually be more developed than the naturally generated world.

### System Origin

A safe landmark at or near `(0, 0)` serves as the Grid's system origin. It needs a recognizable vertical silhouette, light beam, distinctive lighting and sound, an Identity Terminal, safe return access, and validated recovery space. It may support future restoration registration or optional boss activation, but those uses are not version-one requirements.

### Void and Death Recovery

- Falling below the configured Grid recovery height does not create a death.
- Void damage is stopped, motion and fall state are cleared, inventory is preserved, and the player is restored quickly to a safe position at the system origin.
- A normal death inside the Grid always preserves the exact Identity Disc and all of its data.
- The disc returns after respawn even if ordinary inventory follows vanilla gamerules.
- Restoration feedback is brief, readable, and configurable.

### Grid Access and Return

- The primary access structure generates independently near an Ancient City rather than modifying its template directly.
- An optional rare village entrance may remain secondary, never required.
- A newly bound disc has built-in, non-Program homing toward the nearest valid access structure.
- Homing uses structure tags or stored references, not blind block scanning.
- Entry stores the player's source dimension, access position or ID, safe return position, and useful validation metadata.
- Exit returns that player to their original access site when safe.
- Missing or destroyed access sites use a safe fallback.
- Travel never clears terrain, replaces a world-spawn platform, or traps the player inside blocks.

### Identity Terminals

The initial terminal interface supports:

1. Installing a Protocol with the required fragments.
2. Viewing permanently known Programs.
3. Selecting and removing active Programs.
4. Recharging disc energy.

Saved and renamed loadouts, two-disc synchronization, restoration registration, and repair utilities follow after the core is reliable.

Terminals are not initially craftable. They are difficult but possible to relocate: a netherite pickaxe and Silk Touch are required for the terminal drop; failure yields fragments or destroys it. Hardness, feedback, and practical mining time require gameplay tuning.

### Grid Matter

- The disc, its knowledge, identity data, non-Grid items, and explicitly stable-tagged items remain permanent outside the Grid.
- Tagged Grid materials, building blocks, and decorative matter gradually derezz outside the Grid.
- Instability applies consistently to inventories, containers, item entities, and placed blocks.
- Stability persists through breaking, placing, stacking, chunk unloading, and server restarts without reset exploits.
- Decay has clear staged audiovisual warnings and a configurable duration, initially targeting one or two Minecraft days.
- Returning unstable matter to the Grid stops decay and gradually restores stability.
- Grid blocks remain permanently stable inside the Grid so players can build freely there.

## Initial Program Library

The first polished set is intentionally small:

### Combat and Information

- Seeking: redirects toward a validated target.
- Ricochet: enables controlled surface or target bounces.
- Piercing: passes through valid entities or materials.
- Split Circuit: creates capped temporary trajectories.
- Retrieval: gathers valid item entities during return.
- Mark: records a selected target for other targeting behavior.
- Deflection: modifies defensive projectile interactions.
- Identity Scan: reports useful information about Grid entities or targets.

### Mining and Farming

- Mining: breaks valid tagged ore, respects tool level and permissions, and leaves drops unless Retrieval is active.
- Harvest: targets only mature supported crops.
- Replant: consumes valid seeds and obeys normal planting rules.

### Initial Building Assistance

- Measure is the preferred first building Program: temporary distance, alignment, spacing, and symmetry guides without automatic construction.

Blueprint previews, Align, Mirror, Replace, Pattern, Scaffold, Pulse, Relay, Route, Echo, and advanced Scan behaviors remain later candidates.

### Target Combinations

- Mining + Seeking + Retrieval: mines capped valid ore targets and returns drops.
- Harvest + Seeking + Retrieval: gathers mature crops and returns produce.
- Harvest + Replant + Retrieval: maintains a field and returns excess items.
- Seeking + Ricochet + Mark: creates readable indirect combat paths.
- Mark + Seeking + Split Circuit: focuses capped trajectories on one target.

These combinations arise from shared rules. They are not hidden bonuses or separately implemented abilities.

## Disc Combat and Grid Mobs

- Holding use with the disc enters a visible defensive stance.
- Incoming hostile discs can be deflected only within a server-validated facing arc.
- Straight throws are readable; angles, elevation, ricochets, and timing create counterplay.
- Seeking remains readable and counterable after deflection.
- Grid Programs take normal or enhanced disc damage and initially take approximately 25% damage from other sources, configurable through a shared rule.
- Version one adds one hostile Program that drops code fragments and one neutral Program with simple world behavior.
- Absolute immunity and large mob rosters are avoided.

## Art and Content Budget

- Standard block textures return to 16×16.
- Spectacle comes from emissive overlays, animation, particles, models, lighting, and sound.
- Target roughly 5–10 essential block families with slabs, stairs, walls, and directional states providing variety.
- Cyan and white illumination remain restrained; orange is an uncommon authority accent and magenta indicates instability.
- Existing 32×32 construction assets may remain temporarily while replacement work is underway, but they are not the new content target and require no compatibility path.

## Pivot Audit: Existing Systems to Keep, Refactor, or Retire

### Keep and Harden

- NeoForge project, GitHub Actions, registries, creative tab, and GameTest foundation.
- Bound disc identity and exact-stack projectile handoff.
- Charge-based side throw, embedding, trails, and baseline ricochet behavior.
- Grid dimension registration, terminal plumbing, Protocol items, and server-backed menus.
- Existing textures and blocks only where they fit the reduced palette after review.

### Refactor

- Installed Program levels are replaced by permanent known Programs plus a limited active set; old Program data does not need migration.
- Projectile collision becomes an explicit state machine.
- Target selection moves into a shared validation service.
- Normal durability becomes rechargeable Program energy without permanent breakage.
- Terminal installation and loadout logic move into focused services.
- Portal travel stores per-player source and return data.
- Grid generation becomes a thin, sparse surface centered on the system origin.
- Large catch-all GameTests split into focused behavioral suites.

### Retire or Disable

- The 192×192 downtown and repeating 10,000-block city lattice.
- The `(8192, 8192)` canonical arrival anchor.
- Heavy multi-biome terrain that behaves like an ordinary full-depth dimension.
- Numbered Program levels and repeated upgrade costs.
- Identity Disc durability and breaking.
- Initially craftable home terminals.
- Broad 24-material/32×32 construction-set expansion as a version-one priority.
- Any portal behavior that clears terrain or returns everyone to Overworld spawn.

## Implementation Roadmap

### Phase 1 — Stabilize Existing Systems

Goal: make ownership, throwing, recovery, and travel safe before expanding behavior.

- [ ] Store each player's entry dimension, access structure, safe return coordinates, and facing.
- [ ] Return players to their own validated entry point; add a safe fallback.
- [ ] Remove all world-spawn terrain replacement and arrival-area clearing.
- [ ] Guarantee exact-disc recovery for disconnects, death, full inventories, dimension changes, expiration, and server restart.
- [ ] Remove permanent disc durability and establish the minimum energy data model.
- [ ] Correct Piercing so it visibly continues forward.
- [ ] Introduce explicit projectile states and state-based impact handling.
- [ ] Centralize entity and block target validation.
- [ ] Split dense projectile, terminal, portal, and recovery logic into focused services.
- [ ] Add focused regression GameTests for every safety rule.

Exit criteria: the disc cannot duplicate, disappear, rebind, or permanently break, and Grid travel never damages unrelated terrain or loses the player's return point.

### Phase 2 — Thin Grid Foundation

Goal: replace the city-focused Grid with a sparse digital surface over the void.

- [ ] Delete or disable giant downtown and repeating-city generation; legacy chunks do not need retrofitting or preservation.
- [ ] Replace natural terrain with an approximately one-block-thick generated surface.
- [ ] Remove conventional underground resources and cave-like generation.
- [ ] Move the canonical Grid origin and arrival to the safe structure near `(0, 0)`.
- [ ] Build the system-origin landmark, terminal, return device, beam, sound, and safe restoration area.
- [ ] Implement fast non-death void recovery with complete inventory preservation.
- [ ] Ensure normal Grid death preserves and restores the exact disc.
- [ ] Establish sparse paths, isolated facilities, ruins, and open construction sectors.
- [ ] Reduce the block palette toward 5–10 coherent 16×16 families.
- [ ] Tune sky, fog, ambient audio, sparse particles, and emissive density.

Exit criteria: a fresh Grid is thin, safe, sparse, performant, clearly artificial, and leaves most development to players.

### Phase 3 — Access Structure and Homing

Goal: make discovering and traveling to the Grid robust in vanilla and modpacks.

- [ ] Generate an independent access structure in the same broad region as an Ancient City.
- [ ] Register access structures through tags and stable registry keys.
- [ ] Add built-in disc homing with directional pulses and proximity feedback.
- [ ] Keep any village entrance optional and secondary.
- [ ] Validate structure location without scanning for individual blocks.
- [ ] Add modpack-safe fallback discovery and travel behavior.

Exit criteria: a newly bound disc can guide a player to reliable access without quests, coordinates, village dependency, or unsafe terrain edits.

### Phase 4 — Permanent Program Framework

Goal: establish the modular known-versus-active Program model.

- [ ] Replace the experimental Program schema directly; legacy discs do not require migration.
- [ ] Replace numbered levels with one learned state per Program.
- [ ] Store known Programs, active slots, energy, and schema version on the disc.
- [ ] Add a small fixed active-slot limit.
- [ ] Refactor Programs into targeting, movement, collision, effect, return, block interaction, entity interaction, information, and retrieval stages.
- [ ] Update the terminal to install permanent knowledge and configure active Programs.
- [ ] Add readable conflicts, requirements, energy cost, and synchronization.
- [ ] Preserve Program data through every disc and player lifecycle event.

Exit criteria: a disc may know many Programs, expose only a limited active loadout, and combine behaviors without numbered upgrades or brittle special cases.

### Phase 5 — Core Program Set

Goal: prove that two- and three-Program combinations are useful, readable, and safe.

- [ ] Finish Seeking, Ricochet, Piercing, Split Circuit, Retrieval, Mark, Deflection, and Identity Scan.
- [ ] Add Mining with tags, tool checks, permissions, range, and per-throw caps.
- [ ] Add Harvest and Replant with maturity and seed validation.
- [ ] Add Measure as the first limited building-assistance Program.
- [ ] Implement shared item retrieval and drop ownership rules.
- [ ] Add defensive stance, facing-arc deflection, and clear audiovisual feedback.
- [ ] Test the target combinations documented above without adding hidden combination bonuses.

Exit criteria: the core Program set supports distinct combat, mining, farming, and measurement loadouts without unattended automation.

### Phase 6 — Terminal and Fragment Loop

Goal: connect exploration and combat to permanent capability acquisition.

- [ ] Add code or identity fragments dropped by hostile Grid Programs.
- [ ] Require a Protocol plus fragments for first-time learning.
- [ ] Remove repeated numbered upgrade costs.
- [ ] Add terminal energy recharge.
- [ ] Disable terminal crafting for version one.
- [ ] Require netherite plus Silk Touch for terminal relocation.
- [ ] Add breaking feedback and fragment fallback drops.
- [ ] Add saved loadouts after the basic terminal workflow is stable.
- [ ] Defer two-disc synchronization unless the core schedule permits it.

Exit criteria: exploration provides Protocols, combat provides focused installation material, and terminals teach permanent knowledge without becoming a generic currency shop.

### Phase 7 — Grid Matter Instability

Goal: enforce the rule that knowledge crosses worlds permanently while physical Grid matter does not.

- [ ] Define stable and unstable item/block tags.
- [ ] Add persistent stability data to unstable stacks and placed blocks.
- [ ] Implement efficient indexed or scheduled decay outside the Grid.
- [ ] Support inventories, containers, dropped items, and placed blocks consistently.
- [ ] Preserve remaining stability through stacking, breaking, placing, unloading, and restart.
- [ ] Add staged tooltips, visuals, particles, and sound warnings.
- [ ] Recharge matter gradually after it returns to the Grid.
- [ ] Add server configuration for timing, tags, warnings, and exemptions.

Exit criteria: decay is predictable, visible, exploit-resistant, performant, and reversible by returning matter to the Grid.

### Phase 8 — A Basic Living Grid

Goal: make the sparse world feel inhabited without building a full civilization simulator.

- [ ] Add one hostile Program with fragment drops and shared Grid damage rules.
- [ ] Add one peaceful or neutral Program.
- [ ] Add small settlements, isolated inhabited facilities, paths, caches, and patrol posts.
- [ ] Give neutral Programs a few simple behaviors around paths and terminals.
- [ ] Improve combat, ambience, structure, and restoration feedback.
- [ ] Complete multiplayer, dedicated-server, accessibility, and configuration passes.

Exit criteria: the Grid has a small but coherent ecology and exploration loop while remaining spacious and player-buildable.

## Required Test Suites

- Disc identity: exact-stack recovery, persistence, ownership, duplication prevention, full inventory, disconnect, dimension change, death, and restart.
- Projectile behavior: every ricochet face, true Piercing, Seeking validation, deflection, finite lifetime, safety caps, and unloaded chunks.
- Grid travel: stored entry, per-player exit, destroyed-access fallback, no terrain overwrite, multiplayer independence, and safe placement.
- Grid recovery: void restoration, preserved inventory, preserved disc, safe system origin, and normal Grid death.
- Terminal behavior: permanent learning, fragment costs, active selection, persistence, concurrency, ownership, recharge, and relocation rules.
- Utility Programs: mining tags and drops, Retrieval combinations, crop maturity, seed consumption, permissions, range, and caps.
- Matter stability: every storage form, placed blocks, stack merging, conversion, recharge, restart, chunk unloading, and stable-tag exemptions.

## Deferred Backlog

- Light Cycles and focused vehicle batons.
- Corrupted Administrator and Recognizer encounters.
- Saved loadouts if not completed in version one.
- Two-disc Program synchronization.
- Regional restoration relays.
- Authored structure schematics and reconstruction devices.
- Player-created blueprints and automatic megastructures.
- Large settlements, factions, quests, and civilization simulation.
- Optional Create add-on for stabilizing or fabricating Grid matter.
- Moving Create contraption collision and other deep mod integrations.

## KISS Review

Before accepting a feature, ask:

1. Does it strengthen the Identity Disc or the Grid's identity?
2. Does it create a meaningful player choice?
3. Can it reuse an existing system and be explained briefly?
4. Is it required for version one?
5. Does it introduce another progression tree, currency, major UI, or energy network?
6. Does it automate away meaningful play?
7. Will it create extensive special cases or compatibility maintenance?
8. Can it be tested reliably?

Defer features that duplicate vanilla tools without a distinct disc interaction, require dozens of content entries, depend deeply on optional mods, or exist mainly for hypothetical future use.

## Immediate Next Step

Begin Phase 1 with a read-only audit of portal travel, disc recovery, projectile collision, Program persistence, and test coverage. Then implement Grid entry/return safety first, because the current world-spawn platform replacement is the highest-risk behavior in the existing codebase.
