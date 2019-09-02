package com.example.spring.dragndrop;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.shared.Registration;

public class TwinGridSelect extends Composite<FlexLayout> implements HasValue<ValueChangeEvent<Set<SalesOrgDTO>>, Set<SalesOrgDTO>> {

	private Set<SalesOrgDTO> choiceItems = new HashSet<>();
    private Set<SalesOrgDTO> selectedItems = new HashSet<>();
	private Set<ValueChangeListener<? super ValueChangeEvent<Set<SalesOrgDTO>>>> valueChangeListeners = new HashSet<>();
	
	private Grid<SalesOrgDTO> choiceGrid, selectedGrid;
//	private ListDataProvider<SalesOrgDTO> choiceDataProvider, selectedDataProvider;
	
	public TwinGridSelect() {
		this.buildUI();
		
		this.selectedGrid.setItems(selectedItems);
	}
	
	private void buildUI() {
		this.choiceGrid = new Grid<>();
		this.choiceGrid.addColumn(SalesOrgDTO::getId);
		this.choiceGrid.addColumn(SalesOrgDTO::getName);
		this.choiceGrid.setRowsDraggable(true);
		this.choiceGrid.setDropMode(GridDropMode.BETWEEN);
		this.choiceGrid.setSelectionMode(SelectionMode.MULTI);
		
		this.choiceGrid.addDragStartListener(e -> {
			System.out.println("start dragging: " + e);
		});
		this.choiceGrid.addDragEndListener(e -> {
			System.out.println("end dragging: " + e);
		});
		this.choiceGrid.addDropListener(e -> {
			System.out.println("drop: " + e);
			
			Set<SalesOrgDTO> salesOrgs = getDropSalesOrgs(e);
			choiceItems.addAll(salesOrgs);
			selectedItems.removeAll(salesOrgs);
			this.selectedGrid.getDataProvider().refreshAll();
			this.choiceGrid.getDataProvider().refreshAll();
			
			fireValueChangeEvent();
		});

		this.selectedGrid = new Grid<>();
		this.selectedGrid.addColumn(SalesOrgDTO::getId);
		this.selectedGrid.addColumn(SalesOrgDTO::getName);
		this.selectedGrid.setRowsDraggable(true);
		this.selectedGrid.setDropMode(GridDropMode.BETWEEN);
		this.selectedGrid.setSelectionMode(SelectionMode.MULTI);
		
		this.selectedGrid.addDropListener(e -> {
			System.out.println("drop on selected grid: " + e);
			System.out.println("data transfer text: " + e.getDataTransferText());
			System.out.println("data transfer data: " + e.getDataTransferData());
			System.out.println("drop location: " + e.getDropLocation());
			System.out.println("drop target item: " + e.getDropTargetItem());
			System.out.println("source: " + e.getSource());
			
//			Map<String, String> salesIds = new HashMap<>();
			Set<SalesOrgDTO> salesOrgs = getDropSalesOrgs(e);
			selectedItems.addAll(salesOrgs);
			choiceItems.removeAll(salesOrgs);
			this.selectedGrid.getDataProvider().refreshAll();
			this.choiceGrid.getDataProvider().refreshAll();
			
			fireValueChangeEvent();
			
		});
		
		this.getContent().add(choiceGrid, selectedGrid);
		this.getContent().setWidth("100%");
	}

	private Set<SalesOrgDTO> getDropSalesOrgs(GridDropEvent<SalesOrgDTO> e) {
		Set<SalesOrgDTO> salesOrgs = new HashSet<>();
		Optional<String> data = e.getDataTransferData("text/plain");
		if(data.isPresent()) {
			String[] salesOrgRows = data.get().split("\n");
			for(String row : salesOrgRows) {
				String[] parts = row.split("\t");
				if(parts.length == 2) {
					SalesOrgDTO salesOrg = new SalesOrgDTO(parts[0], parts[1]);
					salesOrgs.add(salesOrg);
				}
			}
		}
		return salesOrgs;
	}
	
	
	private void fireValueChangeEvent() {
		ComponentValueChangeEvent<TwinGridSelect, Set<SalesOrgDTO>> e = new ComponentValueChangeEvent<>(this, this, selectedItems, true);
		valueChangeListeners.forEach(listener -> listener.valueChanged(e));
	}

	@Override
	public void setValue(Set<SalesOrgDTO> value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Set<SalesOrgDTO> getValue() {
		return new HashSet<>(selectedItems);
	}
	@Override
	public Registration addValueChangeListener(
			ValueChangeListener<? super ValueChangeEvent<Set<SalesOrgDTO>>> listener) {
		valueChangeListeners.add(listener);
		return (Registration) () -> valueChangeListeners.remove(listener);
	}
	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isRequiredIndicatorVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setItems(List<SalesOrgDTO> salesOrgs) {
		choiceItems.clear();
		choiceItems.addAll(salesOrgs);
		this.choiceGrid.setItems(choiceItems);
	}
}
