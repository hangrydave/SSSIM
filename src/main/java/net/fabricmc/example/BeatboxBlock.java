package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class BeatboxBlock extends Block implements BlockEntityProvider {
    public BeatboxBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BeatboxBlockEntity();
    }
}
