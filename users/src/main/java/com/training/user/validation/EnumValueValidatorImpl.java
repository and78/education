package com.training.user.validation;

import com.training.user.enums.EnumTypeValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Optional;

public class EnumValueValidatorImpl implements ConstraintValidator<EnumValue, Short> {

    private Class<? extends EnumTypeValidation> enumClass;

    @Override
    public void initialize(final EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final Short value, final ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(value)
                .map(val -> Arrays.stream(enumClass.getEnumConstants())
                        .anyMatch(validation -> validation.isValuePresent(val)))
                .orElse(false);
    }
}
