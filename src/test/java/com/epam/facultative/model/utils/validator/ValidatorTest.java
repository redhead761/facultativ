package com.epam.facultative.model.utils.validator;

import com.epam.facultative.model.entities.Status;
import com.epam.facultative.model.exception.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.epam.facultative.model.entities.Status.*;
import static com.epam.facultative.model.utils.validator.Validator.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"testLogin", "testWithDigit1", "TESTONLYCAPS", "testonlysmall"})
    void testValidateLogin(String login) {
        assertDoesNotThrow(() -> validateLogin(login));
    }

    @ParameterizedTest
    @ValueSource(strings = {"te", "12345678", "testMoreThen16Chars"})
    void testInvalidateLogin(String login) {
        assertThrows(ValidateException.class, () -> validateLogin(login));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullLogin(String login) {
        assertThrows(ValidateException.class, () -> validateLogin(login));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password1", "1Password", "Password1234", "1234Password", "Password1_"})
    void testValidatePassword(String password) {
        assertDoesNotThrow(() -> validatePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Less6", "onlysmallchars1", "ONLYCAPSCHARS1", "NoDigitPass", "testPasswordMoreThan20Chars"})
    void testValidateBadPassword(String password) {
        assertThrows(ValidateException.class, () -> validatePassword(password));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullPassword(String password) {
        assertThrows(ValidateException.class, () -> validatePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@facu.com.ua", "test@facu.co", "test.email@.facu.com", "test@fa.uaua"})
    void testValidateEmail(String email) {
        assertDoesNotThrow(() -> validateEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.facu.com.ua", "test@facul", "test@.facul", "@facul.com.ua", "test@facul.com.u"})
    void testValidateBadEmail(String email) {
        assertThrows(ValidateException.class, () -> validateEmail(email));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullEmail(String email) {
        assertThrows(ValidateException.class, () -> validateEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Alex", "Boris Jonson", "alex", "Шевченко", "Квітка-Основ'яненко"})
    void testValidateName(String name) {
        assertDoesNotThrow(() -> validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test1", "Test With 1", "Тес з Ы", "TestNameMustLessThanThirtyCharacters"})
    void testValidateBadName(String name) {
        assertThrows(ValidateException.class, () -> validateName(name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullName(String name) {
        assertThrows(ValidateException.class, () -> validateName(name));
    }

    @Test
    void testValidateTitle() {
        assertDoesNotThrow(() -> validateTitle("Correct title -~`!?@#$^&*()={}| Тітул"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidateTitle(String title) {
        assertThrows(ValidateException.class, () -> validateTitle(title));
    }

    @Test
    void testValidateDescription() {
        assertDoesNotThrow(() -> validateDescription("Correct description -~`!?@#$^&*()={}| В опис додамо і"));
    }

    @ParameterizedTest
    @NullSource
    void testValidateDescription(String description) {
        assertThrows(ValidateException.class, () -> validateDescription(description));
    }

    @Test
    void testValidateStartDate() {
        assertDoesNotThrow(() -> validateStartDate(LocalDate.now()));
    }

    @Test
    void testInvalidateStartDate() {
        assertThrows(ValidateException.class, () -> validateStartDate(LocalDate.now().minusDays(1)));
    }

    @Test
    void testNullStartDate() {
        assertThrows(ValidateException.class, () -> validateStartDate(null));
    }

    @ParameterizedTest
    @MethodSource("validStatus")
    void testValidateStatus(Status status, LocalDate startDate) {
        assertDoesNotThrow(() -> validateStatus(status, startDate));

    }

    @ParameterizedTest
    @MethodSource("invalidStatus")
    void testInvalidateStatus(Status status, LocalDate startDate) {
        assertThrows(ValidateException.class, () -> validateStatus(status, startDate));

    }

    private static Stream<Arguments> validStatus() {
        return Stream.of(Arguments.of(COMING_SOON, LocalDate.now()),
                Arguments.of(IN_PROCESS, LocalDate.now().minusDays(1)),
                Arguments.of(COMPLETED, LocalDate.now().minusDays(1)));
    }

    private static Stream<Arguments> invalidStatus() {
        return Stream.of(Arguments.of(COMING_SOON, LocalDate.now().minusDays(1)),
                Arguments.of(IN_PROCESS, LocalDate.now().plusDays(1)),
                Arguments.of(COMPLETED, LocalDate.now().plusDays(1)),
                Arguments.of(null, LocalDate.now()),
                Arguments.of(IN_PROCESS, null));
    }
}