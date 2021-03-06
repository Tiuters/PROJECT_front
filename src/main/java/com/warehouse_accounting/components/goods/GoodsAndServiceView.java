package com.warehouse_accounting.components.goods;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.warehouse_accounting.components.AppView;
import com.warehouse_accounting.components.goods.forms.GoodsForm;
import com.warehouse_accounting.components.goods.forms.GroupForm;
import com.warehouse_accounting.components.goods.forms.ServiceForm;
import com.warehouse_accounting.components.goods.grids.GoodsGridLayout;
import com.warehouse_accounting.models.dto.ProductDto;
import com.warehouse_accounting.models.dto.ProductGroupDto;
import com.warehouse_accounting.services.interfaces.ProductGroupService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@UIScope
@Component
@Route(value = "goodsAndServiceView", layout = AppView.class)
public class GoodsAndServiceView extends VerticalLayout {
    private final ProductGroupService productGroupService;
    private final TreeGrid<ProductGroupDto> treeGrid = new TreeGrid<>();
    private final TextField textFieldGridSelected = new TextField();
    private final Grid<ProductDto> productDtoGrid = new Grid<>(ProductDto.class, false);
    private Div mainDiv;
    private GoodsGridLayout goodsGridLayout;
    private Long rootGroupId = 1L; //TODO ???????????????????????????? ?????? ???????????????? ????????????????????????

    public GoodsAndServiceView(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
        goodsGridLayout = new GoodsGridLayout(productGroupService, this);
        Div pageContent = new Div();
        pageContent.setSizeFull();
        pageContent.add(goodsGridLayout);
        add(initGroupButtons(), pageContent);
    }

    private HorizontalLayout initGroupButtons() {
        HorizontalLayout groupControl = new HorizontalLayout();

        Button helpButton = new Button(new Icon(VaadinIcon.QUESTION_CIRCLE));
        helpButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        Label textProducts = new Label();
        textProducts.setText("???????????? ?? ????????????");

        Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        refreshButton.addClickListener(buttonClickEvent -> {
            goodsGridLayout.initGrid(rootGroupId);
            goodsGridLayout.initThreeGrid(rootGroupId);
        });

        Button addProductButton = new Button("??????????", new Icon(VaadinIcon.PLUS));
        addProductButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addProductButton.addClickListener(event -> {
            GoodsForm goodsForm = new GoodsForm(mainDiv, this);
            mainDiv.removeAll();
            mainDiv.add(goodsForm);
        });

        Button addServiceButton = new Button("????????????", new Icon(VaadinIcon.PLUS));
        addServiceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addServiceButton.addClickListener(event -> {
            ServiceForm serviceForm = new ServiceForm(mainDiv, this);
            mainDiv.removeAll();
            mainDiv.add(serviceForm);
        });

        Button addKitButton = new Button("????????????????", new Icon(VaadinIcon.PLUS));
        addKitButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button addGroupButton = new Button("????????????", new Icon(VaadinIcon.PLUS));
        addGroupButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        addGroupButton.addClickListener(buttonClickEvent -> {
            Optional<ProductGroupDto> selectedGroup = treeGrid.getSelectedItems().stream().findFirst();
            ProductGroupDto selectGroup = selectedGroup.orElseGet(() -> productGroupService.getById(rootGroupId));
            GroupForm groupForm = new GroupForm(mainDiv, this, productGroupService, selectGroup, false);
            mainDiv.removeAll();
            mainDiv.add(groupForm);
        });

        Button addFilterButton = new Button("????????????", new Icon(VaadinIcon.PLUS));
        addFilterButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

        TextField searchField = new TextField();
        searchField.setPlaceholder("????????????????????????, ?????? ?????? ??????????????");
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.addInputListener(inputEvent -> {
            //grid filter
        });

        HorizontalLayout editMenuBar = initEditMenuBar();

        HorizontalLayout printMenuBar = getPrintMenuBar();

        HorizontalLayout importMenuBar = getImportMenuBar();

        HorizontalLayout exportMenuBar = getExportMenuBar();

        groupControl.add(helpButton, textProducts, refreshButton, addProductButton, addServiceButton,
                addKitButton, addGroupButton, addFilterButton, searchField, editMenuBar,
                printMenuBar, importMenuBar, exportMenuBar);
        setSizeFull();
        return groupControl;
    }

    private HorizontalLayout initEditMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        textFieldGridSelected.setReadOnly(true);
        textFieldGridSelected.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textFieldGridSelected.setWidth("30px");
        textFieldGridSelected.setValue("0");

        MenuBar editMenuBar = new MenuBar();
        editMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout editItem = new HorizontalLayout(new Text("????????????????"), agileDownIcon);
        editItem.setSpacing(false);
        editItem.setAlignItems(Alignment.CENTER);

        MenuItem editMenu = editMenuBar.addItem(editItem);
        editMenu.getSubMenu().addItem("??????????????", menuItemClickEvent -> {
            int selected = goodsGridLayout.getProductGrid().asMultiSelect().getSelectedItems().size();
            Notification notification = new Notification(String.format("???????????????? ?????? ???????????????? %d", selected),
                    3000,
                    Notification.Position.MIDDLE);
            notification.open();
        });
        editMenu.getSubMenu().addItem("???????????????? ????????????????????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("??????????????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("?????????????????? ?? ??????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("?????????????? ???? ????????????", menuItemClickEvent -> {

        });
        editMenu.getSubMenu().addItem("????????...", menuItemClickEvent -> {

        });

        HorizontalLayout groupEdit = new HorizontalLayout();
        groupEdit.add(textFieldGridSelected, editMenuBar);
        groupEdit.setSpacing(false);
        groupEdit.setAlignItems(Alignment.CENTER);
        return groupEdit;
    }

    private HorizontalLayout getPrintMenuBar() {
        MenuBar printMenuBar = new MenuBar();
        printMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        Icon printIcon = new Icon(VaadinIcon.PRINT);
        printIcon.setSize("12px");
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        HorizontalLayout printItem = new HorizontalLayout(printIcon, agileDownIcon);
        printItem.setSpacing(false);
        printItem.setAlignItems(Alignment.CENTER);
        MenuItem print = printMenuBar.addItem(printItem);

        print.getSubMenu().addItem("???????????? (70x49,5????)", e -> {

        });
        print.getSubMenu().addItem("?????????????????????????? (58??40????)", e -> {

        });
        print.getSubMenu().addItem("??????????????????...", e -> {

        });

        HorizontalLayout groupPrint = new HorizontalLayout();
        groupPrint.add(printMenuBar);
        groupPrint.setSpacing(false);
        groupPrint.setAlignItems(Alignment.CENTER);
        return groupPrint;
    }

    private HorizontalLayout getImportMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        MenuBar importMenuBar = new MenuBar();
        importMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("????????????"), agileDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        MenuItem importItem = importMenuBar.addItem(horizontalLayout);

        importItem.getSubMenu().addItem("???????????? ???? Excel", e -> {

        });

        importItem.getSubMenu().addItem("???????????? ???? YML", e -> {

        });

        HorizontalLayout groupImport = new HorizontalLayout();
        groupImport.add(importMenuBar);
        groupImport.setSpacing(false);
        groupImport.setAlignItems(Alignment.CENTER);
        return groupImport;
    }

    private HorizontalLayout getExportMenuBar() {
        Icon agileDownIcon = new Icon(VaadinIcon.ANGLE_DOWN);
        agileDownIcon.setSize("12px");
        MenuBar exportMenuBar = new MenuBar();
        HorizontalLayout horizontalLayout = new HorizontalLayout(new Text("??????????????"), agileDownIcon);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        exportMenuBar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
        MenuItem exportItem = exportMenuBar.addItem(horizontalLayout);

        exportItem.getSubMenu().addItem("?????????????? ?? Excel", e -> {

        });
        exportItem.getSubMenu().addItem("?????????????? ?? YML", e -> {

        });

        HorizontalLayout groupImport = new HorizontalLayout();
        groupImport.add(exportMenuBar);
        groupImport.setSpacing(false);
        groupImport.setAlignItems(Alignment.CENTER);
        return groupImport;
    }

    public Long getRootGroupId() {
        return rootGroupId;
    }

    public GoodsGridLayout getGoodsGridLayout() {
        return goodsGridLayout;
    }

    public void setMainDiv(Div div) {
        this.mainDiv = div;
    }

    public Div getMainDiv() {
        return mainDiv;
    }

    public TextField getTextFieldGridSelected() {
        return textFieldGridSelected;
    }

    public Grid<ProductDto> getProductDtoGrid() {
        return productDtoGrid;
    }

    public TreeGrid<ProductGroupDto> getTreeGrid() {
        return treeGrid;
    }
}