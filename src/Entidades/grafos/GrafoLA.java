package Entidades.grafos;

import Entidades.listaCalificadaOrdenada.Lista;
import Entidades.listaCalificadaOrdenada.NodoLista;

public class GrafoLA implements Grafo {
	int maxNodos; // Tama�o m�ximo de la tabla.
	int numVertices; // N�mero de v�rtices.
	Lista listaAdy[]; // Matriz de adyacencias del grafo.
	int[][] pesos;
	boolean dirigido; // Indica si es dirigido o no.

	// ----------------------------------------
	// CONSTRUCTORES Y M�TODOS DE ACCESO
	// ----------------------------------------

	public GrafoLA(boolean d) { // Grafo vac�o
		maxNodos = numVertices = 0;
		dirigido = d;
	}

	public GrafoLA(int n, boolean d) { // construye una array de listas de tama�o n con 0 v�rtices.
		dirigido = d;
		maxNodos = n;
		numVertices = 0;
		listaAdy = new Lista[n];
		pesos = new int[n][n];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				pesos[i][j] = INFINITO;
			}
		}
	}
	
	public int[][] obtenerPesos(){
		return pesos;
	}

	public int obtenerNumVertices() {
		return this.numVertices;
	}

	// --------------------
	// ARISTAS
	// --------------------

	public void insertaArista(int i, int j) {

		if (i >= numVertices)
			System.out.println("Error, no existe el v�rtice en el grafo");
		else {
			listaAdy[i].insertar(j);
			if (!dirigido)
				listaAdy[j].insertar(i);
		}
	}

	public void insertaPeso(int i, int j, int peso) {
		if (i >= numVertices)
			System.out.println("Error, no existe el v�rtice en el grafo");
		else {
			if (existeArista(i, j)) {
				pesos[i][j] = peso;
				if (!dirigido)
					pesos[j][i] = peso;
			}
		}
	}

	public void insertaArista(int i, int j, int peso) {
		insertaArista(i, j);
		insertaPeso(i, j, peso);
	}

	public void eliminaArista(int i, int j) {
		if (j >= numVertices)
			System.out.println("Error, no existe el v�rtice en el grafo");
		else {
			listaAdy[i].eliminar(j);
			pesos[i][j] = INFINITO;
			if (!dirigido) {
				listaAdy[j].eliminar(i);
				pesos[j][i] = INFINITO;
			}
		}
	}

	public boolean existeArista(int i, int j) {
		if ((i >= numVertices) || (j >= numVertices)) {
			System.out.println("Error, los vertices no se encuentran en el grafo.");
			return false;
		} else
			return (listaAdy[i].busqueda(j));
	}

	// --------------------
	// INSERTAR VERTICES
	// --------------------

	public void insertaVertice(int n) {// n: numero de vertices que quiero a�adir al grafo
		if (n > maxNodos - numVertices)
			System.out.println("Error, se supera el n�mero de nodos m�ximo del grafo");
		else {
			for (int i = 0; i < numVertices + n; i++) {
				listaAdy[i] = new Lista("lista " + i);
				for (int j = numVertices; j < numVertices + n; j++) {
					pesos[i][j] = pesos[j][i] = INFINITO;
				}
			}
		}
		numVertices += n;
	}

	// ----------------------------------------
	// GRADO IN, OUT E INCIDENCIA
	// ----------------------------------------

	public int gradoIn(int v) { // contar las veces que aparece i en las listas
		int gIn = 0;
		for (int i = 0; i < numVertices; i++)
			// if (i!=v){
			if (listaAdy[i].busqueda(v))
				gIn++;
		// }
		return gIn;
	}

	public int gradoOut(int i) { // contar los elementos de la lista
		int gOut = 0;
		NodoLista aux = listaAdy[i].inicio;
		while (aux != null) {
			gOut++;
			aux = aux.sig;
		}
		return gOut;
	}

	public int incidencia(int i) {
		if (!dirigido)
			return gradoIn(i);
		else
			return gradoIn(i) + gradoOut(i);
	}

	// ----------------------------------------
	// ORDEN Y TAMA�O DEL GRAFO
	// ----------------------------------------

	public int orden() {
		return numVertices;
	}

	public int tamanno() { // N�mero de arcos, se cuenta el n�mero de nodos de las listas.
		int tm = 0;
		for (int i = 0; i < numVertices; i++) {
			tm += numElementos(listaAdy[i]);
		}
		if (!dirigido)
			tm = tm / 2;
		return tm;
	}

	static int numElementos(Lista lista) {
		NodoLista aux = lista.inicio;
		int resul = 0;
		while (aux != null) {
			resul += 1;
			aux = aux.sig;
		}
		return resul;
	}

	// ----------------------------------------
	// COMPRUEBA SI ES ""NO"" DIRIGIDO
	// ----------------------------------------

	public boolean esNoDirigido() {
		boolean dir = true;

		for (int i = 0; i < numVertices; i++)
			for (int j = 0; j < numVertices; j++) {
				if (listaAdy[i].busqueda(j) != listaAdy[j].busqueda(i))
					dir = false;
			}
		return dir;
	}

	// ----------------------------------------
	// IMPRIME LAS LISTAS DE ADYACENCIAS
	// ----------------------------------------

	public void imprimirGrafo() {
		System.out.println("Tama�o m�ximo del grafo: " + maxNodos + "\n");
		System.out.println("El grafo contiene " + numVertices + " v�rtices: \n");
		for (int i = 0; i < numVertices; i++) {
			System.out.print("v�rtice " + i + ": ");
			escribir(listaAdy[i]);
		}
	}

	static void escribir(Lista lista) {
		NodoLista aux;
		aux = lista.inicio;
		while (aux != null) {
			System.out.print(aux.clave + ", ");
			aux = aux.sig;
		}
		System.out.println("FIN");
	}

	public void imprimirPesos() {
		System.out.println("La matriz contiene " + numVertices + " v�rtices: \n");
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (pesos[i][j] == INFINITO)
					System.out.print('\u221e' + "\t");
				else
					System.out.print(pesos[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
