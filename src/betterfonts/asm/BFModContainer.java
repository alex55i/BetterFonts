package betterfonts.asm;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;

public class BFModContainer extends DummyModContainer
{
    public BFModContainer()
    {
        super(new ModMetadata());

        ModMetadata md = getMetadata();
        md.modId = "BetterFonts";
        md.name = "Better Fonts";
        md.description = "The BetterFonts mod adds TrueType/OpenType font support for Minecraft. This mod will use the fonts installed on your system for drawing text instead of the builtin bitmap fonts that come with Minecraft. Languages such as Arabic and Hindi look much better with this mod since both require complex layout that the bitmap fonts simply can't provide. All in-game text will change to use the new fonts including GUIs, the F3 debug screen, chat, and even signs. This mod should have little or no impact on performance.";
        md.authorList = Lists.newArrayList("thvortex");
        md.url = "http://www.minecraftforum.net/topic/1142084-/";
        md.version = "1.0.0";
        md.dependencies = Lists.newArrayList();
        md.dependencies.add(new DefaultArtifactVersion("CodeChickenCore", true));
    }
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}
