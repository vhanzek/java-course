package hr.fer.zemris.java.webapps.gallery.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapps.gallery.PictureUtil;

/**
 * The {@code HttpServlet} used for reading image from the disk with name
 * got from {@link HttpServletRequest} parameter. It sends image bytes to
 * {@link HttpServletResponse} output stream.
 * 
 * @author Vjeran
 */
@WebServlet("/picture")
public class PictureServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6591468717023184074L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		
		Path imgPath = Paths.get(PictureUtil.PICTURES_DIR, name);
		BufferedImage image = ImageIO.read(Files.newInputStream(imgPath));
		ImageIO.write(image, "jpg", resp.getOutputStream());
	}

}
