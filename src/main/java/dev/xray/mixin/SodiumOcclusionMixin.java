package dev.xray.mixin;

import dev.xray.XrayState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {
    "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache",
    "me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache"
}, remap = false)
public class SodiumOcclusionMixin {
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true, require = 0)
    private void xray$side(BlockState self, BlockGetter view, BlockPos pos, Direction facing,
                           CallbackInfoReturnable<Boolean> cir) {
        Boolean o = XrayState.override(self);
        if (o != null) cir.setReturnValue(o);
    }
}
