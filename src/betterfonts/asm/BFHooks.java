package betterfonts.asm;

import betterfonts.ConfigParser;
import betterfonts.StringCache;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class BFHooks
{
    public static boolean betterFontsEnabled = true;
    public static boolean dropShadowEnabled = true;
    public static StringCache stringCache;

    public static void initHook(String par1, int[] par2)
    {
        /*
        * Only use OpenType rendering for the primary FontRenderer and not for the enchantment table Standard Galactic renderer.
        * Also, mcpatcher will call initialize() when switching texture packs to reload the bitmap font, but the StringCache
        * should not be re-created a second time or it will leak OpenGL textures.
        */
        if (par1.contains("ascii.png") && stringCache == null)
        {
            stringCache = new StringCache(par2);
            ConfigParser config = new ConfigParser();
            if (config.loadConfig("/config/BetterFonts.cfg"))
            {
                String fontName = config.getFontName("SansSerif");
                int fontSize = config.getFontSize(18);
                boolean antiAlias = config.getBoolean("font.antialias", false);
                dropShadowEnabled = config.getBoolean("font.dropshadow", true);
                stringCache.setDefaultFont(fontName, fontSize, antiAlias);
                System.out.println("BetterFonts configuration loaded");
            }
        }
    }

    public static int gswHook(String par1)
    {
        if (betterFontsEnabled && stringCache != null)
        {
            return stringCache.getStringWidth(par1);
        }
        return 0;
    }

    public static int rsHook(String par1Str, int par2, int par3, int par4, boolean par5)
    {
        if (betterFontsEnabled && stringCache != null && par1Str != null)
        {
            return stringCache.renderString(par1Str, par2, par3, par4, par5);
        }
        return 0;
    }

    public static int sstwHook(String par1Str, int par2)
    {
        if (betterFontsEnabled && stringCache != null)
        {
            return stringCache.sizeStringToWidth(par1Str, par2);
        }
        return 0;
    }

    public static String tstwHook(String par1Str, int par2, boolean par3)
    {
        if (betterFontsEnabled && stringCache != null)
        {
            return stringCache.trimStringToWidth(par1Str, par2, par3);
        }
        return "";
    }

}
