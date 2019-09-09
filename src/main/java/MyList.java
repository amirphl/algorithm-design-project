/**
 * List data structure.
 * for application , before using methods of class , call 'sort' method for sorting elements in O(nlogn) time,
 * then you can check existence of an element with 'contains' method in O(logn) time because it uses binary search.
 */
class MyList {

    private int list[];
    private int index = 0;
    private HeapSort heapSort = new HeapSort();

    /**
     * List data structure.
     */
    MyList() {
        list = new int[10];
    }

    /**
     * append an element to head of the list.
     *
     * @param x the element which being append to head of the list.
     */
    void push(int x) {
        if (index == list.length) {
            int new_list[] = new int[list.length * 2];
            System.arraycopy(list, 0, new_list, 0, list.length);
            list = new_list;
        }
        list[index] = x;
        index++;
    }

    /**
     * pop an element from head of the list.
     *
     * @throws ArrayIndexOutOfBoundsException if list is empty
     */
    int pop() throws ArrayIndexOutOfBoundsException {
        if (index == 0)
            throw new ArrayIndexOutOfBoundsException();
        index--;
        return list[index];
    }

    /**
     * return the element in position 'index' in the list without removing it.
     *
     * @param ind position of element
     * @return the element in position 'index'
     * @throws ArrayIndexOutOfBoundsException if index is more than size of the list
     */
    int get(int ind) throws ArrayIndexOutOfBoundsException {
        return list[ind];
    }

    /**
     * check existence of an element in the list.
     * this method works only when the list is sorted because it uses binary search algorithm.
     *
     * @param x element to b check
     * @return if 'x' exists in the list , true , otherwise , false
     */
    boolean contains(int x) {
        return binarySearch(x, 0, index - 1);
    }

    boolean isEmpty() {
        return index == 0;
    }

    /**
     * binary search algorithm for checking existence of an element in list.
     * this method works only when the list is sorted.
     *
     * @param x the element to be checked
     * @param i starting index
     * @param j ending index
     * @return if 'x' exists in the list , true , otherwise , false
     */
    private boolean binarySearch(int x, int i, int j) {
        if (j < i)
            return false;
        if (x == list[(i + j) / 2])
            return true;
        if (x > list[(i + j) / 2])
            return binarySearch(x, ((i + j) / 2) + 1, j);
        else
            return binarySearch(x, 0, ((i + j) / 2) - 1);
    }

    /**
     * @return size of list
     */
    int size() {
        return index;
    }

    /**
     * sort the elements in the list with heap sort algorithm.
     */
    void sort() {
        heapSort.sort(list, index);
    }
}
