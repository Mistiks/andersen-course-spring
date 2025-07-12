package reservation.model;

import jakarta.validation.constraints.NotBlank;
import reservation.entity.UserProfile;

public class UserProfileModel {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private UserProfile.Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile.Role getRole() {
        return role;
    }

    public void setRole(UserProfile.Role role) {
        this.role = role;
    }
}
