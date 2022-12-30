package um.tds.phototds.interfaz;

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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;

public class PrincipalGUI extends JFrame {
	//Necesario para que no haya warnings
	private static final long serialVersionUID = 1L;
	// Constantes
	private static final int LUPA_SIZE = 10;
	private static final int OPTIONS_SIZE = 15;
	private static final int FOTO_PERFIL_SIZE = 25;
	private static final int PERFIL_MODE_FOTO_PERFIL_SIZE = 25;
	private static final int LIKE_SIZE = 20;
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 150;
	private static final int DEFAULT_SCROLL = 16;
	private static final int DEFAULT_FOTOS = 4;
	private static final int FRAME_SIZE = 600;
	private static final int COMMENT_SIZE = 20;
	private static final int ENTRADA_FOTO_PERFIL_SIZE = 15;
	private static final int CELL_SIZE = FRAME_SIZE / 3 - 12;
	private static final String LUPA_PATH = "resources\\lupa.png";
	private static final String OPTIONS_PATH = "resources\\options.png";
	private static final String LIKE_PATH = "resources\\like.png";
	private static final String COMMENT_PATH = "resources\\comment.png";

	// Atributos
	private JFrame framePrincipal;
	private JTextField txtBuscador;
	private boolean mostrarPerfil;

	/**
	 * Create the frame. Constructor por defecto muestra la pantalla principal
	 */
	public PrincipalGUI() {
		this.mostrarPerfil = false;
		initialize();
	}

	// Constructor para mostrar la ventana de perfil de usuario
	public PrincipalGUI(boolean vistaPerfil) {
		this.mostrarPerfil = vistaPerfil;
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

		JPanel panelGeneral = new JPanel();
		framePrincipal.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		crearPanelNorte(panelGeneral);
		crearPanelCentral(panelGeneral);
	}

	private void crearPanelNorte(JPanel panelGeneral) {
		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		// Titulo: PhotoTDS
		JPanel panelTitulo = new JPanel();
		panelNorte.add(panelTitulo);

		JLabel lblTitulo = new JLabel("Photo TDS");
		lblTitulo.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		panelTitulo.add(lblTitulo);

		// Panel Padding
		JPanel panelPadding0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelPadding0.getLayout();
		flowLayout.setHgap(20);
		panelNorte.add(panelPadding0);

		// Botón para añadir Fotos
		JPanel panelBtnAdd = new JPanel();
		panelNorte.add(panelBtnAdd);

		JButton btnAddFoto = new JButton("+");
		addManejadorBtnAddFoto(btnAddFoto);
		panelBtnAdd.add(btnAddFoto);

		// Barra y botón de búsqueda
		JPanel panelBuscador = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelBuscador.getLayout();
		flowLayout_2.setVgap(10);
		panelNorte.add(panelBuscador);

		txtBuscador = new JTextField();
		txtBuscador.setColumns(10);
		panelBuscador.add(txtBuscador);

		// Botón lupa
		JButton btnLupa = new JButton("Search");
		try {
			btnLupa = crearBtnIcono(LUPA_PATH, LUPA_SIZE, LUPA_SIZE);
		} catch (IOException e) {
			System.err.println("Excepción: Imagen Boton Lupa ");
		}
		// TODO Añadir funcionalidad para buscar ==> Manejador para el btn Lupa
		panelBuscador.add(btnLupa);

		// Foto de Perfil y Ajustes
		JPanel panelAjustes = new JPanel();
		panelNorte.add(panelAjustes);

		// Foto de Perfil
		JButton btnFotoPerfil = new JButton("Profile");
		try {
			btnFotoPerfil = crearBtnIcono(Controlador.INSTANCE.getFotoPerfilUsuario(), FOTO_PERFIL_SIZE,
					FOTO_PERFIL_SIZE);
			btnFotoPerfil.setBackground(new Color(240, 240, 240));
		} catch (IOException e) {
			System.err.println("Excepción: Imagen Perfil usuario");
		}
		panelAjustes.add(btnFotoPerfil);

		// Botón de opciones
		JButton btnOptions = new JButton("Options");
		try {
			btnOptions = crearBtnIcono(OPTIONS_PATH, OPTIONS_SIZE, OPTIONS_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO Añadir manejador a este botón y crear la ventana de opciones
		panelAjustes.add(btnOptions);

	}

	private JButton crearBtnIcono(String path, int width, int height) throws IOException {
		// Abrimos la foto original
		BufferedImage original;
		File fi = new File(path);
		original = ImageIO.read(fi);

		// Redimensionamos la foto al tamaño pedido
		double[] size = new double[2];
		size[0] = original.getWidth();
		size[1] = original.getHeight();
		setProp(size, width, height);
		Image resizedImg = original.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);

		// Creamos botón con imagen dimensionada
		Icon icon = new ImageIcon(resizedImg);
		JButton btnImg = new JButton(icon);
		return btnImg;
	}

	private JLabel crearLabelFoto(String path, int width, int height) throws IOException {
		// Cargamos foto original
		BufferedImage foto;
		File fi = new File(path);
		foto = ImageIO.read(fi);

		// Redimensionamos
		double[] size = new double[2];
		size[0] = foto.getWidth(null);
		size[1] = foto.getHeight(null);
		setProp(size, DEFAULT_X, DEFAULT_Y);

		//Creamos el JLabel
		JLabel picLabel = new JLabel();
		Image resizedImage;
		resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
		picLabel.setIcon(new ImageIcon(resizedImage));
		return picLabel;
	}

	private void crearPanelCentral(JPanel panelGeneral) {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelGeneral.add(panelCentral, BorderLayout.CENTER);

		if (mostrarPerfil)
			cargarPerfilUsuario(panelCentral);
		else
			cargarPantallaPrincipal(panelCentral);
	}

	private void cargarPantallaPrincipal(JPanel panelCentral) {
		// Cargo las fotos en el panelListaFotos
		JPanel panelListaFotos = cargarFotos();

		// Añado el scrollbar
		JScrollPane scrollListaFotos = new JScrollPane(panelListaFotos);
		scrollListaFotos.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);

		// Meto el scrollbar en el panel central
		panelCentral.add(scrollListaFotos);

	}

	private void cargarPerfilUsuario(JPanel panelCentral) {
		// Informaciñon del Usuario
		JPanel panelInfoUser = new JPanel();
		panelCentral.add(panelInfoUser);
		panelInfoUser.setLayout(new BorderLayout(0, 0));

		// Foto de perfil
		JPanel panelFotoPerfil = new JPanel();
		panelInfoUser.add(panelFotoPerfil, BorderLayout.WEST);

		JLabel lblFotoPerfil = new JLabel("Profile Picture");
		try {
			lblFotoPerfil = crearLabelFoto(Controlador.INSTANCE.getFotoPerfilUsuario(), PERFIL_MODE_FOTO_PERFIL_SIZE, PERFIL_MODE_FOTO_PERFIL_SIZE);
		} catch (IOException e) {
			System.err.println("Profile picture fail");
		}
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		panelFotoPerfil.add(lblFotoPerfil);

		// Informacion
		JPanel panelInfo = new JPanel();
		panelInfoUser.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));

		// Username
		JPanel panelUsername = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelUsername.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelUsername);

		JLabel lblUsername = new JLabel(Controlador.INSTANCE.getCorreoUsuario());
		panelUsername.add(lblUsername);

		// Panel Padding
		JPanel panelPadding0 = new JPanel();
		panelUsername.add(panelPadding0);

		// Editar Perfil
		JButton btnEditPerfil = new JButton("Editar perfil");
		btnEditPerfil.setForeground(Color.WHITE);
		btnEditPerfil.setBackground(new Color(0, 0, 255));
		btnEditPerfil.setOpaque(true);
		// TODO Añadir funcionalidad a este boton
		panelUsername.add(btnEditPerfil);

		// Estadísticas del usuario
		JPanel panelEstadisticas = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelEstadisticas.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelEstadisticas);

		// Numero de publicaciones
		JLabel lblNumPublics = new JLabel(Controlador.INSTANCE.getNumPublicaciones() + " publicaciones");
		panelEstadisticas.add(lblNumPublics);

		JPanel panelPadding1 = new JPanel();
		panelEstadisticas.add(panelPadding1);

		// Numero de seguidores
		JLabel lblNumSeguidores = new JLabel(Controlador.INSTANCE.getNumSeguidores() + " seguidores");
		panelEstadisticas.add(lblNumSeguidores);

		JPanel panelPadding2 = new JPanel();
		panelEstadisticas.add(panelPadding2);

		// Numero de seguidos
		JLabel lblNumSeguidos = new JLabel(Controlador.INSTANCE.getNumSeguidos() + " seguidos");
		panelEstadisticas.add(lblNumSeguidos);

		// Nombre completo del Usuario
		JPanel panelNombre = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelNombre.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelNombre);

		JLabel lblNombre = new JLabel(Controlador.INSTANCE.getNombreUsuario());
		panelNombre.add(lblNombre);

		// Panel de seleccion y fotos del usuario
		JPanel panelFotos = new JPanel();
		panelCentral.add(panelFotos);
		panelFotos.setLayout(new BorderLayout(0, 0));

		// Panel de seleccion
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

		// Cargamos las fotos del usuario
		cargarFotosPerfil(panelFotos);
	}

	private void cargarFotosPerfil(JPanel panelFotos) {
		List<Foto> aux = Controlador.INSTANCE.getFotosPrincipal();// TODO Cambiar por getFotosPerfil
		ArrayList<Foto> fotos = new ArrayList<Foto>(aux);
		int cont = 0;
		int width = CELL_SIZE;
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;
		BufferedImage foto;

		try {// Cargamos las imágenes en el modelo dimensionadas
			for (Foto f : fotos) {
				File fi = new File(f.getPath());
				foto = ImageIO.read(fi);

				double[] size = new double[2];
				size[0] = foto.getWidth(null);
				size[1] = foto.getHeight(null);
				setProfileProp(size, width);

				Image resizedImage;
				resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(resizedImage);

				model.add(cont, icon);
				cont++;
			}
		} catch (IOException e) {
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

	private JPanel cargarFotos() {// Cargamos fotos de la pantalla principal
		// He tenido que insertar Foto ==> La interfaz conoce "ALGO" del dominio
		List<Foto> aux = Controlador.INSTANCE.getFotosPrincipal();
		ArrayList<Foto> fotos = new ArrayList<Foto>(aux);

		int padding = 0;
		JPanel panelListaFotos = new JPanel();
		if (fotos.size() < DEFAULT_FOTOS) {
			panelListaFotos.setLayout(new GridLayout(DEFAULT_FOTOS, 0, 0, 0));
			padding = DEFAULT_FOTOS - fotos.size();
		} else {
			panelListaFotos.setLayout(new GridLayout(fotos.size(), 0, 0, 0));
		}

		// Recorremos la lista de fotos para pasarlas al gridLayout
		for (Foto f : fotos) {
			JPanel panelEntradaFoto = new JPanel();
			panelEntradaFoto.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0),
					new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
			panelEntradaFoto.setLayout(new BoxLayout(panelEntradaFoto, BoxLayout.X_AXIS));

			JPanel panelFoto = new JPanel();
			panelEntradaFoto.add(panelFoto);

			// Dimensionamos la foto
			JLabel picLabel = new JLabel("Picture");
			try {// Metemos la imagen dimensionada en la entrada
				picLabel = crearLabelFoto(f.getPath(), DEFAULT_X, DEFAULT_Y);
			} catch (IOException e1) {
				System.err.println("Excepción: Fallo al cargar imagen " + f.getPath());
			}
			panelFoto.add(picLabel);

			// Añadimos informacion de la foto
			JPanel panelInfo = new JPanel();
			panelInfo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			panelEntradaFoto.add(panelInfo);
			panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));

			JPanel panelBotones = new JPanel();
			panelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelInfo.add(panelBotones);

			// Botón Me Gusta
			JPanel panelBtnMG = new JPanel();
			panelBotones.add(panelBtnMG);

			JButton btnMG = new JButton("Like");
			try {
				btnMG = crearBtnIcono(LIKE_PATH, LIKE_SIZE, LIKE_SIZE);
			} catch (IOException e) {
				System.err.println("Excepción: Like Boton");
			}
			// TODO Añadir manejador para que funciones
			panelBtnMG.add(btnMG);

			// Botón comentario
			JPanel panelBtnComentarios = new JPanel();
			panelBotones.add(panelBtnComentarios);

			JButton btnComentarios = new JButton("Coments");
			try {
				btnComentarios = crearBtnIcono(COMMENT_PATH, COMMENT_SIZE, COMMENT_SIZE);
			} catch (IOException e) {
				System.err.println("Excepción: Coment Boton");
			}
			// TODO Añadir manejador para que haga cosas
			panelBtnComentarios.add(btnComentarios);

			// Contador de me gustas
			JPanel panelContadorMG = new JPanel();
			panelBotones.add(panelContadorMG);

			JLabel lblContadorMG = new JLabel(f.getMeGustas() + " Me gusta");
			panelContadorMG.add(lblContadorMG);

			// Foto Perfil Usuario
			JPanel panelPerfilUsuario = new JPanel();
			panelPerfilUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelInfo.add(panelPerfilUsuario);

			JLabel lblFotoUsuario = new JLabel("Profile picture");
			try {
				lblFotoUsuario = crearLabelFoto(Controlador.INSTANCE.getFotoPerfilUsuario(), ENTRADA_FOTO_PERFIL_SIZE, ENTRADA_FOTO_PERFIL_SIZE);
			} catch (IOException e) {
				System.err.println("Excepcion: foto perfil usuario"+f.getUsuario().getUsername());
			}
			panelPerfilUsuario.add(lblFotoUsuario);

			//Nombre completo del usuario
			JLabel lblNombreUsuario = new JLabel(Controlador.INSTANCE.getUsuarioActual());
			panelPerfilUsuario.add(lblNombreUsuario);

			//Titulo y pie de foto
			JTextArea txtTitulo = new JTextArea(" " + f.getTitulo() + "\n\n " + f.getDescripcion());
			txtTitulo.setBackground(new Color(240, 240, 240));
			txtTitulo.setEditable(false);
			txtTitulo.setLineWrap(true);
			txtTitulo.setWrapStyleWord(true);
			panelInfo.add(txtTitulo);

			panelListaFotos.add(panelEntradaFoto);// Añadimos la foto al gridLayout
		}
		
		//Rellenamos los huecos del gridLayout
		while (padding > 0) {
			panelListaFotos.add(new JPanel());
			padding--;
		}
		return panelListaFotos;
	}

	private void addManejadorBtnAddFoto(JButton btnAddFoto) {
		btnAddFoto.addActionListener(e -> {
			AddFotoGUI w = new AddFotoGUI(this.framePrincipal);
			w.setVisible(true);
			framePrincipal.setVisible(false);
		});
	}

	private static void setProp(double[] size, double x, double y) {// SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0];
		newSize[1] = size[1];
		double prop;
		if (size[0] >= x) {
			prop = size[0] / size[1];
			newSize[0] = x;
			newSize[1] = x / prop;
		}
		if (newSize[1] >= y) {
			prop = size[1] / size[0];
			newSize[1] = y;
			newSize[0] = y / prop;
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}

	private static void setProfileProp(double[] size, double w) {// SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0];
		newSize[1] = size[1];
		double prop;
		if (size[1] >= size[0]) {// fotos verticales
			prop = size[0] / size[1];
			newSize[0] = w;
			newSize[1] = w / prop;
		} else {// fotos horizontales
			prop = size[1] / size[0];
			newSize[1] = w;
			newSize[0] = w / prop;
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}
}
