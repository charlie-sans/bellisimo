package com.rainbow;

import com.mojang.brigadier.arguments.*;
import com.rainbow.peripherals.Chat.ChatPeripheral;
import com.rainbow.peripherals.Chat.ChatPeripheralBlock;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import dan200.computercraft.api.ComputerCraftAPI;

import static net.minecraft.server.command.CommandManager.*;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class Register {

    public static final Identifier CHAT_PERIPHERAL_ID = new Identifier(Bellisimo.MOD_NAMESPACE, "chat_peripheral");
    public static final ChatPeripheralBlock CHAT_PERIPHERAL_BLOCK = new ChatPeripheralBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.ANVIL));
    public static final Item CHAT_PERIPHERAL_ITEM = new BlockItem(CHAT_PERIPHERAL_BLOCK, new Item.Settings());
    public static final BlockApiLookup<ChatPeripheral, Direction> CHAT_PERIPHERAL = BlockApiLookup.get(CHAT_PERIPHERAL_ID, ChatPeripheral.class, Direction.class);

    public void register_chat() {
        Registry.register(Registries.BLOCK, CHAT_PERIPHERAL_ID, CHAT_PERIPHERAL_BLOCK);
        Registry.register(Registries.ITEM, CHAT_PERIPHERAL_ID, CHAT_PERIPHERAL_ITEM);
        PeripheralLookup.get().registerForBlocks((world, pos, state, blockEntity, context) -> {
            if (ChatPeripheralBlock.blocks.get(pos) == null) {
                ChatPeripheralBlock.blocks.put(pos, new ChatPeripheral(world, pos, false));
            }
            return ChatPeripheralBlock.blocks.get(pos);
        }, CHAT_PERIPHERAL_BLOCK);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
		.register((itemGroup) -> itemGroup.add(CHAT_PERIPHERAL_ITEM));
    }

    public void register_command() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("bellisimo")
        .executes(ctx -> {
            ctx.getSource().sendFeedback(() -> Text.literal("Say {computer_id} - sends a message to the specified computer\nStop {id?} - prevent the computer from sending messages. Stops all if no id. Is a toggle"), false);
            return 1;
        })

        .then(literal("say")
            .then(argument("id", IntegerArgumentType.integer())
            .then(argument("text", StringArgumentType.string())
            .executes(ctx -> {
                int id = IntegerArgumentType.getInteger(ctx, "id");
                String text = StringArgumentType.getString(ctx, "text");
                if (ChatPeripheral.attachedComputers.containsKey(id)) {
                    ChatPeripheral.attachedComputers.get(id).values().forEach((self) -> {
                        self.on_message(SignedMessage.ofUnsigned(text), ctx.getSource().getPlayer(), null);
                    });
                    ctx.getSource().sendFeedback(() -> Text.literal("Done"), false);
                } else {
                    ctx.getSource().sendFeedback(() ->Text.literal("Computer does not exist or does not have a chat peripheral connected"), false);
                }
                return 1;
            })))
        )

        .then(literal("stop")

            .requires(source -> source.hasPermissionLevel(2)).executes(ctx -> {
                Bellisimo.do_say = !Bellisimo.do_say;
                ctx.getSource().sendFeedback(() -> Text.literal("Done"), true);
                return 1;
            })

            .then(argument("id", IntegerArgumentType.integer())
            .executes(ctx -> {
                int id = IntegerArgumentType.getInteger(ctx, "id");
                if (ChatPeripheral.attachedComputers.containsKey(id)) {
                    ChatPeripheral.attachedComputers.get(id).values().forEach((self) -> {
                        self.enabled = !self.enabled;
                    });
                    ctx.getSource().sendFeedback(() -> Text.literal("Done"), true);
                } else {
                    ctx.getSource().sendFeedback(() -> Text.literal("Computer does not exist"), true);
                }
                return 1;
            }))
        )
        ));
    }
}
