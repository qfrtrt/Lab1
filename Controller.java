package welcome;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import welcome.Graph;
import welcome.Graph.Edge;
import welcome.Graph.Vertex;


public class Controller {

    private Graph graph;
	private List<Vertex> vertexList;
	private Map<String,List<Edge>> ve_Map;

	public ArrayList<String> calcShortestPath(String word1) {
		Vertex start = null;
		int shortest = 0;
		String path = "";
		ArrayList<String> pathList = new ArrayList<String>();
		for(Vertex ver : vertexList) {
			if(ver.isSeekVertex(word1)) {
				start = ver;
	
			}
		}
		if (start == null) {
			System.out.println(word1+" not in graph!!");
			return pathList;
		}
		
		graph.setRoot(start);
		graph.updateChildren(word1);
		System.out.println("All the shortest path :");
		if (vertexList.size()==1)
		{
			path = vertexList.get(0).getName();
			System.out.println("The shortest path \"" + path +"\" = "+ shortest);
		}
		else {
			for(Vertex end : vertexList)
			{
				if(!(end.getName().equals(""))&&(!end.getName().equals(word1))) {
					path = end.getName();
					shortest = end.getAdj();
					while((end.getParent()!=null)&&(!word1.equals(end.getName()))){
					path = end.getParent().getName()+ " -> " + path;
			
						end = end.getParent();
					}
					pathList.add(path);
					System.out.println("The shortest path \"" + path +"\" = "+ shortest);
				}
			}
		}
		return pathList;
	}

	public String calcShortestPath(String word1, String word2) {
		Vertex start = null, end = null;
		String path = word2;
		int shortest = 0;
		for(Vertex ver : vertexList) {
			if(ver.isSeekVertex(word1)) {
				start = ver;
	
			}
			if(ver.isSeekVertex(word2)) {
				end = ver;
				
			}
		}			
		try {
			graph.setRoot(start);
			graph.updateChildren(word1);
	
			shortest = end.getAdj();
			
			while((end.getParent()!=null)&&(!word1.equals(end.getName()))){
			path = end.getParent().getName()+ " -> " + path;
	
				end = end.getParent();
			}
			}
			catch(NullPointerException e) {
				System.out.println("There is not a path from " + word1 + " to " + word2);
				return path;
			}
			
			
		
	
	
		System.out.println("The shortest path \"" + path +"\" = "+ shortest);
		return path;
	}

	public String generateNewText(String inputText) {
		String str = inputText.toLowerCase(), word1 = "", word2 = "", bridgeword;
		String[] list = str.split(" ");
		StringBuilder builder = new StringBuilder();
		ArrayList<String> temp = new ArrayList<String>();
		for(String word : list) {
			
			word1 = word2;
			word2 = word;
			if (!(word1.equals(""))){
				
	            temp.clear();
				builder.append(word1+" ");
				try {
				for(Edge edge1 : ve_Map.get(word1))	 {
					bridgeword = edge1.getEnd().getName();
					for(Edge edge2 : ve_Map.get(bridgeword)) {
						if(edge2.getEnd().getName().equals(word2)) {
							temp.add(bridgeword);
							break;
						}
					}
				}
				
				builder.append(temp.get((int)((temp.size())*Math.random())) + " ");	
				}
				catch(IndexOutOfBoundsException e) {}
				catch(NullPointerException e) {}
				finally {}
			}
			
		}
		builder.append(word2);
		System.out.println(builder.toString());
		return builder.toString();
	}

	@SuppressWarnings("finally")
	
	protected void printInfo(int count, String word1, String word2, ArrayList<String> list) {
		switch (count) {
		case -1:
			System.out.println("No " + word1 + " or " + word2 + " in the graph!");break;
		case 0:
			System.out.println("No bridge words from " + word1 + " to " + word2 +"!");break;
		case 1:
			System.out.println("The bridge words from " + word1 + " to " + word2 + " is: " + list.get(0) + ".");break;
		case 2:
			System.out.println("The bridge words from " + word1 + " to " + word2 + " are: " + list.get(0) + " and " + list.get(1) + ".");break;
		case 3:
			System.out.print("The bridge words from " + word1 + " to " + word2 + " are: ");
			for(int i=0;i<list.size()-2;i++) {
				System.out.print(list.get(i)+", ");
			}
			System.out.println(list.get(list.size()-2)+" and "+list.get(list.size()-1) + ".");
			break;
		default:
	
			break;
		}
	}

	@SuppressWarnings("finally")
	public String queryBridgeWords(String word1, String word2) {
		int count = 0;
		String bridgeword;
		ArrayList<String> list = new ArrayList<String>();
		try {
			List<Edge> e = ve_Map.get(word2);
			e.size();
			for(Edge edge1 : ve_Map.get(word1)) {
				bridgeword = edge1.getEnd().getName();
				for(Edge edge2 : ve_Map.get(bridgeword)) {
					if(edge2.getEnd().getName().equals(word2)) {
						count++;
						list.add(bridgeword);
					}
				}
			}
			
			}
			catch(NullPointerException e)
			{
				count = -1;
			}
			finally {
				printInfo(count, word1, word2, list);
				return list.toString();
			}
			
	}

	@SuppressWarnings("resource")
		public String randomWalk() throws FileNotFoundException, UnsupportedEncodingException {
	
			PrintWriter out = new PrintWriter("C:\\Users\\RIZERO\\Desktop\\random_path.txt", "UTF-8");
			String path = "";
			Vertex start = vertexList.get((int)(vertexList.size()*Math.random()));
			Vertex now = null, next = null;
	        Edge edge = null;
	        
			now = start;
	
			if (ve_Map.get(now.getName()).size()!=0)
			{
				edge = ve_Map.get(now.getName()).get((int)(ve_Map.get(now.getName()).size()*Math.random()));
	
				next = edge.getEnd();
				path = start.getName();
				System.out.print(start.getName()+ " ");
				out.print(start.getName()+ " ");
				while (next != null && !edge.isWalked()) {
					edge.setWalked(true);
					System.out.print(next.getName()+ " ");
					out.print(next.getName()+ " ");
					path += " -> " + next.getName();
					now = next;
					try {
						edge = ve_Map.get(now.getName()).get((int)(ve_Map.get(now.getName()).size()*Math.random()));
					}
					catch(NullPointerException e) {}
					finally {};
					next = edge.getEnd();
				}
				if  (ve_Map.get(next.name)!=null){
					System.out.print(next.getName()+ " ");
					out.println(next.getName()+ " ");
					path += " -> " + next.getName();
				}
				System.out.println();
				out.close();
				return path;
			}
			else {
				System.out.println("start vertex's out-degree is 0");
	//			System.out.println(now.getName());
				out.println(now.getName());
				out.close();
				return now.getName();
			}
				
		}

}
