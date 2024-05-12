package fr.openclassrooms.MDD.dto;

import fr.openclassrooms.MDD.utils.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldsValueMatch(field = "newPassword", fieldMatch = "confirmPassword", message = "Passwords do not match")
public class ChangePasswordRequest {

    @NotBlank(message = "Old password is required")
    String oldPassword;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    String newPassword;

    @NotBlank(message = "Confirm password is required")
    String confirmPassword;
}
