package Entidades.tadCola;


class NodoCola 
{
	// Atributos accesibles desde otras rutinas del paquete
	int dato;
	NodoCola siguiente;
		
	// Constructores

	NodoCola (int elemento, NodoCola n)
	{ 
		dato = elemento; 
		siguiente = n; 
	}
}
