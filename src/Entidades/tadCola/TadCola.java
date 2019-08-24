package Entidades.tadCola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TadCola implements Cola {
	private NodoCola principio;
	private NodoCola fin;
	
	public TadCola () {
		principio = null;
		fin = null;
	}

	public boolean colaVacia () {
		return principio==null;	
	}
	public void eliminarCola () {
		principio = null;
		fin = null;
	}
	public int primero () throws ColaVacia {
    	if (colaVacia ())
    		throw new ColaVacia ("La cola est� vac�a");
    	return principio.dato;
	}
	public void encolar (int x) {
        NodoCola aux;
        aux = new NodoCola(x,null);
        if (principio == null) {
        	principio = aux;
        	fin = aux;
        }
        else {
        	fin.siguiente = aux;
        	fin = aux;
        }
	}
	public int desencolar () throws ColaVacia {
        int resultado;
        if (colaVacia ()) {  
          throw new ColaVacia ("Desencolar: La cola est� vac�a");
        }
        resultado = principio.dato;
        principio = principio.siguiente;
        if (principio == null)
        	fin = null;
        return resultado;
		
	}
	
	public void quitarPrimero () throws ColaVacia {
	    if (colaVacia ()) {  
	    	throw new ColaVacia ("Quitar primero: La cola est� vac�a");
	    }
	    principio = principio.siguiente;
	    if (principio == null)
	    	fin = null;
	}
	
	public void mostrarEstadoCola () {
		System.out.println("Estado de la cola:");
		System.out.println("N�mero de elementos: "+this.numElemCola());
		if(!colaVacia()) {
	        System.out.println("Primer elemento - Clave: "+principio.dato);
	        System.out.println("�ltimo elemento - Clave: "+fin.dato);
		}
    }
	
	public void imprimirCola () {
        NodoCola aux;
        System.out.print ("Estado de la cola: ");
        aux = principio;
        while (aux != null) {
          System.out.print (aux.dato + " ");
          aux = aux.siguiente;
        }
        System.out.println ();
      }
	
	public void leerCola () throws NumberFormatException, IOException {
    	int cantidad, i, x;
        BufferedReader linea_entrada = new BufferedReader(new InputStreamReader(System.in));
    
        System.out.println ("Numero de elementos de la cola: ");
        cantidad = Integer.parseInt(linea_entrada.readLine());
        for (i = 0; i < cantidad; i++) {
           x = Integer.parseInt(linea_entrada.readLine());;
           encolar (x);
         }
    }
	
	public int numElemCola () {
        NodoCola aux;	
        int resul;
        
          aux = principio;
          resul = 0;
          while (aux != null) {
        	++resul;
        	aux = aux.siguiente;
          }
          return resul;
        }
	
	public void invertirCola () throws ColaVacia {
		int elem;
		if (!colaVacia()) {
			elem = desencolar();	
			invertirCola();
			encolar(elem);
		}
	}
}
