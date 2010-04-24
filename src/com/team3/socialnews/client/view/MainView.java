package com.team3.socialnews.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.team3.socialnews.client.Order;
import com.team3.socialnews.client.presenter.MainPresenter;

public class MainView extends AbstractView implements MainPresenter.Display {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}
	
	/* Banner */
	@UiField VerticalPanel rootPanel;
	@UiField HorizontalPanel bannerSection;
	@UiField HorizontalPanel mainSection;
	@UiField HorizontalPanel accountMenu;
	@UiField FocusPanel logo;
	
	/* Places */
	@UiField HorizontalPanel hotButton;
	@UiField HorizontalPanel newButton;
	@UiField Anchor hotAnchor;
	@UiField Anchor newAnchor;
	@UiField Label messageLabel;
	
	private Widget modalWidget = null;
	
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		rootPanel.setCellHeight(bannerSection, "25px");
		accountMenu.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
				
		hotAnchor.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				messageLabel.setVisible(true);
			}
		});		
		newAnchor.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				messageLabel.setVisible(true);
			}
		});

		
	}

	@Override
	public HasWidgets getBannerSection() {
		return bannerSection;
	}
	
	@Override
	public HasWidgets getMainSection() {
		return mainSection;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void addAccountMenuItem(String itemName, ClickHandler handler) {
		Anchor menuItem = new Anchor(itemName);
		menuItem.addClickHandler(handler);
		menuItem.addStyleName("{style.accountMenuItem}");
		accountMenu.add(menuItem);
	}

	@Override
	public void clearAccountMenuItems() {
		accountMenu.clear();
	}

	@Override
	public void hideModal() {
		if (modalWidget != null) {
			rootPanel.remove(modalWidget);
			rootPanel.add(mainSection);
			modalWidget = null;
		}
	}

	@Override
	public void showModal(Widget widget) {
		rootPanel.remove(mainSection);
		rootPanel.add(widget);
		modalWidget = widget;
	}

	@Override
	public void setIsLoggedIn(boolean isLoggedIn) {
		// TODO: Perhaps show the nickname
	}

	@Override
	public void setNickname(String nickname) {
		// Sets the nickname displayed
	}

	@Override
	public HasClickHandlers getLogo() {
		return this.logo;
	}

	@Override
	public void goToSiteUrl(String url) {
		Window.Location.assign(url);
	}

	@Override
	public HasClickHandlers getHotButton() {
		return this.hotAnchor;
	}

	@Override
	public HasClickHandlers getNewButton() {
		// TODO Auto-generated method stub
		return this.newAnchor;
	}

	public void setLoadingMessage(String message) {
		this.messageLabel.setText(message);
		this.messageLabel.setStyleName("placeMessageLabel");
		this.messageLabel.setVisible(true);
	}
	
	public void hideMessage(){
		this.messageLabel.setVisible(false);
	}

	@Override
	public void setLoadingErrorMessage(String error) {
		this.messageLabel.setText(error);
		this.messageLabel.setStyleName("placeErrorLabel");
		this.messageLabel.setVisible(true);
	}

	@Override
	public void setOrder(Order order) {
		if(order == Order.HOT) {
			hotAnchor.setStyleName("placeSelected");
			hotButton.setStyleName("placeButtonSelected");
			newAnchor.setStyleName("place");
			newButton.setStyleName("placeButton");
		}
		else if(order == Order.NEW) {
			hotAnchor.setStyleName("place");
			hotButton.setStyleName("placeButton");
			newAnchor.setStyleName("placeSelected");
			newButton.setStyleName("placeButtonSelected");
		} else {
			hotAnchor.setStyleName("place");
			hotButton.setStyleName("placeButton");
			newAnchor.setStyleName("place");
			newButton.setStyleName("placeButton");
		}
	}
}
