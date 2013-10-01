package betterfonts.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion("1.6.2")
public class BFLoadingPlugin implements IFMLLoadingPlugin
{
    static File coremodLocation;

    @Override
    public String[] getLibraryRequestClass()
    {
        return null;
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] {BFClassTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass()
    {
        return BFModContainer.class.getName();
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        coremodLocation = (File)data.get("coremodLocation");
        System.out.println("Mod location: " + coremodLocation);
    }

}
