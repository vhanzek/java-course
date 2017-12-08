package hr.fer.zemris.java.gui;

/**
 * The class representing a constraint in {@link CalcLayout}.
 * It has two read-only properties {@linkplain #row} and
 * {@linkplain RCPosition#column}, each representing position
 * on the layout.
 * 
 * @author Vjeco
 */
public class RCPosition{
	
	/** The row. */
	private int row;
	
	/** The column. */
	private int column;
	
	/**
	 * Instantiates a new RC position with position
	 * set to given row and column.
	 *
	 * @param row the row
	 * @param column the column
	 */
	public RCPosition(int row, int column) {
		checkParameters(row, column);
		this.row = row;
		this.column = column;
	}

	/**
	 * Helper method for checking if given row or column are negative.
	 *
	 * @param row the row
	 * @param column the column
	 */
	private void checkParameters(int row, int column) {
		if(row < 0){
			throw new IllegalArgumentException(
				"Number of rows must be positive."
			); 
		}
		if(column < 0){
			throw new IllegalArgumentException(
				"Number of columns must be positive."
			);
		}
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
