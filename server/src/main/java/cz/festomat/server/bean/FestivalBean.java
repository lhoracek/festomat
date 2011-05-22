package cz.festomat.server.bean;

import java.util.Date;

public class FestivalBean {
	
	public FestivalBean(String id, String address, String name, String lng, String lat,
			Date start, Date end, String description) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.lng = lng;
		this.lat = lat;
		this.start = start;
		this.end = end;
		this.description = description;
	}
	//key
	private String id;
	private String address;
	private String name;
	//GPS position
	private String lng;
	private String lat;
	private Date start;
	private Date end;
	private String description;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
