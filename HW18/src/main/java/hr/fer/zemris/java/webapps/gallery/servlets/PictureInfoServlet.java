package hr.fer.zemris.java.webapps.gallery.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.webapps.gallery.Picture;
import hr.fer.zemris.java.webapps.gallery.PictureUtil;

/**
 * The {@code HttpServlet} used for retrieving picture information with name
 * got from {@link HttpServletRequest} parameter. It forwards picture's name,
 * description and tags associated with it using {@link Gson} instance.
 * 
 * @author Vjeran
 */
@WebServlet("/picture-info")
public class PictureInfoServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1606671214043666065L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		Picture pic = PictureUtil.getPictureForName(name);
		
		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(pic);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
