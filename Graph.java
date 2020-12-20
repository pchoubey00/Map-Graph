
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Graph {
	// Store list of vertices 
	HashMap<String, Node> vertexes;
	
	// for painting the graph
	double maxLatitude;
	double minLatitude;
	double maxLongitude;
	double minLongitude;

	// construct a graph from a text file
	public Graph(String filename) {
		vertexes = new HashMap<String, Node>();
		maxLatitude = maxLongitude = -1 * Double.MAX_VALUE;
		minLatitude = minLongitude = Double.MAX_VALUE;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			String type;
			StringTokenizer tokenizer;
			while ((line = reader.readLine()) != null) {
				tokenizer = new StringTokenizer(line, "\t");
				type = tokenizer.nextToken();
				if (type.equals("i")) {
					this.addIntersection(tokenizer.nextToken(), Double.parseDouble(tokenizer.nextToken()),
							Double.parseDouble(tokenizer.nextToken()));
				} else if (type.equals("r")) {
					this.addEdge(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void addIntersection(String id, double lat, double lon) {
		
		// add Node to HashSet
		vertexes.put(id, new Node(id, lat, lon));
		// keeping track of max and min lat and lon
		if (lat > maxLatitude) {
			maxLatitude = lat;
		} else if (lat < minLatitude) {
			minLatitude = lat;
		}
		if (lon > maxLongitude) {
			maxLongitude = lon;
		} else if (lon < minLongitude) {
			minLongitude = lon;
		}
	}

	// add edge between node a and node b
	void addEdge(String id, String a, String b) {
		Node anode = vertexes.get(a);
		Node bnode = vertexes.get(b);
		
		if (anode == null) {
			System.out.println(a + " is not on the graph");
			return;
		}
		if (bnode == null) {
			System.out.println(b + " is  not  on the graph");
			return;
		}
		// create edges on both nodes and add them to each edgeList
		anode.adjlist.put(bnode, new Edge(id, anode, bnode));
		bnode.adjlist.put(anode, new Edge(id, bnode, anode));
		
	}

	// shortest path
	List<Node> shortestPath(String start, String end) {
		Node startnode = vertexes.get(start);
		Node endnode = vertexes.get(end);
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		LinkedList<Node> returnlist = new LinkedList<Node>();
		
		for (String s : vertexes.keySet()) { 
			Node n = vertexes.get(s);
			if (s.equals(start)) {
				n.save(true);
				queue.add(n);
			} else {
				n.save(false);
			}
		}
	//if sortest pat not on grap
		if (startnode == null) {
			System.out.println("Shortest path " + start + " is  not on the graph");
			return returnlist;
		}
		if (endnode == null) {
			System.out.println("Shortest path "+ end + " is  not on the graph");
			return returnlist;
		}
		
		while (!queue.isEmpty()) {
			Node current = queue.poll(); 
			current.info.discovered();
			for (Node adj : current.adjlist.keySet()) {
				if(vertexes.get(end).info.discovered) { 
					break;
				}
				Edge e = current.adjlist.get(adj);
				if (current.info.distance + e.weight < adj.info.distance) {
					adj.info.update(current, e);
					if(!adj.info.discovered) {
						if (!adj.info.undiscovered) {
							adj.info.undiscovered();
							queue.add(adj); 
							
						}
						else {
							if(queue.remove(adj)) { 
								queue.add(adj);
							}
						}
					}
				}
			}
		}
		
		if (endnode.info.distance == Double.MAX_VALUE) {
	
			System.out.println(start + " is not connected to " + end);
		} else {
			
			Node ptr = endnode;
			while (ptr != null) {
				returnlist.addFirst(ptr);
				ptr = ptr.info.previous;
			}
		}
		for (String s : vertexes.keySet()) {
			
			vertexes.get(s).delete();
		}
		return returnlist;
	}
   
}
