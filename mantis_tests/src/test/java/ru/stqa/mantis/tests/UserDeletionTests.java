package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;

public class UserDeletionTests extends TestBase {

    @Test
    void canDeleteUser() {
        var user = "user1";
        app.user().delete(user);
    }
}
