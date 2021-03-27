package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SSIMMod implements ModInitializer {
	public static final String ID = "sssim";

	public static final Block SKIP_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PAUSE_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PREVIOUS_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(ID, "skip_block"), SKIP_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "pause_block"), PAUSE_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(ID, "previous_block"), PREVIOUS_BLOCK);

		Registry.register(Registry.ITEM,
				new Identifier(ID, "skip_block"),
				new BlockItem(SKIP_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "pause_block"),
				new BlockItem(PAUSE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM,
				new Identifier(ID, "previous_block"),
				new BlockItem(PREVIOUS_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

		SpotifyAPI api = SpotifyAPI.INSTANCE;
	}
}
