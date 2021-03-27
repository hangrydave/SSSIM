package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class SSIMMod implements ModInitializer {
	public static final Block SKIP_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PAUSE_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));
	public static final Block PREVIOUS_BLOCK = new Block(FabricBlockSettings.of(Material.PISTON).strength(3.0f));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
	}
}
