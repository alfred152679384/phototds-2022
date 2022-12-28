package um.tds.phototds.interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Foto;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
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
	private static final int LUPA_SIZE = 10;
	private static final int OPTIONS_SIZE = 15;
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 150;
	private static final int DEFAULT_SCROLL = 16;
	private static final int DEFAULT_FOTOS = 4;
	private static final int FRAME_SIZE = 600;
	private static final int CELL_SIZE = FRAME_SIZE/3-12;
	

	//Atributos
	private JFrame framePrincipal;
	private JTextField txtBuscador;
	private boolean pushed;

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
		framePrincipal.setBounds(100, 100, FRAME_SIZE, FRAME_SIZE);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pushed = false;

		JPanel panelGeneral = new JPanel();
		framePrincipal.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		JPanel panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);

		//Panel con las fotos
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

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

		JButton btnAddFoto = new JButton("+");//cambiar para poner una foto que esté chulita
		btnAddFoto.addActionListener(e -> {
			AddFotoGUI w = new AddFotoGUI(this.framePrincipal);
			w.setVisible(true);
			framePrincipal.setVisible(false);
		});
		panelBtnAdd.add(btnAddFoto);

		JPanel panelBuscador = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelBuscador.getLayout();
		flowLayout_2.setVgap(10);
		panelNorte.add(panelBuscador);

		txtBuscador = new JTextField();
		panelBuscador.add(txtBuscador);
		txtBuscador.setColumns(10);

		BufferedImage lupa;
		try {
			File fi = new File("resources\\lupa.png");
			lupa = ImageIO.read(fi);
			double[] size = new double[2];
			size[0] = lupa.getWidth();
			size[1] = lupa.getHeight();
			setProp(size, LUPA_SIZE, LUPA_SIZE);
			
			Image resizedLupa = lupa.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(resizedLupa);
			JButton btnLupa = new JButton(icon);
//			btnLupa.setBackground(Color.WHITE);
			panelBuscador.add(btnLupa);
		}catch(IOException e) {
			e.printStackTrace();
		}


		JPanel panelAjustes = new JPanel();
		panelNorte.add(panelAjustes);

		JButton btnFotoPerfil = new JButton("Foto Perfil");
		btnFotoPerfil.addActionListener(e ->{
			if(!pushed) {
				pushed = true;
				panelCentral.remove(scrollListaFotos);
				cargarPerfilUsuario(panelCentral);
				JButton btnAddAlbum = new JButton("A+");
				panelBtnAdd.add(btnAddAlbum);
				framePrincipal.revalidate();
				framePrincipal.repaint();
			}
		});
		panelAjustes.add(btnFotoPerfil);

		BufferedImage options;//Boton de opciones
		try {
			File fi = new File("resources\\options.png");
			options = ImageIO.read(fi);
			double[] size = new double[2];
			size[0] = options.getWidth();
			size[1] = options.getHeight();
			setProp(size, OPTIONS_SIZE, OPTIONS_SIZE);
			
			Image resizedOptions = options.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(resizedOptions);
			JButton btnOptions = new JButton(icon);
			panelAjustes.add(btnOptions);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void cargarPerfilUsuario(JPanel panelCentral) {
		JPanel panelInfoUser = new JPanel();
		panelCentral.add(panelInfoUser);
		panelInfoUser.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFotoPerfil = new JPanel();
		panelInfoUser.add(panelFotoPerfil, BorderLayout.WEST);
		
		JLabel lblFotoPerfil = new JLabel("(Foto Perfil) not button");
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		panelFotoPerfil.add(lblFotoPerfil);
		
		JPanel panelInfo = new JPanel();
		panelInfoUser.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		
		JPanel panelUsername = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelUsername.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelUsername);
		
		JLabel lblUsername = new JLabel(Controlador.INSTANCE.getCorreoUsuario());
		panelUsername.add(lblUsername);
		
		JPanel panelPadding0 = new JPanel();
		panelUsername.add(panelPadding0);
		
		JButton btnEditPerfil = new JButton("Editar perfil");
		btnEditPerfil.setForeground(Color.WHITE);
		btnEditPerfil.setBackground(new Color(0, 0, 255));
		btnEditPerfil.setOpaque(true);
		panelUsername.add(btnEditPerfil);
		
		JPanel panelEstadisticas = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelEstadisticas.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelEstadisticas);
		
		JLabel lblNumPublics = new JLabel(Controlador.INSTANCE.getNumPublicaciones()+" publicaciones");
		panelEstadisticas.add(lblNumPublics);
		
		JPanel panelPadding1 = new JPanel();
		panelEstadisticas.add(panelPadding1);
		
		JLabel lblNumSeguidores = new JLabel(Controlador.INSTANCE.getNumSeguidores()+" seguidores");
		panelEstadisticas.add(lblNumSeguidores);
		
		JPanel panelPadding2 = new JPanel();
		panelEstadisticas.add(panelPadding2);
		
		JLabel lblNumSeguidos = new JLabel(Controlador.INSTANCE.getNumSeguidos()+" seguidos");
		panelEstadisticas.add(lblNumSeguidos);
		
		JPanel panelNombre = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelNombre.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelNombre);
		
		JLabel lblNombre = new JLabel(Controlador.INSTANCE.getNombreUsuario());
		panelNombre.add(lblNombre);
		
		JPanel panelFotos = new JPanel();
		panelCentral.add(panelFotos);
		panelFotos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFotoAlbumes = new JPanel();
		panelFotos.add(panelFotoAlbumes, BorderLayout.NORTH);
		
		JButton btnFotos = new JButton("FOTOS");
		btnFotos.setBackground(Color.WHITE);
		btnFotos.setOpaque(true);
		panelFotoAlbumes.add(btnFotos);
		
		JButton btnAlbumes = new JButton("ALBUMES");
		btnAlbumes.setBackground(Color.WHITE);
		btnAlbumes.setOpaque(true);
		panelFotoAlbumes.add(btnAlbumes);
		
		cargarFotosPerfil(panelFotos);
	}
	
	private void cargarFotosPerfil(JPanel panelFotos) {
		List<Foto> aux = Controlador.INSTANCE.getFotosPrincipal();//TODO Cambiar por getFotosPerfil
		ArrayList<Foto> fotos = new ArrayList<Foto>(aux);
		int cont = 0;
		int width = CELL_SIZE;
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;
		BufferedImage foto;
		
		try {//Cargamos las imágenes en el modelo
			//new image
			for(Foto f: fotos) {
				File fi= new File(f.getPath());
				foto = ImageIO.read(fi);
				
				double[] size = new double[2];
				size[0] = foto.getWidth(null);
				size[1] = foto.getHeight(null);
				setProfileProp(size, width);
				
				Image resizedImage;
				resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(resizedImage);
				
				model.add(cont, icon);
				cont++;	
			}			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		lista = new JList<>(model);
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setFixedCellWidth(width);
		lista.setFixedCellHeight(width);
		lista.setBorder(new LineBorder(Color.black));
		
		JScrollPane scrollFotos = new JScrollPane(lista);
		panelFotos.add(scrollFotos, BorderLayout.CENTER);
	}

	private JPanel cargarFotos() {
		//He tenido que insertar Foto ==> La interfaz conoce "ALGO" del dominio
		List<Foto> aux = Controlador.INSTANCE.getFotosPrincipal();
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
				setProp(size, DEFAULT_X, DEFAULT_Y);

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

			JLabel lblNombreUsuario = new JLabel(Controlador.INSTANCE.getUsuarioActual());
			panelPerfilUsuario.add(lblNombreUsuario);

//			JPanel panelTituloFoto = new JPanel();
//			panelTituloFoto.setLayout(new FlowLayout(FlowLayout.LEFT));
//			panelInfo.add(panelTituloFoto);
			
			JTextArea txtTitulo = new JTextArea(" "+f.getTitulo()+"\n\n "+f.getDescripcion());
			txtTitulo.setBackground(new Color(240, 240, 240));
			txtTitulo.setEditable(false);
			txtTitulo.setLineWrap(true);
			txtTitulo.setWrapStyleWord(true);
			panelInfo.add(txtTitulo);
			
			panelListaFotos.add(panelEntradaFoto);//Añadimos la foto al gridLayout
		}
		while(padding > 0) {
			panelListaFotos.add(new JPanel());
			padding--;
		}
		return panelListaFotos;
	}
	
	private static void setProp(double[] size, double x, double y) {//SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0]; 
		newSize[1] = size[1]; 		
		double prop;
		if(size[0]>= x) {
			prop = size[0]/size[1];
			newSize[0] = x;
			newSize[1] = x/prop;
		}
		if(newSize[1]>= y){
			prop = size[1]/size[0];
			newSize[1] = y;
			newSize[0] = y/prop;			
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}
	
	
	private static void setProfileProp(double[] size, double w) {//SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0]; 
		newSize[1] = size[1]; 		
		double prop;
		if(size[1]>= size[0]) {//fotos verticales
			prop = size[0]/size[1];
			newSize[0] = w;
			newSize[1] = w/prop;
		}
		else {//fotos horizontales
			prop = size[1]/size[0];
			newSize[1] = w;
			newSize[0] = w/prop;			
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}
}
