# Asset and Localization Conventions

All resource identifiers use the `tronmod` namespace and lowercase `snake_case` paths.

## Resource Layout

- Item models: `assets/tronmod/items/<item_id>.json`
- Item textures: `assets/tronmod/textures/item/<item_id>.png`
- Entity textures: `assets/tronmod/textures/entity/<entity_id>.png`
- Sounds: `assets/tronmod/sounds/<feature>/<sound_id>.ogg`
- English localization: `assets/tronmod/lang/en_us.json`
- Data files: `data/tronmod/<registry>/<path>.json`

## Localization Keys

- Items: `item.tronmod.<item_id>`
- Entities: `entity.tronmod.<entity_id>`
- Creative tabs: `itemGroup.tronmod.<tab_id>`
- Enchantments: `enchantment.tronmod.<enchantment_id>`
- UI text: `gui.tronmod.<screen>.<element>`
- Tooltips: `tooltip.tronmod.<feature>.<line>`

Temporary assets should keep their final resource identifier so they can be replaced without changing code or saved data. Tron: Legacy is the leading visual reference, with selected elements from other Tron eras used where they fit the mod's overall style.
