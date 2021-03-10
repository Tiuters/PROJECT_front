package com.warehouse_accounting.components.goods.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class GoodsForm extends VerticalLayout {

    private final Div parentLayer;
    private final Component returnLayer;

    public GoodsForm(Div parentLayer, Component returnLayer) {
        this.parentLayer = parentLayer;
        this.returnLayer = returnLayer;
        VerticalLayout form = initForm();
        add(form);
    }

    private VerticalLayout initForm() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button save = new Button("Сохранить", e -> {
            //Создание и сохранение сущьности
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        Button close = new Button("Закрыть", e -> {
            parentLayer.removeAll();
            parentLayer.add(returnLayer);
        });

        HorizontalLayout groupButton = new HorizontalLayout();
        groupButton.setHeightFull();
        groupButton.add(save,close);

        FormLayout form = getFormLayout();
        HorizontalLayout groupForm = new HorizontalLayout();
        groupForm.add(form);
        verticalLayout.add(groupButton, groupForm);
        return verticalLayout;
    }

    private FormLayout getFormLayout() {
        FormLayout form = new FormLayout();
        TextField nameField = new TextField();
        nameField.setLabel("Наименование");
        nameField.setPlaceholder("наименование товара");
        TextField codField = new TextField();
        codField.setLabel("Код");
        codField.setPlaceholder("код товара");
        TextField articleField = new TextField();
        articleField.setLabel("Артикул");
        articleField.setPlaceholder("артикул товара");

        form.add(nameField, codField, articleField);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        return form;
    }
}
