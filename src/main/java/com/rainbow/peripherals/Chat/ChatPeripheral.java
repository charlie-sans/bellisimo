package com.rainbow.peripherals.Chat;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.rainbow.Bellisimo;
import com.rainbow.Register;

public class ChatPeripheral implements IPeripheral {

    private final Set<IComputerAccess> computers = new HashSet<>(1);
    public boolean open = false;
    
    private World world;
    private BlockPos pos;

    public ChatPeripheral(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
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

    @LuaFunction
    public void open() {
        world.setBlockState(pos, world.getBlockState(pos).with(ChatPeripheralBlock.OPEN, true));
    }

    @LuaFunction
    public void close() {
        world.setBlockState(pos, world.getBlockState(pos).with(ChatPeripheralBlock.OPEN, false));
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

    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }
}
