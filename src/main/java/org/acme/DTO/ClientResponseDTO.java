package org.acme.DTO;

public class ClientResponseDTO {
    private String token;

    public ClientResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
