package plotpkg;

// f(x) = 3 + [x-1] * exp(-x/3)
public class Funzione {
	private final int		// -- DIMENSIONI IN PIXEL DEL PANNELLO --
		W,					// ALTEZZA AREA GRAFICA
		H;					// LARGHEZZA AREA GRAFICA
	private final double	// ------- RANGE DEL DIAGRAMMA ----------
		X_MIN,				// VALORE MINIMO ASSE x
		X_MAX,				// VALORE MASSIMO ASSE x
		Y_MIN,				// VALORE MINIMO ASSE y
		Y_MAX;				// VALORE MASSIMO ASSE y

	// COSTRUTTORE
	public Funzione(int W, int H, double X_MIN, double X_MAX, double Y_MIN, double Y_MAX) {
		this.W = W;
		this.H = H;
		this.X_MIN = X_MIN;
		this.X_MAX = X_MAX;
		this.Y_MIN = Y_MIN;
		this.Y_MAX = Y_MAX;
	}
	// VALUTA IL VALORE DI y IN BASE AL VALORE DI x CIOÈ y = f(x)
	public double y(double x) {
		return (3 + (x - 1) * Math.exp(-x / 3));
	}
	// RESTITUISCE ARRAY COORDINATE x DI PIXEL PER TUTTA LA LUNGHEZZA PANNELLO
	public int[] arrayPixelX() {
		int[] pX = new int[W];
		double x = X_MIN;
		for (int i = 0; i < W; i++) {
			pX[i] = pixelX(x);
			x += (X_MAX - X_MIN) / (W -1);
		}
		return pX;
	}
	// RESTITUISCE ARRAY COORDINATE y DI PIXEL PER TUTTA L'ALTEZZA PANNELLO
	public int[] arrayPixelY() {
		int[] pY = new int[W];
		double x = X_MIN;
		for (int i = 0; i < W; i++) {
			pY[i] = pixelY(y(x));
			x += (X_MAX - X_MIN) / (W -1);
		}
		return pY;
	}
	// PARTENDO DAL VALORE x RESTITUISCE LA RELATIVA COORDINATA SUL PANNELLO
	public int pixelX(double x) {
		return (int) (W * (x - X_MIN) / (X_MAX - X_MIN));
	}
	// PARTENDO DAL VALORE y RESTITUISCE LA RELATIVA COORDINATA SUL PANNELLO
	public int pixelY(double y) {
		return (int) (H * (y - Y_MAX) / (Y_MIN - Y_MAX));
	}
}