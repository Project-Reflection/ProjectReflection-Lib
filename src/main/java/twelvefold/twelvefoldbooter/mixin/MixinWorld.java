package twelvefold.twelvefoldbooter.mixin;

import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twelvefold.twelvefoldbooter.TwelvefoldBooter;
import twelvefold.twelvefoldbooter.api.misc.TwelvefoldMisc;

import java.security.SecureRandom;
import java.util.Random;

@Mixin(World.class)
public abstract class MixinWorld {

    /**
     * @author Project Reflection
     * @reason fix RANDAR exploit
     */
    @Overwrite
    public Random setRandomSeed(int seedX, int seedY, int seedZ) {

        long j2 = (long) seedX * 0x4F_9939_F508L //magic numbers
                + (long) seedY * 0x1E_F156_5BD5L
                + (long) seedZ
                + this.getWorldInfo().getSeed() ;
        TwelvefoldMisc.structureRandom.setSeed(j2);
        if (TwelvefoldBooter.config.getModConfig().verboseDebug) {
            TwelvefoldBooter.logger.info("Setting structure random:seed={}",j2);
        }
        return TwelvefoldMisc.structureRandom;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void postInit(CallbackInfo ci) {
        if (TwelvefoldBooter.config.getModConfig().verboseDebug) {
            TwelvefoldBooter.logger.info("Setting world random");
        }
        rand = new SecureRandom();
    }

    @Shadow
    public abstract WorldInfo getWorldInfo();

    @Shadow
    @Final
    @Mutable
    public Random rand;
}
