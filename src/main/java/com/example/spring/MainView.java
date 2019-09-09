package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.bkb.vaadin.progressline.ProgressLine;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route(value = "", layout=MainLayout.class)
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
@CssImport("./styles/main-view.css")
public class MainView extends VerticalLayout {

    public MainView(@Autowired MessageBean bean) {
    	this.addClassName("main-view");
        Button button = new Button("Click me",
                e -> Notification.show(bean.getMessage()));
        add(button);
        
        Button btnConfirmDialog = new Button("ConfirmDialog", e-> showConfirmDialog());
        add(btnConfirmDialog);
        
        ProgressLine progressLine = new ProgressLine("test");
        progressLine.setItems("Prozessschritt 1", "Prozessschritt 2", "Prozessschritt 3", "Prozessschritt 4");
        add(progressLine);
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
