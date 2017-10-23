package welcome;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import welcome.Graph.Edge;
import welcome.Graph.Vertex;

import java.io.File;
import java.nio.file.Paths;


/**
 * @author fqy04
 *
 */
final class Helloworld {
    private Helloworld() {

    }
    public static void main(String[] args) throws Exception {

        //File file = new File("C:\\Users\\RIZERO\\Desktop\\text.txt");
        //Scanner sc = new Scanner(file,"UTF-8");
        Scanner sc = new Scanner(Paths.get("E:\\mubiao.txt"), "UTF-8");
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        String s = "[a-z]*", word1 = "", word2 = "";
        Pattern pattern = Pattern.compile(s);
        Matcher ma;
        String str;
        boolean flag1 = true, flag2 = true;

        List<Vertex> verList = new LinkedList<Graph.Vertex>();

        Map<String, List<Edge>> veMap = new HashMap<String, List<Edge>>();
        Graph g = new Graph(verList, veMap);

        Vertex vertex = null;

        while (sc.hasNext()) {
            str = sc.next().toLowerCase();
            ma = pattern.matcher(str);
            while (ma.find()) {
                if (!ma.group().equals("")) {
                    list.add(ma.group());
                }
            }
        }
        sc.close();

        for (String word : list) {
            word1 = word2;
            word2 = word;
            flag1 = true;
            flag2 = true;
            vertex = new Vertex(word1);
            if (list.size() == 1) {
                vertex = new Vertex(word);
                verList.add(vertex);
                veMap.put(vertex.getName(), new LinkedList<Graph.Edge>());
            } else {
                for (Vertex ver : verList) {
                    if (word1.equals(ver.getName()) || word1.equals("")) {
                        flag1 = false;
                        break;
                    }
                }

                if (flag1) {
                    vertex = new Vertex(word1);
                    verList.add(vertex);
                    veMap.put(vertex.getName(), new LinkedList<Graph.Edge>());
                }

                for (Edge edge : veMap.get(word1)) {
                    if (word2.equals(edge.getEnd().getName())) {

                        edge.upWeight();
                        flag2 = false;
                        break;
                    }
                }

                if (flag2 && !(word1.equals(""))) {
                    vertex = new Vertex(word2);
                    veMap.get(word1).add(new Edge(vertex, 1));
                }
            }
        }
        System.out.println("����1&����2:�����ı�����������ͼ&չʾ����ͼ");
        for (Vertex ver : verList) {
            for (Edge edge : veMap.get(ver.getName())) {
                System.out.println(ver.getName() + "->" + edge.getEnd().getName() + " = " + edge.getWeight());
            }
        }
        System.out.println("����3:��ѯ�ŽӴ�");
        g.queryBridgeWords("lunch", "noon");
        g.queryBridgeWords("this", "i");
        g.queryBridgeWords("eaten", "in");
        //g.queryBridgeWords("i", "evening");
        //g.queryBridgeWords("lover", "eat");
        System.out.println("����4:�������ı�");
        System.out.println("����:\"donuts  the  and night\"");

        g.generateNewText("donuts the and night");
        System.out.println("����:\"i donuts lunch noon love donuts edge swordmaster in moring\"");
        g.generateNewText("i donuts lunch noon love donuts edge swordmaster in moring");
        System.out.println("����:\"this is apple in evening night like for me\"");
        g.generateNewText("this is apple in evening night like for me");
        System.out.println("����5:�������·��");
        String path = g.calcShortestPath("i", "and");
        //System.out.println("ѡ��:");
        //ArrayList<String> pathList = g.calcShortestPath("i");
        System.out.println("����6:�������");
        String ranPath = g.randomWalk();
        showDirectedGraph(verList, veMap);
        start2(verList, veMap, path, 1);
        start2(verList, veMap, ranPath, 2);
        System.out.println(list);
        System.out.println(new Date());
        in.close();


    }

    private static void showDirectedGraph(List<Vertex> verList, Map<String, List<Edge>> veMap) {
        GraphViz gv = new GraphViz();
        gv.addln(gv.startGraph());
        for (Vertex ver : verList) {
            gv.addln(ver.getName());
            for (Edge edge : veMap.get(ver.getName())) {

                gv.addln(ver.getName() + " -> " + edge.getEnd().getName() + " [label = \"" + edge.getWeight() + "\"]" + ";");

            }
        }

        gv.addln(gv.endGraph());
        //System.out.println(gv.getDotSource());

        //String type = "gif";
        //String type = "dot";
        //String type = "fig";    // open with xfig
        //String type = "pdf";
        //String type = "ps";
        //String type = "svg";    // open with inkscape
        String type = "png";
        //String type = "plain";
        //File out = new File("/tmp/out." + type);   // Linux
        File out = new File("E:\\temp\\out." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    /**
     * Read the DOT source from a file,
     * convert to image and store the image in the file system.
     */
    private static void start2(List<Vertex> verList, Map<String, List<Edge>> veMap, String path, int arg) {
        String word1 = "", word2 = "";
        boolean flag = false;
        GraphViz gv = new GraphViz();
        path.trim();

        String[] str = path.split(" -> ");
        gv.addln(gv.startGraph());

        for (Vertex ver : verList) {
            if (verList.size() == 1) {
                gv.addln(ver.getName());
                if (arg == 1) {
                    gv.addln(ver.getName() + " [color = red]");
                } else {
                    gv.addln(ver.getName() + " [color = lightblue]");
                }
            } else {
                for (Edge edge : veMap.get(ver.getName())) {
                    flag = true;
                    for (String word : str) {
                        word1 = word2;
                        word2 = word;
                        if (word1 != "") {
                            if (ver.getName().equals(word1) && edge.getEnd().getName().equals(word2)) {
                                if (arg == 1) {
                                    gv.addln(word1 + " [color = red]");
                                    gv.addln(word2 + " [color = red]");
                                    gv.addln(word1 + " -> " + word2 + " [label = \"" + edge.getWeight() + "\" " + "color=red]" + ";");
                                } else {
                                    gv.addln(word1 + " [color = lightblue]");
                                    gv.addln(word2 + " [color = lightblue]");
                                    gv.addln(word1 + " -> " + word2 + " [label = \"" + edge.getWeight() + "\" " + "color=lightblue]" + ";");
                                }
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        gv.addln(ver.getName() + " -> " + edge.getEnd().getName() + " [label = \"" + edge.getWeight() + "\"]" + ";");
                    }
                }
            }
        }
        gv.addln(gv.endGraph());
        //System.out.println(gv.getDotSource());

        //String type = "gif";
        // String type = "dot";
        // String type = "fig";    // open with xfig
        //String type = "pdf";
        //String type = "ps";
        //String type = "svg";    // open with inkscape
        String type = "png";
        // String type = "plain";
        //File out = new File("/tmp/out." + type);   // Linux

        File out = new File("E:\\out" + arg + "." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }
}
