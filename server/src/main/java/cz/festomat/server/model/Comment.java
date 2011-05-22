/**
 * 
 */
package cz.festomat.server.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.appengine.api.datastore.Key;

/**
 * @author Luboš Horáček
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Version(strategy = VersionStrategy.VERSION_NUMBER)
public class Comment {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key		key;

	/**
	 * Jméno festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	autor;

	/**
	 * Jméno festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date	time;

	/**
	 * Jméno festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	text;

	/**
	 * Jméno festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Festival	festival;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
	}

}
