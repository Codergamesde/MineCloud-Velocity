package de.codergames.minecloudvelocity.core.cloud;

public enum ServiceStatus {
    Start,
    Stop,
    Prepare,
    ;


    public static ServiceStatus fromString(String name) {

        if (name == "Start") {
            return ServiceStatus.Start;
        }
        if (name == "Stop") {
            return ServiceStatus.Stop;
        }
        if (name == "Prepare") {
            return ServiceStatus.Prepare;
        }
        return ServiceStatus.Start;
    }
}
