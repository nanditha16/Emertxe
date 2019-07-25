package com.graph.basics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class GraphAdjacentList {

	// A graph is an array of adjacency lists
	static class Graph {
		int totalNode;
		LinkedList<Integer> adjListArray[];

		Graph(int totalNode) {
			this.totalNode = totalNode;
			// size of arrayList is same as no of nodes
			adjListArray = new LinkedList[totalNode];

			/*
			 * a new list for each node such that adjacent nodes can be stored
			 * 
			 */
			for (int i = 0; i < totalNode; i++) {
				adjListArray[i] = new LinkedList<>();
			}
		}
	}

	static class GraphEdges {
		final int u;
		final int v;
	
		GraphEdges(int u, int v) {
			this.u = u;
			this.v = v;
		}
		
	}
	
	private static Graph createGraph(int totalNodes, int totalEdges, ArrayList<GraphEdges> edges) {
		Graph graph = new Graph(totalNodes);
		
		for(int i = 0; i < totalEdges; i++) {
			addEdge(graph, edges.get(i).u, edges.get(i).v);
		}
		
		return graph;
		
	}
	
	static void addEdge(Graph graph, int src, int dest) {
		// Add an edge from src to dest.
		graph.adjListArray[src].add(dest);

		// If graph is undirected
		//graph.adjListArray[dest].add(src);

	}

	static void graphBFSTraverseConnectSearch(Graph graph, int src) {
		// step 1: Mark all the nodes as not visited
		boolean visited[] = new boolean[graph.totalNode];

		// step 2 : create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<>();

		// step 3 : mark the current node as visited and enqueue
		visited[src] = true;
		queue.add(src);

		/*
		 * step 4: traversing will start from the source node get the adjacent nodes of
		 * the dequeued node it will be marked as 'visited' and enqueued.
		 */
		while (queue.size() != 0) {
			src = queue.poll(); // head of the list is dequeue
			System.out.print(src + " "); // print

			Iterator<Integer> it = graph.adjListArray[src].listIterator();
			while (it.hasNext()) {
				int n = it.next();

				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}

		}

	}

	static void graphDFSTraverseConnectSearch(Graph graph, int src) {
		// step 1: Mark all the nodes as not visited
		boolean visited[] = new boolean[graph.totalNode];

		// step 2: Call the recursive helper function to print DFS traversal
		DFSUtil(graph, src, visited);
	}

	static void graphDFSTraverseDisconnectSearch(Graph graph, int src) {
		// step 1: Mark all the nodes as not visited, default false
		boolean visited[] = new boolean[graph.totalNode];

		/*
		 * step 2: Call the recursive helper function to print DFS traversal starting
		 * from all vertices one by one
		 */
		// call DFSUtil() for every vertex.
		for (int i = 0; i < graph.totalNode; i++) {
			// check if it is already printed by other DFSUtil() call
			if (visited[i] == false) {
				DFSUtil(graph, src, visited);
			}
		}

	}

	static void DFSUtil(Graph graph, int src, boolean visited[]) {
		// Step 1: mark the node as visited and print
		visited[src] = true;
		System.out.print(src + " ");
		int n;
		// Step 2: Recur for all the adjacent nodes of this current node
		Iterator<Integer> it = graph.adjListArray[src].iterator();
		while (it.hasNext()) {
			n = it.next();
			if (!visited[n]) {
				DFSUtil(graph, n, visited);
			}
		}
	}

	static Graph getTranspose(Graph graph) {
		Graph graphTranspose = new Graph(graph.totalNode);
		for (int i = 0; i < graph.totalNode; i++) {
			// Recur for all the vertices adjacent
			Iterator<Integer> it = graph.adjListArray[i].iterator();

			while (it.hasNext()) {
				graphTranspose.adjListArray[it.next()].add(i);
			}
		}
		return graphTranspose;
	}

	static void printGraph(Graph graph) {
		for (int i = 0; i < graph.totalNode; i++) {
			System.out.print("Adjacency list of vertex " + i);

			for (Integer pCrawl : graph.adjListArray[i]) {
				System.out.print(" -> " + pCrawl);
			}
			System.out.println("\n");
		}
	}

	public static void main(String[] args) {
		// create the graph with 5 nodes
		
		int totalNodes = 5;
		int totalEdges = 5;
		ArrayList<GraphEdges> edgesArray = new ArrayList<>();
		edgesArray.add(new GraphEdges(0, 1));
		edgesArray.add(new GraphEdges(0, 4));
		edgesArray.add(new GraphEdges(1, 2));
		edgesArray.add(new GraphEdges(1, 3));
		edgesArray.add(new GraphEdges(1, 4));
		edgesArray.add(new GraphEdges(2, 3));
		edgesArray.add(new GraphEdges(3, 4));
		//undirected
		edgesArray.add(new GraphEdges(1, 0));
		edgesArray.add(new GraphEdges(4, 0));
		edgesArray.add(new GraphEdges(2, 1));
		edgesArray.add(new GraphEdges(3, 1));
		edgesArray.add(new GraphEdges(4, 1));
		edgesArray.add(new GraphEdges(3, 2));
		edgesArray.add(new GraphEdges(4, 3));

		
		Graph graph = createGraph(totalNodes, totalEdges, edgesArray);
	
		// print the adjacency list representation of
		// the above graph
		printGraph(graph);

		System.out.println("\n Transpose : \n");
		printGraph(getTranspose(graph));

		/*
		 * BFS Application : 
		 * 1. Minimum Spanning tree/shortest path : Unweighed graph 
		 * 		- path with least number of edges 
		 * 		- The level count gives the path 
		 * 2. Peer to Peer Networks - BitTorrent: 
		 * 		- To find neighbor nodes 
		 * 3. Crawlers in Search Engines : 
		 * 		- start from source page 
		 * 		- recur follow all links from source 
		 * 4. Social Networking Websites : 
		 * 		- find X within a given distance ‘k’ from a X
		 * 			using BFS till ‘k’ levels 
		 * 5. GPS Navigation systems : neighboring locations
		 * 6. Broadcasting in Network : shortest route 
		 * 7. In Garbage Collection : 
		 * 		1. Cheney’s algorithm 
		 * 8. Detecting a cycle : undirected graphs only 
		 * 		- if there is an adjacent u such that 
		 * 		- visited[u] == true and 
		 * 		- u is not parent of v 
		 * 9. Maximum flow: 
		 * 		1. Ford-Fulkerson algorithm/ Edmond karp Algo 
		 * 			- weighted graph
		 * 			- BFS of residual graph 
		 * 10. bipartite graph (2 disjoint and independent sets): 
		 * 		divide into 2 independent sets U and V such that every edge (u,v) 
		 * 		either u belongs to U and v to V or u belongs to V and v to U. 
		 * 		i.e, there is  no edge that connects vertices of same set 
		 * 			- color the start node 
		 * 			- run DFS from this vertex 
		 * 			- for each new vertex, color opposite to it's parent 
		 * 			- check for edges that it doesnt link two vertices of same color 
		 * 11. Path finding :
		 * 12. Finding Strongly Connected Components :
		 */

		/*
		 * Connected graph.
		 */
		// print BFS traverse - ascending order
		System.out.println("\n \n BFS traverse : \n");
		graphBFSTraverseConnectSearch(graph, 0);

		/*
		 * DFS Application : 
		 * 1. Minimum Spanning tree/shortest path : Unweighed graph 
		 * 2. Detecting a cycle :in directed/undirected graph, back edge during DFS = true
		 * 		- if there is an adjacent u such that 
		 * 		- visited[u] == true and 
		 * 		- u is not parent of v 
		 * 3. Path finding : 
		 * 		- call DFS with src, 
		 * 		- tack the path trace from src to current 
		 * 		- return stack if dest is encountered 
		 * 4. Topological sorting : for scheduling jobs 
		 * 		directed acyclic graph G = (V,E) is a linear ordering of
		 * 		all its vertices such that 
		 * 			- if G contains an edge (u,v) then 
		 * 			- u appears before v in ordering 
		 * 5. bipartite graph (2 disjoint and independent sets):
		 * 		divide into 2 independent sets U and V such that every edge (u,v) either u
		 * 		belongs to U and v to V or u belongs to V and v to U. i.e, 
		 * 		there is no edge that connects vertices of same set 
		 * 			- color the start node - run DFS from this  vertex 
		 * 			- for each new vertex, color opposite to it's parent 
		 * 			- check for edges that it doesnt link two vertices of same color 
		 * 6. Finding Strongly Connected  Components : 
		 * 		directed graph if there is a path between all pairs of vertices
		 * 		maximal strongly connected subgraph 
		 * 		1. Kosaraju's Algo : 2passes of DFS -
		 * 		2. Tarjan's Algo : 1 pass of DFS 
		 * 7. mazes : include nodes on the current path in the visited set.
		 */

		/*
		 * Connected graph.
		 */
		// print DFS traverse - recursive
		System.out.println("\n  \n DFS traverse : \n");
		graphDFSTraverseConnectSearch(graph, 0);
		/*
		 * Disconnected graph : call DFSUtil() for every vertex. before calling
		 * DFSUtil(), check if it is already printed by other call of DFSUtil()
		 */
		System.out.println("\n  \n DFS traverse : \n");
		graphDFSTraverseDisconnectSearch(graph, 0);
		System.out.println("\n");

		/*
		 * ALGORITHMS : Strongly Connected Components : 
		 * 1. Kosaraju's Algorithm : 2passes of DFS 
		 * 2. Tarjan's Algorithm : 1 pass of DFS 
		 * 3. Bellman-Ford Algorithm 
		 * 4. Floyd Warshall Algorithm
		 */
		System.out.println("Following are strongly connected components " + "in given graph ");
		System.out.println("\n  \n KosarajusAlgorithm : \n");
		printSCCsKosarajusAlgorithm();
		
		System.out.println("\n  \n TarjanAlgorithm : \n");
		printSCCsTarjanAlgorithm();
		
		/*
		 * Minimum Spanning Tree : 
		 * 3. Prim’s Minimum Spanning Tree 
		 * 4. Dijkstra’s Single Source Shortest Path 
		 * 5. Cheney’s algorithm 
		 * 6. Ford-Fulkerson algorithm / Edmond Karp Algorithm 
		 * 7. Kruskal's Minimum Spanning Tree Algorithm 
		 * 8. Boruvka's Algorithm 
		 * 9. Steiner Tree 
		 * 12. Johnson's Algorithm 
		 * 13. Dial's Algorithm 
		 * 14. Euler's Circuit in a Directed Graph 
		 * 15. Eulerian Path in undrected graph 
		 * 16. Karger's Algorithm 
		 * 17. Dinic's Algorithm 
		 * 18. Erdos Renyl  Model 
		 * 19, Hierholzer's Algorithm 
		 * 20. Hopcroft Karp Algorithm 
		 * 21. Hamiltonian Path
		 */

	}

	
	private static void printSCCsKosarajusAlgorithm() {
		int totalNodes = 5;
		int totalEdges = 5;
		ArrayList<GraphEdges> edgesArray = new ArrayList<>();
		edgesArray.add(new GraphEdges(0,2));
		edgesArray.add(new GraphEdges(1, 0));
		edgesArray.add(new GraphEdges(2, 1));
		edgesArray.add(new GraphEdges(0, 3));
		edgesArray.add(new GraphEdges(3, 4));
		// undirected
		edgesArray.add(new GraphEdges(2, 0));
		edgesArray.add(new GraphEdges(0, 1));
		edgesArray.add(new GraphEdges(1, 2));
		edgesArray.add(new GraphEdges(3, 0));
		edgesArray.add(new GraphEdges(4, 3)); 

		Graph graph = createGraph(totalNodes, totalEdges, edgesArray);
		
		
		StronglyConnectedComponentAlgorithms printSCC = new StronglyConnectedComponentAlgorithms();

		// Step 1:
		// - create an empty stack S
		Stack<Integer> stack = new Stack<>();

		// - recur DFS(G,src) for adjacent vertices of a vertex
		// Mark all the vertices as not visited (For first DFS)
		boolean visited[] = new boolean[graph.totalNode];

		// - push the vertex to stack
		// Fill vertices in stack according to their finishing times
		for (int i = 0; i < graph.totalNode; i++) {
			if (!visited[i]) {
				printSCC.DFSKosarajusAlgoModified(graph, i, visited, stack);
			}
		}

		// Step 2:
		Graph graphTranspose = getTranspose(graph);

		// Step 3:
		// Mark all the vertices as not visited (For second DFS)
		for (int i = 0; i < graph.totalNode; i++) {
			visited[i] = false;
		}

		// Now process all vertices in order defined by Stack
		while (!stack.empty()) {
			// - v <-- S.pop() till S is not empty.
			int v = (int) stack.pop();

			// - recur DFS(Gt, v)
			if (!visited[v]) {
				DFSUtil(graphTranspose, v, visited);
				System.out.println();
			}
		}

	}

	private static void printSCCsTarjanAlgorithm() {
		// Create graph with edges
		int totalNodes = 8;
		int totalEdges = 14;
		ArrayList<GraphEdges> edgesArray = new ArrayList<>();
		edgesArray.add(new GraphEdges(0,1));
		edgesArray.add(new GraphEdges(1, 2));
		edgesArray.add(new GraphEdges(2, 3));
		edgesArray.add(new GraphEdges(3, 2));
		edgesArray.add(new GraphEdges(3, 7));
		edgesArray.add(new GraphEdges(7, 3));
		edgesArray.add(new GraphEdges(2, 6));
		edgesArray.add(new GraphEdges(7, 6));
		edgesArray.add(new GraphEdges(5, 6));
		edgesArray.add(new GraphEdges(6, 5));
		edgesArray.add(new GraphEdges(1, 5));
		edgesArray.add(new GraphEdges(4, 5));
		edgesArray.add(new GraphEdges(4, 0));
		edgesArray.add(new GraphEdges(1, 4));
         

		// Step 1: Initialization
		Graph graph = createGraph(totalNodes, totalEdges, edgesArray);

		// - create an empty stack S
		Stack<Integer> stack = new Stack<>();

		// - to Find Low-Link Value of all edges
		int[] low = new int[graph.totalNode];
		// preorder number counter
		int preCount = 0;
		
		StronglyConnectedComponentAlgorithms printSCC = new StronglyConnectedComponentAlgorithms();

		// STEP 2: To get SCC 		
		
		// - recur DFS(G,src) for adjacent vertices of a vertex
		// Mark all the vertices as not visited (For DFS)
		boolean visited[] = new boolean[graph.totalNode];

		// - push the vertex to stack
		// Fill vertices in stack according to their finishing times 
		for (int i = 0; i < graph.totalNode; i++) {
			if (!visited[i]) {
				printSCC.DFSTarjanAlgoModified(graph, i, visited, stack, low, preCount);	
			}
		}

	}
	
}
