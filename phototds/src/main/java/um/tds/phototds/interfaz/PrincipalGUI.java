package um.tds.phototds.interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Foto;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Scrollbar;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;

public class PrincipalGUI extends JFrame {
	//Constantes
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 150;
	private static final int DEFAULT_SCROLL = 16;
	private static final int DEFAULT_FOTOS = 4;

	//Atributos
	private JFrame framePrincipal;
	private JTextField txtBuscador;

	/**
	 * Create the frame.
	 */
	public PrincipalGUI() {
		initialize();
	}

	public void mostrarVentana() {
		framePrincipal.setResizable(false);
		framePrincipal.setLocationRelativeTo(null);
		framePrincipal.setVisible(true);
	}

	private void initialize() {
		framePrincipal = new JFrame("Photo TDS");
		framePrincipal.setBounds(100, 100, 537, 600);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelGeneral = new JPanel();
		framePrincipal.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		JPanel panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);

		//Panel con las fotos
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));

		//Cargo las fotos en el panelListaFotos
		JPanel panelListaFotos = cargarFotos();

		//Añado el scrollbar
		JScrollPane scrollListaFotos = new JScrollPane(panelListaFotos);
		scrollListaFotos.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);

		//Meto el scrollbar en el panel central
		panelCentral.add(scrollListaFotos);


		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panelTitulo = new JPanel();
		panelNorte.add(panelTitulo);

		JLabel lblTitulo = new JLabel("Photo TDS");
		lblTitulo.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		panelTitulo.add(lblTitulo);

		JPanel panelPadding0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelPadding0.getLayout();
		flowLayout.setHgap(20);
		panelNorte.add(panelPadding0);

		JPanel panelBtnAdd = new JPanel();
		panelNorte.add(panelBtnAdd);

		JButton btnAddFoto = new JButton("+");
		btnAddFoto.addActionListener(e -> {
			AddFotoGUI w = new AddFotoGUI(this.framePrincipal);
			w.setVisible(true);
			this.framePrincipal.revalidate();
			this.framePrincipal.repaint();
		});
		panelBtnAdd.add(btnAddFoto);

		JPanel panelBuscador = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelBuscador.getLayout();
		flowLayout_2.setVgap(10);
		panelNorte.add(panelBuscador);

		txtBuscador = new JTextField();
		panelBuscador.add(txtBuscador);
		txtBuscador.setColumns(10);

		JButton btnLupa = new JButton();
		try {
			Image imgLupa = ImageIO.read(getClass().getResource("E:\\UNIVERSIDAD\\3.º\\C1\\TDS\\workspace\\lupa.bmp"));
			btnLupa.setIcon(new ImageIcon(imgLupa));
		} catch (Exception ex) {
			System.out.println(ex);
		}	
		panelBuscador.add(btnLupa);

		JPanel panelPadding1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelPadding1.getLayout();
		flowLayout_1.setHgap(20);
		panelNorte.add(panelPadding1);

		JPanel panelAjustes = new JPanel();
		panelNorte.add(panelAjustes);

		JLabel lblFotoPerfil = new JLabel("Foto");
		lblFotoPerfil.setVerticalAlignment(SwingConstants.BOTTOM);
		lblFotoPerfil.setIcon(new ImageIcon("E:\\UNIVERSIDAD\\3.º\\C1\\TDS\\workspace\\fotoPerfil.ico"));
		panelAjustes.add(lblFotoPerfil);

		JButton btnNewButton = new JButton("Ajustes");
		panelAjustes.add(btnNewButton);
	}

	private JPanel cargarFotos() {

		//He tenido que insertar Foto ==> La interfaz conoce "ALGO" del dominio
		List<Foto> aux = Controlador.INSTANCE.getFotos();
		ArrayList<Foto> fotos = new ArrayList<Foto>(aux);
		
		int padding = 0;
		JPanel panelListaFotos = new JPanel();
		if(fotos.size() < DEFAULT_FOTOS) {
			panelListaFotos.setLayout(new GridLayout(DEFAULT_FOTOS, 0, 0, 0));
			padding = DEFAULT_FOTOS - fotos.size();
		} else{
			panelListaFotos.setLayout(new GridLayout(fotos.size(), 0, 0, 0));
		}

		//Recorremos la lista de fotos para pasarlas al gridLayout
		for(Foto f : fotos) {		
			JPanel panelEntradaFoto = new JPanel();
			panelEntradaFoto.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
			panelEntradaFoto.setLayout(new BoxLayout(panelEntradaFoto, BoxLayout.X_AXIS));
			
			JPanel panelFoto = new JPanel();
			panelEntradaFoto.add(panelFoto);

			BufferedImage foto;
			try {
				File fi = new File(f.getPath());
				foto = ImageIO.read(fi);
				JLabel picLabel = new JLabel();

				double[] size = new double[2];
				size[0] = foto.getWidth(null);
				size[1] = foto.getHeight(null);
				setProp(size);

				Image resizedImage;
				resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
				picLabel.setIcon(new ImageIcon(resizedImage));
				panelFoto.add(picLabel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			JPanel panelInfo = new JPanel();
			panelInfo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			panelEntradaFoto.add(panelInfo);
			panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));

			JPanel panelBotones = new JPanel();
			panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelInfo.add(panelBotones);

			JPanel panelBtnMG = new JPanel();
			panelBotones.add(panelBtnMG);

			JButton btnMG = new JButton("MG");
			panelBtnMG.add(btnMG);

			JPanel panelBtnComentarios = new JPanel();
			panelBotones.add(panelBtnComentarios);

			JButton btnComentarios = new JButton("Comentarios");
			panelBtnComentarios.add(btnComentarios);

			JPanel panelContadorMG = new JPanel();
			panelBotones.add(panelContadorMG);

			JLabel lblContadorMG = new JLabel(f.getMeGustas()+" Me gusta");
			panelContadorMG.add(lblContadorMG);

			JPanel panelPerfilUsuario = new JPanel();
			panelPerfilUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelInfo.add(panelPerfilUsuario);

			JLabel lblFotoUsuario = new JLabel("(Foto)");
			panelPerfilUsuario.add(lblFotoUsuario);

			JLabel lblNombreUsuario = new JLabel(Controlador.INSTANCE.getUsuarioActual()+"\n"
			+f.getTitulo()+"\n    "+f.getDescripcion());
			panelPerfilUsuario.add(lblNombreUsuario);

			panelListaFotos.add(panelEntradaFoto);//Añadimos la foto al gridLayout
		}
		while(padding > 0) {
			panelListaFotos.add(new JPanel());
			padding--;
		}
		return panelListaFotos;
	}

	private static void setProp(double[] size) {//SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0]; 
		newSize[1] = size[1]; 		
		double prop;
		if(size[0]>= DEFAULT_X) {
			prop = size[0]/size[1];
			newSize[0] = DEFAULT_X;
			newSize[1] = DEFAULT_X/prop;
			//			System.out.println("1:new size = ["+newSize[0]+"]["+size[1]+"]");
		}
		if(newSize[1]>= DEFAULT_Y){
			prop = size[1]/size[0];
			newSize[1] = DEFAULT_Y;
			newSize[0] = DEFAULT_Y/prop;			
			//			System.out.println("2 new size = ["+size[0]+"]["+size[1]+"]");
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
		//		System.out.println("new size = ["+size[0]+"]["+size[1]+"]");
	}
}
