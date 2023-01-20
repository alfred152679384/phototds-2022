package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import um.tds.phototds.controlador.ComunicacionConGUI;
import um.tds.phototds.controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ShowAlbumGUI extends JDialog {
	private static final long serialVersionUID = 1L;

	// Constantes
	private static final int FRAME_SIZE = 450;
	private static final int ALBUM_CELL_SIZE = FRAME_SIZE / 3 - 12;
	private static final int DEFAULT_SCROLL = 10;
	private static final int LIKE_SIZE = 25;
	private static final String LIKE_PATH = "resources\\like.png";
	

	// Atributos
	private JFrame owner;
	private ComunicacionConGUI album;
	private JPanel panelCentral;
	private String propietarioAlbum;
	private JPanel panelNorte;

	/**
	 * Create the dialog.
	 */
	public ShowAlbumGUI(JFrame owner, ComunicacionConGUI album) {
		super(owner, "Mostrar Album", true);
		this.setResizable(false);
		this.owner = owner;
		this.panelCentral = new JPanel();
		setBounds(400, 100, 450, 550);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.white);
		this.propietarioAlbum = album.getUsername();
		this.album = album;
		this.panelNorte = new JPanel();

		panelCentral.setLayout(new BorderLayout(0, 0));
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		crearPanelNorte();
		cargarPanelCentral();
		crearPanelSur();
	}

	private void crearPanelNorte() {
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
		{
			JPanel panelTituloPantalla = new JPanel();
			panelNorte.add(panelTituloPantalla);
			{
				JLabel lblTituloPantalla = new JLabel("Album");
				lblTituloPantalla.setFont(new Font("Tahoma", Font.BOLD, 20));
				panelTituloPantalla.add(lblTituloPantalla);
			}
		}
		{
			JPanel panelTituloAlbum = new JPanel();
			panelNorte.add(panelTituloAlbum);
			{
				JLabel lblTituloAlbum = new JLabel(this.album.getTitulo());
				panelTituloAlbum.add(lblTituloAlbum);
			}
		}
		{
			JPanel panelDescAlbum = new JPanel();
			panelNorte.add(panelDescAlbum);
			{
				JTextArea txtDescAlbum = new JTextArea(this.album.getDescripcion());
				txtDescAlbum.setBackground(new Color(240,240,240));
				txtDescAlbum.setEditable(false);
				panelDescAlbum.add(txtDescAlbum);
			}
		}
		{
			JPanel panelContadorMG = new JPanel();
			panelNorte.add(panelContadorMG);
			{
				int mg = Controlador.INSTANCE.getMeGustasFoto(this.album.getIdPubli());
				JLabel lblContadorMG = new JLabel(Integer.toString(mg)+" Me gustas");
				panelContadorMG.add(lblContadorMG);
			}
		}
	}
	
	private void cargarPanelCentral() {
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;

		// Cargar imágenes en el modelo
		cargarImagenesModelo(model);

		lista = new JList<>(model);
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setFixedCellWidth(ALBUM_CELL_SIZE);
		lista.setFixedCellHeight(ALBUM_CELL_SIZE);
		crearManejadorListaFotosAlbumes(lista);

		JScrollPane scrollAlbumes = new JScrollPane(lista);
		scrollAlbumes.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL);
		scrollAlbumes.getVerticalScrollBar().setValue(0);
		panelCentral.add(scrollAlbumes, BorderLayout.CENTER);
	}

	private void crearManejadorListaFotosAlbumes(JList<ImageIcon> lista) {
		lista.addListSelectionListener(ev -> {
			ShowImageGUI w = new ShowImageGUI(owner, album.getListaFotosAlbum().get(lista.getSelectedIndex()),
					ShowImageGUI.MODE_FOTO_ONLY);
			w.setVisible(true);
		});
	}

	private void cargarImagenesModelo(DefaultListModel<ImageIcon> model) {
		BufferedImage foto;
		int cont = 0;
		try {// Cargamos las imágenes en el modelo dimensionadas
			for (ComunicacionConGUI f : this.album.getListaFotosAlbum()) {
				File fi = new File(f.getPathFoto());
				foto = ImageIO.read(fi);

				double[] size = new double[2];
				size[0] = foto.getWidth(null);
				size[1] = foto.getHeight(null);
				Controlador.setProfileProp(size, ALBUM_CELL_SIZE);

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

	private void crearPanelSur() {
		JPanel panelSur = new JPanel();
		panelSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panelSur, BorderLayout.SOUTH);
	
		if(Controlador.INSTANCE.getUsuarioActual().equals(this.propietarioAlbum)) {
			JButton btnAddFoto = new JButton("Añadir Foto");
			btnAddFoto.addActionListener(ev -> {
				AddFotoGUI w = new AddFotoGUI(owner, AddFotoGUI.MODE_ALBUM);
				w.setVisible(true);
				Controlador.INSTANCE.addFotosToAlbum(this.album.getIdPublicacion(), w.getListFotos());
				this.album.addFotos(w.getListFotos());
				panelCentral.removeAll();
				cargarPanelCentral();
				ShowAlbumGUI.this.revalidate();
				ShowAlbumGUI.this.revalidate();
			});
			panelSur.add(btnAddFoto);
		}
		
		JButton btnMG = new JButton("Like");
		try {
			btnMG = cargarImagenLike();
		} catch (IOException e) {
			System.err.println("Excepción: Like Boton in showAlbumGUI");
			e.printStackTrace();
		}
		addManejadorBtnMG(btnMG);
		panelSur.add(btnMG);
	}
	
	private void addManejadorBtnMG(JButton btnMG) {
		btnMG.addActionListener(ev -> {
			panelNorte.removeAll();
			Controlador.INSTANCE.darMeGusta(this.album.getIdPubli());
			crearPanelNorte();
			ShowAlbumGUI.this.revalidate();
			ShowAlbumGUI.this.repaint();
		});
	}
	
	private JButton cargarImagenLike() throws IOException {
		BufferedImage original;
		File fi = new File(LIKE_PATH);
		original = ImageIO.read(fi);

		// Redimensionamos la foto al tamaño pedido
		double[] size = new double[2];
		size[0] = original.getWidth();
		size[1] = original.getHeight();
		Controlador.setProp(size, LIKE_SIZE, LIKE_SIZE);
		Image resizedImg = original.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);

		// Creamos botón con imagen dimensionada
		Icon icon = new ImageIcon(resizedImg);
		return new JButton(icon);
	}
}
