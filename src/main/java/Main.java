import java.io.*;

public class Main {

    /**
     * initialize line 8 to 11 before running
     */
    private static int V;//assign a number to this variable for Edge coloring.
    private static String OUTPUT_NAME;
    private static MyList[] adj_graph;
    private static EdgeColor ec;


    /**
     * start of program
     * put path in args[0] because we want to pass it to methods for reading input file
     */
    public static void main(String[] args) {
        long before = System.currentTimeMillis();
        if (args.length == 3) {
            V = Integer.valueOf(args[1]);
            ec = new EdgeColor(V);
            OUTPUT_NAME = args[2];
            doVertexColoring(args);
        } else if (args.length == 2) {
            V = Integer.valueOf(args[0]);
            ec = new EdgeColor(V);
            OUTPUT_NAME = args[1];
            doEdgeColoring();
        } else {
            System.out.println("Invalid number of arguments...");
            System.out.println("------------------------------");
            show_examples();
        }
        long after = System.currentTimeMillis();
        System.out.println("Total Execution Time: " + (after - before) + " milliseconds");
    }

    private static void show_examples() {
        System.out.println("See examples below:");
        System.out.println("Directed graph vertex coloring example:");
        System.out.println("java -jar mincolor-coloring.jar $path/to/input $number_of_vertices $path/to/output");
        System.out.println("Complete graph edge coloring example:");
        System.out.println("Java -jar mincolor-coloring.jar $number_of_vertices $path/to/output");
    }

    /**
     * Get result of edge coloring from the instance of EdgeColor class
     * then write and print result
     * The output path is in OUTPUT_NAME variable
     */
    private static void doEdgeColoring() {
        int res[] = ec.getMinColoring();
        int VPrime = V;
        if (VPrime % 2 != 0)
            VPrime++;
        try {
            FileWriter pw = new FileWriter(new File(OUTPUT_NAME));
            for (int i = 0; i < res.length; i++) {
                System.out.println((i / (VPrime)) + "," + (i % (VPrime)) + "," + res[i]);
                pw.write(String.valueOf(i / (VPrime)));
                pw.write(",");
                pw.write(String.valueOf((i % (VPrime))));
                pw.write(",");
                pw.write(String.valueOf(res[i]));
                pw.write("\n");
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in writing results.");
        }
    }

    /**
     * Get adj_list graph form the instance of class GraphReader
     * Get result of vertex coloring from method vertexColor
     * then write and print result
     * The output path is in OUTPUT_NAME variable
     */
    private static void doVertexColoring(String[] args) {
        GraphReader ge = new GraphReader(new File(args[0]));
        try {
            adj_graph = ge.getGraph();
            V = adj_graph.length;

            int[] res = vertexColor();

            FileWriter pw = new FileWriter(new File(OUTPUT_NAME));
            for (int i = 0; i < res.length; i++) {
                System.out.println(ge.get_real_label_of_vertex(i) + "," + res[i]);
                pw.write(String.valueOf(ge.get_real_label_of_vertex(i)));
                pw.write(",");
                pw.write(String.valueOf(res[i]));
                pw.write("\n");
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in IO.");
        }
    }

    /**
     * The algorithm for optimal coloring a graph.(vertex)
     * Full description of the algorithm is available in report.
     */
    private static int[] vertexColor() {
        int result[] = new int[V];
        for (int i = 0; i < V; i++)
            result[i] = 0;

        int color = 1;
        for (int i = 0; i < V; i++) {
            int highest_degree = -1;
            int highest_index = -1;
            for (int j = 0; j < V; j++)
                if (result[j] == 0 && adj_graph[j].size() > highest_degree) {
                    highest_degree = adj_graph[j].size();
                    highest_index = j;
                }

            if (highest_degree == -1)
                break;
            result[highest_index] = color;
            MyList adjacents = adj_graph[highest_index];
            for (int j = 0; j < V; j++)
                if (!adjacents.contains(j) && j != highest_index && result[j] == 0) {
                    MyList neighbors = adj_graph[j];
                    boolean isThereNeighborColored = false;
                    for (int k = 0; k < neighbors.size(); k++) {
                        if (result[neighbors.get(k)] == color) {
                            isThereNeighborColored = true;
                            break;
                        }
                    }
                    if (!isThereNeighborColored)
                        result[j] = color;
                }
            color++;
        }
        return result;
    }

    /**
     * print adjacency list
     */
    private static void print() {
        System.out.println("Content of adj_list :");
        int co = 0;
        for (MyList list : adj_graph) {
            int size = list.size();
            System.out.print(co + " : ");
            for (int i = 0; i < size; i++) {
                System.out.print(list.get(i) + ",");
            }
            System.out.println();
            co++;
        }
        System.out.println();
    }
}
