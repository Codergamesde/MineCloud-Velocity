package de.codergames.minecloudvelocity.core.network.responds;

public class RespondsBody {

    private int code;
    private String response;


    public RespondsBody(int code, String response) {
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }
}
