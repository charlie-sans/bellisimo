package com.rainbow;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bellisimo implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("bellisimo");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("initializing Bellisimo");

		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
			LOGGER.info(message.getContent().getString()); // TODO: make this do shit
			return;
		});
	}
}
