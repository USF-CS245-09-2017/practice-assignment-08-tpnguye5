/**
 * 
 * @author Theresa Nguyen
 * Program requires an unweighted, directed graph. 
 * Graph will be represented with an adjacency-matrix representation. 
 * However, this graph can also be represented with an adjacency-list.
 *
 */

import java.util.LinkedList;
import java.util.Queue;

public class GraphAdjMatrix implements Graph{
	//----------------------------------------------------------------------
	//Datatypes for class
	//----------------------------------------------------------------------
	private Vertex[][] vertices_list; 
	
	//----------------------------------------------------------------------
	//inner class
	//----------------------------------------------------------------------
	class Vertex{
		int neighbor;
		int cost;
		Vertex next; 
	}
	
	//----------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------
	public GraphAdjMatrix(int vertices){
		vertices_list = new Vertex[vertices][vertices];
		
		for(int i = 0; i < vertices; i++){
			for(int j = 0; j < vertices; j++){
				Vertex v = new Vertex();
				v.cost = 0;
				vertices_list[i][j] = v;
			}
		}
	}
	
	//----------------------------------------------------------------------
	//Method 
	//Purpose: changes the cost in the matrix from 0 to 1.
	//----------------------------------------------------------------------
	public void addEdge(int v1, int v2){
		vertices_list[v1][v2].cost = 1;
	}
	//----------------------------------------------------------------------
	//Method
	//"sorts" the vertices
	//----------------------------------------------------------------------
	public void topologicalSort(){
		
		/*
		 * First, we need to find a vertex with no incident edges.
		 * 
		 * We do this by keeping a count of incident edges of all the vertices in the graph.
		 */
		
		int[] NumIncident = new int[vertices_list.length];
		Queue<Integer> q = new LinkedList<Integer>();
		//Queue for BFS
		/*
		 * Then, instantiate a queue to keep track of the vertices with no edges pointing to it. 
		 * We want the vertices with edges pointing out.
		 */
		
		//We populate the array with 0s
		for (int v = 0; v < vertices_list.length; v++){
			NumIncident[v] = 0;
		}
		//We go through the matrix and look for vertices with a cost of 1 or not 0.
		//Then, we update out NumIncident array.
		for (int v = 0; v < vertices_list.length; v++){
			for (int u = 0; u < vertices_list[v].length; u++){
				if (vertices_list[v][u].cost != 0){
					NumIncident[u]++;
				}
			}
		}
		//Go through the vertices again, we check if the NumIncident array at some vertex
		//is 0. If it is, then we add it to the queue because that vertex has edges pointing 
		//away from it. 
		for (int v = 0; v < vertices_list.length; v++){
			if (NumIncident[v] == 0){
				q.add(v);
			}
		}
		//Instantiate array of int for our sort. 
		int[] topSortArr = new int[vertices_list.length];
		int count = 0;

		while (!q.isEmpty()){
			int v = q.poll();
			//add vertex to the topological sort
			topSortArr[count++] = v;
			for (int u = 0; u < vertices_list.length; u++){
				if (vertices_list[v][u].cost != 0){
					NumIncident[u]--; 
					if (NumIncident[u]== 0){
						q.add(u);
					}
				}
			}
			
		}
	}
	//----------------------------------------------------------------------
	//Private helper method for neighbor method. 
	//Purpose: provides the length of the vertex array because it will change.
	//----------------------------------------------------------------------
	private int listLength(int vertex){
		int e = 0;
		
		for (int j = 0; j < vertices_list.length; j++){
			if (vertices_list[vertex][j].cost != 0){
				e++;
			}
		}
		return e; 
	}
	//----------------------------------------------------------------------
	//Returns:
	//An array of vertex IDs such that each ID represents a vertex which 
	//is the destination of the edge origination from the argument.
	//----------------------------------------------------------------------
	public int[] neighbors(int vertex){
		int[] result;		
		int count=0;
		result = new int[listLength(vertex)];
		
		for (int i = 0; i < vertices_list.length; i++){
			if (vertices_list[vertex][i].cost != 0){
				result[count] = i;
				count++;
			}
		}
		
		return result;
	}
	
}
