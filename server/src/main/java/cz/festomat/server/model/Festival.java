/**
 * 
 */
package cz.festomat.server.model;

import java.util.Collection;
import java.util.Date;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.Order;
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
public class Festival {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key		key;

	/**
	 * Jméno festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	jmeno;

	/**
	 * Adresa festivalu - zatím bude stačit jednořádkové info podle kterého kdyžtak můžeme odkazovat do map
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	adresa;

	/**
	 * GPS souřednice festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	lng, lat;

	/**
	 * Info, program a jine hlouposti z festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String	popis;
	
	/**
	 * Začátak festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date	zacaten;
	
	/**
	 * Konec festivalu
	 */
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date	konec;

	/**
	 * Kommenty festivalu
	 */
	@Persistent(mappedBy = "festival")
	@Element(dependent = "true")
	@Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "time desc"))
	private Collection<Comment>	komentare;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getPopis() {
		return popis;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public Date getZacaten() {
		return zacaten;
	}

	public void setZacaten(Date zacaten) {
		this.zacaten = zacaten;
	}

	public Date getKonec() {
		return konec;
	}

	public void setKonec(Date konec) {
		this.konec = konec;
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

	public Collection<Comment> getKomentare() {
		return komentare;
	}

	public void setKomentare(Collection<Comment> komentare) {
		this.komentare = komentare;
	}

}
