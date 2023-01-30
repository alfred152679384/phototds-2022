package um.tds.phototds.interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Album;
import um.tds.phototds.dominio.Foto;
import um.tds.phototds.dominio.Notificacion;
import um.tds.phototds.dominio.Publicacion;
import um.tds.phototds.dominio.Usuario;

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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.awt.GridLayout;
import pulsador.Luz;

public class PrincipalGUI extends JFrame {
	// Necesario para que no haya warnings
	private static final long serialVersionUID = 1L;
	// Constantes
	private static final int LUPA_SIZE = 10;
	private static final int OPTIONS_SIZE = 15;
	private static final int FOTO_PERFIL_SIZE = 20;
	private static final int PERFIL_MODE_FOTO_PERFIL_SIZE = 100;
	private static final int LIKE_SIZE = 25;
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 150;
	private static final int DEFAULT_SCROLL = 10;
	private static final int DEFAULT_FOTOS = 3;
	private static final int FRAME_SIZE = 600;
	private static final int COMMENT_SIZE = 20;
	private static final int ENTRADA_FOTO_PERFIL_SIZE = 50;
	private static final int CELL_SIZE = FRAME_SIZE / 3 - 12;
	private static final int ALBUM_CELL_SIZE = FRAME_SIZE / 4 - 8;
	private static final int MODE_FOTO = 0;
	private static final int MODE_ALBUM = 1;

	private static final String LUPA_PATH = "resources\\lupa.png";
	private static final String OPTIONS_PATH = "resources\\options.png";
	private static final String LIKE_PATH = "resources\\like.png";
	private static final String COMMENT_PATH = "resources\\comment.png";

	// Atributos
	private JFrame framePrincipal;
	private JTextField txtBuscador;
	private Usuario usuarioActual;
	private boolean mostrarPerfil;
	private boolean searchingUser;
	private JPanel panelGeneral;
	private JPanel panelCentral;
	private JPanel panelFotosPerfil;
	private JPanel panelInfoUser;

	/**
	 * Create the frame. Constructor por defecto muestra la pantalla principal
	 */
	public PrincipalGUI() {
		this.mostrarPerfil = false;
		this.searchingUser = false;
		this.usuarioActual = Controlador.INSTANCE.getUsuarioActual();
		initialize();
	}

	// Constructor para mostrar la ventana de perfil de usuario
	public PrincipalGUI(boolean vistaPerfil) {
		this.mostrarPerfil = vistaPerfil;
		this.searchingUser = false;
		this.usuarioActual = Controlador.INSTANCE.getUsuarioActual();
		initialize();
	}

	public void mostrarVentana() {
		framePrincipal.setResizable(false);
		framePrincipal.setLocationRelativeTo(null);
		framePrincipal.setVisible(true);
		cargarNotificaciones();
	}

	private void cargarNotificaciones() {
		List<Notificacion> notifs = Controlador.INSTANCE.getNotificaciones(usuarioActual);
		for (Notificacion n : notifs) {
			JOptionPane.showMessageDialog(framePrincipal,
					n.getFechaPublicacion().format(Controlador.HUMAN_FORMATTER) + ":\nEl usuario "
							+ n.getPublicacion().getUsuario().getUsername()
							+ " ha realizado una publicación con título:\n" + n.getPublicacion().getTitulo(),
					"NOTIFICACION", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void initialize() {
		framePrincipal = new JFrame("Photo TDS");
		framePrincipal.setBounds(100, 100, FRAME_SIZE, FRAME_SIZE);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelGeneral = new JPanel();
		framePrincipal.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		crearPanelNorte();
		crearPanelCentral();
	}

	private void crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		// Titulo: PhotoTDS
		JPanel panelTitulo = new JPanel();
		panelNorte.add(panelTitulo);

		JButton btnTitulo = new JButton("Photo TDS");
		btnTitulo.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		addManejadorBtnPrincipal(btnTitulo);
		panelTitulo.add(btnTitulo);

		// Panel Padding
		JPanel panelBtnJavaBean = new JPanel();
		FlowLayout fl_panelBtnJavaBean = (FlowLayout) panelBtnJavaBean.getLayout();
		fl_panelBtnJavaBean.setHgap(20);
		panelNorte.add(panelBtnJavaBean);

		Luz luz = new Luz();
		addManejadorLuz(luz);
		panelBtnJavaBean.add(luz);

		// Botón para añadir Fotos
		JPanel panelBtnAdd = new JPanel();
		panelNorte.add(panelBtnAdd);

		JButton btnAddFoto = new JButton("+");
		addManejadorBtnAddFoto(btnAddFoto);
		panelBtnAdd.add(btnAddFoto);

		// Botón para añadir Albumes
		if (mostrarPerfil) {
			JButton btnAddAlbum = new JButton("A+");
			addManejadorBtnAddAlbum(btnAddAlbum);
			panelBtnAdd.add(btnAddAlbum);
		}

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
			Icon i = crearBtnIcono(LUPA_PATH, LUPA_SIZE, LUPA_SIZE);
			btnLupa = new JButton(i);
		} catch (IOException e) {
			System.err.println("Excepción: Imagen Boton Lupa ");
		}
		addManejadorBtnLupa(btnLupa);
		framePrincipal.getRootPane().setDefaultButton(btnLupa);
		panelBuscador.add(btnLupa);

		// Foto de Perfil y Ajustes
		JPanel panelAjustes = new JPanel();
		panelNorte.add(panelAjustes);

		// Foto de Perfil
		JButton btnFotoPerfil = new JButton("Profile");
		try {
			Icon i = crearBtnIcono(usuarioActual.getFotoPerfil(), FOTO_PERFIL_SIZE,
					FOTO_PERFIL_SIZE);
			btnFotoPerfil = new JButton(i);
			btnFotoPerfil.setBackground(new Color(240, 240, 240));
		} catch (IOException e) {
			System.err.println("Excepción: Imagen Perfil usuario");
		}
		addManejadorBtnFotoPerfil(btnFotoPerfil);
		panelAjustes.add(btnFotoPerfil);

		// Botón de opciones
		JButton btnOptions = new JButton("Options");
		try {
			Icon i = crearBtnIcono(OPTIONS_PATH, OPTIONS_SIZE, OPTIONS_SIZE);
			btnOptions = new JButton(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		crearMenuOpciones(btnOptions);
		panelAjustes.add(btnOptions);

	}

	private void addManejadorLuz(Luz luz) {
		luz.addEncendidoListener(ev -> {
			JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
			int retVal = fc.showOpenDialog(framePrincipal);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				if (!Controlador.INSTANCE.cargarFotosBean(fc.getSelectedFile())) {
					JOptionPane.showMessageDialog(framePrincipal, "Debe escoger un fichero XML", "Fichero Incorrecto",
							JOptionPane.ERROR_MESSAGE);
					luz.setColor(Color.WHITE);
				} else
					luz.setColor(Color.GREEN);
				panelCentral.removeAll();
				if (this.mostrarPerfil) {
					cargarPerfilUsuario(usuarioActual);
				} else {
					cargarPantallaPrincipal();
				}
				recargarVentana();
			}
		});
	}

	private void addManejadorBtnAddAlbum(JButton btn) {
		btn.addActionListener(ev -> {
			CrearAlbumGUI w = new CrearAlbumGUI(framePrincipal);
			w.setVisible(true);
			if (w.getOk()) {
				panelCentral.removeAll();
				cargarPerfilUsuario(usuarioActual);
				panelFotosPerfil.removeAll();
				cargarAlbumesPerfil(usuarioActual);
				recargarVentana();
			}
		});
	}

	private void crearMenuOpciones(JButton btnOptions) {
		JPopupMenu popOptions = new JPopupMenu();

		// Elementos del menu
		{
			JMenuItem itemPremium = new JMenuItem("Premium");
			addManejadorItemPremium(itemPremium);
			popOptions.add(itemPremium);

			JMenuItem itemPdf = new JMenuItem("Generar PDF");
			addManejadorItemPdf(itemPdf);
			popOptions.add(itemPdf);

			JMenuItem itemExcel = new JMenuItem("Generar Excel");
			addManejadorItemExcel(itemExcel);
			popOptions.add(itemExcel);

			JMenuItem itemTopMG = new JMenuItem("Top Me gusta");
			addManejadorItemTopMG(itemTopMG);
			popOptions.add(itemTopMG);
		}
		// Añadimos el menu al boton
		btnOptions.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				popOptions.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private void addManejadorItemPdf(JMenuItem itemPdf) {
		itemPdf.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!isUsuarioPremium())
					return;
				Controlador.INSTANCE.crearPdfSeguidoeres();
				JOptionPane.showMessageDialog(framePrincipal, "El fichero PDF ha sido creado correctamente",
						"PDF generado", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void addManejadorItemExcel(JMenuItem itemExcel) {
		itemExcel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!isUsuarioPremium())
					return;
				Controlador.INSTANCE.crearExcelSeguidores();
				JOptionPane.showMessageDialog(framePrincipal, "El fichero Excel ha sido creado correctamente",
						"Excel generado", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void addManejadorItemTopMG(JMenuItem item) {
		item.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!isUsuarioPremium())
					return;
				TopMgGUI w = new TopMgGUI(framePrincipal);
				w.setVisible(true);
			}
		});
	}

	private boolean isUsuarioPremium() {
		if (usuarioActual.isPremium())
			return true;
		JOptionPane.showMessageDialog(framePrincipal, "Usted no es un usuario premium", "No es Premium",
				JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void addManejadorItemPremium(JMenuItem itemPremium) {
		itemPremium.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (usuarioActual.isPremium()) {
					JOptionPane.showMessageDialog(framePrincipal, "Usted ya es un usuario premium", "Ya es Premium",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String[] options = { "Descuento por Edad", "Descuento por Me Gustas", "Sin descuento" };
				int response = JOptionPane.showOptionDialog(framePrincipal,
						"Escoge el tipo de descuento que desea aplicar", "Descuento Premium",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
				if (response == -1) {
					return;
				}
				double precio = Controlador.INSTANCE.comprobarDescuento(response);

				String[] yesNo = { "Si", "No" };
				int havePayed = JOptionPane.showOptionDialog(framePrincipal,
						"Precio para premium: " + precio + "€. ¿Desea realizar el pago?", "Pagar",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesNo, yesNo[0]);

				if (havePayed == 0) {
					Controlador.INSTANCE.setUsuarioActualPremium();
				}
			}
		});
	}

	private void addManejadorBtnLupa(JButton btnLupa) {
		btnLupa.addActionListener(ev -> {
			String busq = txtBuscador.getText();
			if (busq.equals(""))
				return;
			if (busq.startsWith("#")) {// Búsqueda de publicacion
				busquedaPublicacion(busq);

			} else {// Busqueda de usuario
				busquedaUsuario(busq);
			}
		});
	}

	private void busquedaUsuario(String busq) {
		List<Usuario> results = Controlador.INSTANCE.buscarUsuario(busq);
		if (results.isEmpty()) {
			errorBusqueda();
			return;
		}
		BuscadorGUI w = new BuscadorGUI(framePrincipal, results);
		w.setVisible(true);
		this.searchingUser = true;
		txtBuscador.setText("");
		panelCentral.removeAll();
		Optional<Usuario> selected = w.getUserSelected();
		if (selected.isEmpty())
			return;
		cargarPerfilUsuario(selected.get());
		recargarVentana();
	}

	private void busquedaPublicacion(String busq) {
		List<Publicacion> results = Controlador.INSTANCE.buscarPublicacion(busq);
		if (results.isEmpty()) {
			errorBusqueda();
			txtBuscador.setText("");
			return;
		}
		BuscadorGUI w = new BuscadorGUI(framePrincipal, results, busq);
		w.setVisible(true);
		txtBuscador.setText("");
	}

	private void errorBusqueda() {
		JOptionPane.showMessageDialog(framePrincipal, "La búsqueda no ha arrojado ningún resultado", "No results",
				JOptionPane.ERROR_MESSAGE);
		return;
	}

	private void crearPanelCentral() {
		panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);

		if (mostrarPerfil)
			cargarPerfilUsuario(Controlador.INSTANCE.getUsuarioActual());
		else
			cargarPantallaPrincipal();
	}

	private void cargarPantallaPrincipal() {
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		// Cargo las fotos en el panelListaFotos
		JPanel panelListaFotos;
		// if (this.panelFotosCentral.isEmpty())
		panelListaFotos = cargarFotos();
		// else
		// panelListaFotos = this.panelFotosCentral.get();

		// Añado el scrollbar
		JScrollPane scrollListaFotos = new JScrollPane(panelListaFotos);
		scrollListaFotos.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);
		scrollListaFotos.getVerticalScrollBar().setValue(0);

		// Meto el scrollbar en el panel central
		panelCentral.add(scrollListaFotos);

	}

	private void cargarPerfilUsuario(Usuario user) {
		panelCentral.setLayout(new BorderLayout(0, 0));
		// Informacion del Usuario
		panelInfoUser = new JPanel();
		panelCentral.add(panelInfoUser, BorderLayout.NORTH);
		panelInfoUser.setLayout(new BorderLayout(0, 0));

		// Foto de perfil
		JPanel panelFotoPerfil = new JPanel();
		panelInfoUser.add(panelFotoPerfil, BorderLayout.WEST);

		JLabel lblFotoPerfil = new JLabel("Profile Picture");
		try {
			lblFotoPerfil = crearLabelFoto(user.getFotoPerfil(), PERFIL_MODE_FOTO_PERFIL_SIZE,
					PERFIL_MODE_FOTO_PERFIL_SIZE);
		} catch (IOException e) {
			System.err.println("Profile picture fail");
		}
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		panelFotoPerfil.add(lblFotoPerfil);

		// Informacion
		JPanel panelInfo = new JPanel();
		panelInfoUser.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		{
			// Correo
			JPanel panelCorreo = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panelCorreo.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			panelInfo.add(panelCorreo);

			JLabel lblCorreo = new JLabel(user.getEmail());
			panelCorreo.add(lblCorreo);

			// Panel Padding
			JPanel panelPadding0 = new JPanel();
			panelCorreo.add(panelPadding0);

			if (!searchingUser) {
				// Editar Perfil
				JButton btnEditPerfil = new JButton("Editar perfil");
				btnEditPerfil.setForeground(Color.WHITE);
				btnEditPerfil.setBackground(new Color(0, 0, 255));
				btnEditPerfil.setOpaque(true);
				addManejadorBtnEditarPerfil(btnEditPerfil);
				panelCorreo.add(btnEditPerfil);
			} else if(!user.getSeguidores().contains(usuarioActual)){
				JButton btnSeguir = new JButton("Seguir");
				btnSeguir.setForeground(Color.WHITE);
				btnSeguir.setBackground(new Color(0, 0, 255));
				btnSeguir.setOpaque(true);
				addManejadorBtnSeguirUsuario(btnSeguir, user);
				panelCorreo.add(btnSeguir);
			} else {
				JButton btnSeguir = new JButton("Seguido");
				btnSeguir.setForeground(Color.WHITE);
				btnSeguir.setBackground(new Color(0, 0, 255));
				btnSeguir.setOpaque(true);
				panelCorreo.add(btnSeguir);
			}

			// Estadísticas del usuario
			JPanel panelEstadisticas = new JPanel();
			FlowLayout flowLayout_1 = (FlowLayout) panelEstadisticas.getLayout();
			flowLayout_1.setAlignment(FlowLayout.LEFT);
			panelInfo.add(panelEstadisticas);

			// Numero de publicaciones
			JLabel lblNumPublics = new JLabel(user.getNumPublicaciones() + " publicaciones");
			panelEstadisticas.add(lblNumPublics);

			JPanel panelPadding1 = new JPanel();
			panelEstadisticas.add(panelPadding1);

			// Numero de seguidores
			JLabel lblNumSeguidores = new JLabel(user.getNumSeguidores() + " seguidores");
			panelEstadisticas.add(lblNumSeguidores);

			JPanel panelPadding2 = new JPanel();
			panelEstadisticas.add(panelPadding2);

			// Numero de seguidos
			JLabel lblNumSeguidos = new JLabel(user.getNumSeguidos() + " seguidos");
			panelEstadisticas.add(lblNumSeguidos);

			// Nombre completo del Usuario
			JPanel panelNombre = new JPanel();
			FlowLayout flowLayout_2 = (FlowLayout) panelNombre.getLayout();
			flowLayout_2.setAlignment(FlowLayout.LEFT);
			panelInfo.add(panelNombre);

			JLabel lblNombre = new JLabel(user.getNombre());
			panelNombre.add(lblNombre);
		}

		// Creacion del panel fotos (con antelacion)
		panelFotosPerfil = new JPanel();
		panelCentral.add(panelFotosPerfil, BorderLayout.CENTER);
		panelFotosPerfil.setLayout(new BorderLayout(0, 0));

		{// Seleccion de modo para el panelFotos
			// Panel de seleccion
			JPanel panelFotoAlbumes = new JPanel();
			panelInfoUser.add(panelFotoAlbumes, BorderLayout.SOUTH);

			JButton btnFotos = new JButton("FOTOS");
			btnFotos.setBackground(Color.WHITE);
			btnFotos.setOpaque(true);
			btnFotos.addActionListener(ev -> {
				panelFotosPerfil.removeAll();
				cargarFotosPerfil(user);
				recargarVentana();
			});
			panelFotoAlbumes.add(btnFotos);

			JButton btnAlbumes = new JButton("ALBUMES");
			btnAlbumes.setBackground(Color.WHITE);
			btnAlbumes.setOpaque(true);
			btnAlbumes.addActionListener(ev -> {
				panelFotosPerfil.removeAll();
				cargarAlbumesPerfil(user);
				recargarVentana();
			});
			panelFotoAlbumes.add(btnAlbumes);
		}

		// Cargamos las fotos del usuario
		cargarFotosPerfil(user);
	}

	private void cargarAlbumesPerfil(Usuario user) {
		// Si no estaba cargado
		List<Publicacion> pubList = new LinkedList<>(user.getAlbumesPerfil());
		if (pubList.isEmpty())
			return;
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;

		cargarImagenesModelo(new ArrayList<>(pubList), model);

		lista = new JList<>(model);
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setFixedCellWidth(ALBUM_CELL_SIZE);
		lista.setFixedCellHeight(ALBUM_CELL_SIZE);
		crearManejadorListaPerfil(lista, pubList, MODE_ALBUM);

		JScrollPane scrollAlbumes = new JScrollPane(lista);
		scrollAlbumes.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);
		scrollAlbumes.getVerticalScrollBar().setValue(0);
		panelFotosPerfil.add(scrollAlbumes, BorderLayout.CENTER);
	}

	private void addManejadorBtnSeguirUsuario(JButton btn, Usuario user) {
		btn.addActionListener(ev -> {
			Controlador.INSTANCE.seguirUsuario(user);
			panelCentral.removeAll();
			cargarPerfilUsuario(user);
			recargarVentana();
		});
	}

	private void addManejadorBtnEditarPerfil(JButton btn) {
		btn.addActionListener(ev -> {
			RegisterGUI w = new RegisterGUI(framePrincipal, RegisterGUI.MODE_UPDATE);
			w.setVisible(true);
			if (w.getOk()) {
				panelGeneral.removeAll();
				crearPanelNorte();
				crearPanelCentral();
				mostrarPerfil = true;
				recargarVentana();
			}
		});
	}

	private void cargarFotosPerfil(Usuario user) {
		List<Publicacion> fotos = new LinkedList<>(user.getFotosPerfil());
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;

		cargarImagenesModelo(new ArrayList<>(fotos), model);

		lista = new JList<>(model);
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setFixedCellWidth(CELL_SIZE);
		lista.setFixedCellHeight(CELL_SIZE);
		lista.setBorder(new LineBorder(Color.black));
		crearManejadorListaPerfil(lista, fotos, MODE_FOTO);

		JScrollPane scrollFotos = new JScrollPane(lista);
		scrollFotos.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);
		scrollFotos.getVerticalScrollBar().setValue(0);
		panelFotosPerfil.add(scrollFotos, BorderLayout.CENTER);
	}

	private void crearManejadorListaPerfil(JList<ImageIcon> lista, List<Publicacion> publics, int mode) {
		lista.addMouseListener(new MouseAdapter() {
			int index;

			public void mouseClicked(MouseEvent e) {
				index = lista.getSelectedIndex();
				// Opción de que se pinche con click izquierdo la foto
				if (e.getButton() == MouseEvent.BUTTON1) {
					mostrarPublicacion(publics.get(index), mode);
				}
				// Opción de que se pinche con click derecho
				if (e.getButton() == MouseEvent.BUTTON3) {
					index = lista.locationToIndex(new Point(e.getPoint()));
					JPopupMenu popFotoPerfil = new JPopupMenu();

					// Elementos del menu
					{
						// Botón para ver foto
						JMenuItem itemVerFoto = new JMenuItem("Ver");
						// manejador pulsando la opción de ver foto
						itemVerFoto.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent ev) {
								mostrarPublicacion(publics.get(index), mode);
							}
						});
						popFotoPerfil.add(itemVerFoto);

						// Botón para borrar foto
						if (publics.get(0).getUsuario().getUsername().equals(usuarioActual.getUsername())) {
							JMenuItem itemBorrarFoto = new JMenuItem("Eliminar");
							// manejador pulsando opción de eliminar
							itemBorrarFoto.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent ev) {
									borrarPublicacion(publics.get(index), mode);
								}
							});
							popFotoPerfil.add(itemBorrarFoto);
						}
					}

					popFotoPerfil.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private void borrarPublicacion(Publicacion p, int mode) {
		Controlador.INSTANCE.deletePublicacion(p);
		panelFotosPerfil.removeAll();
		if (mode == MODE_FOTO)
			cargarFotosPerfil(usuarioActual);
		else
			cargarAlbumesPerfil(this.usuarioActual);
		recargarVentana();
	}

	private void mostrarPublicacion(Publicacion publi, int mode) {
		if (mode == MODE_FOTO) {// Cargamos la foto
			ShowImageGUI w = new ShowImageGUI(framePrincipal, publi, ShowImageGUI.MODE_FOTO_COMENTARIO);
			w.setVisible(true);
		} else if (mode == MODE_ALBUM) {// Cargamos el album
			ShowAlbumGUI w = new ShowAlbumGUI(framePrincipal, publi);
			w.setVisible(true);
			panelFotosPerfil.removeAll();
			cargarAlbumesPerfil(publi.getUsuario());
			recargarVentana();
		}
	}

	private JPanel cargarFotos() {// Cargamos fotos de la pantalla principal
		List<Foto> fotos = usuarioActual.getFotosPrincipal();

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
			addManejadorPanelFoto(panelFoto, f);

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
				Icon i = crearBtnIcono(LIKE_PATH, LIKE_SIZE, LIKE_SIZE);
				btnMG = new JButton(i);
			} catch (IOException e) {
				System.err.println("Excepción: Like Boton");
			}
			panelBtnMG.add(btnMG);

			// Botón comentario
			JPanel panelBtnComentarios = new JPanel();
			panelBotones.add(panelBtnComentarios);

			JButton btnComentarios = new JButton("Coments");
			try {
				Icon i = crearBtnIcono(COMMENT_PATH, COMMENT_SIZE, COMMENT_SIZE);
				btnComentarios = new JButton(i);
			} catch (IOException e) {
				System.err.println("Excepción: Coment Boton");
			}
			addManejadorBtnComentario(btnComentarios, f);
			panelBtnComentarios.add(btnComentarios);

			// Contador de me gustas
			JPanel panelContadorMG = new JPanel();
			panelBotones.add(panelContadorMG);

			JLabel lblContadorMG = new JLabel(f.getMeGustas() + " Me gusta");
			panelContadorMG.add(lblContadorMG);

			// Añadimos el manejador del boton de dar Me gusta
			addManejadorBtnMG(btnMG, panelContadorMG, f);

			// Foto Perfil Usuario
			JPanel panelPerfilUsuario = new JPanel();
			panelPerfilUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelInfo.add(panelPerfilUsuario);

			JLabel lblFotoUsuario = new JLabel("Profile picture");
			try {
				lblFotoUsuario = crearLabelFoto(f.getUsuario().getFotoPerfil(), ENTRADA_FOTO_PERFIL_SIZE,
						ENTRADA_FOTO_PERFIL_SIZE);
			} catch (IOException e) {
				System.err.println("Excepcion: foto perfil usuario" + f.getUsuario().getUsername());
			}
			panelPerfilUsuario.add(lblFotoUsuario);

			// Panel padding
			panelPerfilUsuario.add(new JPanel());

			// Username
			JLabel lblNombreUsuario = new JLabel(f.getUsuario().getUsername());
			lblNombreUsuario.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 5, 5, 5)));
			panelPerfilUsuario.add(lblNombreUsuario);

			// Panel padding
			panelPerfilUsuario.add(new JPanel());

			// Fecha de publicacion
			JLabel lblFecha = new JLabel("Fecha: " + f.getFecha().format(Controlador.HUMAN_FORMATTER));
			panelPerfilUsuario.add(lblFecha);

			// Titulo y pie de foto
			JTextArea txtTitulo = new JTextArea(" " + f.getTitulo() + "\n\n " + f.getDescripcion());
			txtTitulo.setBackground(new Color(240, 240, 240));
			txtTitulo.setEditable(false);
			txtTitulo.setLineWrap(true);
			txtTitulo.setWrapStyleWord(true);
			txtTitulo.setBorder(new LineBorder(Color.gray));
			panelInfo.add(txtTitulo);

			panelListaFotos.add(panelEntradaFoto);// Añadimos la foto al gridLayout
		}

		// Rellenamos los huecos del gridLayout
		while (padding > 0) {
			panelListaFotos.add(new JPanel());
			padding--;
		}
		return panelListaFotos;
	}

	private void addManejadorBtnComentario(JButton btnComentarios, Foto f) {
		btnComentarios.addActionListener(ev -> {
			PresentationGUI w = new PresentationGUI(framePrincipal, PresentationGUI.MODE_COMENTARIO);
			w.mostrarVentana();
			Optional<String> optComentario = w.getTexto();
			if (optComentario.isPresent() && !optComentario.get().equals("")) {
				Controlador.INSTANCE.escribirComentario(f, optComentario.get());
			}
		});
	}

	private void addManejadorBtnMG(JButton btnMG, JPanel panelContadorMG, Foto f) {
		btnMG.addActionListener(ev -> {
			Controlador.INSTANCE.darMeGusta(f);
			panelContadorMG.removeAll();
			JLabel newLabelContadorMG = new JLabel(f.getMeGustas() + " Me gusta");
			panelContadorMG.add(newLabelContadorMG);
			recargarVentana();
		});
	}

	private void addManejadorPanelFoto(JPanel p, Foto f) {
		p.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ShowImageGUI w = new ShowImageGUI(framePrincipal, f, ShowImageGUI.MODE_FOTO_ONLY);
				w.setVisible(true);
			}
		});
	}

	private void addManejadorBtnAddFoto(JButton btnAddFoto) {
		btnAddFoto.addActionListener(e -> {
			AddFotoGUI w = new AddFotoGUI(this.framePrincipal, mostrarPerfil);
			w.setVisible(true);
		});
	}

	private void addManejadorBtnFotoPerfil(JButton btn) {
		btn.addActionListener(ev -> {
			if (!mostrarPerfil || searchingUser) {
				this.mostrarPerfil = true;
				this.searchingUser = false;
				panelGeneral.removeAll();
				crearPanelNorte();
				crearPanelCentral();
				recargarVentana();
			}
		});
	}

	private void addManejadorBtnPrincipal(JButton btn) {
		btn.addActionListener(ev -> {
			if (mostrarPerfil || searchingUser) {
				this.mostrarPerfil = false;
				this.searchingUser = false;
				this.panelGeneral.removeAll();
				crearPanelNorte();
				crearPanelCentral();
				recargarVentana();
			}
		});
	}

	private void recargarVentana() {
		framePrincipal.revalidate();
		framePrincipal.repaint();
	}
	
	//Métodos generalizados
	private void cargarImagenesModelo(ArrayList<Publicacion> publics, DefaultListModel<ImageIcon> model) {
		BufferedImage foto;
		int cont = 0;
		try {// Cargamos las imágenes en el modelo dimensionadas
			for (Publicacion f : publics) {
				File fi;
				if (f instanceof Album) {
					fi = new File(((Album) f).getListaFotos().get(0).getPath());
				} else {
					fi = new File(((Foto) f).getPath());
				}
				foto = ImageIO.read(fi);

				double[] size = new double[2];
				size[0] = foto.getWidth(null);
				size[1] = foto.getHeight(null);
				setProfileProp(size, CELL_SIZE);

				Image resizedImage;
				resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(resizedImage);

				model.add(cont, icon);
				cont++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Icon crearBtnIcono(String path, int width, int height) throws IOException {
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
		return icon;
	}

	public static JLabel crearLabelFoto(String path, int width, int height) throws IOException {
		// Cargamos foto original
		BufferedImage foto;
		File fi = new File(path);
		foto = ImageIO.read(fi);

		// Redimensionamos
		double[] size = new double[2];
		size[0] = foto.getWidth(null);
		size[1] = foto.getHeight(null);
		setProp(size, width, height);

		// Creamos el JLabel
		JLabel picLabel = new JLabel();
		Image resizedImage;
		resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
		picLabel.setIcon(new ImageIcon(resizedImage));
		return picLabel;
	}

	public static void setProp(double[] size, double x, double y) {// SetProporcion
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

	public static void setProfileProp(double[] size, double w) {// SetProporcion
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
