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
 * The {@code HttpServlet} used for retrieving all defined picture tags.
 * {@link Gson} instance is used for passing data .HTML file.
 * 
 * @author Vjeran
 */

@WebServlet("/tags")
public class TagsServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6411840437864592417L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> tags = PictureUtil.getTags();
		String[] tagArray = new String[tags.size()];
		tags.toArray(tagArray);

		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(tagArray);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
