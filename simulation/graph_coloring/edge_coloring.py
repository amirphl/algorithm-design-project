import csv

from matplotlib import pyplot


def get_y(v, max_v):
    """
    assume v is x_coordiante, method returns its y_coordinate
    :param v: the x coordinate
    :param max_v: maximum label in input graph
    :return: the y coordinate
    """
    x = v - max_v / 2
    return x ** 2


def get_color(c):
    """
    return the color corresponding to c
    :param c: the label of vertex
    :return: the color corresponding to the label
    """
    if c == 1:
        return '#003366'
    if c == 2:
        return '#00ffcc'
    if c == 3:
        return '#cc6600'
    if c == 4:
        return '#000000'
    if c == 5:
        return '#66ff33'
    if c == 6:
        return '#006600'
    if c == 7:
        return '#cc3300'
    if c == 8:
        return '#ff0066'
    if c == 9:
        return '#0099ff'
    if c == 10:
        return '#00ff00'
    if c == 11:
        return '#990033'
    if c == 12:
        return '#0066ff'
    if c == 13:
        return '#9900ff'
    if c == 14:
        return '#cc99ff'
    if c == 15:
        return '#999966'
    if c == 16:
        return '#003399'
    if c == 17:
        return '#00cc66'
    if c == 18:
        return '#ffff00'
    if c == 19:
        return '#cc3399'
    print(
        'There is no color defined for this number.You must define more colors in this method to get a correct result.')


def do_edge_coloring(input_path1):
    """
    draw graph with colored edges with matplotlib library
    :param input_path1: the path to output of edge_coloring process of java code
    :return:
    """
    src = []
    dst = []
    colors = []
    my_max = 0

    with open(input_path1, 'r') as csv_file:
        reader = csv.reader(csv_file)
        for row in reader:
            src.append(int(row[0]))
            dst.append(int(row[1]))
            colors.append(int(row[2]))
            temp = int(row[0])
            if int(row[1]) > temp:
                temp = int(row[1])
            my_max = temp
    for i in range(0, len(src)):
        if src[i] == dst[i]:
            continue
        x = src[i]
        y = get_y(x, my_max)
        a = dst[i]
        b = get_y(a, my_max)

        d1 = [x, a]

        d2 = [y, b]

        pyplot.plot(d1, d2, get_color(colors[i]), linewidth=2)

    pyplot.show()

    print('sources')
    print(src)
    print('destinations')
    print(dst)
    print('colors')
    print(colors)
