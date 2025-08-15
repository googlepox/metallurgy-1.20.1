package net.googlepox.metallurgy.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class Utils {

    public static Random random = new Random();

    private static final MobEffect[] randomEffectsList = {
            MobEffects.BLINDNESS,
            MobEffects.LEVITATION,
            MobEffects.HUNGER,
            MobEffects.DARKNESS,
            MobEffects.HUNGER,
            MobEffects.NIGHT_VISION,
            MobEffects.POISON,
            MobEffects.MOVEMENT_SLOWDOWN,
            MobEffects.REGENERATION
    };

    public static MobEffect getRandomEffect()
    {
        return randomEffectsList[(int) (Math.random() * Utils.getMaxIndexEffect())];
    }

    private static int getMaxIndexEffect()
    {
        return randomEffectsList.length;
    }

    public static String capitalize(String string)
    {
        if (string == null || string.isEmpty()) {
            return string;
        }
        String firstLetter = string.substring(0, 1).toUpperCase();
        String remainingLetters = string.substring(1);
        return firstLetter + remainingLetters;
    }

    public static Path getPath(String resource)
    {
        FileSystem filesystem;

        try
        {
            URL url = Metallurgy.class.getResource(resource);

            if (url != null)
            {
                URI uri = url.toURI();
                Path path;

                if ("file".equals(uri.getScheme()))
                {
                    path = Paths.get(Metallurgy.class.getResource(resource).toURI());
                }
                else
                {
                    try
                    {
                        filesystem = FileSystems.getFileSystem(uri);
                    }
                    catch (FileSystemNotFoundException e)
                    {
                        //If the file system doesn't exist we create a new one
                        filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    }

                    path = filesystem.getPath(resource);
                }

                return path;
            }
        }
        catch (URISyntaxException | IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean copyFile(Path originalPath, String newPath, boolean overwrite)
    {
        File userConfigFile = new File(newPath);

        try
        {
            if (!userConfigFile.exists() || overwrite)
            {
                Files.copy(originalPath, userConfigFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static <T> boolean listContains(List<T> list, T item, BiPredicate<? super T, ? super T> comparator)
    {
        return list.stream().anyMatch(listItem -> comparator.test(listItem, item));
    }

    public static <T> T makeDo(T thing, Consumer<T> todo)
    {
        todo.accept(thing);
        return thing;
    }

}
