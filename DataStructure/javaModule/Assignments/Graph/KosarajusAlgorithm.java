package com.graph.basics;

import java.util.Iterator;
import java.util.Stack;

import com.graph.basics.GraphAdjacentList.Graph;

/*
 * Kosaraju’s algorithm : STRONGLY CONNECTED COMPONENT
 * 
 * Step 1: Recur DFS(G) to compute finish time for each vertex O(V+E)
 * 			- create an empty stack S
 * 			- recur DFS(G,src) for adjacent verticies of a vertex
 * 			- push the vertex to stack
 * Step 2: Compute transpose of Graph - edges reversed  O(V+E)
 * 			- reverse directions of all edges Gt
 * Step 3: Call DFS(transpose of G) on vertices in decreasing order
 * 			of their finish time.  O(V+E)
 * 			- v <-- S.pop() till S is not empty.
 * 			- recur DFS(Gt, v)
 * Step 4: output vertices as separate SCCs
 * 			- DFS starting from v prints strongly connected component
 * 
 *  *  O(V+E)
 * NOTE: DFS of a graph with only one SCC always produces a tree
 */
public class KosarajusAlgorithm {

	/*
	 * DFS + save in stack 
	 */
	public void DFSKosarajusAlgoModified(Graph graph, int v, boolean[] visited, Stack stack) {
		// Step 1: mark the node as visited and print
		visited[v] = true;
		int n;
		// Step 2: Recur for all the adjacent nodes of this current node
		Iterator<Integer> it = graph.adjListArray[v].iterator();
		while(it.hasNext()) {
			n = it.next();
			if(!visited[n]) {
				DFSKosarajusAlgoModified(graph, n, visited, stack);
			}
		}
		
		// All vertices reachable from v are processed by now, 
        // push v to Stack 
        stack.push(new Integer(v)); 
		
	}

}
