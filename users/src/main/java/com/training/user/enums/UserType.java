package com.training.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum UserType implements EnumTypeValidation {

    PROFESSOR("Professor", 1),
    STUDENT("Student", 2);

    private final String name;

    private final int index;

    @Override
    public String toString() {
        return index + " = " + name;
    }

    public static String getName(final int index) {
        if (index - 1 > values().length || index < 1) {
            return "Not found";
        }
        return values()[index - 1].getName();
    }

    @Override
    public boolean isValuePresent(final short value) {
        return Arrays.stream(values())
                .anyMatch(userType -> userType.getIndex() == value);
    }
}
