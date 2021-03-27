package net.fabricmc.example;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;

public class BeatboxBlockEntity extends ChestBlockEntity {
    private static final double MUSIC_DISTANCE = 10.0d;

    public BeatboxBlockEntity() {
        super(SSSIMMod.BEATBOX_BLOCK_ENTITY);
    }

    private PlayerEntity player;
    private int tick = 0;

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient)
            return;

        tick++;
        if (tick % 10 != 0)
            return;

        if (player == null) {
            player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), MUSIC_DISTANCE, false);
            if (player == null)
                return;
        }

        double distance = pos.getSquaredDistance(player.getPos(), true);
        if (distance > MUSIC_DISTANCE) {
            player = null;
            SpotifyAPI.INSTANCE.setVolume(0);
        } else {
            int percentage = (int) (100.0d - ((distance / MUSIC_DISTANCE) * 100.0d));
            SpotifyAPI.INSTANCE.setVolume(percentage);
        }
    }
}
