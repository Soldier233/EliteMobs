package com.magmaguy.elitemobs.versionnotifier;

import com.magmaguy.elitemobs.MetadataHandler;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VersionChecker {

    public static boolean pluginIsUpToDate = true;

    public static void updateComparer(){

        String currentVersion = MetadataHandler.PLUGIN.getDescription().getVersion();
        String publicVersion = "";

        try {
            Bukkit.getLogger().info("[EliteMobs] Latest public release is " + VersionChecker.readStringFromURL());
            Bukkit.getLogger().info("[EliteMobs] Your version is " + currentVersion);
            publicVersion = VersionChecker.readStringFromURL();
        } catch (IOException e) {
            Bukkit.getLogger().warning("[EliteMobs] Couldn't check latest version");
            return;
        }

        if (Double.parseDouble(currentVersion.split("\\.")[0]) < Double.parseDouble(publicVersion.split("\\.")[0])) {
            outOfDateHandler();
            return;
        }
        if (Double.parseDouble(currentVersion.split("\\.")[0]) > Double.parseDouble(publicVersion.split("\\.")[0])) {
            return;
        }
        if (Double.parseDouble(currentVersion.split("\\.")[1]) < Double.parseDouble(publicVersion.split("\\.")[1])){
            outOfDateHandler();
            return;
        }
        if (Double.parseDouble(currentVersion.split("\\.")[1]) > Double.parseDouble(publicVersion.split("\\.")[0])) {
            return;
        }
        if (Double.parseDouble(currentVersion.split("\\.")[2]) < Double.parseDouble(publicVersion.split("\\.")[2])){
            outOfDateHandler();
            return;
        }

        Bukkit.getLogger().warning("[EliteMobs] You are running the latest version!");

        pluginIsUpToDate = true;

    }

    private static String readStringFromURL() throws IOException {

        try (Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=40090").openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

    }

    private static void outOfDateHandler(){

        Bukkit.getLogger().warning("[EliteMobs] A newer version of this plugin is available for download!");
        pluginIsUpToDate = false;

    }

}
