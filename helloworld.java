package welcome;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.file.Paths;
import welcome.Graph.*;

public class helloworld {
	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception{

//		File file = new File("C:\\Users\\RIZERO\\Desktop\\text.txt");
//		Scanner sc = new Scanner(file,"UTF-8");
		Scanner sc = new Scanner(Paths.get("C:\\Users\\RIZERO\\Desktop\\text.txt"),"UTF-8");
		Scanner in = new Scanner(System.in);
		ArrayList<String> list = new ArrayList<String>();
		String s = "[a-z]*", word1 = "", word2 = "";
		Pattern pattern = Pattern.compile(s);
		Matcher ma;
		String str;
		boolean flag1 = true, flag2 = true;

		List<Vertex> verList = new LinkedList<Graph.Vertex>();

		Map<String, List<Edge>> ve_Map = new HashMap<String, List<Edge>>();
        Graph g = new Graph(verList, ve_Map);

		Vertex vertex = null;

		while(sc.hasNext())
		{
	        str = sc.next().toLowerCase();
	        ma = pattern.matcher(str);
			while(ma.find())
				if(!ma.group().equals(""))
				    list.add(ma.group());
		}
		sc.close();

			for(String word : list) {
				word1 = word2;
				word2 = word;
				flag1 = true;
				flag2 = true;
				vertex = new Vertex(word1);
				if (list.size()==1)
				{
					vertex = new Vertex(word);
					verList.add(vertex);
					ve_Map.put(vertex.getName(), new LinkedList<Graph.Edge>());
				}
				else {
					for(Vertex ver : verList) {
						if (word1.equals(ver.getName())|| word1.equals("")) {
							flag1 = false;
							break;
						}
					}

	                if(flag1) {
	                	vertex = new Vertex(word1);
						verList.add(vertex);
						ve_Map.put(vertex.getName(), new LinkedList<Graph.Edge>());
					}

	                for(Edge edge : ve_Map.get(word1)) {
						if (word2.equals(edge.getEnd().getName())) {

							edge.upWeight();
							flag2 = false;
							break;
							}
						}

	                if(flag2 && !(word1.equals(""))) {
	                	vertex = new Vertex(word2);
	                	ve_Map.get(word1).add(new Edge(vertex, 1));
	                }
				}
			}
		System.out.println("功能1&功能2:读入文本并生成有向图&展示有向图");
		for(Vertex ver : verList) {
			for(Edge edge : ve_Map.get(ver.getName())) {
				System.out.println(ver.getName()+"->"+edge.getEnd().getName()+" = "+edge.getWeight());
			}
		}
		System.out.println("功能3:查询桥接词");
		g.queryBridgeWords("lunch", "noon");
		g.queryBridgeWords("this", "i");
		g.queryBridgeWords("eaten", "in");
//		g.queryBridgeWords("i", "evening");
//		g.queryBridgeWords("lover", "eat");
		System.out.println("功能4:生成新文本");
		System.out.println("输入:\"donuts  the  and night\"");

		g.generateNewText("donuts the and night");
		System.out.println("输入:\"i donuts lunch noon love donuts edge swordmaster in moring\"");
		g.generateNewText("i donuts lunch noon love donuts edge swordmaster in moring");
		System.out.println("输入:\"this is apple in evening night like for me\"");
		g.generateNewText("this is apple in evening night like for me");
		System.out.println("功能5:计算最短路径");
		String path = g.calcShortestPath("i", "and");
//		System.out.println("选做:");
//		ArrayList<String> pathList = g.calcShortestPath("i");
		System.out.println("功能6:随机游走");
        String ran_path = g.randomWalk();
		showDirectedGraph(verList, ve_Map);
	    start2(verList, ve_Map, path, 1);
        start2(verList, ve_Map, ran_path, 2);
		System.out.println(list);
		System.out.println(new Date());


 }

	private static void showDirectedGraph(List<Vertex> verList, Map<String, List<Edge>> ve_Map)
	   {
	      GraphViz gv = new GraphViz();
	      gv.addln(gv.start_graph());
	      for(Vertex ver : verList) {
	    	  gv.addln(ver.getName());
	    	  for(Edge edge : ve_Map.get(ver.getName())) {

	    		  gv.addln(ver.getName()+" -> "+edge.getEnd().getName()+" [label = \"" + edge.getWeight() +"\"]" + ";");

	    	  }
	      }

	      gv.addln(gv.end_graph());
//	      System.out.println(gv.getDotSource());

//	      String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux
	      File out = new File("D:\\temp\\out." + type);
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	   }

	   /**
	    * Read the DOT source from a file,
	    * convert to image and store the image in the file system.
	    */
	private static void start2(List<Vertex> verList, Map<String, List<Edge>> ve_Map, String path, int arg)
	   {
		  String word1 = "", word2 = "";
		  boolean flag = false;
		  GraphViz gv = new GraphViz();
		  path.trim();

	      String[] str = path.split(" -> ");
	      gv.addln(gv.start_graph());

	      for(Vertex ver : verList) {
	    	  if (verList.size()==1)
	    	  {
		    	  gv.addln(ver.getName());
		    	  if (arg == 1) {
					  gv.addln(ver.getName() + " [color = red]");
				  }
				  else {
					  gv.addln(ver.getName() + " [color = lightblue]");
				  }
	    	  }
	    	  else {
		    	  for(Edge edge : ve_Map.get(ver.getName())) {
		    		  flag = true;
		    		  for (String word : str)
		    		  {
		    			  word1 = word2;
		    			  word2 = word;
		    			  if(word1!="")
		    			  {
			    			  if (ver.getName().equals(word1)&&edge.getEnd().getName().equals(word2)) {
			    				  if (arg == 1) {
			    					  gv.addln(word1 + " [color = red]");
			    					  gv.addln(word2 + " [color = red]");
			    					  gv.addln(word1+" -> "+word2+" [label = \"" + edge.getWeight() +"\" " + "color=red]" + ";");
			    				  }
			    				  else {
			    					  gv.addln(word1 + " [color = lightblue]");
			    					  gv.addln(word2 + " [color = lightblue]");
			    					  gv.addln(word1+" -> "+word2+" [label = \"" + edge.getWeight() +"\" " + "color=lightblue]" + ";");
			    				  }
			    				  flag = false;
			    			  }
		    			  }
		    		  }
		    		  if(flag)
		    		  gv.addln(ver.getName()+" -> "+edge.getEnd().getName()+" [label = \"" + edge.getWeight() +"\"]" + ";");
		    	  }
	    	  }
	      }
//	      if (arg == 1) {
//	    	  gv.addln(path+" [color = red]");
//	      }
//	      else {
//	    	  gv.addln(path+" [color = lightblue]");
//	      }

	      gv.addln(gv.end_graph());
//	      System.out.println(gv.getDotSource());

//	      String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux

	      File out = new File("D:\\temp\\out"+arg+"." + type);
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	   }
}11
