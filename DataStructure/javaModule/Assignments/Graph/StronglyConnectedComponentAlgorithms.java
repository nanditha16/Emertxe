package com.graph.basics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.graph.basics.GraphAdjacentList.Graph;


public class StronglyConnectedComponentAlgorithms {

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
	 * DFS + save in stack 
	 * 
	 */
	public void DFSKosarajusAlgoModified(Graph graph, int v, boolean[] visited, Stack<Integer> stack) {
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

	/*
	 * Tarjan Algorithm : STRONGLY CONNECTED COMPONENT
	 * 
	 * 
	 * Step 1 : DFS Search to form DFS Tree
	 * Step 2 : Strongly Connected Components form subtrees of the DFS tree 		
	 * Step 3: Low-Link Value  : find the head/ mother vertex of each subtree 
	 * 		and print/store all the nodes in that subtree which is one SCC
	 *  	- Determine LowLink for each node while doing DFS
	 * 		- A node u is head if disc[u] = low[u] where
	 * 			low[u] --> the vertex with minimum discovery time
	 * 		- Case1 (Tree Edge): If node v is not visited already, then 
	 * 			after DFS of v is complete, 
	 * 			low[u] = min(low[u], low[v]);
	 * 		- Case 2 (Back Edge): When child v is already visited, 
	 * 			low[u] = min(low[u], disc[v]);
	 * 
	 */
	public void DFSTarjanAlgoModified(Graph graph, int v, boolean[] visited, Stack<Integer> stack, int[] low, int preCount) {
		// Step 1: mark the node as visited and print
		low[v] = preCount++;
		visited[v] = true;
		
		// Step 2: push v to Stack 
        stack.push(new Integer(v)); 		
		
		// Step 3: the vertex with minimum discovery time
        int min = low[v];
        
        // Step 4: Recur for all the adjacent nodes of this current node
     	Iterator<Integer> it = graph.adjListArray[v].iterator();
     	while(it.hasNext()) {
			int n = it.next();
			if(!visited[n]) {
				DFSTarjanAlgoModified(graph, n, visited, stack, low, preCount);
			}
			// Case 2 (Back Edge)
			if(low[n] < min) {
				min = low[n];
			}
		}	
     	// Case1 (Tree Edge)
     	if(low[v] > min) {
			low[v] = min;
			return;
		}
     	
     	List<Integer> component = new ArrayList<Integer>();
     	int n;
     	
     	do {
     		n = stack.pop();
     		component.add(n);
     		low[n] = graph.totalNode;
     	} while (n != v);
     	
     	System.out.println(component);
     	
	}
}
