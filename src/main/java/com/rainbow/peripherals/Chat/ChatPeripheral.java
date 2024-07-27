package com.rainbow.peripherals.Chat;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.message.MessageType.Parameters;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.rainbow.Bellisimo;

public class ChatPeripheral implements IPeripheral {

    public static final Map<Integer, Map<IComputerAccess, ChatPeripheral>> attachedComputers = new HashMap<>();
    private IComputerAccess computer;
    public boolean open = false;
    public boolean enabled = true;
    
    private World world;
    private BlockPos pos;
    private boolean pocket;

    private Integer id;

    Random rand = new Random();

    public ChatPeripheral(World world, BlockPos pos, boolean pocket) {
        this.world = world;
        this.pos = pos;
        this.pocket = pocket;
		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {on_message(message, sender, params);});
    }

    public void on_message(SignedMessage message, ServerPlayerEntity sender, Parameters params) {
        String username = sender.getNameForScoreboard();
        on_message(message, username, params);
    }

    public void on_message(SignedMessage message, String username, Parameters params) {
        if (!open) {
            return;
        }
        String chat_message = message.getContent().getString(); // get chat message

        computer.queueEvent("chat_message", chat_message, username);
    }

    @Override
    public String getType() {
        return "chat_peripheral";
    }

    @LuaFunction
    public final void open() {
        open = true;
        if (!pocket)
            world.setBlockState(pos, world.getBlockState(pos).with(ChatPeripheralBlock.OPEN, true));
    }

    @LuaFunction
    public final void close() {
        open = false;
        if (!pocket)
            world.setBlockState(pos, world.getBlockState(pos).with(ChatPeripheralBlock.OPEN, false));
    }

    @LuaFunction
    public final boolean send(String message) {
        if (enabled & Bellisimo.do_say) {
            for (ServerPlayerEntity player : Bellisimo.SERVER.getPlayerManager().getPlayerList() ) {
                String msg;

                Bellisimo.LOGGER.info(String.format("Computer %d sent message %s", id, message));

                msg = String.format("<%d>: %s",id, message);
                player.sendMessage(Text.literal(msg));
            }
            for (Map<IComputerAccess, ChatPeripheral> perf : attachedComputers.values()) {
                if (perf.containsKey(computer)) {
                    continue;
                }
                ((ChatPeripheral) perf.values().toArray()[0]).on_message(SignedMessage.ofUnsigned(message), "Computer " + String.valueOf(id), null);
            }
            return true;
        }
        return false;
    }

    // Every TestPeripheral instance is equivalent to every other test peripheral instance, since there is no state
    // held in this peripheral.
    @SuppressWarnings("null")
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
        this.computer = computer;
        id = computer.getID();
        attachedComputers.computeIfAbsent(computer.getID(), HashMap::new).put(computer, this);
    }

    public void detach(IComputerAccess computer) {
        attachedComputers.remove(computer.getID());
        open = false;
    }
}
