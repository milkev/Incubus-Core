package net.id.incubus_core.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.misc.WorthinessChecker;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@SuppressWarnings("ConstantConditions")
@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public class CapeMixin {
    @Inject(
            method = "getCapeTexture",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir){
        var cape = WorthinessChecker.getCapeType(((Entity) (Object) (this)).getUuid());
        if(cape.render) {
            cir.setReturnValue(cape.capePath);
            cir.cancel();
        }
        else if(IncubusCore.bypassWorthiness) {
            cir.setReturnValue(new Identifier("incubus_core", "textures/capes/immortal.png"));
            cir.cancel();
        }
    }
}
