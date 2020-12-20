/*
Author: Prinaya Choubey
UR ID: Pchoubey
LAB: TR 4:50 – 6:05  
Email: pchoubey@u.rochester.edu

Author: Sarah Zaman 
UR  ID: szaman 
LAB: TR 6:15  - 7:30 
Email:  szaman@u.rochester.edu 
*/
public class Edge {
	String id;
	double weight;

	// constructor
	public Edge(String identity, Node o, Node d) {
		this.id = identity;
		this.weight = distance(o.latitude, o.longitude, d.latitude, d.longitude);
	}

//  the following code for the Haversin Functon is taken from: https://rosettacode.org/wiki/Haversine_formula
		private static final int R = 3959; // Approx Earth radius in MILEs

		  public static double distance(double lat1, double lon1, double lat2, double lon2) {
		        double dLat = Math.toRadians(lat2 - lat1);
		        double dLon = Math.toRadians(lon2 - lon1);
		        lat1 = Math.toRadians(lat1);
		        lat2 = Math.toRadians(lat2);
		 
		        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
		        double c = 2 * Math.asin(Math.sqrt(a));
		        return R * c;
		    }

}
