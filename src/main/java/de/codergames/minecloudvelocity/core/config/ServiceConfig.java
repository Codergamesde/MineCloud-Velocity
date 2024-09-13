package de.codergames.minecloudvelocity.core.config;

import com.google.gson.*;
import de.codergames.minecloudvelocity.Service;
import de.codergames.minecloudvelocity.core.cloud.*;
import de.codergames.minecloudvelocity.core.lib.Address;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceConfig {

    private String name;
    private ServiceStatus status;
    private String time;
    private Address serverAddress;
    private Address pluginListener;
    private Address cloudListener;
    private Task task;

    private Service service;


    public ServiceConfig(Service service) {
        this.service = service;
    }

    public ServiceConfig() {
        String filePath = new java.io.File(".game_cloud/service_config.json").getAbsolutePath();
        System.out.println(filePath);
        if (filePath.isEmpty()) {
            service.getLogger().error("Service Config is empty");
            return;
        }
        try {

            // JSON-Datei analysieren
            JsonParser jsonParser = new JsonParser();
            FileReader fileReader = new FileReader(filePath);
            JsonObject jsonObject = jsonParser.parse(fileReader).getAsJsonObject();

            // Name
            if (jsonObject.has("name")) {
                this.name = jsonObject.get("name").getAsString();
            }

            // Status
            if (jsonObject.has("status")) {
                String statusString = jsonObject.get("status").getAsString();
                this.status = ServiceStatus.valueOf(statusString);
            }

            // Time
            if (jsonObject.has("start_time")) {
                this.time = jsonObject.get("start_time").getAsString();
            }

            // Server Address
            if (jsonObject.has("server_address")) {
                JsonObject serverAddressObject = jsonObject.getAsJsonObject("server_address");
                String serverIp = serverAddressObject.get("ip").getAsString();
                int serverPort = serverAddressObject.get("port").getAsInt();
                this.serverAddress = new Address(serverIp, serverPort);
            }

            // Plugin Listener
            if (jsonObject.has("plugin_listener")) {
                JsonObject pluginListenerObject = jsonObject.getAsJsonObject("plugin_listener");
                String pluginIp = pluginListenerObject.get("ip").getAsString();
                int pluginPort = pluginListenerObject.get("port").getAsInt();
                this.pluginListener = new Address(pluginIp, pluginPort);
            }

            // Cloud Listener
            if (jsonObject.has("cloud_listener")) {
                JsonObject cloudListenerObject = jsonObject.getAsJsonObject("cloud_listener");
                String cloudIp = cloudListenerObject.get("ip").getAsString();
                int cloudPort = cloudListenerObject.get("port").getAsInt();
                this.cloudListener = new Address(cloudIp, cloudPort);
            }

            // Task
            if (jsonObject.has("task")) {
                JsonObject taskObject = jsonObject.getAsJsonObject("task");
                String taskName = taskObject.get("name").getAsString();
                char taskSplit = taskObject.get("split").getAsCharacter();
                boolean taskDeleteOnStop = taskObject.get("delete_on_stop").getAsBoolean();
                boolean taskStaticService = taskObject.get("static_service").getAsBoolean();
                int maxRam = taskObject.get("max_ram").getAsInt();
                int startPort = taskObject.get("start_port").getAsInt();
                int minServiceCount = taskObject.get("min_service_count").getAsInt();

                // Nodes
                List<String> nodes = new ArrayList<>();
                JsonArray nodesArray = taskObject.getAsJsonArray("nodes");
                for (int i = 0; i < nodesArray.size(); i++) {
                    nodes.add(nodesArray.get(i).getAsString());
                }

                // Groups
                List<String> groups = new ArrayList<>();
                JsonArray groupsArray = taskObject.getAsJsonArray("groups");
                for (int i = 0; i < groupsArray.size(); i++) {
                    groups.add(groupsArray.get(i).getAsString());
                }

                // Software
                JsonObject softwareObject = taskObject.getAsJsonObject("software");
                String softwareType = softwareObject.get("software_type").getAsString();
                String softwareName = softwareObject.get("name").getAsString();
                Software software = new Software(softwareType, softwareName);

                // Installer
                String installerName = taskObject.get("installer").getAsString();
                Installer installer = Installer.fromString(installerName);

                // Templates
                List<Template> templates = new ArrayList<>();
                JsonArray templatesArray = taskObject.getAsJsonArray("templates");
                for (int i = 0; i < templatesArray.size(); i++) {
                    JsonObject templateObject = templatesArray.get(i).getAsJsonObject();
                    String templateName = templateObject.get("name").getAsString();
                    String templateTemplate = templateObject.get("template").getAsString();
                    int templatePriority = templateObject.get("priority").getAsInt();
                    Template template = new Template(templateTemplate, templateName, templatePriority);
                    templates.add(template);
                }


                // Task erstellen
                this.task = new Task(taskName, taskSplit, taskDeleteOnStop, taskStaticService, nodes, software, maxRam, startPort, minServiceCount, groups, installer, templates);
            }

            fileReader.close();
        } catch (IOException e) {
            service.getLogger().error("Es wurde die service_config file nicht gefunden stelle sicher das dieses Plugin im Netzwerk der MineCloud ausgeführt wird.");
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Address getPluginListener() {
        return pluginListener;
    }

    public Address getCloudListener() {
        return cloudListener;
    }

    public Task getTask() {
        return task;
    }

    public Address getServerAddress() {
        return serverAddress;
    }

}

