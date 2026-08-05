# Tron Mod

A Tron-inspired Minecraft mod built with NeoForge.

## Current Features

- Craftable Identity Disc with iron ingots, light blue dye, and a diamond core.
- Persistent owner UUID, owner name, creation date, unique disc ID, and combat statistics.
- Automatic binding to the crafter, with first-use binding as a fallback.
- Owner protection that prevents a bound disc from being silently rebound.
- Melee weapon behavior with iron-tier durability and repair materials.
- Bow-like variable side wind-up: quick tosses travel slowly and full charges launch faster and farther, followed by two ricochets, impact damage, cyan trails, and trident-style embedding/pickup.
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
