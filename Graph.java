package welcome;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import welcome.Graph.Edge;
public class Graph {
	
	private List<Vertex> vertexList;
	private Map<String,List<Edge>> ve_Map;
	
	public Graph(List<Vertex> vertexList, Map<String,List<Edge>> ve_Map) {
		super();
		this.vertexList = vertexList;
		this.ve_Map = ve_Map;
	}
	
	public List<Vertex> getVertexList() {
		return vertexList;
	}
	
	public void setVertexList(List<Vertex> vertexList) {
		this.vertexList = vertexList;
	}
	

	public Map<String,List<Edge>> get_ve_Map() {
		return ve_Map;
	}
	
	public void set_ve_Map(Map<String,List<Edge>> ve_Map) {
		this.ve_Map = ve_Map;
	}
	
	void updateChildren(String word) {
		Vertex v = null;
		
		if (ve_Map.get(word)==null||ve_Map.get(word).size()==0){
			return;
		}
		
			
	//		System.out.print("v:"+v.getName()+" "+v.getAdj()+"    ");
	
			List<Vertex> childrenList = new LinkedList<Graph.Vertex>();
			for(Edge edge : ve_Map.get(word)) {
				for(Vertex ver : vertexList) {
					if(ver.isSeekVertex(word)) {
		
						v = ver;
						break;
					}
				}
				Vertex child = edge.getEnd();
				if(!child.isVisited()) {
					child.setVisited(true);
	//				System.out.println(v.getName()+" "+v.getAdj()+" "+edge.getWeight()+ " ");
					child.setAdj(v.getAdj() + edge.getWeight());
					for(Vertex ver1 : vertexList) {
						if(ver1.isSeekVertex(child.getName())) {
							ver1.setAdj(v.getAdj() + edge.getWeight());
							ver1.setParent(v);
							break;
						}
					}
	//				System.out.println(child.getName()+" "+child.getAdj());
					child.setParent(v);
					
	
					childrenList.add(child);
				}
			
				int nowDist = v.getAdj()+edge.getWeight();
				
				if(nowDist < child.getAdj()) {
					for(Vertex ver1 : vertexList) {
						if(ver1.isSeekVertex(child.getName())) {
							ver1.setAdj(v.getAdj() + edge.getWeight());
							ver1.setParent(v);
							break;
						}
					}
					child.setAdj(nowDist);
					child.setParent(v);

				}
				
			}
		
	
			for(Vertex v1 : childrenList) {
				updateChildren(v1.getName());
			}
		
		
	}
	
	public void setRoot(Vertex v)  
    {  
		v.setAdj(0);  
        v.setParent(null);  
    }  
	
	static class Edge {
		private Vertex start;
		private Vertex end;
		private int weight;
	    private boolean walked = false; 
		public Edge(Vertex start, Vertex end, int weight) {
			super();
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
		

		
		public Vertex getStart() {
			return start;
		}
		
		public void setStart(Vertex start) {
			this.start = start;
		}
		
		public Vertex getEnd() {
			return end;
		}
		public boolean isWalked() {
			return walked;
		}
		
		public void setWalked(boolean v) {
			this.walked = v;
		}
		
		public void setEnd(Vertex end) {
			this.end = end;
		}
		
		public int getWeight() {
			return weight;
		}
		
		public void upWeight() {
			this.weight++;
		}
	}
	
	static class Vertex{
		private final static int INF = Integer.MAX_VALUE/10;
		
		String name;
		private boolean visited;
		private int adj;
		private Vertex parent;
		
		public Vertex(String name) {
			this.visited = false;
			this.adj = INF;
			this.parent = null;
			this.name = name;
		}
		
		public boolean isSeekVertex(String name) {
			if (name.equals(this.name)) {
				return true;
			}
			return false;
		}
		public boolean isVisited() {
			return visited;
		}
		
		public void setVisited(boolean v) {
			this.visited = v;
		}
		
		public String getName() {
			return name;
		}
		
		public int getAdj() {
			return adj;
		}
		public void setAdj(int a) {
			this.adj = a;
		}
		
		public Vertex getParent() {
			return parent;
		}
        
		public void setParent(Vertex parent) {
			this.parent = parent;
		}
	}
}
