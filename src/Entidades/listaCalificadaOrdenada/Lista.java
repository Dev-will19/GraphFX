package Entidades.listaCalificadaOrdenada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lista {
	public NodoLista inicio;
	public String nombre;
	public Lista (String nombreLista) {
		inicio = null;
		nombre = nombreLista;
	}
	static boolean busqueda (NodoLista nodoLista, int x) {
		boolean resul = false;
		if (nodoLista != null)
			if (nodoLista.clave == x)
				resul = true;
			else if (nodoLista.clave < x)
					 resul = busqueda (nodoLista.sig, x);
		return resul;
	}
	public boolean busqueda (int x) {
		return busqueda (inicio, x);
	 }
	static NodoLista insertar (NodoLista nodoLista, int dato) {
		NodoLista resul = nodoLista;
		if (nodoLista != null) 
			if (nodoLista.clave < dato)
				nodoLista.sig = insertar (nodoLista.sig, dato);
			else if (nodoLista.clave > dato)
				resul = new NodoLista (dato, nodoLista);
			else System.out.println ("la clave ya existe");
		else resul = new NodoLista (dato, nodoLista);
		return resul;
	}
	public void insertar (int dato) {
		inicio = insertar (inicio, dato);
	}
	public void insertarIterativo (int dato) {
		NodoLista anterior, actual, aux;
		boolean encontrado;

		anterior = inicio;
		actual = inicio;
		encontrado = false;
		while ((actual != null) && !encontrado)  
			if (actual.clave >= dato) 
				encontrado = true;
			else {
				anterior = actual;
				actual = actual.sig;
			 }
		if (actual == null) {
			aux = new NodoLista (dato, null);
			if (inicio == null)
				inicio = aux;
			else anterior.sig = aux;
		 }
		else if (encontrado && (actual.clave > dato)) {
					aux = new NodoLista (dato, actual);
					if (inicio == actual) 
						inicio = aux;
					else anterior.sig = aux;
				 }
				else System.out.println ("Error. Elemento repetido");
	 }
	static NodoLista eliminar (NodoLista nodoLista, int dato) {
		NodoLista resul = nodoLista;
		if (nodoLista != null) 
			if (nodoLista.clave < dato)
				nodoLista.sig = eliminar (nodoLista.sig, dato);
			else if (nodoLista.clave > dato)
					 System.out.println ("la clave no existe");
			else resul = nodoLista.sig;
		else System.out.println ("la clave no existe");
		return resul;
	}
	public void eliminar (int dato) {
		inicio = eliminar (inicio, dato);
	}
	public void cargarLista () throws NumberFormatException, IOException {
		int cantidad, i, x;
		BufferedReader lineaEntrada = new BufferedReader(new InputStreamReader(System.in));
		    
		inicio = null;
		        
		System.out.println ("Numero de elementos de la lista: ");
		cantidad = Integer.parseInt(lineaEntrada.readLine());
		for (i = 0; i < cantidad; i++) {
		    x = Integer.parseInt(lineaEntrada.readLine());
		    insertar (x);
		}
	}
	public void imprimirLista () {
		NodoLista aux;
		
		aux = inicio;
		System.out.print (nombre+": ");
		while (aux != null) {
			System.out.print (aux.clave + " ");
			aux = aux.sig;
		}
		System.out.println ("FIN");
	}
}
