package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.item.IdentityDiscItem;
import com.ryanm.tronmod.item.ProtocolItem;
import com.ryanm.tronmod.component.ProgramType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TronMod.MOD_ID);

    public static final DeferredItem<IdentityDiscItem> IDENTITY_DISC = ITEMS.registerItem(
            "identity_disc",
            IdentityDiscItem::new,
            properties -> properties.sword(ToolMaterial.IRON, 2.0F, -2.2F)
    );
    public static final DeferredItem<?> GRID_ACCESS_DEVICE = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_ACCESS_DEVICE);
    public static final DeferredItem<?> IDENTITY_TERMINAL = ITEMS.registerSimpleBlockItem(ModBlocks.IDENTITY_TERMINAL);
    public static final DeferredItem<Item> GRID_SHARD = ITEMS.registerSimpleItem("grid_shard");
    public static final DeferredItem<?> GRID_SHARD_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_SHARD_ORE);
    public static final DeferredItem<?> GRID_STONE = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_STONE);
    public static final DeferredItem<?> CIRCUIT_TILES = ITEMS.registerSimpleBlockItem(ModBlocks.CIRCUIT_TILES);
    public static final DeferredItem<?> GRID_ENERGY_FIELD = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_ENERGY_FIELD);
    public static final DeferredItem<Item> GRID_ALLOY = ITEMS.registerSimpleItem("grid_alloy");
    public static final DeferredItem<?> DARK_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_PANEL);
    public static final DeferredItem<?> POLISHED_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_PANEL);
    public static final DeferredItem<?> REINFORCED_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_PANEL);
    public static final DeferredItem<?> RIBBED_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.RIBBED_PANEL);
    public static final DeferredItem<?> VENT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.VENT_PANEL);
    public static final DeferredItem<?> FLOOR_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.FLOOR_PANEL);
    public static final DeferredItem<?> PLATFORM_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.PLATFORM_PANEL);
    public static final DeferredItem<?> PORTAL_ALLOY = ITEMS.registerSimpleBlockItem(ModBlocks.PORTAL_ALLOY);
    public static final DeferredItem<?> CYAN_CIRCUIT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_CIRCUIT_PANEL);
    public static final DeferredItem<?> WHITE_CIRCUIT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_CIRCUIT_PANEL);
    public static final DeferredItem<?> ORANGE_CIRCUIT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.ORANGE_CIRCUIT_PANEL);
    public static final DeferredItem<?> CYAN_LINE_TILE = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_LINE_TILE);
    public static final DeferredItem<?> WHITE_LINE_TILE = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_LINE_TILE);
    public static final DeferredItem<?> ORANGE_LINE_TILE = ITEMS.registerSimpleBlockItem(ModBlocks.ORANGE_LINE_TILE);
    public static final DeferredItem<?> CYAN_LIGHT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_LIGHT_PANEL);
    public static final DeferredItem<?> WHITE_LIGHT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_LIGHT_PANEL);
    public static final DeferredItem<?> ORANGE_LIGHT_PANEL = ITEMS.registerSimpleBlockItem(ModBlocks.ORANGE_LIGHT_PANEL);
    public static final DeferredItem<?> CYAN_GRID_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_GRID_GLASS);
    public static final DeferredItem<?> WHITE_GRID_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_GRID_GLASS);
    public static final DeferredItem<?> DARK_GRID_GLASS = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_GRID_GLASS);
    public static final DeferredItem<?> CYAN_TOWER_FACADE = ITEMS.registerSimpleBlockItem(ModBlocks.CYAN_TOWER_FACADE);
    public static final DeferredItem<?> WHITE_TOWER_FACADE = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_TOWER_FACADE);
    public static final DeferredItem<?> GRID_ROAD = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_ROAD);
    public static final DeferredItem<?> DATA_CONDUIT = ITEMS.registerSimpleBlockItem(ModBlocks.DATA_CONDUIT);
    public static final DeferredItem<?> DARK_PANEL_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_PANEL_SLAB);
    public static final DeferredItem<?> DARK_PANEL_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_PANEL_STAIRS);
    public static final DeferredItem<?> DARK_PANEL_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_PANEL_WALL);
    public static final DeferredItem<?> POLISHED_PANEL_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_PANEL_SLAB);
    public static final DeferredItem<?> POLISHED_PANEL_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_PANEL_STAIRS);
    public static final DeferredItem<?> POLISHED_PANEL_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.POLISHED_PANEL_WALL);
    public static final DeferredItem<?> REINFORCED_PANEL_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_PANEL_SLAB);
    public static final DeferredItem<?> REINFORCED_PANEL_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_PANEL_STAIRS);
    public static final DeferredItem<?> REINFORCED_PANEL_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.REINFORCED_PANEL_WALL);
    public static final DeferredItem<?> PLATFORM_PANEL_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.PLATFORM_PANEL_SLAB);
    public static final DeferredItem<?> PLATFORM_PANEL_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.PLATFORM_PANEL_STAIRS);
    public static final DeferredItem<?> PLATFORM_PANEL_WALL = ITEMS.registerSimpleBlockItem(ModBlocks.PLATFORM_PANEL_WALL);
    public static final DeferredItem<ProtocolItem> REBOUND_PROTOCOL = protocol("rebound_protocol", ProgramType.REBOUND);
    public static final DeferredItem<ProtocolItem> VELOCITY_PROTOCOL = protocol("velocity_protocol", ProgramType.VELOCITY);
    public static final DeferredItem<ProtocolItem> IMPACT_PROTOCOL = protocol("impact_protocol", ProgramType.IMPACT);
    public static final DeferredItem<ProtocolItem> RICOCHET_PROTOCOL = protocol("ricochet_protocol", ProgramType.RICOCHET);
    public static final DeferredItem<ProtocolItem> RECALL_PROTOCOL = protocol("recall_protocol", ProgramType.RECALL);
    public static final DeferredItem<ProtocolItem> SEEKING_PROTOCOL = protocol("seeking_protocol", ProgramType.SEEKING);
    public static final DeferredItem<ProtocolItem> PIERCING_PROTOCOL = protocol("piercing_protocol", ProgramType.PIERCING);
    public static final DeferredItem<ProtocolItem> SPLIT_CIRCUIT_PROTOCOL = protocol("split_circuit_protocol", ProgramType.SPLIT_CIRCUIT);
    public static final DeferredItem<ProtocolItem> DISRUPTION_PROTOCOL = protocol("disruption_protocol", ProgramType.DISRUPTION);
    public static final DeferredItem<ProtocolItem> PERFECT_RETURN_PROTOCOL = protocol("perfect_return_protocol", ProgramType.PERFECT_RETURN);

    private ModItems() {
    }

    private static DeferredItem<ProtocolItem> protocol(String name, ProgramType program) {
        return ITEMS.registerItem(name, properties -> new ProtocolItem(properties.stacksTo(16), program));
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
