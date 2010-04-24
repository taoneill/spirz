package com.team3.socialnews.client;

public class Pager {
	
	private long perPage = 24;
	private long start = 0, end = perPage;
	
	public void nextPage() {
		start += perPage;
		end += perPage;
	}
	
	public void previousPage() {
		start -= perPage;
		end -= perPage;
	}
	
	public boolean canGoNextPage(long howManyLeft) {
		return howManyLeft >= getNumberPerPage();
	}
	
	public boolean canGoPreviousPage() {
		return getPageStart() != 0;
	}	
	
	public long getPageStart() {
		return start;
	}
	
	public long getPageEnd() {
		return end;
	}
	
	public long getNumberPerPage() {
		return perPage;
	}
	
	public void setNumberPerPage(long numberPerPage) {
		this.perPage = numberPerPage; 
	}

	public void setPage(Integer page) {
		start = perPage*page;
		end = start+perPage;
	}
	
	public long getPage(){
		return start/perPage;
	}
}
