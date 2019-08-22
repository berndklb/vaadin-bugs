package com.example.spring;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RoutePrefix;

//@RoutePrefix("logout")
@Route(value = "test")
//@CssImport(value = "./styles/menu.css")
//@CssImport(value = "./styles/colors.css")
//@CssImport(value = "./styles/fonts.css")
public class TestView extends VerticalLayout{

	public TestView() {
		
		this.add(new Span("test"));
	}
}
