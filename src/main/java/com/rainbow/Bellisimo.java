package com.rainbow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dan200.computercraft.api.ComputerCraftAPI;
import com.rainbow.peripherals.TestPeripheralProvider;
import com.rainbow.peripherals.TestTurtleUpgrade;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
//import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class Bellisimo implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_NAMESPACE = "bellisimo";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAMESPACE);


    public static final Identifier CHAT_PERIPHERAL_ID = new Identifier(MOD_NAMESPACE, "test_peripheral");
    // Declare block singleton to be used as peripheral block, with appropriate block settings.
    public static final Block CHAT_PERIPHERAL_BLOCK = new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS));
    // Declare item singleton that will represent our new block.
    public static final Item CHAT_PERIPHERAL_ITEM = new BlockItem(CHAT_PERIPHERAL_BLOCK, new Item.Settings());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("initializing Bellisimo");

		// For minecraft to use the new block, it must be registered with an associated identifier.
        Registry.register(Registries.BLOCK, CHAT_PERIPHERAL_ID, CHAT_PERIPHERAL_BLOCK);
        // Also register the item associated with our block. Without this the new block could be /setblock'd into the
        // world, but not held in an inventory.
        Registry.register(Registries.ITEM, CHAT_PERIPHERAL_ID, CHAT_PERIPHERAL_ITEM);
        // The model, texture, and loot table for this block are defined with .json files. see resources/assets/cc_test

        // Register the peripheral provider that will connect our block to our peripheral.
        ComputerCraftAPI.registerPeripheralProvider(new ChatPeripheralProvider());
        // Register the turtle upgrade too.
        ComputerCraftAPI.registerTurtleUpgrade(new ChatTurtleUpgrade());

		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
			String chat_message = message.getContent().getString(); // TODO: make this do shit
		});
	}
}
