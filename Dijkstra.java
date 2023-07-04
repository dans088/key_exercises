import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Dijkstra {
	
	int nodes, edges;
	Scanner x;
	
	@SuppressWarnings("unchecked")
	ArrayList<Node>[] graph = new ArrayList[nodes];
	
	
	void node_creation (int x, int y, int z){  //función que acomoda los nodos en la lista de adyacencia
		
		graph[x].add(new Node(y,z,Double.POSITIVE_INFINITY, 0));
		graph[y].add(new Node(x,z,Double.POSITIVE_INFINITY,0));
	}
	
	@SuppressWarnings("unchecked")
	void leeGrafo(String archivo)
	{
		try{
			x = new Scanner(new File(archivo));//captura archivo
		}
		catch(Exception e) {
			System.out.println("No se encontró el archivo");
		}
		nodes = x.nextInt();//lee primer entero para guardar la cantidad de nodos
		edges = x.nextInt();//lee segundo entero para guardar la cantidad de aristas
		
		graph = new ArrayList[nodes+1];
		
		for(int i = 1; i<=nodes; i++)//lee los demas nodos mientras el scanner tenga un entero que leer
		{
			graph[i] = new ArrayList<>();	
		}
		
		for(int i = 1; i<=edges; i++) 
		{
			for(int j =0; j<3; j++)
			{
				if(x.hasNextInt())
				{
					node_creation(x.nextInt(),x.nextInt(),x.nextInt()); //crear nodos
				}
			}
		}
		
		
		
		x.close();
	}
	
	
	
	void dijkstra1(int first, int last) 
	{
		int [] visited = new int[nodes+1];  //arreglo para guardar nodos visitados
		Node [] result = new Node[nodes+1];  //arreglo para guardar el camino de dijkstra
		int source = first;
		
		double current_path = 0;
		
		PriorityQueue<Node> heap = new PriorityQueue<Node>(); //heap para correr el algoritmo

		for(int i = 1; i< nodes+1; i++)//inicializar nodos como no visitados 
		{
			visited[i] = 0;
			result[i] = new Node(i,0,Double.POSITIVE_INFINITY,0);
		}
		
		visited[first] = 1; //nodo fuente visitado
		
		for(Node j: graph[first]) //agregar nodos vecinos del nodo fuente
		{
			heap.add(j);
			result[j.getChild()] = j; //agregar nodos al arreglo resultado
			result[j.getChild()].setParent(first);
			result[j.getChild()].setPath(j.getCost()); //agregar costos al arreglo resultado
		}
		
		
		
		current_path = heap.peek().getCost();//guardar costo
		first = heap.poll().getChild(); //tomar el nodo con el camino más corto
		
		while(!heap.isEmpty())
		{
			if(visited[first] != 1 ) { //mientras el nodo no haya sido visitado
				
				for(Node j: graph[first])  //etiquetar vecinos
				{	
					heap.add(j); 
					
					if(result[j.getChild()].getPath() > j.getCost() + current_path || result[j.getChild()].getPath() == j.getCost() + current_path) {
						result[j.getChild()].setParent(first);
						result[j.getChild()].setPath(j.getCost() + current_path); 
					}else {	
						heap.remove(j);
					}		
					
				}
				
				visited[first] = 1; //nodo visitado
				current_path = result[heap.peek().getChild()].getPath();
				first = heap.poll().getChild(); //visitar nodo con el costo más pequeño	
			}else{
				visited[first] = 1; //nodo visitado
				current_path = result[heap.peek().getChild()].getPath();
				first = heap.poll().getChild(); //visitar nodo con el costo más pequeño
				}
			
		}
		
		//System.out.println("");
		
		
		
		System.out.println("Cámino más corto");
		//System.out.println("");
		
		//ciclo que imprime el camino más corto
		if(result[last].getParent() == source) {
			System.out.println("Costo: " +result[last].getPath());
			System.out.print(last+ "-"); 
		}else {
			System.out.println(result[last].getPath());
			System.out.print(last+ "-"); 
			while(result[last].getParent()!=source)
			{
				System.out.print(result[last].getParent()+"-"); 
				last = result[last].getParent();
			}
		}
		
		System.out.print(source); 
		System.out.println(""); 
		
	}
	
	void printGraph()
	{
		
		for(int i = 1; i<= nodes; i++)
		{
			System.out.print("Node: " + i);
			for(Node j:graph[i])
			{
				System.out.print(" ("+ j.getChild() +","+ j.getCost()+ ")");
			}
			
			System.out.println();
		}
	}
	
	void dijkstra(String archivo, int first, int last)
	{
		leeGrafo(archivo);
		dijkstra1(first, last);
	}
	
	
	public static void main(String[] args) {
		Dijkstra graph = new Dijkstra();
		String archivo = "Prueba.txt";
		graph.dijkstra(archivo,5, 6);
		//graph.printGraph();
	}
}