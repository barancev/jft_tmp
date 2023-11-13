package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;

import java.time.Duration;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {

    public static Stream<String> randomUser() {
        return Stream.of(CommonFunctions.randomString(10));
    }

    @ParameterizedTest
    @MethodSource("randomUser")
    void canRegisterUser(String username) {
        var email = String.format("%s@localhost", username);
        app.jamesApi().addUser(email, "password");
        app.user().startRegistration(username, email);
        var messages = app.mail().receive(email, "password", Duration.ofSeconds(60));
        var url = CommonFunctions.extractUrl(messages.get(0).content());
        app.user().finishRegistration(url, username, "password");
        app.http().login(username, "password");
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
