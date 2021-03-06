package me.dev.legacy.api.mixin.mixins;

import com.mojang.authlib.GameProfile;
import me.dev.legacy.api.event.events.move.PlayerJumpEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityPlayer.class})
public abstract class MixinEntityPlayer extends EntityLivingBase {
    EntityPlayer player;

    public MixinEntityPlayer(World worldIn, GameProfile gameProfileIn) {
        super(worldIn);
    }

    @Inject(
            method = {"jump"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void onJump(CallbackInfo ci) {
        if (Minecraft.func_71410_x().field_71439_g.func_70005_c_() == this.func_70005_c_()) {
            MinecraftForge.EVENT_BUS.post(new PlayerJumpEvent(this.field_70159_w, this.field_70181_x));
        }

    }
}
