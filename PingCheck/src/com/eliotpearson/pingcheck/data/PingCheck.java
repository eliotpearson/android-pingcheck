package com.eliotpearson.pingcheck.data;

public class PingCheck {
	private long id;
	private String url;
	private long ping;
	private long start;
	private long end;
	
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public long getPing() {
		return ping;
	}
	
	public void setPing(long ping) {
		this.ping = ping;
	}
	
	@Override
	public String toString() {
		long totalPing = end - start;
		String timeMessage = "";
		
		if(totalPing > 0) {
			timeMessage = " - " + totalPing + " ms";
		}
		
		return url + timeMessage;
	}
}
