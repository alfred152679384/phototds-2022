package um.tds.phototds.interfaz;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import um.tds.phototds.controlador.ComunicacionConGUI;
import um.tds.phototds.controlador.Controlador;

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

	// Atributos
	private JFrame owner;
	private List<ComunicacionConGUI> list;
	private int selectedIndex;

	/**
	 * Create the dialog.
	 */
	public BuscadorGUI(JFrame owner, List<ComunicacionConGUI> list) {
		super(owner, true);
		this.owner = owner;
		this.list = list;
		this.selectedIndex = -1;
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
		panelLista.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new EtchedBorder(EtchedBorder.RAISED, null, null)));
		panelGeneral.add(panelLista, BorderLayout.CENTER);
		panelLista.setLayout(new BorderLayout(0, 0));

		try {
			if (list.get(0).getMode() == ComunicacionConGUI.MODE_USUARIOS)
				cargarListaUsuarios(panelLista);
			else
				cargarListaHashtags(panelLista);
		} catch (IOException e) {
			System.err.println("Error búsqueda");
		}
	}

	private void cargarListaHashtags(JPanel panelLista) {
		DefaultListModel<String> model = new DefaultListModel<>();
		int cont = 0;
		for (ComunicacionConGUI c : this.list) {
			String s = c.getHashtag() + " --> " + Integer.toString(c.getSeguidoresUsuario());
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
		for (ComunicacionConGUI c : this.list) {
			JPanel panelEntradaBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel lblUser = new JLabel(c.getNombreUsuario());

			File fi = new File(c.getPathFoto());
			BufferedImage foto;
			foto = ImageIO.read(fi);

			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			Controlador.setProfileProp(size, FOTO_SIZE);

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
	
	public String getUserSelected() {
		if(selectedIndex == -1)
			return "";
		return this.list.get(this.selectedIndex).getUsername();
	}
}
