package hr.fer.zemris.java.webapps.gallery;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * The utility class 
 */
public class PictureUtil {
	
	/** The directory with thumbnails. */
	public static final String THUMBNAIL_DIR = "src/main/webapp/WEB-INF/thumbnails";
	
	/** The directory with pictures. */
	public static final String PICTURES_DIR = "src/main/webapp/WEB-INF/slike";
	
	/** The file with pictures information. */
	public static final String PICTURE_INFO_FILE = "src/main/webapp/WEB-INF/opisnik.txt";
	
	/** The size of the thumbnail. */
	private static final int THUMBNAIL_SIZE = 150;
	

	/**
	 * Gets the names of pictures that has given tag.
	 *
	 * @param tag the tag
	 * @return the picture names with given tag
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<String> getPictureNamesForTag(String tag) throws IOException {
		List<String> pictureNames = new ArrayList<>();
		
		List<String> lines = Files.readAllLines(Paths.get(PICTURE_INFO_FILE));
		for(int i = 0, n = lines.size(); i < n; i++){
			if(i % 3 == 2){
				List<String> tags = Arrays.asList(lines.get(i).split("\\s*,\\s*"));
				if(tags.contains(tag)){
					pictureNames.add(lines.get(i - 2));
				}
			}
		}
		return pictureNames;
	}
	
	/**
	 * Gets the instance of class {@link Picture} with given name.
	 *
	 * @param name the picture name
	 * @return the picture with given name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Picture getPictureForName(String name) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(PICTURE_INFO_FILE));
		for(int i = 0, n = lines.size(); i < n; i++){
			if(i % 3 == 0){
				if(lines.get(i).equals(name)){
					String desc = lines.get(i + 1);
					List<String> tags = Arrays.asList(lines.get(i + 2).split("\\s*,\\s*"));
					return new Picture(name, desc, tags);
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets all defined picture tags. Every tag is defined only once.
	 * Also, tags are sorted alphabetically.
	 *
	 * @return the tags
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static List<String> getTags() throws IOException{
		Set<String> tags = new HashSet<>();
		
		List<String> lines = Files.readAllLines(Paths.get(PICTURE_INFO_FILE));
		for(int i = 0, n = lines.size(); i < n; i++) {
			if(i % 3 == 2) {
				String[] lineTags = lines.get(i).split("\\s*,\\s*"); 
				tags.addAll(Arrays.asList(lineTags));
			}
		}
		List<String> tagList = new ArrayList<>(tags);
		Collections.sort(tagList);
		return tagList;
	}
	
	/**
	 * Method for saving created thumbnail into directory with given path
	 * {@code thumbPath}. The name of the thumbnail is the same as the original
	 * picture
	 *
	 * @param thumbPath the thumbnail path
	 * @return the created thumbnail
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage saveThumbnail(Path thumbPath) throws IOException{
		BufferedImage image = ImageIO.read(
			Files.newInputStream(Paths.get(PICTURES_DIR, thumbPath.getFileName().toString()))
		);
		BufferedImage thumbnail = createThumbnail(image);
		
		ImageIO.write(
			thumbnail, "jpg", Files.newOutputStream(thumbPath)
		);
		return thumbnail;
	}
	
	/**
	 * Helper method for creating thumbnail from given image {@code img}.
	 * It creates small 150x150 picture using original picture.
	 *
	 * @param img the original picture
	 * @return the thumbnail of original picture
	 */
	private static BufferedImage createThumbnail(BufferedImage img) {
		BufferedImage thumbnail = 
			new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D) thumbnail.createGraphics();
		g.setRenderingHint(
			RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BICUBIC
		);
		g.drawImage(img, 0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE, null);
		g.dispose();
		return thumbnail;
	}	
}
