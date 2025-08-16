package twelvefold.twelvefoldbooter.config;

public class TFModConfig{
    public boolean debug=false;
    public boolean verboseDebug=false;
    public boolean modifyHealthBar=true;
    @Override
    public Object clone()
    {
        TFModConfig cfg= new TFModConfig();
        cfg.debug=this.debug;
        cfg.verboseDebug=this.verboseDebug;
        cfg.modifyHealthBar=this.modifyHealthBar;
        return cfg;
    }
}
