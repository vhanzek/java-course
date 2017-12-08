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
 * The {@code HttpServlet} used for reading thumbnail with name got from
 * {@link HttpServletRequest} parameter if it already exists on the disk.
 * If the thumbnail does not exist, it is created and put into corresponding
 * directory.
 * 
 * @author Vjeran
 */
@WebServlet("/thumbnail")
public class ThumbnailServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1366516434429889211L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		
		Path thumbPath = Paths.get(PictureUtil.THUMBNAIL_DIR, name);
		BufferedImage thumbnail = null;
		if(Files.notExists(thumbPath)){
			thumbnail = PictureUtil.saveThumbnail(thumbPath);
		} else {
			thumbnail = ImageIO.read(Files.newInputStream(thumbPath));
		}
		ImageIO.write(thumbnail, "jpg", resp.getOutputStream());
	}

}
