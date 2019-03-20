package com.nykseli.bontho.ui;

import com.nykseli.bontho.backend.BeerStatus;
import com.nykseli.bontho.entity.Beer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

/**
 * BeerForm
 */
public class BeerForm extends FormLayout {

    private static final long serialVersionUID = 1000L;

    private TextField brand = new TextField("Brand");
    private TextField name = new TextField("Name");
    private NumberField amount = new NumberField("Amount");
    private ComboBox<String> status = new ComboBox<>("Status");
    private DatePicker created = new DatePicker("Created");

    // private BeerService service = BeerService.getInstance();
    private Beer beer;
    private BeerView beerview;

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Beer> binder = new Binder<>(Beer.class);

    public BeerForm(BeerView beerview) {
        this.beerview = beerview;
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());

        amount.setValue(1d);
        amount.setStep(1);
        amount.setMin(0);
        amount.setMax(999);
        amount.setHasControls(true);

        add(brand, name, amount, status, created, buttons);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        status.setItems(BeerStatus.statusText);

        // Use updateBeerValues and setFields instead of binding
        // so we can handle status texts better
        // binder.bindInstanceFields(this);

        setBeer(null);

    }

    public void setBeer(Beer beer) {
        this.beer = beer;
        binder.setBean(this.beer);
        boolean enabled = beer != null;
        setVisible(enabled);
        if (enabled) {
            // Set the field values to be the current beerobject values
            setFields();
            this.brand.focus();
        }
    }

    public void delete() {
        if (beer != null) {
            beerview.deleteBeer(beer);
            beerview.updateList();
            setBeer(null);
        }
    }

    public void save() {
        if (beer != null) {
            // Get the values form before saving
            updateBeerValues();
            beerview.saveBeer(beer);
            beerview.updateList();
            setBeer(null);
        }
    }

    /**
     * Updates the current beer object with the values from the form fields
     */
    private void updateBeerValues() {
        if (beer != null) {
            beer.setBrand(brand.getValue());
            beer.setName(name.getValue());
            beer.setAmount(amount.getValue());
            beer.setStatus(BeerStatus.getStatusIndex(status.getValue()));
            beer.setCreated(created.getValue());
        }
    }

    /**
     * Set the form field values with the current beer values
     */
    private void setFields() {
        if (beer != null) {
            brand.setValue(beer.getBrand());
            name.setValue(beer.getName());
            amount.setValue(beer.getAmount());
            status.setValue(beer.getStatusText());
            created.setValue(beer.getCreated());
        }
    }

}
