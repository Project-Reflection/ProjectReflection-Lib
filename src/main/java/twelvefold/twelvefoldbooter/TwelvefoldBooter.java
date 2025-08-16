package twelvefold.twelvefoldbooter;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import twelvefold.twelvefoldbooter.config.TFModConfig;
import twelvefold.twelvefoldbooter.api.config.TwelvefoldConfig;

public class TwelvefoldBooter extends DummyModContainer
{
    public TwelvefoldBooter()
    {
        super(new ModMetadata());
        ModMetadata metadata=this.getMetadata();
        metadata.modId=MODID;
        metadata.name=NAME;
    }
    public static final String MODID = "twelvefoldbooter";
    public static final String NAME = "ProjectReflection Library";
    public static Logger logger;

    public static TwelvefoldConfig<TFModConfig> config;
	@Instance(MODID)
    @SuppressWarnings("unused")
	public static TwelvefoldBooter instance;
    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        logger=event.getModLog();
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}