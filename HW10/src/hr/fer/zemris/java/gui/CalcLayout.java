package hr.fer.zemris.java.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>The class used for automatic deployment of components in a frame.</p>
 * 
 * <p>This layout contains fixed {@linkplain #NUMBER_OF_ROWS}, and fixed 
 * {@linkplain #NUMBER_OF_COLUMNS}. All added components are the same size, 
 * except for the component at the position 1,1 (row, column). That component
 * has width of 5 "normal" components concatenated together. Also, this layout 
 * supports adding components with constraints. For example, if we want to add
 * component at the position 5,7, component will be added at the down right 
 * corner of the frame. Constraints can be instances of class {@link RCPosition}
 * or {@link String}. Any other class used as constraint will throw an exception.</p>
 * 
 * @author Vjeco
 */
public class CalcLayout implements LayoutManager2 {
	
	/** Number of rows. */
	private final static int NUMBER_OF_ROWS = 5;
	
	/** Number of columns. */
	private final static int NUMBER_OF_COLUMNS = 7;
	
	/** First position, */
	private final static RCPosition FIRST_POSITION = new RCPosition(1, 1);
	
	/** The gap between components, horizontal and vertical. */
	private int gap;
	
	/** The map with stored component and theirs positions. */
	private Map<Component, RCPosition> positions;

	/**
	 * Instantiates a new calculator layout.
	 * Variable {@linkplain #gap} is set to given parameter.
	 *
	 * @param gap the gap between components
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		this.positions = new HashMap<>();
	}
	
	/**
	 * Instantiates a new calculator layout.
	 * Variable {@linkplain #gap} is set to 0.
	 */
	public CalcLayout(){
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateDim(parent, Component::getPreferredSize);	
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateDim(parent, Component::getMinimumSize);	
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		
		int totalWidthGap = (NUMBER_OF_COLUMNS - 1) * gap;
		int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
		int compWidth = (widthWOInsets - totalWidthGap)/NUMBER_OF_COLUMNS;
		
		int totalHeightGap = (NUMBER_OF_ROWS - 1) * gap;
		int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
		int compHeight = (heightWOInsets - totalHeightGap)/NUMBER_OF_ROWS;
		
		int n = parent.getComponentCount();
		for(int i = 0; i < n; i++){
			Component comp = parent.getComponent(i);
			RCPosition pos = positions.get(comp);
			
			int column = pos.getColumn();
			int row = pos.getRow();
			
			int x = insets.left + (column-1)*(compWidth + gap);
			int y = insets.top + (row-1)*(compHeight + gap);
			
			int width = pos.equals(FIRST_POSITION) ? compWidth*5 + gap*4 : compWidth;
			
			comp.setBounds(x, y, width, compHeight);
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition pos = null;
		if(constraints instanceof String){
			pos = getPosition((String) constraints);
		} else if(constraints instanceof RCPosition){
			pos = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException(
				"Illegal constraints."
			);
		}
		checkIllegalPosition(pos);
		checkIfExists(comp, pos);
		
		positions.put(comp, pos);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	/**
	 * Helper method for checking if component is added to container
	 * at the legal position.
	 *
	 * @param pos the position of the component
	 */
	private void checkIllegalPosition(RCPosition pos) {
		if(pos.getRow() == 1 && (pos.getColumn() > 1 && pos.getColumn() <=5)){
			throw new IllegalArgumentException(
				"Access denied."
			);
		}
		
		if(pos.getRow() > NUMBER_OF_ROWS || pos.getColumn() > NUMBER_OF_COLUMNS){
			throw new IllegalArgumentException(
				"Position is out of bounds."
			);
		}
	}

	/**
	 * Helper method for getting {@link RCPosition} instance if constraint is
	 * given with {@link String} instance.
	 *
	 * @param name the position given by string
	 * @return the position as {@link RCPosition} instance
	 */
	private RCPosition getPosition(String name) {
		try {
			String[] elements = name.split(",");
			RCPosition pos = new RCPosition(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
			return pos;
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException(
				"Illegal constraints."
			);
		}
	}

	/**
	 * Helper method for checking if component at the specified position
	 * already exists or if the specified component is already added to
	 * the panel.
	 *
	 * @param comp the component
	 * @param pos the specified position of the component
	 */
	private void checkIfExists(Component comp, RCPosition pos) {
		RCPosition existing = positions.get(comp);
		if(existing != null){
			throw new IllegalArgumentException(
				"Component is already added to container."
			);
		}
		
		if(positions.containsValue(pos)){
			throw new IllegalArgumentException(
				"Component with given constraints already exists."
			);
		}
	}
	
	/**
	 * The strategy for retrieving certain size of the specified
	 * component.
	 * 
	 * @author Vjeco
	 */
	private interface SizeGetter {
		
		/**
		 * Method for getting the specified size of the container.
		 *
		 * @param comp the component
		 * @return the size of the component
		 */
		Dimension getSize(Component comp);
	}
	
	/**
	 * Method for calculating specified dimension of the container.
	 * This is done by finding the maximum size of all components added
	 * to this container.
	 *
	 * @param parent the parent component
	 * @param getter the strategy for getting wanted size of the component
	 * @return the specified dimension of the container i.e. parent component
	 */
	private Dimension calculateDim(Container parent, SizeGetter getter){
		Dimension dim = new Dimension(0, 0);
		
		int n = parent.getComponentCount();
		for(int i = 0; i < n; i++){
			Component comp = parent.getComponent(i);
			Dimension cdim = getter.getSize(comp);
			if(cdim != null){
				if(!positions.get(comp).equals(FIRST_POSITION)){
					dim.width = Math.max(dim.width, cdim.width);
				}
				dim.height = Math.max(dim.height, cdim.height);
			}
		}
		dim.width = NUMBER_OF_COLUMNS*dim.width + gap*(NUMBER_OF_COLUMNS - 1);
		dim.height = NUMBER_OF_ROWS*dim.height + gap*(NUMBER_OF_ROWS - 1);
		
		Insets parentInsets = parent.getInsets();
		dim.width += parentInsets.left + parentInsets.right;
		dim.height += parentInsets.top + parentInsets.bottom;
		
		return dim;
	}

}
