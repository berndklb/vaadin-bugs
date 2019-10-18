package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route(value = "", layout=MainLayout.class)
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {
	private Binder<Person> binder;
	private Checkbox txtOveraged;
	private TextField txtAge;
	private boolean toggle = false;
	
    public MainView(@Autowired MessageBean bean) {
//        Button button = new Button("Click me",
//                e -> Notification.show(bean.getMessage()));
//        add(button);
//        
//        Button btnConfirmDialog = new Button("ConfirmDialog", e-> showConfirmDialog());
//        add(btnConfirmDialog);
    	
    	Button btnSetIgnore = new Button("set ignore flag");
    	btnSetIgnore.addClickListener(e -> {
    		toggle = !toggle;
    		binder.setDisabledFieldsIgnore(toggle);
    	});
    	add(btnSetIgnore);
        
        binder = new Binder<>();
        binder.setDisabledFieldsIgnore(toggle);
        
        TextField txtName = new TextField();
        binder.forField(txtName).asRequired()
        	.bind(Person::getName, Person::setName);
        txtName.setReadOnly(true);
        
        txtOveraged = new Checkbox();
        binder.forField(txtOveraged)
        	.bind(Person::isOveraged, Person::setOveraged);
        
        txtAge = new TextField();
        binder.forField(txtAge).asRequired()
        	.withConverter(new StringToIntegerConverter("not an integer"))
        	.withValidator(new IntegerRangeValidator("not over 18", 18, Integer.MAX_VALUE))
        	.bind(Person::getAge, Person::setAge);
        txtAge.setEnabled(false);
        
        TextField txtCity = new TextField();
        binder.forField(txtCity).asRequired()
        	.bind(Person::getCity, Person::setCity);
        txtCity.setVisible(false);
        
        binder.addValueChangeListener(e -> binderChanged(e));
        
        
        FormLayout form = new FormLayout(txtName, txtOveraged, txtAge, txtCity);
        
        add(form);
        
        Button btnValidate = new Button("validate");
        btnValidate.addClickListener(e -> validate());
        add(btnValidate);
        
        Person person = new Person();
        binder.setBean(person);
    }
    
    private void binderChanged(ValueChangeEvent<?> e) {
    	Person bean = this.binder.getBean();
        
        Boolean isNecessary = false;
        if(e.getHasValue() == this.txtOveraged) {
            isNecessary = (Boolean)e.getValue();
        }else {
            isNecessary = bean.isOveraged();
        }
        
        if(isNecessary != null && isNecessary) {
            this.txtAge.setEnabled(true);
        }else {
            this.txtAge.setEnabled(false);
            bean.setAge(0);
        }
        
        this.binder.setBean(bean);
	}

	private void validate() {
    	BinderValidationStatus<Person> validate = binder.validate();
    	System.out.println("validate: " + validate.isOk());
    }
    
    private void showConfirmDialog() {
    	ConfirmDialog dialog = new ConfirmDialog("header", "text", "confirmText", e-> confirmListener(e));
    	new ConfirmDialog();
    	dialog.open();
    }

	private void confirmListener(ConfirmEvent e) {
		System.out.println("confirm pressed");
	}

}
