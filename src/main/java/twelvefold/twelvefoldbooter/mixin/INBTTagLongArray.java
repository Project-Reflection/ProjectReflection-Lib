package twelvefold.twelvefoldbooter.mixin;

import net.minecraft.nbt.NBTTagLongArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NBTTagLongArray.class)
@FunctionalInterface
public interface INBTTagLongArray {
    @Accessor("data")
    long[] getData();
}
