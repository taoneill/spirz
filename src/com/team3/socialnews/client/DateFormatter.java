package com.team3.socialnews.client;

import java.util.Date;

public class DateFormatter {
	public static String getTimeAgoFromDate(Date date){
		long msecAgo = new Date().getTime() - date.getTime();
		long daysAgo = msecAgo / (24*3600000);
		String timeAgo = Long.toString(daysAgo) + " days ago";
		if(daysAgo == 0){
			long hoursAgo = msecAgo / 3600000; // in hours
			timeAgo = Long.toString(hoursAgo) + " hours ago";
			if(hoursAgo == 0) {
				long minsAgo = msecAgo / 60000;
				timeAgo = Long.toString(minsAgo) + " minutes ago";
				if(minsAgo == 0){
					long secsAgo = msecAgo / 1000;
					if(secsAgo > 0) timeAgo = Long.toString(secsAgo) + " seconds ago";
					else timeAgo = "0 seconds ago";	// there's a bug: this is just to avoid showing negative timeAgo values 
				} else if(minsAgo == 1){
					timeAgo = "1 minute ago";
				}
			} else if(hoursAgo == 1){
				timeAgo = "1 hour ago";
			}
			
		} else if(daysAgo == 1){
			timeAgo = "1 day ago";
		}
		
		return timeAgo;
	}
}
