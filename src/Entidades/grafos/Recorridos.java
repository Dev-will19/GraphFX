package Entidades.grafos;

import Entidades.tadCola.*;

public class Recorridos {

	public static void recorrerProfundidad (Grafo g, int v, boolean[] visitados) {
		  //se marca el v�rtice v como visitado
		  visitados[v] = true;
		  //el tratamiento del v�rtice consiste �nicamente en imprimirlo en pantalla
		  System.out.println(v);
		  //se examinan los v�rtices adyacentes a v para continuar el recorrido
		  for (int i=0; i<g.obtenerNumVertices();i++){
			if ((v!=i) && (!visitados[i]) && (g.existeArista(v,i)) )
				recorrerProfundidad(g, i, visitados);					
		  }
	  }
	  
	   //procedimiento no recursivo
	   public static  void profundidad (Grafo g) {
		   boolean visitados[] = new boolean[g.obtenerNumVertices()];
		   //inicializo el vector: pongo todos los campos a false
		   for (int i=0;i<g.obtenerNumVertices();i++)
			   visitados[i]=false;
		   
		   //Se relanza el recorrido en cada v�rtice no visitado hasta que est�n todos visitados
		   for (int i=0;i<g.obtenerNumVertices();i++){
			   if (!visitados[i])
				   recorrerProfundidad(g, i, visitados);
		   }
	   }
	 /**
	  * 
	  *   	RECORRIDO EN AMPLITUD
	  *     @throws ColaVacia 
	  *   
	  */
	   
	   public static void amplitud (Grafo g) throws ColaVacia {
		   //se requiere una cola/lista donde guardar
		   TadCola cola = new TadCola();
		   boolean visitados[] = new boolean[g.obtenerNumVertices()];
		   int v; //v�rtice actual
		   
		   //Se inicializa el array visitados[] a false
		   for (int i=0;i<g.obtenerNumVertices();i++)
			   visitados[i]=false;
		   
		   //El recorrido en amplitud se inicia en cada v�rtice no visitado 
		   for (int i=0; i<g.obtenerNumVertices(); i++){
			   //se pone en la cola el v�rtide de partida y se marca como visitado
			if (!visitados[i]){
				cola.encolar(i);	
			    visitados[i]=true;
			   
			    while (!cola.colaVacia()) {
				   //desencolo, trato el v�rtice (en este caso, solo es mostrar pantalla)
				   v = cola.desencolar();				  
				   System.out.println(v);
				   //y encolo los nodos adyacentes a v.
				   for (int j=0; j < g.obtenerNumVertices(); j++){
					   if ( (v !=j ) && ( ( g.existeArista(v, j) ) && (!visitados[j])) ){
						   cola.encolar( j );
						   visitados[j]=true;
						   }
				   }
				} 
			 }
	      }
	   }
	   
} // fin de la clase


