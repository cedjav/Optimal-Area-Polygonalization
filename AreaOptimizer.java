import processing.core.PApplet;

	/**
	 * Main program that takes as input a 2D point cloud (with even integer coordinates)
	 * and computes a simple polygons with minimal (and maximal) area.
	 * 
	 * @author Luca Castelli Aleardi (Ecole Polytechnique, 2020)
	 *
	 */
public class AreaOptimizer {

	public static void main(String[] args) {
		System.out.println("Tools for the \"INF562 final evaluation\"");
		if(args.length<1) {
			System.out.println("Error: one argument required: input file storing 2D integer points");
			System.exit(0);
		}
		
		String inputFile=args[0]; // input file name
		GridPoint_2[] points=PointCloud_IO.read(inputFile); // load input points from text file
		int[] drawingBounds=PointCloud_IO.getBoundingBox(points); // get the bounding box [0..xmax]x[0..ymax]
		
		// set the input parameters for 2D rendering
		PointCloudViewer.sizeX=1200; // setting canvas width (number of pixels)
		PointCloudViewer.sizeY=800; // setting canvas height (pixels)
		PointCloudViewer.inputPoints=points; // set the input points for rendering in the PApplet
		PointCloudViewer.drawingWidth=Math.max(drawingBounds[0], drawingBounds[1]); // setting the width of the drawing area (square area)
		PointCloudViewer.drawingHeight=Math.max(drawingBounds[0], drawingBounds[1]);  // setting the height of the drawing area (square area)
		
		System.out.println("\n--- Starting computations ---");
		// initialize the main class for computing optimal polygons
		OptimalPolygon op=new OptimalPolygon(points);
		
		float areaCH=op.computeAreaConvexHull(); // compute the area of the convex hull (this function MUST BE IMPLEMENTED)
		System.out.println("Area of the Convex Hull: "+areaCH);
		int MAX_ITER=3;

		// Minimal polygon
		int[] minimal= {0,1,2};
		double areaMinMin=1E100;
		double time_Best_Min=1E100;
		double startTime=System.nanoTime(), endTime;

		for (int i=0;i<MAX_ITER;i++) {
			double startTime_this_it=System.nanoTime();
			minimal=op.computeMinimalAreaPolygon(); // compute the polygon of minimal area (this function MUST BE IMPLEMENTED)
			boolean isValidMin=op.checkValidity(minimal); // check whether the polygon is valid (this function MUST BE IMPLEMENTED)
			if(isValidMin==false)
				System.out.println("The minimal polygon is not valid (or not defined)"); //Should not happen
			
			double areaMin=op.computeArea(minimal); // compute the area of the polygon (this function MUST BE IMPLEMENTED)
			if (areaMin<areaMinMin && minimal.length==points.length) {
				areaMinMin=areaMin;
				time_Best_Min=System.nanoTime()-startTime_this_it;
				System.out.println("Better area of the minimal polygon: "+areaMin);
				//for (int j=0; j<minimal.length; j++) 
				//	System.out.print(minimal[j]+" ");
				//System.out.println("");
				PointCloud_IO.write(minimal, inputFile.replace("instance", "min")); // the output file has the same prefix of the corresponding instance
			}
		}
		endTime=System.nanoTime();

		System.out.println("-------Rappel----------"); 
		System.out.println("Nom de l'instance : "+inputFile); 
		System.out.println("Nb de points : "+points.length); 
		System.out.println("Nb d(iération du Min : "+MAX_ITER); 
		double duration=(double)(endTime-startTime)/1000000000.;
		System.out.println("Time per iteration (including long lasting failures): "+duration/MAX_ITER); 
		System.out.println("Time to compute the best iteration: "+time_Best_Min/1000000000);
			
		// Maximal polygon
		// Just do it once : there is no random

		int[] maximal=op.computeMaximalAreaPolygon(); // compute the polygon of maximal area 
		boolean isValidMax=op.checkValidity(maximal); // check whether the polygon is valid 
		if(isValidMax==false)
			System.out.println("The maximal polygon is not valid (or not defined)"); //Should not happen
			
		double areaMax=op.computeArea(maximal); // compute the area of the polygon
		PointCloud_IO.write(maximal, inputFile.replace("instance", "max")); // the output file has the same prefix of the corresponding instance

		System.out.println("Best area of the minimal polygon: "+areaMinMin);
		System.out.println("Best area of the maximal polygon: "+areaMax);
		System.out.println("Final score : "+areaMax/areaMinMin);
		System.out.println("\n --- end of computations ---");

		// uncomment the line below to show in a Processing frame the 2D point cloud together with a polygon (minimal or maximal)
		//PointCloudViewer.optimalPolygon=op.hullPolygon; // set the polygon to be rendered
		PointCloudViewer.optimalPolygon=minimal;
		if(PointCloudViewer.optimalPolygon==null)
			System.out.println("Warning: the polygon is not defined");
		PApplet.main(new String[] { "PointCloudViewer" }); // launch the Processing viewer*/

	}

}
