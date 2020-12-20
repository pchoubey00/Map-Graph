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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Node implements Comparable<Node> {
	// stores the edges that this node is connected to (undirected graph)
	HashMap<Node, Edge> adjlist;
	
	
	String ID;
	double latitude;
	double longitude;
	Information info;



	// constructor
	public Node(String id, double lat, double lon) {
		this.ID = id;
		this.latitude = lat;
		this.longitude = lon;
		this.info = null;
		adjlist = new HashMap<Node, Edge>();
	}
	
	//creates new instance of info
	public void save(boolean start) {
		this.info = new Information(start);
	}
	
	//removes the instance of info 
	public void delete() {
		this.info = null;
	}
	
	static public double pathLength(List<Node> list) {
		double returnnum = 0;
		for(int i = 0; i<list.size()-1; i++) {
			try {
				returnnum+=list.get(i).adjlist.get(list.get(i+1)).weight;
			}catch(NullPointerException e){
				System.out.println("pathLength: shortest path messed up. Nodes are disconnected. :(");
			}
		}
		return returnnum;
	}
	
	public String toString() {
		return ID;
	}

	@Override
	public int compareTo(Node n) {
		
		if (this.info == null || n.info == null||this.info.distance == n.info.distance) {
			return 0;
		}
		if (this.info.distance < n.info.distance) {
			return -1;
		}else {
			return 1;
		}
	}
	
	
	
}
