package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class UserHelper extends HelperBase {
    public UserHelper(ApplicationManager manager) {
        super(manager);
    }

    public void startCreation(String user, String email) {
        if (!manager.session().isLoggedIn()) {
            manager.session().login(manager.property("web.username"), manager.property("web.password"));
        }
        manager.driver().get(String.format("%s/manage_user_create_page.php", manager.property("web.baseUrl")));
        type(By.name("username"), user);
        type(By.name("realname"), user);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit']"));
    }

    public void delete(String user) {
        if (!manager.session().isLoggedIn()) {
            manager.session().login(manager.property("web.username"), manager.property("web.password"));
        }
        manager.driver().get(String.format("%s/manage_user_page.php", manager.property("web.baseUrl")));
        var userLink = manager.driver().findElements(By.cssSelector("a[href^='manage_user_edit_page.php']"))
                .stream().filter(link -> link.getText().equals(user)).findFirst().get();
        userLink.click();
        click(By.cssSelector("#manage-user-delete-form input[type='submit'"));
        click(By.cssSelector("div.alert input[type='submit']"));
    }

    public void finishCreation(String confirmationUrl, String password) {
        manager.driver().get(confirmationUrl);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }

    public void startRegistration(String username, String email) {
        if (manager.session().isLoggedIn()) {
            manager.session().logout();
        }
        manager.driver().get(String.format("%s/signup_page.php", manager.property("web.baseUrl")));
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit']"));
    }

    public void finishRegistration(String confirmationUrl, String realname, String password) {
        manager.driver().get(confirmationUrl);
        type(By.name("realname"), realname);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }

}
