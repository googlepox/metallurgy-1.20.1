package net.googlepox.metallurgy.material;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.util.Utils;
import net.minecraft.resources.ResourceLocation;
import java.util.Set;

public class ModMetals {

    public static void init()
    {
        Set<MetalStats> defaultStats = JsonMaterialHelper.readConfig(JsonMaterialHelper.DEFAULT_CONFIG, null);

        //boolean copied = Utils.copyFile(defaultConfigPath, Metallurgy.materialConfig, !GeneralConfig.enableCustomMaterialStatsConfig);

        Set<MetalStats> playerStats = defaultStats;

        //If the configuration file was already copied and the custom stats loader was enabled
        if (true)//if (!copied)
        {
            try
            {
                playerStats = JsonMaterialHelper.readConfig(Metallurgy.materialConfig, defaultStats);
            }
            catch (JsonSyntaxException e)
            {
                Metallurgy.logger.error("There was an error while loading custom stats for Metallurgy Materials (CHECK YOUR JSON CONFIG FOR MISTAKES)");
                Metallurgy.logger.error("Error Message: " + e.getMessage());
                Metallurgy.logger.warn("Metallurgy will now load the default material stats automatically...");
            }
            catch (JsonParseException e)
            {
                Metallurgy.logger.error("There was an error while loading the Metallurgy Materials json config (Your JSON file was invalid!)");
                Metallurgy.logger.error("Below this line you can read the error StackTrace to try to debug the error: ");
                e.printStackTrace();
                Metallurgy.logger.warn("Metallurgy will now load the default material stats automatically...");
            }

        }

        playerStats.forEach(MetalRegistry::registerMetal);
    }

}
