package com.rainbow;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dan200.computercraft.shared.computer.core.ServerComputerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class Bellisimo implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_NAMESPACE = "bellisimo";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAMESPACE);
    //public static final MinecraftServer server = MinecraftServer.;
    public static final Register register = new Register();

	public static boolean do_say = true;

	public static MinecraftServer SERVER;
 
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("initializing the funny");

        register.register_chat();
		register.register_command();

		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			SERVER = server;
		});

		LOGGER.info("the funny initialized");
	}
}
