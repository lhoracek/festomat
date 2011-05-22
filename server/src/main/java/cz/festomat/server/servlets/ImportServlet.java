/**
 * 
 */
package cz.festomat.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import cz.festomat.server.model.Festival;

/**
 * @author Luboš Horáček
 * 
 */
public class ImportServlet extends HttpServlet {

	private static final long	serialVersionUID	= -114121341295497171L;

	private final String		urlFeedu			= "http://www.letnifestivaly.info/festivaly.php";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		URL url = new URL(urlFeedu);
		InputStream is = url.openStream();
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader r;

		String text = null;

		Collection<Festival> festivaly = new ArrayList<Festival>();

		try {
			r = f.createXMLStreamReader(is);

			while (r.hasNext()) {
				r.next();
				if (r.getEventType() == XMLStreamConstants.END_DOCUMENT) {
					r.close();
					break;
				}
				if (r.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if (r.getLocalName().equals("marker")) {
						Festival fest = new Festival();
						for (int i = 0; i < r.getAttributeCount(); i++) {
							if (r.getAttributeName(i).getLocalPart().equals("name")) {
								fest.setJmeno(r.getAttributeValue(i));
							} else if (r.getAttributeName(i).getLocalPart().equals("description")) {
								fest.setPopis(r.getAttributeValue(i));
							} else if (r.getAttributeName(i).getLocalPart().equals("lng")) {
								fest.setLng(r.getAttributeValue(i));
							} else if (r.getAttributeName(i).getLocalPart().equals("lat")) {
								fest.setLat(r.getAttributeValue(i));
							} else if (r.getAttributeName(i).getLocalPart().equals("city")) {
								fest.setAdresa(r.getAttributeValue(i));
							} else if (r.getAttributeName(i).getLocalPart().equals("date")) {
								// TODO parse data
							}
						}
						festivaly.add(fest);
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}

		PersistenceManager pm = PMF.getManager();

		try {
			for (Festival fest : festivaly) {
				pm.makePersistent(fest);
			}
		} finally {
			pm.close();
		}

		super.doGet(req, resp);
	}

}
