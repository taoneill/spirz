package com.team3.socialnews.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdjustedColumns extends Composite implements HasWidgets {

	private static AdjustedColumnsUiBinder uiBinder = GWT
			.create(AdjustedColumnsUiBinder.class);

	interface AdjustedColumnsUiBinder extends UiBinder<Widget, AdjustedColumns> {
	}
	
	int maxColumnWidth = 600;
	@UiField
	HorizontalPanel rootPanel;
	ArrayList<Widget> widgets = new ArrayList<Widget>();
	
	public AdjustedColumns() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		widgets.add(w);
		refresh();
	}
	
	public void addAll(Collection<Widget> widgets) {
		this.widgets.addAll(widgets);
		refresh();
	}
	
	public void resizeColumns() {
		int width = this.getOffsetWidth();
		int maxColumnCount = width/maxColumnWidth;
		int curentColumnCount = rootPanel.getWidgetCount();
		int widgetCount = widgets.size();
		
		if (curentColumnCount < maxColumnCount) {
			// We could possibly fit more columns
			// If we have enough widgets, do so
			if (widgetCount > curentColumnCount) {
				refresh();
			}
		} else  if (curentColumnCount > maxColumnCount) {
			// We have too many columns
			refresh();
		}
	}
	
	private void refresh() {
		rootPanel.clear();
		int widgetCount = widgets.size();
		if (widgetCount == 0)
			return;
		int width = this.getOffsetWidth();
		int maxColumnCount = Math.max(width/maxColumnWidth, 1);
		
		int columnCount;
		if (widgetCount >= maxColumnCount) {
			columnCount = maxColumnCount;
		} else {
			columnCount = widgetCount;
		}
		
		// Create the columns
		int columnPercent = 100/columnCount;
		for (int i = 0; i < columnCount ; i++) {
			VerticalPanel column = new VerticalPanel();
			column.setWidth("100%");
			rootPanel.add(column);
			rootPanel.setCellWidth(column, columnPercent+"%");
		}
		
		if (columnCount == 1) {
			for(Widget w : widgets) {
				VerticalPanel column = 
					(VerticalPanel)rootPanel.getWidget(0);
				column.add(w);
			}
		} else {
			for(Widget w : widgets) {
				int minColumnIndex = 0;
				int minColumnHeight = Integer.MAX_VALUE;
				for(int i = 0; i < rootPanel.getWidgetCount(); i++) {
					VerticalPanel column = 
						(VerticalPanel)rootPanel.getWidget(i);
					int columnHeight = column.getOffsetHeight();
					if (columnHeight < minColumnHeight) {
						minColumnIndex = i;
						minColumnHeight = columnHeight;
					}
				}
				
				VerticalPanel column = 
					(VerticalPanel)rootPanel.getWidget(minColumnIndex);
				column.add(w);
			}
		}
	}

	@Override
	public void clear() {
		widgets.clear();
		rootPanel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return widgets.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		if (!widgets.remove(w))
			return false;
		refresh();
		return true;
	}
}
