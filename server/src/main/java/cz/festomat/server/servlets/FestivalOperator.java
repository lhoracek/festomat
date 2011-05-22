/**
 * 
 */
package cz.festomat.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;

import cz.festomat.server.bean.CommentBean;
import cz.festomat.server.bean.FestivalBean;
import cz.festomat.server.model.Comment;
import cz.festomat.server.model.Festival;

/**
 * @author Luboš Horáček
 * 
 */
public class FestivalOperator extends HttpServlet {

	private static final long	serialVersionUID		= 491455767310850876L;

	private final String		uriFestivalDetail		= "^/[a-zA-Z0-9]+$";
	private final String		uriFestivalComments		= "^/[a-zA-Z0-9]+/comments$";
	private final String		uriFestivalList			= "^/list$";
	private final String		uriFestivalCommentNew	= "^/[a-zA-Z0-9]+/comments/new$";

	private static final Logger	log						= Logger.getLogger(FestivalOperator.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		PrintWriter out = resp.getWriter();
		log.info(uri);

		resp.setContentType("text/plain; charset=UTF-8");

		if (uri.matches(uriFestivalComments)) {
			Festival f = PMF.getManager().getObjectById(Festival.class, KeyFactory.stringToKey(uri.split("/")[1]));

			Collection<CommentBean> obj = new ArrayList<CommentBean>();

			for (Comment c : f.getKomentare()) {
				obj.add(new CommentBean(c.getAutor(), c.getTime(), c.getText()));
			}

			Gson gson = new Gson();
			out.write(gson.toJson(obj));

		} else if (uri.matches(uriFestivalList)) {
			Query q = PMF.getManager().newQuery(Festival.class);
			Collection<Festival> result = (Collection) q.execute();

			Map obj = new LinkedHashMap();
			for (Festival f : result) {
				obj.put(KeyFactory.keyToString(f.getKey()), removeDiacritics(f.getJmeno()));
			}

			Gson gson = new Gson();
			out.write(gson.toJson(obj));

		} else if (uri.matches(uriFestivalDetail)) {
			Festival f = PMF.getManager().getObjectById(Festival.class, KeyFactory.stringToKey(uri.split("/")[1]));

			FestivalBean fb = new FestivalBean(KeyFactory.keyToString(f.getKey()), f.getAdresa(),
					removeDiacritics(f.getJmeno()), f.getLng(), f.getLat(), f.getZacaten(), f.getKonec(),
					removeDiacritics(f.getPopis()));

			Gson gson = new Gson();
			out.write(gson.toJson(fb));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.matches(uriFestivalCommentNew)) {
			Gson gson = new Gson();
			CommentBean cb = gson.fromJson(req.getParameter("comment"), CommentBean.class);

			log.info(req.getParameter("comment"));

			PersistenceManager pm = PMF.getManager();

			Festival f = pm.getObjectById(Festival.class, KeyFactory.stringToKey(uri.split("/")[1]));

			Comment c = new Comment();
			c.setTime(cb.getTime());
			c.setAutor(cb.getAuthor());
			c.setFestival(f);
			c.setText(cb.getText());

			try {
				pm.makePersistent(c);
			} finally {
				pm.close();
			}

		}
	}

	public static String removeDiacritics(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

}
