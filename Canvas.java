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
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Canvas extends JPanel {
	double l; //length and width of panel 
	double w;
	
	Graph graphrep; //graph that will be represented as a map
	
	//variables used to Draw Shortest Path
	Node origin;
	int ox, oy, dx, dy;
	Node dest;
	HashMap<Node, Node> sp = new HashMap<Node, Node>(); 
	List<Node> way;
	
	//to show pathh
	Boolean display;

	
	//Constructor for graph
	public Canvas(Graph g) {
		this.graphrep = g;
		display = false;
		repaint();
	}
	
	//Constructor   for  rendering  sortest path
	public Canvas(Graph g, List<Node> l) {
		this.graphrep = g;
		display = true;
		way = l;
		
		//this takes the nodes and adds them to a queue
		Queue<Node> nodes = new LinkedList<Node>();
		nodes.addAll(l);
		while(nodes.size() != 1) {
			Node origin = nodes.poll();
			Node dest = nodes.peek();
			sp.put(origin, dest);
		}
		
		origin = l.get(0);
		dest = l.get(l.size()-1);
		
		repaint();
	}

	//generates x and y coordinates based on the given latitude and longitude
	public double xCoordinate(Node node) {
		double pad = 0.0;
		if(getWidth() > getHeight()) {
			pad = (getWidth() - getHeight())/2;
		}
		
		return ((node.longitude-graphrep.minLongitude)/(graphrep.maxLongitude - graphrep.minLongitude)*w) + pad;
	}

	public double yCoordinate(Node n) {
		double pad = 0.0;
		if(getHeight() > getWidth()) {
			pad = (getHeight() - getWidth())/2;
		}
		return (l - (n.latitude-graphrep.minLatitude)/(graphrep.maxLatitude - graphrep.minLatitude)*l)+ pad;
	}

	//renders the map
	@Override
	public void paintComponent(Graphics g) {
		l = getHeight();
		w = getWidth();
		
		Graphics2D g2 = (Graphics2D) g;
		if (getWidth() > getHeight()) {
			l = w = getHeight();
		} else {
			l = w = getWidth();
		}
		

		for (String s : graphrep.vertexes.keySet()) { 
			Node node = graphrep.vertexes.get(s);
			int pointx1 = (int) xCoordinate(node);
			int pointy1 = (int) yCoordinate(node);
			//draw the edges
			for (Node destination : node.adjlist.keySet()) {
					int pointx2 = (int) xCoordinate(destination);
					int pointy2 = (int) yCoordinate(destination);
					if(node == origin) { ox = pointx1; oy = pointy1;}
					if(destination == this.dest) {dx = pointx2; dy = pointy2;}
			
					//for shortest path
					if(display && sp.containsKey(node) && sp.get(node).equals(destination)) {
						g2.setColor(Color.PINK);
						g2.setStroke(new BasicStroke(5));
						g2.drawLine(pointx1, pointy1, pointx2, pointy2);
					//draw map	
					} else {
						g2.setColor(Color.BLACK);
						g2.setStroke(new BasicStroke(1));
						g2.drawLine(pointx1, pointy1, pointx2, pointy2);
					}
				}
		}
		
		
	
	}
}
