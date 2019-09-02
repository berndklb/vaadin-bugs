package com.example.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.spring.dragndrop.SalesOrgDTO;
import com.example.spring.dragndrop.TwinGridSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route(value = "", layout=MainLayout.class)
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    public MainView(@Autowired MessageBean bean) {
        Button button = new Button("Click me",
                e -> Notification.show(bean.getMessage()));
        add(button);
        
        Button btnConfirmDialog = new Button("ConfirmDialog", e-> showConfirmDialog());
        add(btnConfirmDialog);
        
        List<SalesOrgDTO> salesOrgs = getDummyData();
        TwinGridSelect gridSelect = new TwinGridSelect();
        gridSelect.setItems(salesOrgs);
        add(gridSelect);
    }
    
    private List<SalesOrgDTO> getDummyData() {
    	List<SalesOrgDTO> salesOrgs = new ArrayList<>();
    	for(int i=0; i<20; i++) {
    		SalesOrgDTO salesOrg = new SalesOrgDTO(""+i, "Orga " + i);
    		salesOrgs.add(salesOrg);
    	}
    	return salesOrgs;
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
