package Utils.tadGrafos.grafos;

public class CaminoMinimo {
	private int[][] Pesos;
	private int[] ultimo;
	private int[] D;
	private boolean[] F;
	private int s, n; // v�rtice origen y n�mero de v�rtices

	public CaminoMinimo(GrafoMA gp, int origen) {
		n = gp.obtenerNumVertices();
		s = origen;
		Pesos = gp.pesos;
		ultimo = new int[n];
		D = new int[n];
		F = new boolean[n];
	}

	public void caminoMinimos() {
		// valores iniciales
		for (int i = 0; i < n; i++) {
			F[i] = false;
			D[i] = Pesos[s][i];
			ultimo[i] = s;
		}
		F[s] = true;
		D[s] = 0;
		// Pasos para marcar los n-1 v�rtices
		for (int i = 1; i < n; i++) {
			int v = minimo(); /*
								 * selecciona v�rtice no marcado de menor distancia
								 */
			F[v] = true;
			// actualiza distancia de v�rtices no marcados
			for (int w = 1; w < n; w++)
				if (!F[w])
					if ((D[v] + Pesos[v][w]) < D[w]) {
						D[w] = D[v] + Pesos[v][w];
						ultimo[w] = v;
					}
		}
	}

	private int minimo() {
		int mx = GrafoMA.INFINITO;
		int v = 1;
		for (int j = 1; j < n; j++)
			if (!F[j] && (D[j] <= mx)) {
				mx = D[j];
				v = j;
			}
		return v;
	}
	
	public void recuperaCamino(int v) {
		int anterior = ultimo[v];
		if (v != s) {
			recuperaCamino(anterior); // vuelve al �ltimo del �ltimo
			System.out.print(" -> V" + v);
		} else
			System.out.print("V" + s);
	}
}
