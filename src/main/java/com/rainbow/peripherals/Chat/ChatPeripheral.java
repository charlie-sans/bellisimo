package com.rainbow.peripherals.Chat;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.rainbow.Bellisimo;

public class ChatPeripheral implements IPeripheral {

    public ChatPeripheralBlock block = null;
    private final Set<IComputerAccess> computers = new HashSet<>(1);

    public ChatPeripheral() {
		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
			String chat_message = message.getContent().getString(); // get chat message
            String username = sender.getNameForScoreboard();
            //Bellisimo.client
            for (var computer : computers) {
                computer.queueEvent("chat_message", chat_message, username);
            }
		});
    }

    @Override
    public String getType() {
        return "chat_peripheral";
    }

    // Every TestPeripheral instance is equivalent to every other test peripheral instance, since there is no state
    // held in this peripheral.
    @Override
    public boolean equals(IPeripheral other) {
        if(other instanceof ChatPeripheral) {
            return true;
        }
        return false;
    }

    @Override
    public Object getTarget() {
        return ChatPeripheralBlock.class;
    }

    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }
}
