package com.nykseli.bontho.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Route("")
public class MainView extends VerticalLayout {
    private static final long serialVersionUID = 1001L;

    public MainView() {
        // Show the Beer view if user is logged in
        // Show the Login if user is not
        if (LoginView.isLogged()) {
            BeerView bw = new BeerView();
            bw.setSizeFull();
            add(bw);
        } else {
            LoginView lw = new LoginView();
            lw.setSizeFull();
            add(lw);
        }
        setSizeFull();
    }

}
