package plotpkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraficoFinestra {
	private Funzione f;				// FUNZIONE GRAFICO
	private JFrame Finestra;		// FINESTRA
	private Graphics2D g;			// BOX GRAFICO
	private Container contenitore;	// CONTENITORE
	private JPanel					// PANNELLI
		cmdsPanel,
		graphPanel;
	private JButton drawBtn;		// BOTTONE
	private JLabel					// ETICHETTE
		labX_MIN,
		labX_MAX,
		labX_U,
		labY_MIN,
		labY_MAX,
		labY_U;
	private JTextField				// CASELLE DI TESTO
		txtX_MIN,
		txtX_MAX,
		txtX_U,
		txtY_MIN,
		txtY_MAX,
		txtY_U;
	public GraficoFinestra() {
		// --------- SETTING DELLA FINESTRA ---------
		// CREAZIONE FINESTRA
		Finestra = new JFrame("Plotting :: f(x) = [(x-1)  e^{-x/3}] + 3");
		// SETTING CHIUSURA FINESTRA
		Finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ICONA FINESTRA
		Finestra.setIconImage(Toolkit.getDefaultToolkit().getImage("img/chart2.png"));
		// DIMENSIONE E POSIZIONE FINESTRA FINESTRA
		Finestra.setBounds(100, 100, 800, 600);
		// POSSIBILITÀ RIDIMENSIONAMENTO FINESTRA
		Finestra.setResizable(true);

		// ETICHETTE
		labX_MIN = new JLabel("x min");
		labX_MAX = new JLabel("x max");
		labX_U = new JLabel("x (unità)");
		labY_MIN = new JLabel("y min");
		labY_MAX = new JLabel("y max");
		labY_U = new JLabel("y (unità)");

		// CASELLE DI TESTO
		txtX_MIN = new JTextField("-1.5");
		txtX_MAX = new JTextField("8.5");
		txtX_U = new JTextField("0.5");
		txtY_MIN = new JTextField("-1.2");
		txtY_MAX = new JTextField("4.0");
		txtY_U = new JTextField("0.4");

		// ALLINEAMENTO A DESTRA CONTENUTO CASELLE DI TESTO
		txtX_MIN.setHorizontalAlignment(JTextField.RIGHT);
		txtX_MAX.setHorizontalAlignment(JTextField.RIGHT);
		txtX_U.setHorizontalAlignment(JTextField.RIGHT);
		txtY_MIN.setHorizontalAlignment(JTextField.RIGHT);
		txtY_MAX.setHorizontalAlignment(JTextField.RIGHT);
		txtY_U.setHorizontalAlignment(JTextField.RIGHT);

		// BOTTONE
		drawBtn = new JButton("GO");
		drawBtn.setBackground(new Color(0x7896FF));		//255, 231, 238
		// EVENTO BOTTONE PREMUTO
		drawBtn.addActionListener(e -> disegnaGrafico());

		// PANNELLI E CONTENITORI
		contenitore = new Container();	// OGGETTO CONTENITORE
		cmdsPanel = new JPanel();		// PANNELLO IMPOSTAZIONI
		graphPanel = new JPanel();		// SPAZIO PER IL GRAFICO

		// SETTING PANNELLI
		cmdsPanel.setPreferredSize(new Dimension(0, 27));
		cmdsPanel.setBackground(new Color(0xB3CBFF));	//255, 204, 221
		graphPanel.setBackground(new Color(0xE6ECFF));	// 255, 81, 137

		// AGGIUNTA DEI DUE PANNELLI NEL CONTENITORE
		contenitore.setLayout(new BorderLayout());
		contenitore.add(cmdsPanel, BorderLayout.NORTH);
		contenitore.add(graphPanel, BorderLayout.CENTER);

		// AGGIUNTA ELEMENTI AL PANNELLO IMPOSTAZIONI
		cmdsPanel.setLayout(new GridLayout(1, 13));
		cmdsPanel.add(drawBtn);
		cmdsPanel.add(txtX_MIN);
		cmdsPanel.add(labX_MIN);
		cmdsPanel.add(txtX_MAX);
		cmdsPanel.add(labX_MAX);
		cmdsPanel.add(txtX_U);
		cmdsPanel.add(labX_U);
		cmdsPanel.add(txtY_MIN);
		cmdsPanel.add(labY_MIN);
		cmdsPanel.add(txtY_MAX);
		cmdsPanel.add(labY_MAX);
		cmdsPanel.add(txtY_U);
		cmdsPanel.add(labY_U);

		Finestra.add(contenitore);	// AGGIUNTA CONTENITORE ALLA FINESTRA
		Finestra.setVisible(true);	// VISUALIZZAZIONE FINESTRA
	}
	private void disegnaGrafico() {
		final int
			W = graphPanel.getWidth(),	// LARGHEZZA PANNELLO
			H = graphPanel.getHeight(),	// ALTEZZA PANNELLO
			d = 10;						// DIAMETRO PALLINI
		final double
			X_MIN = Double.parseDouble(txtX_MIN.getText()),
			X_MAX = Double.parseDouble(txtX_MAX.getText()),
			X_U = Double.parseDouble(txtX_U.getText()),
			Y_MIN = Double.parseDouble(txtY_MIN.getText()),
			Y_MAX = Double.parseDouble(txtY_MAX.getText()),
			Y_U = Double.parseDouble(txtY_U.getText());

		g = (Graphics2D) graphPanel.getGraphics();
		graphPanel.update(g);

		// DISEGNO LO SFONDO
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, W, H);

		f = new Funzione(W, H, X_MIN, X_MAX, Y_MIN, Y_MAX);

		// GRIGLIA VERTICALE
		for (double t = X_MIN; t <= X_MAX; t += X_U) {
			g.setColor(new Color(0xE6E6FF));
			g.drawLine(f.pixelX(t), 0, f.pixelX(t), H);
			g.setColor(new Color(0x4D4D4D));
			g.drawLine(f.pixelX(t), f.pixelY(0) - d / 2, f.pixelX(t), f.pixelY(0) + d / 2);
			g.drawString("%.1f".formatted(t), f.pixelX(t) - d, f.pixelY(0) - d);
		}
		// GRIGLIA ORIZONTALE
		for (double t = Y_MIN; t <= Y_MAX; t += Y_U) {
			g.setColor(new Color(0xE6ECFF));
			g.drawLine(0, f.pixelY(t), W, f.pixelY(t));
			g.setColor(new Color(0x4D4D4D));
			g.drawLine(f.pixelX(0) - d / 2, f.pixelY(t), f.pixelX(0) + d / 2, f.pixelY(t));
			g.drawString("%.1f".formatted(t), f.pixelX(0) + d, f.pixelY(t) + d / 2);
		}
		// ASSI CARTESIANI
		g.setColor(new Color(0x00254D));
		g.drawLine(0, f.pixelY(0), W, f.pixelY(0));		// asse-x
		g.drawLine(f.pixelX(0), 0, f.pixelX(0), H);		// asse-y

		// GRAFICO DELLA FUNZIONE
		g.setColor(new Color(0x009CDF));
		g.drawPolyline(f.arrayPixelX(), f.arrayPixelY(), W);

		// INTERSEZIONE asse-x
		g.setColor(Color.RED);
		g.fillOval(f.pixelX(-1.088) - d / 2, f.pixelY(0) - d / 2, d, d);
		g.drawString("f(x) = 0", f.pixelX(-1.088), f.pixelY(0) + d * 2);

		// INTERSEZIONE asse-y
		g.setColor(Color.RED);
		g.fillOval(f.pixelX(0) - d / 2, f.pixelY(f.y(0)) - d / 2, d, d);
		g.drawString("f(0)", f.pixelX(0) - d * 2, f.pixelY(f.y(0)) - d);

		// MASSIMO-ASSOLUTO
		g.setColor(Color.RED);
		g.fillOval(f.pixelX(4) - d / 2, f.pixelY(f.y(4)) - d / 2, d, d);
		g.drawString("Max assoluto", f.pixelX(4) - d * 2, f.pixelY(f.y(4)) + d * 2);

		// FLESSO
		g.setColor(Color.RED);
		g.fillOval(f.pixelX(7) - d / 2, f.pixelY(f.y(7)) - d / 2, d, d);
		g.drawString("Flesso", f.pixelX(7) - d * 2, f.pixelY(f.y(7)) + d * 2);

		// ASINTODO ORIZONTALE
		g.setColor(Color.RED);
		g.drawLine(f.pixelX(1.5), f.pixelY(3), f.pixelX(X_MAX), f.pixelY(3));
		g.drawString("Asintodo Oriz", f.pixelX(3.1), f.pixelY(3) + d * 2);
	}
	public static void main(String[] args) {
		new GraficoFinestra();
	}
}