package twelvefold.twelvefoldbooter.api.misc;

import net.minecraft.nbt.*;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import twelvefold.twelvefoldbooter.mixin.INBTTagLongArray;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings({"deprecation","unused"})
public final class NBTUtils {
    private NBTUtils(){}

    public static Object nbtToNative(NBTBase nbt) throws IllegalAccessException {
        switch (nbt.getId())
        {
            case 1:
                return ((NBTTagByte)nbt).getByte();
            case 2:
                return ((NBTTagShort)nbt).getShort();
            case 3:
                return ((NBTTagInt)nbt).getInt();
            case 4:
                return ((NBTTagLong)nbt).getLong();
            case 5:
                return ((NBTTagFloat)nbt).getFloat();
            case 6:
                return ((NBTTagDouble)nbt).getDouble();
            case 7:
                return ((NBTTagByteArray)nbt).getByteArray();
            case 8:
                return ((NBTTagString)nbt).getString();
            case 9: {
                ArrayList<Object> list=new ArrayList<>();
                for(NBTBase nbtBase : (NBTTagList)nbt){
                    list.add(nbtToNative(nbtBase));
                }
                return list;
            }
            case 10:
                return nbtToDocument((NBTTagCompound) nbt);
            case 11:
                return ((NBTTagIntArray)nbt).getIntArray();
            case 12: {
                return ((INBTTagLongArray)nbt).getData();
            }
            default:
                return null;
        }
    }

    public static Document nbtToDocument(NBTTagCompound nbt){
        Document document=new Document();
        ArrayList<String> keys=new ArrayList<>(nbt.getKeySet());
        Collections.sort(keys);
        for(String key:keys)
        {
            try {
                document.append(key,nbtToNative(nbt.getTag(key)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return document;
    }

    public static String nbtToJson(NBTTagCompound nbt)
    {
        return nbtToDocument(nbt).toJson(JsonWriterSettings.builder().outputMode(JsonMode.STRICT).indent(true).build());
    }
}
