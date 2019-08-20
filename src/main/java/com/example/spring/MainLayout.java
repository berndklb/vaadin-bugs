package com.example.spring;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

//@Theme(value = Material.class, variant = Material.LIGHT)
@Theme(value = Lumo.class)
public class MainLayout extends AppLayout {

	public MainLayout() {
		Tabs menu = createMenuTabs();

		this.addToNavbar(true, menu);
	}
	
	private Tabs createMenuTabs() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
		tabs.add(getAvailableTabs());
		return tabs;
	}

	private Tab[] getAvailableTabs() {
		final List<Tab> tabs = new ArrayList<>(4);
		tabs.add(createTab(VaadinIcon.HOME_O, "menu.home", MainView.class));
		tabs.add(createTab(VaadinIcon.ABACUS, "menu.home", AppTabView.class));
		tabs.add(createTab(VaadinIcon.ABSOLUTE_POSITION, "menu.home", MainView.class));
		tabs.add(createTab(VaadinIcon.HOME_O, "menu.home", MainView.class));
		tabs.add(createTab(VaadinIcon.HOME_O, "menu.home", MainView.class));
		tabs.add(createTab(VaadinIcon.HOME_O, "menu.home", MainView.class));
		tabs.add(createTab(VaadinIcon.YOUTUBE_SQUARE, "menu.home", MainView.class));

		final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
		final Tab logoutTab = createTab(createLogoutLink(contextPath));
		tabs.add(logoutTab);
		return tabs.toArray(new Tab[tabs.size()]);
	}

	private Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
		return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
	}

	private Tab createTab(Component content) {
		final Tab tab = new Tab();
		tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		tab.add(content);
		return tab;
	}

	private Anchor createLogoutLink(String contextPath) {
		final Anchor a = populateLink(new Anchor(), VaadinIcon.SIGN_OUT, "menu.logout");
		a.setHref(contextPath + "/logout");
		return a;
	}
	
	private <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
		a.add(icon.create());
		a.add(getTranslation(title));
		return a;
	}
}
