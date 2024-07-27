package com.rainbow.peripherals.Chat;

import javax.annotation.Nullable;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.shared.pocket.peripherals.PocketSpeakerPeripheral;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChatPocketPeripheral extends ChatPeripheral {
    private final IPocketAccess access;
    private @Nullable World level;
    private Vec3d position = Vec3d.ZERO;

    public ChatPocketPeripheral(IPocketAccess access) {
        super(null, null, true);
        this.access = access;
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof PocketSpeakerPeripheral;
    }

    public void update() {
        var entity = access.getEntity();
        if (entity != null) {
            level = entity.level();
            position = entity.position();
        }
    }
}