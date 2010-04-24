package com.team3.socialnews.client.view;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RoundedPanel extends Composite implements HasWidgets {

	private static RoundedPanelUiBinder uiBinder = GWT
			.create(RoundedPanelUiBinder.class);

	interface RoundedPanelUiBinder extends UiBinder<Widget, RoundedPanel>{
	}
	
	@UiField
	FlowPanel roundedRoot;
	@UiField
	FlowPanel roundedContainer;
	@UiField
	VerticalPanel midColumn;
	@UiField
	FlowPanel roundedTopLeft;
	@UiField
	FlowPanel roundedLeft;
	@UiField
	FlowPanel roundedBottomLeft;
	@UiField
	FlowPanel roundedTop;
	@UiField
	FlowPanel roundedBottom;
	@UiField
	FlowPanel roundedTopRight;
	@UiField
	FlowPanel roundedRight;
	@UiField
	FlowPanel roundedBottomRight;
	
	private int highlightLevel = 0;
	
	public RoundedPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void add(Widget w) {
		roundedContainer.add(w);
	}

	@Override
	public void clear() {
		roundedContainer.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return roundedContainer.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return roundedContainer.remove(w);
	}
	
	public void setMidColumnWidth(double width){
		midColumn.setWidth(width + "px");
	}
	
	public void setHighlightLevel(int level){
		this.highlightLevel = level;
		if(this.highlightLevel == 0){
			this.roundedTopLeft.addStyleName("roundedTopLeft");
			this.roundedLeft.addStyleName("roundedLeft");
			this.roundedBottomLeft.addStyleName("roundedBottomLeft");
			this.roundedTop.addStyleName("roundedTop");
			this.roundedBottom.addStyleName("roundedBottom");
			this.roundedTopRight.addStyleName("roundedTopRight");
			this.roundedRight.addStyleName("roundedRight");
			this.roundedBottomRight.addStyleName("roundedBottomRight");
		}
		else {
			this.roundedTopLeft.addStyleName("roundedTopLeft"+this.highlightLevel);
			this.roundedLeft.addStyleName("roundedLeft"+this.highlightLevel);
			this.roundedBottomLeft.addStyleName("roundedBottomLeft"+this.highlightLevel);
			this.roundedTop.addStyleName("roundedTop"+this.highlightLevel);
			this.roundedBottom.addStyleName("roundedBottom"+this.highlightLevel);
			this.roundedTopRight.addStyleName("roundedTopRight"+this.highlightLevel);
			this.roundedRight.addStyleName("roundedRight"+this.highlightLevel);
			this.roundedBottomRight.addStyleName("roundedBottomRight"+this.highlightLevel);
		}
	}
}
