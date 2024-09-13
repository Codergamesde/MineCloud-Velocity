package de.codergames.minecloudvelocity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import de.codergames.minecloudvelocity.core.config.ServiceConfig;
import de.codergames.minecloudvelocity.core.ServiceInfo;

public class Cloud {

    private Service service;
    private ServiceConfig serviceConfig;

    public Cloud(Service service) {
        this.service = service;
        this.serviceConfig = service.getServiceConfig();
    }

    public void getOnlineService() {
        String url = "http://" + service.getServiceConfig().getCloudListener().toString() + "/cloud/api/get/onlineService"; // Die URL des Servers

        try {
            // Verbindung herstellen
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                this.service.getLogger().warn("Service konnte nicht von der Cloud geladen werden");
                return;
            }

            // Antwort lesen
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Entferne die äußeren Anführungszeichen
                String jsonResponse = response.toString();
                System.out.println("Received JSON: " + jsonResponse);

                jsonResponse = jsonResponse.replaceAll("\\\\", "");
                jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);

                // Konvertiere den String in ein JSON-Array
                System.out.println("Received JSON: " + jsonResponse);
                Gson gson = new Gson();
                Type serviceInfoListType = new TypeToken<List<ServiceInfo>>(){}.getType();
                List<ServiceInfo> serviceInfoList = gson.fromJson(jsonResponse, serviceInfoListType);

                // Verarbeite die Service-Informationen hier...
                for (ServiceInfo serviceInfo : serviceInfoList) {
                    service.addServer(serviceInfo);
                    service.getLogger().info("Service " + serviceInfo.getName() + " ad to try server list");
                }

            }

            // Verbindung schließen
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnlineMode() {

        String url = "http://" + serviceConfig.getCloudListener().toString() + "/cloud/api/service/setOnlineMode";
        String json = "{\"name\": \"" + serviceConfig.getName() + "\"}";
        service.getLogger().info(url);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                service.getLogger().info("Online mode sent to Cloud.");
            } else {
                service.getLogger().error("Failed to send online mode to Cloud. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
