package com.nykseli.bontho.ui;

import com.nykseli.bontho.backend.UserCookie;
import com.nykseli.bontho.database.BeerRepository;
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
    private final BeerRepository beerRepository;

    @Autowired
    public MainView(UserRepository userRepository, BeerRepository beerRepository) {
        this.userRepository = userRepository;
        this.beerRepository = beerRepository;

        // Try to get the userId from saved cookie
        Integer userId = UserCookie.getUserId();

        // If userId exists, show the users BeerView
        if (userId != null) {
            BeerView bw = new BeerView(this.beerRepository, userId);
            bw.setSizeFull();
            add(bw);
        } else {
            // Show the LoginView if there userId from a cookie is not found
            LoginView lw = new LoginView(this.userRepository);
            lw.setSizeFull();
            add(lw);
        }
        setSizeFull();
    }

}
