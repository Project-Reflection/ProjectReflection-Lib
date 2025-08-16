package twelvefold.twelvefoldbooter.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twelvefold.twelvefoldbooter.TwelvefoldBooter;
import twelvefold.twelvefoldbooter.api.misc.TwelvefoldMisc;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngame {

    @Redirect(method = "renderHealth",at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/player/EntityPlayer;getHealth()F"),remap = false)
    private float redirect_renderPlayerStats(EntityPlayer instance)
    {
        if(TwelvefoldBooter.config.getModConfig().verboseDebug)
        {
            TwelvefoldBooter.logger.info("Rendering health bar");
        }
        final float originalHealth = instance.getHealth();
        if((!TwelvefoldBooter.config.getModConfig().modifyHealthBar) || (TwelvefoldBooter.config.getModConfig().debug && instance.isSneaking()))
        {
            if(TwelvefoldBooter.config.getModConfig().verboseDebug)
            {
                TwelvefoldBooter.logger.info("Skip modifying health bar");
            }
            return originalHealth;
        }

        final float minHealth= TwelvefoldMisc.getMinHealth(instance);
        final float maxHealth = instance.getMaxHealth();
        final float Ln = (minHealth - 1.0f) * ((minHealth + 2.0f) / 2.0f) / ((maxHealth - 1.0f) * ((maxHealth + 2.0f) / 2.0f));
        final float moddedHealth = maxHealth * Math.min(1.0f, Ln);
        if(TwelvefoldBooter.config.getModConfig().verboseDebug)
        {
            TwelvefoldBooter.logger.info("Original health:{}, Modified health:{}",originalHealth,moddedHealth);
        }
        return instance.isDead ? originalHealth : Math.max(moddedHealth, Float.MIN_NORMAL);
    }
}
