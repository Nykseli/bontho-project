package com.nykseli.bontho.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LoginView extends VerticalLayout {

    private static final long serialVersionUID = 1003L;
    private static boolean isLogged = false;

    private LoginForm view = new LoginForm();

    public LoginView() {

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

    private boolean authenticate(String username, String password) {
        if (username.equals("admin") && password.equals("1234")) {
            return true;
        }
        return false;
    }

    public static boolean isLogged() {
        return LoginView.isLogged;
    }
}
