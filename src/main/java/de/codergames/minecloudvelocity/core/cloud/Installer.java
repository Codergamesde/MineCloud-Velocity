package de.codergames.minecloudvelocity.core.cloud;

public enum Installer {
    InstallAll,
    InstallRandom,
    InstallRandomWithPriority,
    ;


    public static Installer fromString(String name) {

        if (name == "InstallAll") {
            return Installer.InstallAll;
        }
        if (name == "InstallRandom") {
            return Installer.InstallRandom;
        }
        if (name == "InstallRandomWithPriority") {
            return Installer.InstallRandomWithPriority;
        }
        return Installer.InstallAll;
    }

}

