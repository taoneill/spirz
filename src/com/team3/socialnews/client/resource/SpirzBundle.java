package com.team3.socialnews.client.resource;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface SpirzBundle extends ClientBundle {
	@Source("dots-gray-s.png")
	DataResource dotsGrayS();

	@Source("dots-s.png")
	DataResource dotsS();
	
	@Source("dots-blue-s.png")
	DataResource dotsBlueS();

	@Source("plus-gray-s.png")
	DataResource plusGrayS();

	@Source("plus-s.png")
	DataResource plusS();
	
	@Source("plus-blue-s.png")
	DataResource plusBlueS();
	
	@NotStrict
	@Source("spirz.css")
	CssResource css();
	
	@Source("banner_bg.jpg")
	DataResource bannerBg();

	@Source("logo.jpg")
	DataResource bannerLogo();
	
	@Source("box_bottom_left.jpg")
	DataResource plainLinkBoxBottomLeft();
	
	@Source("box_bottom_right.jpg")
	DataResource plainLinkBoxBottomRight();
	
	@Source("box_bottom.jpg")
	DataResource plainLinkBoxBottom();
	
	@Source("box_top_right.jpg")
	DataResource plainLinkBoxTopRight();
	
	@Source("box_top_left.jpg")
	DataResource plainLinkBoxTopLeft();
	
	@Source("box_top.jpg")
	DataResource plainLinkBoxTop();
	
	@Source("box_right.jpg")
	DataResource plainLinkBoxRight();
	
	@Source("box_left.jpg")
	DataResource plainLinkBoxLeft();
	
	@Source("box_first_bottom_left.jpg")
	DataResource firstLinkBoxBottomLeft();
	
	@Source("box_first_bottom_right.jpg")
	DataResource firstLinkBoxBottomRight();
	
	@Source("box_first_bottom.jpg")
	DataResource firstLinkBoxBottom();
	
	@Source("box_first_top_right.jpg")
	DataResource firstLinkBoxTopRight();
	
	@Source("box_first_top_left.jpg")
	DataResource firstLinkBoxTopLeft();
	
	@Source("box_first_top.jpg")
	DataResource firstLinkBoxTop();
	
	@Source("box_first_right.jpg")
	DataResource firstLinkBoxRight();
	
	@Source("box_first_left.jpg")
	DataResource firstLinkBoxLeft();
	
	@Source("menu_second_bg_button_pressed_left.jpg")
	DataResource secondMenuBgPressedLeft();
	
	@Source("menu_second_bg_button_pressed_right.jpg")
	DataResource secondMenuBgPressedRight();
	
	@Source("menu_second_bg_button_pressed.jpg")
	DataResource secondMenuBgPressed();
	
	@Source("menu_second_bg.jpg")
	DataResource secondMenuBg();
	
	@Source("button_next.jpg")
	DataResource largeNextButton();
	
	@Source("title_whats_hot.jpg")
	DataResource whatsHotTitle();
	
	@Source("button_comment.jpg")
	DataResource commentButton();
	
//	@Source("button_vote_enabled.jpg")
//	DataResource voteButtonEnabled();
//	
//	@Source("button_vote_on.jpg")
//	DataResource voteButtonOn();
	
	@Source("vote-arrow-unvoted.png")
	DataResource voteButtonEnabled();
	
	@Source("vote-arrow-voted.png")
	DataResource voteButtonOn();
}
