package com.nykseli.bontho.ui;

import com.nykseli.bontho.database.UserRepository;
import com.nykseli.bontho.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;

@UIScope
public class LoginView extends VerticalLayout {

    @Autowired
    private UserRepository userRepository;

    private static final long serialVersionUID = 1003L;
    private static boolean isLogged = false;
    // private static final Logger LOGGER2 =
    // Logger.getLogger(LoginView.class.getName());

    private LoginForm view = new LoginForm();

    public LoginView(UserRepository userRepository) {
        this.userRepository = userRepository;

        view.addLoginListener(event -> {
            isLogged = authenticate(event.getUsername(), event.getPassword());
            if (isLogged) {
                // Refresh the page and the user will be get the beer grid view
                UI.getCurrent().getPage().reload();
            } else {
                // Show the incorrect username or password error
                view.setError(true);
            }
        });

        // Don't show the forgot the password since we don't have a way to show it
        view.setForgotPasswordButtonVisible(false);
        add(view);
        setSizeFull();
    }

    /**
     * Check if the username and the password matches
     *
     * @param username the username we try to find
     * @param password the plaintext password
     * @return boolean for successful on nonsuccessful login
     */
    private boolean authenticate(String username, String password) {

        // Get user with username from the database
        User user = userRepository.findByUsername(username);

        // If user is not found
        if (user == null) {
            return false;
        }

        // If the password matches
        if (user.matchPassword(password)) {
            return true;
        }

        return false;
    }

    public static boolean isLogged() {
        return LoginView.isLogged;
    }
}
