/**
 * 
 */
package cz.festomat.server.bean;

import java.util.Date;

/**
 * @author Luboš Horáček
 *
 */
public class FestivalListBean {

	private String	id;
	private String	name;
	private String	lng, lat;
	private Date	start;

	public FestivalListBean(String id, String name, String lng, String lat, Date start) {
		super();
		this.id = id;
		this.name = name;
		this.lng = lng;
		this.lat = lat;
		this.start = start;
	}

	public FestivalListBean() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
