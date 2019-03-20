
package com.nykseli.bontho.ui;

import java.util.List;
import java.util.logging.Logger;

import com.nykseli.bontho.backend.UserCookie;
import com.nykseli.bontho.database.BeerRepository;
import com.nykseli.bontho.entity.Beer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * BeerView
 */
@UIScope
public class BeerView extends VerticalLayout {

    private static final long serialVersionUID = 1005L;
    private static final Logger LOGGER = Logger.getLogger(BeerView.class.getName());

    /**
     * userId is the integer from a cookie {@link UserCookie#getUserId()}
     */
    private Integer userId;

    @Autowired
    private BeerRepository beerRepository;

    // private BeerService service = BeerService.getInstance();
    private Grid<Beer> grid = new Grid<>(Beer.class);
    private TextField filterText = new TextField();

    private BeerForm form;

    public BeerView(BeerRepository beerRepository, Integer userId) {
        this.userId = userId;
        this.beerRepository = beerRepository;
        this.form = new BeerForm(this);
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button clearFilterTextBtn = new Button(VaadinIcon.CLOSE_CIRCLE.create());
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        Button addCustomerBtn = new Button("TODO: Add new Beer");
        // TODO:
        // addCustomerBtn.addClickListener(e -> {
        // grid.asSingleSelect().clear();
        // form.setBeer(new Beer());
        // });

        Button logOutButton = new Button("Logout");
        logOutButton.addClickListener(e -> {
            // Log out by destroying the cookie and refreshing the page.
            // When page is refreshed and the cookie is no longer found,
            // user sees the LoginView
            UserCookie.destroyCookie();
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn, logOutButton);

        grid.setColumns("brand", "name", "amount", "status");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        add(toolbar, main);

        setSizeFull();

        updateList();

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setBeer(event.getValue());
        });

    }

    public void updateList() {
        List<Beer> beers = beerRepository.findByUserId(this.userId);
        grid.setItems(beers);
    }

    public void saveBeer(Beer beer) {
        beerRepository.save(beer);
    }

    public void deleteBeer(Beer beer) {
        beerRepository.delete(beer);
    }

}
