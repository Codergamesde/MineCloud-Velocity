package de.codergames.minecloudvelocity.core.cloud;

import java.util.List;

public class Task {

    private String name;
    private char split;
    private boolean delete_on_stop;
    private boolean static_service;
    private List<String> nodes;
    private Software software;
    private int max_ram;
    private int start_port;
    private int min_service_count;
    private List<String> groups;
    private Installer installer;
    private List<Template> templates;


    public Task(String name, char split, boolean delete_on_stop, boolean static_service, List<String> nodes, Software software, int max_ram, int start_port, int min_service_count, List<String> groups, Installer installer, List<Template> templates) {
        this.name = name;
        this.split = split;
        this.delete_on_stop = delete_on_stop;
        this.static_service = static_service;
        this.nodes = nodes;
        this.software = software;
        this.max_ram = max_ram;
        this.start_port = start_port;
        this.min_service_count = min_service_count;
        this.groups = groups;
        this.installer = installer;
        this.templates = templates;
    }


    public String getName() {
        return this.name;
    }
    public char getSplit() {
        return this.split;
    }
    public boolean isDelete_on_stop() {
        return this.delete_on_stop;
    }
    public boolean isStatic_service() {
        return this.static_service;
    }
    public List<String> getNodes() {
        return this.nodes;
    }
    public Software getSoftware() {
        return this.software;
    }
    public int getMax_ram() {
        return this.max_ram;
    }
    public int getStart_port() {
        return this.start_port;
    }
    public int getMin_service_count() {
        return this.min_service_count;
    }
    public List<String> getGroups() {
        return this.groups;
    }
    public Installer getInstaller() {
        return this.installer;
    }
    public List<Template> getTemplates() {
        return this.templates;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSplit(char split) {
        this.split = split;
    }
    public void setDelete_on_stop(boolean delete_on_stop) {
        this.delete_on_stop = delete_on_stop;
    }
    public void setStatic_service(boolean static_service) {
        this.static_service = static_service;
    }
    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
    public void setSoftware(Software software) {
        this.software = software;
    }
    public void setMax_ram(int max_ram) {
        this.max_ram = max_ram;
    }
    public void setStart_port(int start_port) {
        this.start_port = start_port;
    }
    public void setMin_service_count(int min_service_count) {
        this.min_service_count = min_service_count;
    }
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
    public void setInstaller(Installer installer) {
        this.installer = installer;
    }
    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }


}
