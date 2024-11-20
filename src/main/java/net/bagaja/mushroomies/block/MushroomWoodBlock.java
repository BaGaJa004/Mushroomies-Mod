package net.bagaja.mushroomies.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolActions;

public class MushroomWoodBlock extends Block {
    public MushroomWoodBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0f)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        );
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float destroySpeed = super.getDestroyProgress(state, player, level, pos);
        ItemStack stack = player.getMainHandItem();

        // If using an axe, mine at normal speed
        if (stack.canPerformAction(ToolActions.AXE_DIG)) {
            return destroySpeed;
        }

        // If using any other tool or hand, mine at reduced speed
        return destroySpeed * 0.5f;
    }
}