package welcome;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**.

* set default mock parameter.（方法说明）

* @return data manager(返回值说明)

* @throws Exception if has error(异常说明)

*/
/**
 * @author fqy04
 *
 */
public class Graph {

    /**
    *
    */
    private List<Vertex> vertexList;

    /**
    *
    */
    private Map<String, List<Edge>> veMap;


    /**
     * @param vertexList
     * @param veMap
     */
    public Graph(final List<Vertex> vertexList,
    final Map<String, List<Edge>> veMap) {
        super();
        this.vertexList = vertexList;
        this.veMap = veMap;
        }

    /**
     * @param count
     * @param word1
     * @param word2
     * @param list
     */
    protected void printInfo(final int count, final String word1,
    final String word2, final ArrayList<String> list) {
        switch (count) {
        case -1:
            System.out.println(
                 "No " + word1 + " or " + word2 + " in the graph!");
            break;
        case 0:
             System.out.println(
                 "No bridge words from " + word1 + " to " + word2 + "!");
             break;
        case 1:
             System.out.println("The bridge words from "
        + word1 + " to " + word2 + " is: " + list.get(0) + ".");
             break;
        case 2:
            System.out.println("The bridge words from " + word1 + " to "
        + word2 + " are: " + list.get(0) + " and " + list.get(1) + ".");
            break;
        case 3:
            System.out.print("The bridge words from "
        + word1 + " to " + word2 + " are: ");
            for (int i = 0; i < list.size() - 2; i++) {
                System.out.print(list.get(i) + ", ");
            }
            System.out.println(list.get(list.size() - 2)
                + " and " + list.get(list.size() - 1) + ".");
            break;
        default:

            break;
        }
    }

    /**
    * @param word1
    * @param word2
    * * @return
    * */
    @SuppressWarnings("finally")
    public String queryBridgeWords(final String word1, final String word2) {
        int count = 0;
        String bridgeword;
        ArrayList<String> list = new ArrayList<String>();
        try {
            List<Edge> e = veMap.get(word2);
            e.size();
            for (Edge edge1 : veMap.get(word1)) {
                bridgeword = edge1.getEnd().getName();
                for (Edge edge2 : veMap.get(bridgeword)) {
                    if (edge2.getEnd().getName().equals(word2)) {
                        count++;
                        list.add(bridgeword);
                    }
                }
            }

        } catch (NullPointerException e) {
            count = -1;
        } finally {
            printInfo(count, word1, word2, list);
            return list.toString();
        }

    }

    /**
     * @param inputText
     * @return
     */
    public String generateNewText(final String inputText) {
        String str = inputText.toLowerCase(),
                word1 = "", word2 = "", bridgeword;
        String[] list = str.split(" ");
        StringBuilder builder = new StringBuilder();
        ArrayList<String> temp = new ArrayList<String>();
        for (String word : list) {

            word1 = word2;
            word2 = word;
            if (!(word1.equals(""))) {

                temp.clear();
                builder.append(word1 + " ");
                try {
                    for (Edge edge1 : veMap.get(word1)) {
                        bridgeword = edge1.getEnd().getName();
                        for (Edge edge2 : veMap.get(bridgeword)) {
                            if (edge2.getEnd().getName().equals(word2)) {
                                temp.add(bridgeword);
                                break;
                            }
                        }
                    }

                    builder.append(temp.get((int) ((temp.size())
                            * Math.random())) + " ");
                } catch (IndexOutOfBoundsException e) {

                } catch (NullPointerException e) {

                }
            }

        }
        builder.append(word2);
        System.out.println(builder.toString());
        return builder.toString();
    }
    /**
     * @return
     */
    public List<Vertex> getVertexList() {
        return vertexList;
    }

    /**
     * @param vertexList
     */
    public void setVertexList(final List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    /**
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public String randomWalk() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter out = new PrintWriter("E:\\asssss.txt", "UTF-8");
        String path = "";
        Vertex start = vertexList.get((int)
                (vertexList.size() * Math.random()));
        Vertex now = null, next = null;
        Edge edge = null;

        now = start;

        if (veMap.get(now.getName()).size() != 0) {
            edge = veMap.get(now.getName()).get((int)
                    (veMap.get(now.getName()).size() * Math.random()));

            next = edge.getEnd();
            path = start.getName();
            System.out.print(start.getName() + " ");
            out.print(start.getName() + " ");
            while (next != null && !edge.isWalked()) {
                edge.setWalked(true);
                System.out.print(next.getName() + " ");
                out.print(next.getName() + " ");
                path += " -> " + next.getName();
                now = next;
                try {
                    edge = veMap.get(now.getName()).get((int)
                            (veMap.get(now.getName()).size() * Math.random()));
                } catch (NullPointerException e) {

                }
                next = edge.getEnd();
            }
            if  (veMap.get(next.name) != null) {
                System.out.print(next.getName() + " ");
                out.println(next.getName() + " ");
                path += " -> " + next.getName();
            }
            System.out.println();
            out.close();
            return path;
        } else {
            System.out.println("start vertex's out-degree is 0");
            //          System.out.println(now.getName());
            out.println(now.getName());
            out.close();
            return now.getName();
        }

    }
    /**
     * @return
     */
    public Map<String, List<Edge>> getveMap() {
        return veMap;
    }

    /**
     * @param veMap
     */
    public void setveMap(final Map<String, List<Edge>> veMap) {
        this.veMap = veMap;
    }

    /**
     * @param word1
     * @return
     */
    public ArrayList<String> calcShortestPath(final String word1) {
        Vertex start = null;
        int shortest = 0;
        String path = "";
        ArrayList<String> pathList = new ArrayList<String>();
        for (Vertex ver : vertexList) {
            if (ver.isSeekVertex(word1)) {
                start = ver;

            }
        }
        if (start == null) {
            System.out.println(word1 + " not in graph!!");
            return pathList;
        }

        setRoot(start);
        updateChildren(word1);
        System.out.println("All the shortest path :");
        if (vertexList.size() == 1) {
            path = vertexList.get(0).getName();
            System.out.println(
                    "The shortest path \"" + path + "\" = " + shortest);
        } else {
            for (Vertex end : vertexList) {
                if (!(end.getName().equals(""))
                        && (!end.getName().equals(word1))) {
                    path = end.getName();
                    shortest = end.getAdj();
                    while ((end.getParent() != null)
                            && (!word1.equals(end.getName()))) {
                        path = end.getParent().getName() + " -> " + path;

                        end = end.getParent();
                    }
                    pathList.add(path);
                    System.out.println(
                            "The shortest path \"" + path + "\" = " + shortest);
                }
            }
        }
        return pathList;
    }
    public String calcShortestPath(final String word1, final String word2) {
        Vertex start = null, end = null;
        String path = word2;
        int shortest = 0;
        for (Vertex ver : vertexList) {
            if (ver.isSeekVertex(word1)) {
                start = ver;

            }
            if (ver.isSeekVertex(word2)) {
                end = ver;

            }
        }
        try {
            setRoot(start);
            updateChildren(word1);

            shortest = end.getAdj();

            while ((end.getParent() != null)
                    && (!word1.equals(end.getName()))) {
                path = end.getParent().getName() + " -> " + path;

                end = end.getParent();
            }
        } catch (NullPointerException e) {
            System.out.println(
                    "There is not a path from " + word1 + " to " + word2);
            return path;
        }





        System.out.println("The shortest path \"" + path + "\" = " + shortest);
        return path;
    }

    private void updateChildren(final String word) {
        Vertex v = null;

        if (veMap.get(word) == null || veMap.get(word).size() == 0) {
            return;
        }


        //System.out.print("v:"+v.getName()+" "+v.getAdj()+"    ");

        List<Vertex> childrenList = new LinkedList<Graph.Vertex>();
        for (Edge edge : veMap.get(word)) {
            for (Vertex ver : vertexList) {
                if (ver.isSeekVertex(word)) {

                    v = ver;
                    break;
                }
            }
            Vertex child = edge.getEnd();
            if (!child.isVisited()) {
                child.setVisited(true);
                //System.out.println(v.getName()
                //+" "+v.getAdj()+" "+edge.getWeight()+ " ");
                child.setAdj(v.getAdj() + edge.getWeight());
                for (Vertex ver1 : vertexList) {
                    if (ver1.isSeekVertex(child.getName())) {
                        ver1.setAdj(v.getAdj() + edge.getWeight());
                        ver1.setParent(v);
                        break;
                    }
                }
                //System.out.println(child.getName()+" "+child.getAdj());
                child.setParent(v);


                childrenList.add(child);
            }

            int nowDist = v.getAdj() + edge.getWeight();

            if (nowDist < child.getAdj()) {
                for (Vertex ver1 : vertexList) {
                    if (ver1.isSeekVertex(child.getName())) {
                        ver1.setAdj(v.getAdj() + edge.getWeight());
                        ver1.setParent(v);
                        break;
                    }
                }
                child.setAdj(nowDist);
                child.setParent(v);

            }

        }


        for (Vertex v1 : childrenList) {
            updateChildren(v1.getName());
        }


    }

    public void setRoot(final Vertex v) {
        v.setAdj(0);
        v.setParent(null);
    }

    /**
     * @author fqy04
     *
     */
    static class Edge {
        //private Vertex start;
        private Vertex end;
        private int weight;
        private boolean walked = false;
        Edge(/*Vertex start, */final Vertex end, final int weight) {
            super();
            //this.start = start;
            this.end = end;
            this.weight = weight;
        }

        Edge() { }

        public Vertex getEnd() {
            return end;
        }
        public boolean isWalked() {
            return walked;
        }

        public void setWalked(final boolean v) {
            this.walked = v;
        }

        public void setEnd(final Vertex end) {
            this.end = end;
        }

        public int getWeight() {
            return weight;
        }

        public void upWeight() {
            this.weight++;
        }
    }
    /**
     * @author fqy04
     *
     */
    static class Vertex {
        private static final int INF = Integer.MAX_VALUE / 10;

        private String name;
        private boolean visited;
        private int adj;
        private Vertex parent;

        Vertex(final String name) {
            this.visited = false;
            this.adj = INF;
            this.parent = null;
            this.name = name;
        }

        public boolean isSeekVertex(final String name) {
            if (name.equals(this.name)) {
                return true;
            }
            return false;
        }
        public boolean isVisited() {
            return visited;
        }

        public void setVisited(final boolean v) {
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

        public void setParent(final Vertex parent) {
            this.parent = parent;
        }
    }
}

