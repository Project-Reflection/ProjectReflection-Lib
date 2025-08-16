package twelvefold.twelvefoldbooter.api.misc;

import ichttt.mods.firstaid.api.CapabilityExtendedHealthSystem;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import twelvefold.twelvefoldbooter.api.LateMixinLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.function.Predicate;

public class TwelvefoldMisc{
    public static final Random structureRandom=new Random();
    public static Predicate<String> getStringPredicate(LateMixinLoader lateMixinLoader, Class<?> clazz) throws NoSuchMethodException {
        Predicate<String >shouldMixinConfigQueue=x->true;
        String methodName= lateMixinLoader.shouldMixinConfigQueue();
        if(!methodName.isEmpty())
        {
            Method method= clazz.getMethod(methodName,String.class);
            shouldMixinConfigQueue=x-> {
                try {
                    return (boolean) method.invoke(null,x);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };
        }
        return shouldMixinConfigQueue;
    }
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[4096];
        int read;
        while ((read=inputStream.read(buffer))!=-1)
        {
            byteArrayOutputStream.write(buffer,0,read);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static float getMinHealth(EntityPlayer entityPlayer)
    {
        float minHealth=entityPlayer.getHealth();
        float maxHealth=entityPlayer.getMaxHealth();
        if(Loader.isModLoaded("firstaid") && entityPlayer.hasCapability(CapabilityExtendedHealthSystem.INSTANCE,null))
        {
            AbstractPlayerDamageModel model=entityPlayer.getCapability(CapabilityExtendedHealthSystem.INSTANCE,null);
            assert model != null;
            minHealth=Math.min(minHealth,model.HEAD.currentHealth/model.HEAD.getMaxHealth()*maxHealth);
            minHealth=Math.min(minHealth,model.BODY.currentHealth/model.BODY.getMaxHealth()*maxHealth);
        }
        return minHealth;
    }
}
