package com.nykseli.bontho.ui;

import com.nykseli.bontho.database.UserRepository;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 */
@Route("")
public class MainView extends VerticalLayout {
    private static final long serialVersionUID = 1001L;

    private final UserRepository userRepository;

    @Autowired
    public MainView(UserRepository userRepository) {
        this.userRepository = userRepository;
        // Show the Beer view if user is logged in
        // Show the Login if user is not
        if (LoginView.isLogged()) {
            BeerView bw = new BeerView();
            bw.setSizeFull();
            add(bw);
        } else {
            LoginView lw = new LoginView(this.userRepository);
            lw.setSizeFull();
            add(lw);
        }
        setSizeFull();
    }

}
