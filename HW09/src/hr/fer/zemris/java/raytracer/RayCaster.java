package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import static java.lang.Math.*;

/**
 * The class is used for drawing certain 3D scene with objects in it.
 * In order to achieve that, we are using ray-tracing technique.
 * 
 * @see https://en.wikipedia.org/wiki/Ray_tracing_(graphics)
 * 
 * @author Vjeco
 */
public class RayCaster {
	
	/** The epsilon. */
	private final static double EPS = 1E-9;
	
	/**
	 * The main method, program starts here. Using {@link RayTracerViewer} we are able
	 * to produce the scene and the objects that define the scene.
	 *
	 * @param args the command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
							 new Point3D(10, 0, 0), 
							 new Point3D(0, 0, 0),
							 new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Gets implementation of the {@link IRayTracerProducer} that specifies objects which are capable 
	 * to create scene snapshots by using ray-tracing technique.
	 *
	 * @return the new ray tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height,
								long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = calculateZAxis(eye, view);
				Point3D yAxis = calculateYAxis(zAxis, viewUp);
				Point3D xAxis = calculateXAxis(zAxis, yAxis);
				
				Point3D screenCorner = calculateScreenCorner(view, xAxis, yAxis, horizontal, vertical);
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = calculateScreenPoint(screenCorner, xAxis, yAxis, horizontal, vertical, x, y, width, height);
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb, eye);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Method for calculating exact point on the screen with given parameters.
			 * 
			 * @param screenCorner the point of the screen left upper corner
			 * @param xAxis the <code>i</code> vector of the screen plane
			 * @param yAxis the <code>j</code> vector of the screen plane
			 * @param horizontal distance between the center and the side edge of the screen
			 * @param vertical distance between the center and the upper edge of the screen
			 * @param x current x-coordinate
			 * @param y current y-coordinate
			 * @param width width of the screen
			 * @param height height of the screen
			 * @return point on the screen
			 */
			private Point3D calculateScreenPoint(Point3D screenCorner, Point3D xAxis, Point3D yAxis, double horizontal,
												 double vertical, int x, int y, int width, int height) {
				return screenCorner.add(xAxis.scalarMultiply((x*horizontal)/(width-1))
								   .modifySub(yAxis.scalarMultiply((y*vertical)/(height-1))));
			}

			/**
			 * Method for calculating point of the screen upper left corner.
			 * 
			 * @param view vector
			 * @param xAxis the <code>i</code> vector of the screen plane
			 * @param yAxis the <code>j</code> vector of the screen plane
			 * @param horizontal distance between the center and the side edge of the screen
			 * @param vertical distance between the center and the upper edge of the screen
			 * @return the point of the screen upper left corner
			 */
			private Point3D calculateScreenCorner(Point3D view, Point3D xAxis, Point3D yAxis, double horizontal, double vertical) {
				return view.sub(xAxis.scalarMultiply(horizontal/2))
						   .modifyAdd(yAxis.scalarMultiply(vertical/2));
			}

			/**
			 * Method for calculating the <code>i</code> vector of the screen plane.
			 * 
			 * @param zAxis the <code>k</code> vector of the screen plane
			 * @param yAxis the <code>j</code> vector of the screen plane
			 * @return the <code>i</code> vector of the screen plane
			 */
			private Point3D calculateXAxis(Point3D zAxis, Point3D yAxis) {
				return zAxis.vectorProduct(yAxis).normalize();
			}

			/**
			 * Method for calculating the <code>j</code> vector of the screen plane.
			 * 
			 * @param zAxis <code>k</code> vector of the screen plane
			 * @param viewUp vector
			 * @return the <code>j</code> vector of the screen plane
			 */
			private Point3D calculateYAxis(Point3D zAxis, Point3D viewUp) {
				viewUp.modifyNormalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)));
				return yAxis.normalize();
			}

			/**
			 * Method for calculating the <code>k</code> vector of the screen plane.
			 * 
			 * @param eye the point of the observer
			 * @param view vector
			 * @return the <code>k</code> vector of the screen plane
			 */
			private Point3D calculateZAxis(Point3D eye, Point3D view) {
				return view.sub(eye).normalize();
			}		
		};
	}

	/**
	 * Method for calculating colors of the every pixel in the frame.
	 * If there is no ray-object intersection, pixel's every color component 
	 * intensity is set to 0. However, if intersection exists, the closest
	 * intersection of the given ray and any object in the scene is taken.
	 *
	 * @param scene the scene
	 * @param ray the ray between the observer and the point of the intersection
	 * @param rgb the array with 3 color components - <b>r</b>ed, <b>g</b>reen, <b>b</b>lue
	 * @param eye the point of the observer
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		List<GraphicalObject> objects = scene.getObjects();
		
		RayIntersection intersection = getClosestIntersection(scene, ray);
		if(intersection == null){
			setRgb((short) 0, rgb);
		} else {
			determineColorFor(scene, objects, intersection, rgb, eye);
		}
	}

	/**
	 * Determines color of the current processed pixel based on three components described in
	 * Phongs model. Ambient, diffuse and reflective component, each of them affects the final
	 * intensity of the pixel's color. 
	 *
	 * @param scene the scene
	 * @param objects the graphical objects in the scene
	 * @param intersection the intersection on the sphere
	 * @param rgb the array with 3 color components
	 * @param eye the point of the observer
	 * 
	 * @see https://en.wikipedia.org/wiki/Phong_reflection_model
	 */
	private static void determineColorFor(Scene scene, List<GraphicalObject> objects, RayIntersection intersection, short[] rgb, Point3D eye) {
		setRgb((short) 15, rgb); 
		
		List<LightSource> lightSources = scene.getLights();
		for(LightSource ls : lightSources){
			Ray lsRay = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
            RayIntersection closestIntersection = getClosestIntersection(scene, lsRay);
            
            if (closestIntersection != null && 
            	ls.getPoint().sub(closestIntersection.getPoint()).norm() + EPS < ls.getPoint().sub(intersection.getPoint()).norm()) {
                continue;
			} else {
				addDiffuseAndReflectiveComp(rgb, ls, intersection, eye);
			}
		}
	}

	/**
	 * Helper method for calculating diffuse and reflective component. Those
	 * two component also affect the final pixel's intensity. 
	 *
	 * @param rgb the array with 3 color components
	 * @param ls the light source
	 * @param intersection the intersection between the ray and the sphere
	 * @param eye the point of the observer
	 */
	private static void addDiffuseAndReflectiveComp(short[] rgb, LightSource ls, RayIntersection intersection, Point3D eye) {
		Point3D toLight = ls.getPoint().sub(intersection.getPoint()).normalize();
		Point3D normal = intersection.getNormal();
		double cosinusDiffuse = toLight.scalarProduct(normal);
		
		Point3D toEye = eye.sub(intersection.getPoint()).normalize();
		Point3D reflected = normal.scalarMultiply(cosinusDiffuse * 2).modifySub(toLight).normalize();
		double cosinusReflected = reflected.scalarProduct(toEye);
		
		 if(cosinusDiffuse > 0) {
             rgb[0] += (short) (ls.getR() * intersection.getKdr() * cosinusDiffuse);
             rgb[1] += (short) (ls.getG() * intersection.getKdg() * cosinusDiffuse);
             rgb[2] += (short) (ls.getB() * intersection.getKdb() * cosinusDiffuse);
         }
		
		 double krn = intersection.getKrn();
		 if (cosinusReflected > 0) {
             rgb[0] += (short) (ls.getR() * intersection.getKrr()
                     * pow(cosinusReflected, krn));
             rgb[1] += (short) (ls.getG() * intersection.getKrg()
                     * pow(cosinusReflected, krn));
             rgb[2] += (short) (ls.getB() * intersection.getKrb()
                     * pow(cosinusReflected, krn));
         }		
	}
	
	/**
	 * Gets the closest intersection of the given ray and the any objects
	 * in the scene.
	 *
	 * @param scene the scene
	 * @param ray the ray
	 * @return the closest intersection 
	 */
	private static RayIntersection getClosestIntersection(Scene scene, Ray ray) {
		double distance = Double.MAX_VALUE;
		
        RayIntersection intersection = null;
        for (GraphicalObject obj : scene.getObjects()) {
            RayIntersection tempInt = obj.findClosestRayIntersection(ray);
            if (tempInt == null) continue;
            if (tempInt.getDistance() < distance) {
            	distance = tempInt.getDistance();
                intersection = tempInt;
            }
        }
        return intersection;
	}

	/**
	 * Sets every slot of the array with 3 color components to 
	 * given parameter {@code n}.
	 *
	 * @param n the new color intensity
	 * @param rgb the array with 3 color components
	 */
	private static void setRgb(short n, short[] rgb) {
		for(int i = 0; i < rgb.length; i++){
			rgb[i] = n;
		}
	}
}