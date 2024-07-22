package com.rainbow;

import com.rainbow.peripherals.Chat.ChatPeripheral;
import com.rainbow.peripherals.Chat.ChatPeripheralBlock;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
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
            Bellisimo.LOGGER.info(pos.toString());
            Bellisimo.LOGGER.info(world.toString());
            Bellisimo.LOGGER.info("New Perf -----------------------------------------------------------------------");
            return new ChatPeripheral(world, pos);
        }, CHAT_PERIPHERAL_BLOCK);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
		.register((itemGroup) -> itemGroup.add(CHAT_PERIPHERAL_ITEM));
    }
}
