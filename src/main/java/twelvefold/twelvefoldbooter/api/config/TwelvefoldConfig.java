package twelvefold.twelvefoldbooter.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TwelvefoldConfig<T> {
    private final Class<T> type;
//    private static TwelvefoldConfig instance=null;
    private T modConfig;// = ModConfig.getDefaultConfig();

    private TwelvefoldConfig(File minecraftHome, String configName, Class<T> type, T defaultConfig)
    {
        this.type = type;
        modConfig=defaultConfig;
        readConfig(minecraftHome,configName);
    }
    private void readConfig(File minecraftHome,String configName) {
        File configFile=new File(minecraftHome, configName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(configFile.isFile())
        {
            try {
                FileInputStream fileInputStream=new FileInputStream(configFile);
                modConfig =gson.fromJson(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8),type);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Failed to load config file",e);
            }
        }else {
            configFile.getParentFile().mkdirs();
        }
        try(FileOutputStream fileOutputStream=new FileOutputStream(configFile)){
            fileOutputStream.write(gson.toJson(modConfig).getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to create config file",e);
        }
    }

    public static <T>TwelvefoldConfig<T> init(File minecraftHome,String configName,T defaultConfig)
    {
//        if(instance != null)
//            return;
        return new TwelvefoldConfig<T>(minecraftHome,configName, (Class<T>) defaultConfig.getClass(),defaultConfig);
    }

    // region Accessors

//    public static boolean debugMode()
//    {
//        return instance.modConfig.debug;
//    }
//    public static boolean verboseDebugMode()
//    {
//        return debugMode() && instance.modConfig.verboseDebug;
//    }
//    public static boolean modifyHealthBar()
//    {
//        return instance.modConfig.modifyHealthBar;
//    }
    // endregion
    public T getModConfig()
    {
        return this.modConfig;
    }
}
