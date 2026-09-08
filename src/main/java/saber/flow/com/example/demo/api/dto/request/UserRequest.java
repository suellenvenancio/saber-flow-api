package saber.flow.com.example.demo.api.dto.request;

public record UserRequest(
        String name,
        String email,
        String password
) {
}