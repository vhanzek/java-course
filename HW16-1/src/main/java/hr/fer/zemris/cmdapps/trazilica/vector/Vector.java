package hr.fer.zemris.cmdapps.trazilica.vector;

/**
 * The class {@code Vector} representing vector implementation.
 * It offers method for scalar product {@link #scalarProduct(Vector)}
 * and method for calculating vector's norm {@link #norm()}.
 * 
 * @author Vjeran
 */
public class Vector {

	/** The vector components. */
	private double[] components;
	
	/**
	 * Instantiates a new vector.
	 *
	 * @param components the vector components
	 */
	public Vector(double[] components) {
		this.components = new double[components.length];
		this.components = components;
	}
	
	/**
	 * Gets the vector components.
	 *
	 * @return the vector components
	 */
	public double[] getComponents() {
		return components;
	}
	
	/**
	 * Calculates norm of the vector.
	 * 
	 * @return norm
	 */
	public double norm() {
		double sum = 0;
		for(double comp : components){
			sum += Math.pow(comp, 2);
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * Calculates scalar product this * p.
	 * 
	 * @param p other vector
	 * @return scalar product
	 */
	public double scalarProduct(Vector p) {
		double[] pComp = p.components;
		
		double result = 0;
		for(int i = 0; i < components.length; i++){
			result += components[i]*pComp[i];
		}
		
		return result;
	}
}
