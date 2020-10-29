import processing.core.PApplet;

public class CheckOutputFiles {
	public static void main(String[] args) {
		System.out.println("Vérification que la sauvegarde est correcte : on recharge et on doit trouver les mêmes résultats\"");
		if(args.length<1) {
			System.out.println("Error: one argument required: input file storing 2D integer points");
			System.exit(0);
		}
		
		String inputFile=args[0]; // input file name
		GridPoint_2[] points=PointCloud_IO.read(inputFile); // Reload input points from text file
		//int[] drawingBounds=PointCloud_IO.getBoundingBox(points); // get the bounding box [0..xmax]x[0..ymax]

		System.out.println("\n--- Starting computations ---");
		// initialize the main class for computing optimal polygons
		OptimalPolygon op=new OptimalPolygon(points);
		
		float areaCH=op.computeAreaConvexHull(); // compute the area of the convex hull (this function MUST BE IMPLEMENTED)
		System.out.println("Area of the Convex Hull: "+areaCH);
		int n=points.length;
		int[] minimal= PointCloud_IO.read_result(inputFile.replace("instance", "min"),n);
		int[] maximal=PointCloud_IO.read_result(inputFile.replace("instance", "max"),n);
				
		boolean isValidMin=op.checkValidity(minimal); // check whether the polygon is valid (this function MUST BE IMPLEMENTED)
		if(isValidMin==false)
			System.out.println("The minimal polygon is not valid (or not defined)"); //Should not happen

		boolean isValidMax=op.checkValidity(maximal); // check whether the polygon is valid 
		if(isValidMax==false)
			System.out.println("The maximal polygon is not valid (or not defined)"); //Should not happen

		double areaMin=op.computeArea(minimal); // compute the area of the polygon (this function MUST BE IMPLEMENTED)
		double areaMax=op.computeArea(maximal); // compute the area of the polygon

		System.out.println("Best area of the minimal polygon: "+areaMin);
		System.out.println("Best area of the maximal polygon: "+areaMax);
		System.out.println("Final score : "+areaMax/areaMin);
		
}
}