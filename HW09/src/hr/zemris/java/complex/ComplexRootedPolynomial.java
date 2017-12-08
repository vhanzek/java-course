package hr.zemris.java.complex;

/**
 * <p>This class represents a model for complex rooted polynomials. This class is similar to
 * class {@link ComplexPolynomial} but its record is a little bit different. Namely, this
 * polynomial is shown as product of polynomials with form <code>(z-c)</code> where 
 * <code>z</code> is unknown and <code>c</code> is one root of the polynomial.</p>
 * 
 * <p>For example, complex rooted polynomial is <code>(z-1)(z+1)(z-i)(z+i)</code>, and it
 * can be converted to complex polynomial as <code>z^4-1</code>. For this type of converting,
 * method {@linkplain #toComplexPolynom()} is provided.</p>
 * 
 * @author Vjeco
 */
public class ComplexRootedPolynomial {
	
	/** The roots of the polynomial. */
	private Complex[] roots;
	
	/**
	 * Instantiates a new complex rooted polynomial.
	 *
	 * @param roots the roots of the polynomial
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		if(roots.length == 0){
			throw new IllegalArgumentException("Number of roots must be greater than 0.");
		}
		this.roots = new Complex[roots.length];
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point {@code z}.
	 *
	 * @param z the z given complex point
	 * @return the result complex number
	 */
	public Complex apply(Complex z) {
		ComplexPolynomial cp = this.toComplexPolynom();
		return cp.apply(z);
	}
	
	/**
	 * Method for converting this representation to {@link ComplexPolynomial} type.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] polynomials = new ComplexPolynomial[roots.length];
		for(int i = 0; i < roots.length; i++){
			polynomials[i] = new ComplexPolynomial(roots[i].negate(), Complex.ONE);
		}
		
		ComplexPolynomial result = polynomials[0];
		for(int i = 1; i < polynomials.length; i++){
			result = result.multiply(polynomials[i]);
		}
		
		return result;
	}
	
	/**
	 * Finds index of the closest root for given complex number {@code z} 
	 * that is within treshold. If such root does not exist, -1 it returned.
	 *
	 * @param z the z given complex number
	 * @param treshold the maximum root distance
	 * @return the index of the closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		for(int i = 0; i < roots.length; i++){
			if(roots[i].sub(z).module() <= treshold){			
				return i + 1;
			}
		}
		
		return -1;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Complex root : roots){
			if(root.equals(Complex.ZERO)){
				sb.append("z");
			} else if(root.getImaginary() == 0){
				root = root.negate();
				sb.append("(z" + ((root.getReal() > 0.0) ? "+" : "") + root + ")");
			} else if (root.getReal() == 0){
				sb.append("(z" + root.negate() + ")");
			} else {
				sb.append("(z-(" + root + "))");
			}
		}
		return sb.toString();
	}
}
