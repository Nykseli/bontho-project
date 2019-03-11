
package com.nykseli.bontho.ui;

import com.nykseli.bontho.backend.Beer;
import com.nykseli.bontho.backend.BeerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * BeerView
 */
public class BeerView extends VerticalLayout {

    private static final long serialVersionUID = 1005L;

    private BeerService service = BeerService.getInstance();
    private Grid<Beer> grid = new Grid<>(Beer.class);
    private TextField filterText = new TextField();

    private BeerForm form;

    public BeerView() {
        this.form = new BeerForm(this);
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button clearFilterTextBtn = new Button(VaadinIcon.CLOSE_CIRCLE.create());
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        Button addCustomerBtn = new Button("Add new Beer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setBeer(new Beer());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);

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
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
