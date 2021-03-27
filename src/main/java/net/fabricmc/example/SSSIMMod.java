package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SSSIMMod implements ModInitializer {
	public static final String ID = "sssim";

	public static final Block SKIP_BLOCK = new ForwardBlock(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PAUSE_BLOCK = new PauseBlock(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PLAY_BLOCK = new PlayBlock(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PREVIOUS_BLOCK = new PreviousBlock(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block BEATBOX_BLOCK = new BeatboxBlock(FabricBlockSettings.of(Material.PISTON).strength(3.0f));

	public static BlockEntityType<BeatboxBlockEntity> BEATBOX_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(ID, "skip_block"), SKIP_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "pause_block"), PAUSE_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "play_block"), PLAY_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "previous_block"), PREVIOUS_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "beatbox_block"), BEATBOX_BLOCK);

		Registry.register(Registry.ITEM,
				new Identifier(ID, "skip_block"),
				new BlockItem(SKIP_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "pause_block"),
				new BlockItem(PAUSE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "play_block"),
				new BlockItem(PLAY_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "previous_block"),
				new BlockItem(PREVIOUS_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "beatbox_block"),
				new BlockItem(BEATBOX_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

		BEATBOX_BLOCK_ENTITY = Registry.register(
				Registry.BLOCK_ENTITY_TYPE,
				ID + ":beatbox_block_entity",
				BlockEntityType.Builder.create(BeatboxBlockEntity::new, BEATBOX_BLOCK)
						.build(null));

		SpotifyAPI api = SpotifyAPI.INSTANCE;
	}
}
