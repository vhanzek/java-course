package hr.fer.zemris.java.webapps.gallery.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.webapps.gallery.PictureUtil;

/**
 * The {@code HttpServlet} used for retrieving names of the pictures
 * that has tag got from {@link HttpServletRequest} parameter. It uses
 * {@link Gson} for passing data.
 * 
 * @author Vjeran
 */
@WebServlet("/pictures")
public class PicturesServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1366516434429889211L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tag");
		List<String> pics = PictureUtil.getPictureNamesForTag(tag);
		
		String[] picArray = new String[pics.size()];
		pics.toArray(picArray);

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(picArray);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
