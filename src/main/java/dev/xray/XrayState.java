package dev.xray;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Set;

public final class XrayState {
    public static boolean enabled = false;

    private static final Set<String> HIDDEN = Set.of(
        "stone", "deepslate", "dirt", "grass_block", "gravel", "sand", "sandstone",
        "red_sand", "granite", "diorite", "andesite", "tuff", "netherrack",
        "cobblestone", "cobbled_deepslate", "basalt", "blackstone", "end_stone",
        "clay", "podzol", "mycelium", "coarse_dirt", "calcite", "dripstone_block");

    private static boolean isOre(String path) {
        return path.endsWith("_ore") || path.equals("ancient_debris");
    }

    /** TRUE = force draw, FALSE = force hide, null = normal behaviour. */
    public static Boolean override(BlockState state) {
        if (!enabled) return null;
        String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        if (isOre(path)) return Boolean.TRUE;
        if (HIDDEN.contains(path)) return Boolean.FALSE;
        return null;
    }
}
