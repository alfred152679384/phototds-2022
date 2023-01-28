package um.tds.phototds.interfaz;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Publicacion;
import um.tds.phototds.dominio.Usuario;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class BuscadorGUI extends JDialog {
	// Necesario para quitar warnings
	private static final long serialVersionUID = 1L;

	// Constantes
	private static final int FOTO_SIZE = 50;
	private static final int BUSQ_USUARIO = 0;
	private static final int BUSQ_PUBLI = 1;

	// Atributos
	private JFrame owner;
	private List<Usuario> userList;
	private List<Publicacion> pubList;
	private int selectedIndex;
	private int mode;
	private String hashtag;

	/**
	 * Create the dialog.
	 */
	public BuscadorGUI(JFrame owner, List<Usuario> userList) {
		super(owner, true);
		this.owner = owner;
		this.userList = userList;
		this.pubList = Collections.emptyList();
		this.selectedIndex = -1;
		this.mode = BUSQ_USUARIO;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(800, 175, 250, 300);
		initialize();
	}

	public BuscadorGUI(JFrame owner, List<Publicacion> pubList, String hashtag) {
		super(owner, true);
		this.owner = owner;
		this.userList = Collections.emptyList();
		this.pubList = pubList;
		this.selectedIndex = -1;
		this.mode = BUSQ_PUBLI;
		this.hashtag = hashtag;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(800, 175, 250, 300);
		initialize();
	}

	private void initialize() {
		JPanel panelGeneral = new JPanel();
		getContentPane().add(panelGeneral, BorderLayout.CENTER);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		JPanel panelLista = new JPanel();
		panelLista.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5),
				new EtchedBorder(EtchedBorder.RAISED, null, null)));
		panelGeneral.add(panelLista, BorderLayout.CENTER);
		panelLista.setLayout(new BorderLayout(0, 0));

		try {
			if (this.mode == BUSQ_PUBLI)
				cargarListaHashtags(panelLista);
			else
				cargarListaUsuarios(panelLista);
		} catch (IOException e) {
			System.err.println("Error búsqueda");
		}
	}

	private void cargarListaHashtags(JPanel panelLista) {
		DefaultListModel<String> model = new DefaultListModel<>();
		int cont = 0;
		for (Publicacion p : this.pubList) {
			String s = p.getHashtagContaining(hashtag) + " ==> " 
					+ Integer.toString(p.getUsuario().getNumSeguidores());
			model.add(cont, s);
			cont++;
		}
		JList<String> listHashtags = new JList<>(model);

		JScrollPane scrollPanel = new JScrollPane(listHashtags);
		panelLista.add(scrollPanel, BorderLayout.CENTER);
	}

	private void cargarListaUsuarios(JPanel panelLista) throws IOException {
		JList listUsers = new JList();
		listUsers.setCellRenderer(new ImageListCellRenderer());

		ArrayList<JPanel> listPanel = new ArrayList<>();
		for (Usuario u : this.userList) {
			JPanel panelEntradaBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel lblUser = new JLabel(u.getNombre());

			File fi = new File(u.getFotoPerfil());
			BufferedImage foto;
			foto = ImageIO.read(fi);

			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			PrincipalGUI.setProfileProp(size, FOTO_SIZE);

			Image resizedImage;
			resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(resizedImage);

			JLabel lblFoto = new JLabel(icon);
			panelEntradaBuscador.add(lblFoto);
			panelEntradaBuscador.add(lblUser);
			listPanel.add(panelEntradaBuscador);
		}
		listUsers.setListData(listPanel.toArray());
		crearManejadorListaUsers(listUsers);

		JScrollPane scrollPanel = new JScrollPane(listUsers);
		panelLista.add(scrollPanel, BorderLayout.CENTER);
	}

	private void crearManejadorListaUsers(JList list) {
		list.addListSelectionListener(ev -> {
			this.selectedIndex = list.getSelectedIndex();
			this.dispose();
		});
	}

	public Optional<Usuario> getUserSelected() {
		if (selectedIndex == -1)
			return Optional.empty();
		return Optional.of(this.userList.get(this.selectedIndex));
	}
}
