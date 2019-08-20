package com.example.spring;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "app-tab-view", layout=MainLayout.class)
public class AppTabView extends VerticalLayout {

	public AppTabView() {
		
		this.add(new Span("test"));

	}
	
}
