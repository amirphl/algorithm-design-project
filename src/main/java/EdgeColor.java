/**
 * optimal coloring a COMPLETE graph.
 */
class EdgeColor {
    private int V;

    EdgeColor(int V) {
        this.V = V;
    }

    /**
     * Perform algorithm to get edge coloring of complete graph.
     * If degree of graph is even then perform algorithm same as algorithm in report,
     * But if degree is odd, we create an edge coloring for complete graph with degree equals to degree of previous graph plus 1,
     * This work does not affect time complexity of algorithm. It also colors graph in optimal manner,
     * because you can delete new vertex which is added before without losing generality.
     *
     * @return result of coloring graph
     */
    int[] getMinColoring() {
        boolean changed = false;
        if (V % 2 != 0) {
            V++;
            changed = true;
        }
        int result[] = new int[(V) * (V)];
        oddMinColoring(result);
        for (int i = 0; i < V - 1; i++)
            result[(V - 1) * V + i] = result[(i) * V + V - 1] = result[((i - 1 + V - 1) % (V - 1)) * V + ((i + 1) % (V - 1))];
        if (changed)
            V--;
        return result;
    }

    /**
     * The algorithm implemented in this method is described in report.
     */
    private void oddMinColoring(int[] result) {
        int color = 1;
        int VPrime = V - 1;
        for (int i = 0; i < VPrime - 1; i++) {
            result[i * V + i + 1] = color;
            result[(i + 1) * V + i] = color;
            color++;
        }

        result[(VPrime - 1) * V] = color;
        result[VPrime - 1] = color;

        for (int i = 0; i < VPrime; i++) {
            parallelColoring(i, (i + 1) % (VPrime), result[i * V + ((i + 1) % (VPrime))], result);
        }
    }

    /**
     * Coloring edges which are parallel to edge with source = src and destination = dst with color of this edge.
     *
     * @param src   source of an edge
     * @param dst   destination of an edge
     * @param color color of input edge
     */
    private void parallelColoring(final int src, final int dst, final int color, int result[]) {
        int VPrime = V - 1;
        int i = 1;
        while (i - 1 < (VPrime - 2) / 2) {
            int u = (src - i + VPrime) % VPrime;
            int v = (dst + i) % VPrime;
            result[u * V + v] = color;
            result[v * V + u] = color;
            i++;
        }
    }
}