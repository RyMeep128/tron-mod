# Tron Mod

A Tron-inspired Minecraft mod built with NeoForge.

## Current Features

- Craftable Identity Disc with iron ingots, light blue dye, and a diamond core.
- Persistent owner UUID, owner name, creation date, unique disc ID, and combat statistics.
- Automatic binding to the crafter, with first-use binding as a fallback.
- Owner protection that prevents a bound disc from being silently rebound.
- Melee weapon behavior with iron-tier durability and repair materials.
- Bow-like variable side wind-up: quick tosses travel slowly and deal 3 damage, while full charges launch faster and farther and deal 9 damage, followed by two ricochets, cyan trails, and trident-style embedding/pickup.
- Disk Programs: Rebound returns the disc, Velocity increases throw speed, Impact adds damage, and Ricochet adds extra bounces (capped at eight total). Install them at an Identity Terminal with a disk in one hand and a Protocol in the other.
- Rare Grid access sites now generate in the Overworld. Use a bound Identity Disk on the central device to enter the test Grid; the arrival platform includes a return device.
- Automated GameTests for identity serialization and ownership.

## Development

- Minecraft: 26.1.2
- NeoForge: 26.1.2.94
- Java: 25

Build the mod on Windows:

```powershell
.\gradlew.bat build
```

Run a development client:

```powershell
.\gradlew.bat runClient
```

The built JAR is written to `build/libs/`.

## License

MIT
