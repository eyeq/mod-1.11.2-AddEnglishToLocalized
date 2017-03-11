package eyeq.addenglishtolocalized;

import eyeq.addenglishtolocalized.client.resources.ResourceManagerReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static eyeq.addenglishtolocalized.AddEnglishToLocalized.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class AddEnglishToLocalized {
    public static final String MOD_ID = "eyeq_addenglishtolocalized";

    @Mod.Instance(MOD_ID)
    public static AddEnglishToLocalized instance;

    public static boolean isResources;
    public static boolean isSentence;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        load(new Configuration(event.getSuggestedConfigurationFile()));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(event.getSide().isServer()) {
            return;
        }
        SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
        resourceManager.registerReloadListener(new ResourceManagerReloadListener());
    }
	
    public static void load(Configuration config) {
        config.load();

        isResources = config.get("Boolean", "isResources", false).getBoolean(false);
        isSentence = config.get("Boolean", "isSentence", true).getBoolean(false);

        if(config.hasChanged()) {
            config.save();
        }
    }
}
