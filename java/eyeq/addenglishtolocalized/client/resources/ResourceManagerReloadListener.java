package eyeq.addenglishtolocalized.client.resources;

import com.google.common.collect.Lists;
import eyeq.addenglishtolocalized.AddEnglishToLocalized;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Iterator;
import java.util.Map;

public class ResourceManagerReloadListener implements IResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        Language language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
        if(language.getLanguageCode().equals(LanguageResourceManager.EN_US)) {
            return;
        }

        Locale currentLocale = getCurrentLocale();
        Locale englishLocale = new Locale();
        englishLocale.loadLocaleDataFiles(Minecraft.getMinecraft().getResourceManager(), Lists.newArrayList(LanguageResourceManager.EN_US));

        Map<String, String> currentProperties = getProperties(currentLocale);
        Map<String, String> englishProperties = getProperties(englishLocale);
        Iterator<String> keys = currentProperties.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            String current = currentProperties.get(key);
            String english = englishProperties.get(key);
            if(current.equals(english)) {
                current = "";
            }
            current += "(" + english + ")";
            englishProperties.put(key, current);

            if(AddEnglishToLocalized.isResources) {
                currentProperties.put(key, current);
            }
        }
        if(AddEnglishToLocalized.isSentence) {
            LanguageMap.replaceWith(englishProperties);
        }
    }

    public static Locale getCurrentLocale() {
        return ObfuscationReflectionHelper.getPrivateValue(LanguageManager.class, null, "CURRENT_LOCALE", "field_135049_a");
    }

    public static Map<String, String> getProperties(Locale locale) {
        return ObfuscationReflectionHelper.getPrivateValue(Locale.class, locale, "properties", "field_135032_a");
    }
}
