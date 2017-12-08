package hr.fer.zemris.java.hw16.jvdraw.actions;

import static java.lang.Integer.parseInt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.Icons;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.components.DrawingButtonGroup;
import hr.fer.zemris.java.hw16.jvdraw.components.JExportPanel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.GeoObjectsDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractGeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * The abstract class {@code JVDrawActions} containing all defined actions 
 * used in {@link JVDraw} program.
 * 
 * @author Vjeran
 */
public abstract class JVDrawActions {
	
	/** The drawing frame. */
	private static JVDraw drawingFrame;
	
	/** The model. */
	private static GeoObjectsDrawingModel model;
	
	/** The opened file path. */
	private static Path openedFilePath;
	
	/**
	 * Method for initializing {@link GeoObjectsDrawingModel} model used
	 * in defined actions.
	 *
	 * @param drawingFrame the drawing frame
	 * @param model the model
	 */
	public static void initialize(JVDraw drawingFrame, GeoObjectsDrawingModel model, JDrawingCanvas canvas){
		JVDrawActions.drawingFrame = drawingFrame;
		JVDrawActions.model = model;
	}
	
	/** 
	 * The action representing "Draw line" button in tool bar of {@link JVDraw}
	 * program. Its {@linkplain Action#actionPerformed(ActionEvent)} is has empty
	 * implementation because all buttons in tool bar are mutually exclusive and
	 * the one which is currently selected can be retrieved from {@link DrawingButtonGroup}
	 * class.
	 */
	public static final Action drawLineAction = new AbstractAction() {

		private static final long serialVersionUID = -7236891280202202404L;
		
		{
			createAction(this, "Line", "line", "control L", KeyEvent.VK_L, "Draw line.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};
	
	/** 
	 * The action representing "Draw circle" button in tool bar of {@link JVDraw}
	 * program. Its {@linkplain Action#actionPerformed(ActionEvent)} is has empty
	 * implementation because all buttons in tool bar are mutually exclusive and
	 * the one which is currently selected can be retrieved from {@link DrawingButtonGroup}
	 * class.
	 */
	public static final Action drawCircleAction = new AbstractAction() {

		private static final long serialVersionUID = -7236891280202202404L;
		
		{
			createAction(this, "Circle", "circle", "control C", KeyEvent.VK_C, "Draw circle.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};
	
	/** 
	 * The action representing "Draw filled circle" button in tool bar of {@link JVDraw}
	 * program. Its {@linkplain Action#actionPerformed(ActionEvent)} is has empty
	 * implementation because all buttons in tool bar are mutually exclusive and
	 * the one which is currently selected can be retrieved from {@link DrawingButtonGroup}
	 * class.
	 */
	public static final Action drawFilledCircleAction = new AbstractAction() {

		private static final long serialVersionUID = -7236891280202202404L;
		
		{
			createAction(this, "Filled circle", "filled_circle", "control F", 
						 KeyEvent.VK_F, "Draw filled circle.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};
	
	/** 
	 * The action for opening file that already exists on the disc.
	 * User is asked which file wants to open. After file has been selected,
	 * its content is processed and rendered to user.
	 */
	public static final Action openAction = new AbstractAction() {

		private static final long serialVersionUID = -3250410616317752370L;

		{
			createAction(this, "Open", null, "control O", KeyEvent.VK_O, "Open JVD file.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("JVD files", "jvd");
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(filter);
			fc.setDialogTitle("Open");
			
			int retValue = fc.showOpenDialog(drawingFrame);
			if (retValue != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = fc.getSelectedFile();
			Path filePath = file.toPath();
			
			if(!isJVDfile(filePath.toString())) {
				showErrorMessage("File " + file.getAbsolutePath() + " does not have .jvd exstension!");
				return;
			}
			
			if (!Files.isReadable(filePath)) {
				showErrorMessage("File " + file.getAbsolutePath() + " does not exist!");
				return;
			}
			
			List<String> lines = null;
			try {
				lines = Files.readAllLines(filePath);
			} catch (Exception ex) {
				showErrorMessage("Error reading file " + file.getAbsolutePath());
				return;
			}
			
			processLines(lines);
		}

		private boolean isJVDfile(String file) {
			int index = file.lastIndexOf('.');
			return file.substring(index + 1, file.length()).toLowerCase().equals("jvd");
		}

		private void processLines(List<String> lines) {
			model.clear();
			for(String line : lines){
				String[] elements = line.split("\\s");
				GeometricalObject object = getObject(elements);
				if(object != null){
					model.add(object);
				}
			}
		}

		private GeometricalObject getObject(String[] e) {
			switch(e[0]){
			case "LINE":
				return new Line(new Point(parseInt(e[1]), parseInt(e[2])), 
								new Point(parseInt(e[3]), parseInt(e[4])), 
								new Color(parseInt(e[5]), parseInt(e[6]), parseInt(e[7])));
			case "CIRCLE":
				return new Circle(new Point(parseInt(e[1]), parseInt(e[2])), parseInt(e[3]), 
								  new Color(parseInt(e[4]), parseInt(e[5]), parseInt(e[6])));
			case "FCIRCLE":
				return new FilledCircle(new Point(parseInt(e[1]), parseInt(e[2])), parseInt(e[3]), 
						  				new Color(parseInt(e[4]), parseInt(e[5]), parseInt(e[6])),
						  				new Color(parseInt(e[7]), parseInt(e[8]), parseInt(e[9])));
			default:
				return null;
			}
		}
	};
	
	/** 
	 * The action for saving file (information from drawn objects) to disc.
	 * User is asked where and under what file needs to be saved. If file already
	 * exist, a user is again asked if he wants to replace it. Also, if editor has not
	 * been modified i.e. if information has been saved, program automatically saves
	 * drawing canvas's information to file associated with earlier mentioned canvas. 
	 * After file has been selected, information of drawn objects is written to file. 
	 */
	public static final Action saveAction = new AbstractAction() {

		private static final long serialVersionUID = 2744298545392415513L;

		{
			createAction(this, "Save", null, "control S", KeyEvent.VK_S, "Save JVD file.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;
			if (openedFilePath == null) {
				flag = openSaveDialog("Save");
			}
			if(flag) {
				saveFile();
			}
		}
	};
	
	/** 
	 * The action similar to {@link #saveAction}, but it always asks
	 * user where and under which name file wants to be saved.
	 */
	public static final Action saveAsAction = new AbstractAction() {

		private static final long serialVersionUID = 4253146260741109566L;

		{
			createAction(this, "Save As...", null, "control alt pressed S", KeyEvent.VK_A, "Save JVD file.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(openSaveDialog("Save As")) saveFile();
		}
	};
	
	/** 
	 * The action for exporting current drawing editor situation as image to disc.
	 * The user is asked to choose image type - JPG, PNG, GIF. Similar to 
	 * {@link #saveAction} user is asked to overwrite file if selected file to export
	 * to already exists.
	 */
	public static final Action exportAction = new AbstractAction() {

		private static final long serialVersionUID = 4295593901644409857L;

		{
			createAction(this, "Export", null, "control E", KeyEvent.VK_E, "Export JVD file.");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			openExportDialog();
		}
	};
	
	
	/* --------------------------------- HELPER METHODS --------------------------------- */
	
	/**
	 * Helper method for creating specified action. It sets its name,
	 * accelerator key, mnemonic key, short description and its icon.
	 *
	 * @param action the action
	 * @param name the name
	 * @param iconName the icon name
	 * @param accKey the accelerator key
	 * @param mnemonicKey the mnemonic key
	 * @param description the description
	 */
	private static void createAction(Action action, String name, String iconName, String accKey, 
									 int mnemonicKey, String description) {
		action.putValue(Action.NAME, name);
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accKey));
		action.putValue(Action.MNEMONIC_KEY, mnemonicKey);
		action.putValue(Action.SHORT_DESCRIPTION, description);
		if(iconName != null){
			action.putValue(Action.SMALL_ICON, Icons.getInstance().getIcon(iconName));
		}
	}
	
	/**
	 * Helper method for showing error message to user.
	 *
	 * @param message the error message
	 */
	private static void showErrorMessage(String message){
		JOptionPane.showMessageDialog(
			drawingFrame,
			message,
			"Error",
			JOptionPane.ERROR_MESSAGE
		);
	}
	
	/**
	 * Method which opens {@link JFileChooser} and ask user to choose
	 * where and under what name drawing editor information will be saved. 
	 * If selected file already exist, user is asked if he wants to replace
	 * it with the new one.
	 *
	 * @param title title of the {@link JFileChooser}
	 * @return <code>true</code>, if successful
	 * 		   <code>false</code> otherwise 
	 */
	private static boolean openSaveDialog(String title){
		FileNameExtensionFilter filter = 
			new FileNameExtensionFilter("JVD files", "jvd");
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		fc.setDialogTitle(title);
		
		while(true){
			int retValue = fc.showSaveDialog(drawingFrame);
			if (retValue != JFileChooser.APPROVE_OPTION) {
				return false;
			}
			
			String path = fc.getSelectedFile().toPath().toString();
			openedFilePath = Paths.get(path.toLowerCase().endsWith(".jvd") ? path : path + ".jvd");
			
			if(Files.exists(openedFilePath)){
				 int reply = 
					JOptionPane.showConfirmDialog(
						drawingFrame,
						openedFilePath.getFileName() + " already exists.\nDo you want to replace it?",
						"Confirm Save As",
						JOptionPane.YES_NO_OPTION
					);
				if (reply == JOptionPane.YES_OPTION){
					break;
				}
			} else break;
		}
		
		return true;
	}
	
	/**
	 * Method for writing information of all drawn object to file
	 * selected in {@link #openSaveDialog(String)} method.
	 */
	private static void saveFile(){
		String content = "";
		for(int i = 0, n = model.getSize(); i < n; i++){
			AbstractGeometricalObject o = (AbstractGeometricalObject) model.getObject(i);
			if(o instanceof Line){
				Point start = o.getStartingPoint();
				Point end = o.getEndingPoint();
				Color color = o.getForegroundColor();
				content += 
					String.format(
						"%s %.0f %.0f %.0f %.0f %d %d %d%n", 
						o.getName(), start.getX(), start.getY(), 
						end.getX(), end.getY(), 
						color.getRed(), color.getGreen(), color.getBlue()
					);
			} else {
				AbstractCircle ac = (AbstractCircle) o;
				Point center = ac.getCenterPoint();
				int radius = ac.getRadius();
				Color fgc = ac.getForegroundColor();
				if (o instanceof Circle){
					content += 
						String.format(
							"%s %.0f %.0f %d %d %d %d%n", 
							o.getName(), center.getX(), center.getY(), 
							radius, fgc.getRed(), fgc.getBlue(), fgc.getGreen()
						);
				} else {
					Color bgc = ac.getBackgroundColor();
					content += 
						String.format(
							"%s %.0f %.0f %d %d %d %d %d %d %d%n", 
							o.getName(), center.getX(), center.getY(), 
							radius, fgc.getRed(), fgc.getBlue(), fgc.getGreen(),
							bgc.getRed(), bgc.getBlue(), bgc.getGreen()
						);
				}
			}
			
			try {
				Files.write(openedFilePath, content.getBytes());
			} catch (IOException ex) {
				showErrorMessage("Error writing file " + openedFilePath);
				return;
			}
		}
	}
	
	/**
	 * Method used in {@link #exportAction}. First, user is asked to choose
	 * exported image type (JPG, PNG, GIF). After that {@link JFileChooser}
	 * is opened and user picks where and under what name image will be exported.
	 * If selected file already exist, user is asked if he wants to replace
	 * it with the new one.
	 */
	private static void openExportDialog(){
		JExportPanel imgTypes = new JExportPanel();
		int response = 
			JOptionPane.showConfirmDialog(
				drawingFrame,
				imgTypes,
				"Export type",
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.INFORMATION_MESSAGE
			);
		
		String imgType = null;
		 if (response == JOptionPane.OK_OPTION){
			 imgType = imgTypes.getSelectedButtonText();
		 } else {
			 return;
		 }
		 imgType = imgType.toLowerCase();
		 
		FileNameExtensionFilter filter = 
			new FileNameExtensionFilter(imgType.toUpperCase() + " files", imgType);
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		fc.setDialogTitle("Export");
		
		while (true) {
			int retValue = fc.showSaveDialog(drawingFrame);
			if (retValue != JFileChooser.APPROVE_OPTION) {
				return;
			}

			String path = fc.getSelectedFile().toPath().toString();
			Path exportPath = Paths.get(path.toLowerCase().endsWith("."+imgType) ? path : path + "."+imgType);

			if (Files.exists(exportPath)) {
				int reply = 
					JOptionPane.showConfirmDialog(
						drawingFrame,
						exportPath.getFileName() + " already exists.\nDo you want to replace it?",
						"Confirm Export", JOptionPane.YES_NO_OPTION
					);
				if (reply == JOptionPane.YES_OPTION) {
					saveImage(imgType, exportPath);
					break;
				}
			} else {
				saveImage(imgType, exportPath);
				break;
			}
		}
	}

	/**
	 * Helper method for saving current drawing canvas situation as
	 * image to disc as file chosen in {@link #openExportDialog()} method.
	 *
	 * @param imgType the image type
	 * @param exportPath the export path
	 */
	private static void saveImage(String imgType, Path exportPath) {
		int maxX = 0, maxY = 0;
        for (int i = 0; i < model.getSize(); i++) {
            GeometricalObject object = model.getObject(i);
            if (object instanceof Line) {
                Line line = (Line) object;
                maxX = 
                 (int) Math.max(Math.max(line.getStartingPoint().getX(), line.getEndingPoint().getX()), maxX);
                maxY = 
                 (int) Math.max(Math.max(line.getStartingPoint().getY(), line.getEndingPoint().getY()), maxY);
            } else if (object instanceof Circle) {
                Circle circle = (Circle) object;
                maxX = (int) Math.max(circle.getCenterPoint().getX() + circle.getDiameter(), maxX);
                maxY = (int) Math.max(circle.getCenterPoint().getY() + circle.getDiameter(), maxY);
            } else if (object instanceof FilledCircle) {
                FilledCircle circle = (FilledCircle) object;
                maxX = (int) Math.max(circle.getCenterPoint().getX() + circle.getDiameter(), maxX);
                maxY = (int) Math.max(circle.getCenterPoint().getY() + circle.getDiameter(), maxY);
            }
        }
       
        BufferedImage image = new BufferedImage(
            maxX, maxY, BufferedImage.TYPE_3BYTE_BGR
        );
        Graphics2D g = image.createGraphics();
       
        g.setColor(Color.white);
        g.fillRect(0, 0, maxX, maxY);
        for(int i = 0; i < model.getSize(); ++i) {
            model.getObject(i).draw(g);
        }
        g.dispose();
        
        try {
            ImageIO.write(image, imgType, exportPath.toFile());
        } catch (IOException e) {
           showErrorMessage("Unable to export " + exportPath.getFileName() + " file.");
        }
	}
}
