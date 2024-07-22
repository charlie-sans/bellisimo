package com.rainbow.peripherals.Chat;
// package com.rainbow.peripheral.Chat;

// import dan200.computercraft.api.peripheral.IPeripheral;
// import com.rainbow.Bellisimo;
// import net.minecraft.block.BlockState;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.Direction;
// import net.minecraft.world.World;

// public class ChatPeripherialProvider implements IPeripheralProvider {
//     // In this simple case the peripheral has no state, so only one instance is needed.
//     private static final IPeripheral CHAT_PERIPHERAL = new ChatPeripheral();

//     // Computer Craft will call this method for blocks placed next to a computer or modem to find a peripheral.
//     // If whatever is at blockPos is something we want to provide a peripheral for, we return the peripheral.
//     // If not, return null and Computer Craft will go on to check other registered providers.
//     @Override
//     public IPeripheral getPeripheral(World world, BlockPos blockPos, Direction direction) {
//         BlockState blockState = world.getBlockState(blockPos);

//         if(blockState.getBlock().is(Bellisimo.CHAT_PERIPHERAL_BLOCK)) {
//             return CHAT_PERIPHERAL;
//         }

//         return null;
//     }
// }