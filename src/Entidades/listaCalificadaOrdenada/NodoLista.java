package Entidades.listaCalificadaOrdenada;

public class NodoLista {
	public int clave;
	public NodoLista sig;
	public NodoLista (int x, NodoLista n) {
		clave = x;
		sig = n;
	}
}

