package com.nykseli.bontho.ui;

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
    private ComboBox<Integer> status = new ComboBox<>("Status");
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

        // TODO: this properly
        Integer[] col = { 1, 4, 5 };
        status.setItems(col);

        binder.bindInstanceFields(this);

        setBeer(null);

    }

    public void setBeer(Beer beer) {
        this.beer = beer;
        binder.setBean(this.beer);
        boolean enabled = beer != null;
        setVisible(enabled);
        if (enabled) {
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
            beerview.saveBeer(beer);
            beerview.updateList();
            setBeer(null);
        }
    }

}
