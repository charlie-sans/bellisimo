package com.rainbow.peripherals.Chat;

import org.jetbrains.annotations.Nullable;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.AbstractPocketUpgrade;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.upgrades.UpgradeType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ChatPocket extends AbstractPocketUpgrade {
    public ChatPocket(Identifier id, ItemStack item) {
        super(id, UpgradeSpeakerPeripheral.ADJECTIVE, item);
    }

    @Nullable
    @Override
    public IPeripheral createPeripheral(IPocketAccess access) {
        return new ChatPeripheral(access);
    }

    @Override
    public void update(IPocketAccess access, @Nullable IPeripheral peripheral) {
        if (!(peripheral instanceof PocketSpeakerPeripheral)) return;
        ((PocketSpeakerPeripheral) peripheral).update();
    }

    @Override
    public UpgradeType<? extends IPocketUpgrade> getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
