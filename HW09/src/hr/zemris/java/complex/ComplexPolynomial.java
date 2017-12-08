package hr.zemris.java.complex;

/**
 * <p>This class represents a model for complex polynomials. Form of this polynomial is
 * <code>az^n + bz^(n-1) + ... + xz + y</code> where a,b,...,x,y are complex factors of 
 * the polynomial. They are stored in an array {@linkplain #factors}.</p>
 * 
 * <p>Also, this class provides a few methods for performing calculations with
 * complex polynomials.</p>
 * 
 * @author Vjeco
 */
public class ComplexPolynomial {
	
	/** The factors of the polynomial. */
	private Complex[] factors;
	
	/**
	 * Instantiates a new complex polynomial.
	 *
	 * @param factors the factors of the polynomial
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new Complex[factors.length];
		this.factors = factors;
	}
	
	/**
	 * Returns order of this polynomial; 
	 * For example:(7+2i)z^3+2z^2+5z+1 returns 3.
	 *
	 * @return the order of the polynomial
	 */
	public short order() {
		short order = 0;
		for(int i = 0; i < factors.length; i++){
			if(factors[i] != Complex.ZERO){
				order = (short) i;
			}
		}
		return order;
	}
	
	/**
	 * Computes a new polynomial by multiplying this with given polynomial.
	 *
	 * @param p the p second operand
	 * @return the result complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] resFactors = new Complex[this.order() + p.order() + 1];

		for(int i = 0, thisOrder = this.order(); i <= thisOrder; i++){
			for(int j = 0, pOrder = p.order(); j <= pOrder ; j++){
				resFactors[i+j] = (resFactors[i+j] == null) ? 
								   this.factors[i].multiply(p.factors[j]) : 
								   resFactors[i+j].add((this.factors[i].multiply(p.factors[j])));	
			}
		}
		return new ComplexPolynomial(resFactors);
		
	}
	
	/**
	 * Computes first derivative of this polynomial;
	 * For example, (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] resFactors = new Complex[this.order()];
		for(int i = 1, len = factors.length; i < len; i++){
			resFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(resFactors);
	}
	
	/**
	 * Computes polynomial value at given point {@code z}.
	 *
	 * @param z the z given point
	 * @return the result complex number
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();
		for(int i = factors.length - 1; i >= 0; i--){
			result = result.add(factors[i].multiply(z.power(i)));
		}
		return result;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = factors.length - 1; i >= 0; i--){
			if(!factors[i].equals(Complex.ZERO)){
				String factor = factors[i].toString();
				if(factors[i].getImaginary() == 0.0){
					if(factors[i].getReal() > 0.0 && i != factors.length - 1){
						sb.append("+");
					}
				} else {
					if(factors[i].getReal() != 0.0){
						factor = "(" + factors[i] + ")";
						if(i != factors.length - 1) sb.append("+");
					}
				}
				
				if(i == 0){
					sb.append(factor);					
				} else {
					if(factor.equals("1")){
						factor = "";
					} else if (factor.equals("-1")){
						factor = "-";
					}
					sb.append(factor + ((i == 1) ? "z" : "z^" + i));
				} 
			}
		}
		
		return sb.toString();
	}
}
