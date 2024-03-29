# Algorithm Design Project

## Description
implementations of vertex coloring for directed graphs and edge coloring for complete graphs using minimum number of colors and execution time (polynomial) + simulation

## Getting Started

### Dependencies
python 3.5</br>
Java 1.8</br>
libraries: csv, matplotlib

### Executing program

* Vertex Coloring Example:
```
java -jar mincolorcoloring.jar $path_to_input_graph_without_space $number_of_vertices $path_to_output_without_space
```
```
java -jar mincolorcoloring.jar /home/amirphl/graph.csv 100 /home/amirphl/output.csv
```
* Edge Coloring Example:
```
java -jar mincolorcoloring.jar $number_of_vertices $path_to_output_without_space
```
```
java -jar mincolorcoloring.jar 100 /home/amirphl/output.csv
```
* Simulation (simulation\graph_coloring\runapp.py):
```
python3.5 runapp.py
```

### Examples
![edge coloring example](https://github.com/amirphl/algorithm-design-project/blob/master/edge-coloring-result-example.PNG)
![vertex coloring example](https://github.com/amirphl/algorithm-design-project/blob/master/vertex-coloring-result-example.PNG)
