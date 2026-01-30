package com.ramazanbatbay.security_webapp.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationTest {

    private StrongPasswordValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeEach
    void setUp() {
        validator = new StrongPasswordValidator();
    }

    private void mockContext() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
    }

    @Test
    void isValid_ValidPassword_ReturnsTrue() {
        assertTrue(validator.isValid("StrongP@ssw0rd", context));
    }

    @Test
    void isValid_TooShort_ReturnsFalse() {
        mockContext();
        assertFalse(validator.isValid("Short1!", context));
    }

    @Test
    void isValid_NoUppercase_ReturnsFalse() {
        mockContext();
        assertFalse(validator.isValid("weakp@ssw0rd", context));
    }

    @Test
    void isValid_NoLowercase_ReturnsFalse() {
        mockContext();
        assertFalse(validator.isValid("WEAKP@SSW0RD", context));
    }

    @Test
    void isValid_NoDigit_ReturnsFalse() {
        mockContext();
        assertFalse(validator.isValid("NoDigitP@ssword", context));
    }

    @Test
    void isValid_NoSpecialChar_ReturnsFalse() {
        mockContext();
        assertFalse(validator.isValid("NoSpecialChar123", context));
    }

    @Test
    void isValid_NullPassword_ReturnsFalse() {
        assertFalse(validator.isValid(null, context));
    }
}
