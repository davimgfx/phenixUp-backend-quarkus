package org.acme.DTO;

public class ClientResponseDTO {
    private String email;
    private String token;

    public ClientResponseDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
