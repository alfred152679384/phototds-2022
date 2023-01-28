package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Foto;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

public class TopMgGUI extends JDialog {
	private static final long serialVersionUID = 1L;
	
	//Constantes
	private static final int DEFAULT_FOTOS = 2;
	
	//Atributos
	private final JPanel panelCentral = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public TopMgGUI(JFrame owner) {
		super(owner, "Fotos Top Me Gusta", true);
		setBounds(100, 100, 450, 600);
		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		
		initialize();
	}

	private void initialize() {
		panelCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		List<Foto> fotos = Controlador.INSTANCE.getUsuarioActual().getTopMG();
		
		JPanel panelListaFotos = new JPanel();
		int padding = 0;
		if(fotos.size() < DEFAULT_FOTOS) {
			panelListaFotos.setLayout(new GridLayout(DEFAULT_FOTOS, 0, 0, 0));
			padding = DEFAULT_FOTOS - fotos.size();
		}else {
			panelListaFotos.setLayout(new GridLayout(fotos.size(), 0, 0, 0));
		}
			
		for(Foto f : fotos) {
			JPanel panelEntradaFoto = new JPanel();
			panelEntradaFoto.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0),
					new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
			panelEntradaFoto.setLayout(new BoxLayout(panelEntradaFoto, BoxLayout.X_AXIS));
			
			JPanel panelFoto = new JPanel();
			panelEntradaFoto.add(panelFoto);
			
			//Dimensionamos la foto
			JLabel picLabel = new JLabel("Picture");
			try {
				picLabel = PrincipalGUI.crearLabelFoto(f.getPath(), 200, 150);
			}catch(IOException e) {
				System.err.println("Excepción: Error al cargar imagen");
			}
			panelFoto.add(picLabel);
			
			JPanel panelMeGustas = new JPanel();
			panelEntradaFoto.add(panelMeGustas);
			
			JLabel lblMG = new JLabel(f.getMeGustas()+" Me gustas");
			panelMeGustas.add(lblMG);
			
			panelListaFotos.add(panelEntradaFoto);
		}
		
		while(padding > 0) {
			panelListaFotos.add(new JPanel());
			padding--;
		}
		
		JScrollPane scroll = new JScrollPane(panelListaFotos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		panelCentral.add(scroll);
		
	}
}
