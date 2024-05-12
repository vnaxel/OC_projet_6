package fr.openclassrooms.MDD.utils;

import fr.openclassrooms.MDD.dto.ChangePasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        ChangePasswordRequest request = (ChangePasswordRequest) obj;
        return request.getNewPassword().equals(request.getConfirmPassword());
    }
}