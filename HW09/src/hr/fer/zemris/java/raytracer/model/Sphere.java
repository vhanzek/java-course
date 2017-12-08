package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.*;

/**
 * This clas represents model for sphere as a graphical object.
 * 
 * @author Vjeco
 */
public class Sphere extends GraphicalObject {
	
	/** The center of the sphere. */
	private Point3D center;
	
	/** The radius of the sphere. */
	private double radius;
	
	/** The coefficient for diffuse component for red color. */
	private double kdr;
	
	/** The coefficient for diffuse component for green color. */
	private double kdg;
	
	/** The coefficient for diffuse component for blue color. */
	private double kdb;
	
	/** The coefficient for reflective component for red color. */
	private double krr;
	
	/** The coefficient for reflective component for green color. */
	private double krg;
	
	/** The coefficient for reflective component for blue color. */
	private double krb;
	
	/** The coefficient n for reflective component. */
	private double krn;

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center the center of the sphere
	 * @param radius the radius of the sphere
	 * @param kdr the coefficient for diffuse component for red color
	 * @param kdg the coefficient for diffuse component for green color
	 * @param kdb the coefficient for diffuse component for blue color
	 * @param krr the coefficient for reflective component for red color
	 * @param krg the coefficient for reflective component for green color
	 * @param krb the coefficient for reflective component for blue color
	 * @param krn the coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, 
				  double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * @see hr.fer.zemris.java.raytracer.model.GraphicalObject#findClosestRayIntersection(hr.fer.zemris.java.raytracer.model.Ray)
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {		
		Point3D startToCenter = ray.start.sub(center);
		double scalar = ray.direction.scalarProduct(startToCenter);
		double radiusSquared = pow(radius, 2);
		
		double determinant = calculateDeterminant(scalar, startToCenter, radiusSquared);
		
		double closestDistance = calculateClosestDistance(determinant, scalar);
		if(closestDistance == -1){
			return null;
		} else {
			Point3D intersection = calculateIntersection(ray, closestDistance);
			return getRayIntersection(intersection, closestDistance);
		}
	}

	/**
	 * Method for getting ray-sphere intersection. It implements and returns new
	 * {@link RayIntersection} anonymous class.
	 *
	 * @param intersection the point where ray and sphere intersect
	 * @param closestDistance the closest distance i.e. the first point where ray passes through the sphere
	 * @return the ray-sphere intersection
	 */
	private RayIntersection getRayIntersection(Point3D intersection, double closestDistance) {
		return new RayIntersection(intersection, closestDistance, true) {
			@Override
			public Point3D getNormal() {
				return intersection.sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Calculate exact point where ray of light and sphere first intersect.
	 *
	 * @param ray the ray of light
	 * @param distance the distance between the observer and the point of intersection
	 * @return the point of the intersection
	 */
	private Point3D calculateIntersection(Ray ray, double distance) {
		Point3D distDir = ray.direction.scalarMultiply(distance);
		return ray.start.add(distDir);
	}

	/**
	 * Calculates value of the closest distance between the observer and the point of
	 * intersection.
	 *
	 * @param determinant the determinant
	 * @param scalar the scalar
	 * @return the double
	 */
	private double calculateClosestDistance(double determinant, double scalar) {
		double distance = 0.0;
		if(determinant < 0.0){
			distance = -1;
		} else if (determinant == 0.0){
			distance = -scalar;
		} else {
			double dist1 = -scalar + sqrt(determinant);
			double dist2 = -scalar - sqrt(determinant);
			distance = (dist2 >= dist1) ? dist1 : dist2;
		}
		return distance;
	}

	/**
	 * Calculates value of the determinant of the equation where <code>ray = sphere</code>.
	 * It <code>determinant < 0</code>, there are no intersections, if <code>determinant = 0</code>, 
	 * ray is tangent to given sphere, else ray has two intersection with the sphere.
	 *
	 * @param scalar the scalar product of the ray's direction vector and start-center vector
	 * @param startToCenter the ray's start to center vector
	 * @param radiusSquared the radius squared
	 * @return the value of the determinant
	 */
	private double calculateDeterminant(double scalar, Point3D startToCenter, double radiusSquared) {
		return pow(scalar, 2) - pow(startToCenter.norm(), 2) + radiusSquared;
	}

}
