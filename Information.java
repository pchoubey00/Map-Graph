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
public class Information {
	// a collection of information that the node must store when using Dijkstra's Algorithm
	
		boolean undiscovered;
		boolean discovered;
		Node previous;
		double distance;
		
		// the starting vertex must have a distance of 0 
		public Information(boolean start) {
			previous = null;
			discovered  = false;
			if (start) {
				distance = 0;
				undiscovered = true;
			} else {
				distance = Integer.MAX_VALUE;
				undiscovered = false;
			}
		}

		public void update(Node newprev, Edge workingEdge) {
			this.previous = newprev;
			this.distance = newprev.info.distance + workingEdge.weight;
		}
		public void undiscovered() {
			undiscovered = true;
		}
		public void discovered() {
			discovered = true;
		}
		public String toString() {
			String info;
			if(previous!=null) {
				info = previous.toString();
			}else {
				info = "noprev";
			}
			return  info + " " + distance;
		}
	}