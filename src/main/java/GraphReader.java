import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * An interface for reading .csv file which contains graph.
 */
class GraphReader {

    private File path;
    private int[][] table;

    /**
     * contract:
     * if there exists an edge from vertex i to j, there is no need to be an edge from vertex j to i in the file.
     * each line must contains an edge in format  'i,j'  which means there is an edge from vertex i to j.
     *
     * @param path path to .csv file
     */
    GraphReader(File path) {
        this.path = path;
    }

    /**
     * Read graph and store it in adjacency list.
     * here each vertex has a list which contains its neighbors,
     * so adjacency list is an array of lists.list is an instance of 'MyList' class.
     * neighbors of a vertex are sorted in its corresponding list.
     * for example , if 5 , 6 , 7 are neighbors of 11 , then list of 11 contains elements
     * in order 5 , 6 , 7 not 7 , 5 , 6 or any other permutation.
     *
     * @return adjacency list as an array of lists
     * @throws IOException if file path is invalid or file does not contains graph in csv mode
     */
    MyList[] getGraph() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        String cvsSplitBy = ",";
        ArrayList<Integer> starts = new ArrayList<Integer>();
        ArrayList<Integer> ends = new ArrayList<Integer>();

        int max = 1;
        int number_of_lines = 0;
        int a, b, temp;
        while ((line = br.readLine()) != null) {
            String[] country = line.split(cvsSplitBy);
            a = Integer.valueOf(country[0]);
            b = Integer.valueOf(country[1]);
            starts.add(a);
            ends.add(b);
            temp = (a > b) ? a : b;
            max = (max > temp) ? max : temp;
            number_of_lines++;
        }
        br.close();

        int matrix[][] = new int[2][number_of_lines];
        for (int i = 0; i < number_of_lines; i++) {
            matrix[0][i] = starts.get(i);
            matrix[1][i] = ends.get(i);
        }

        max = convert(matrix, max);

//        for (int i = 0; i < number_of_lines; i++) {
//            System.out.print(matrix[0][i] + " , " + matrix[1][i] + "      ");
//        }

        MyList adj_list[] = new MyList[max + 1];
        for (int i = 0; i < adj_list.length; i++)
            adj_list[i] = new MyList();

        for (int i = 0; i < matrix[0].length; i++) {
            adj_list[matrix[0][i]].push(matrix[1][i]);
            adj_list[matrix[1][i]].push(matrix[0][i]);
        }

        for (MyList anAdj_list : adj_list) anAdj_list.sort();

        return adj_list;
    }

    /**
     * convert vertex numbers of input file to continuous numbers.
     * for example , converts sequence 0 , 1 , 2 , 5 ,11 , 350 , 3569 , 15246 to sequence 0 , 1 , 2 , 3 , 4 , 5 , 6 , 7
     * this works reduces time for searching in adj_list graph and memory for storing matrix.
     *
     * @param matrix input adj_list
     * @param max    maximum vertex number appeared in input file . in above example , equals 15246
     */
    private int convert(int[][] matrix, int max) {
        ArrayList<Integer> my_table = new ArrayList<Integer>();
        int i = 0;
        boolean isThere = false;
        while (i < max) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[0][j] == i || matrix[1][j] == i) {
                    isThere = true;
                    break;
                }
            }
            if (isThere) {
                i++;
            } else {
                my_table.add(i);
                my_table.add(max);
                int temp = -1;
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[0][j] == max)
                        matrix[0][j] = i;
                    if (matrix[1][j] == max)
                        matrix[1][j] = i;
                    temp = (temp > matrix[0][j]) ? temp : matrix[0][j];
                    temp = (temp > matrix[1][j]) ? temp : matrix[1][j];
                }
                max = temp;
                i++;
            }
            isThere = false;
        }
        create_table(my_table);
        return max;
    }

    /**
     * The one_to_one function which performs conversion in convert method
     * Domain and range of function is in Array list,
     * store this array list in 2D array. This 2D array is called 'table'
     *
     * @param list_table the array list contains domain and range of function
     */
    private void create_table(ArrayList<Integer> list_table) {
        table = new int[list_table.size() / 2][2];
        int j = 0;
        for (int i = 0; i < list_table.size() / 2; i++) {
            table[i][0] = list_table.get(j);
            j++;
            table[i][1] = list_table.get(j);
            j++;
        }
    }

    /**
     * Use table (container of domain and range) for get the actual label of vertex v.
     * If v is not in domain of function, then return itself,v
     *
     * @param v the vertex we want its actual label
     * @return the actual label of vertex v
     */
    int get_real_label_of_vertex(int v) {
        for (int[] ints : table) {
            if (ints[0] == v)
                return ints[1];
        }
        return v;
    }
}