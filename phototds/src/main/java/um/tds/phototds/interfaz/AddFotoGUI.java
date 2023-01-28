package um.tds.phototds.interfaz;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.awt.Color;

public class AddFotoGUI extends JDialog {// Añadir fotos por drag and drop o del sistema de ficheros
	// Necesario para quitar warnings
	private static final long serialVersionUID = 1L;

	// Constantes
	public static final int MODE_FOTO = 0;
	public static final int MODE_ALBUM = 1;

	// Atributos
	private final JEditorPane panelGeneral = new JEditorPane();
	private JFrame owner;
	private boolean type;
	private int mode;
	private List<String> listTitulo;
	private List<String> listDesc;
	private List<String> listPath;

	/**
	 * Constructor para añadir fotos 
	 * @param owner
	 * @param type false para abrir pantalla principal, true para abrir perfil de usuario
	 */
	public AddFotoGUI(JFrame owner, boolean type) {
		super(owner, "Añadir Foto", true);
		this.mode = MODE_FOTO;
		this.type = type;
		this.owner = owner;
		this.listTitulo = new LinkedList<>();
		this.listDesc = new LinkedList<>();
		this.listPath = new LinkedList<>();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 400, 300);
		initialize();
	}

	/**
	 * Constructor de addfotogui para añadir fotos a albumes
	 * @param owner
	 * @param mode
	 */
	public AddFotoGUI(JFrame owner, int mode) {
		super(owner, "Añadir Foto", true);
		this.mode = mode;
		if (mode == MODE_ALBUM)
			this.setTitle("Añadir Album");
		this.owner = owner;
		this.listTitulo = new LinkedList<>();
		this.listDesc = new LinkedList<>();
		this.listPath = new LinkedList<>();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 400, 300);
		initialize();
	}

	private void initialize() {
		// Ponemos el texto en la ventana
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(panelGeneral);
		panelGeneral.setContentType("text/html");
		panelGeneral.setText(
				"<center><h1>Agregar Foto</h1><p>Anímate a compartir una foto con tus amigos. <br> <h2>Arrastra la foto aquí.<h2> </p></center>");
		panelGeneral.setEditable(false);

		crearDragNDrop();
		crearFileChooser();
	}

	private void crearFileChooser() {
		JPanel panelBtnFileChooser = new JPanel();
		getContentPane().add(panelBtnFileChooser);

		JButton btnFileChooser = new JButton("Selecciona la foto de tu ordenador");
		btnFileChooser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFileChooser.setForeground(new Color(0, 0, 255));
		btnFileChooser.addActionListener(ev -> {
			JFileChooser fc = new JFileChooser();
			int retVal = fc.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				if (mode == MODE_FOTO) {
					mostrarFoto(f);
					PrincipalGUI w = new PrincipalGUI(type);
					w.mostrarVentana();
				}
				if (mode == MODE_ALBUM) {
					ShowImageGUI w = new ShowImageGUI(this.owner, f, ShowImageGUI.MODE_ALBUM);
					w.setVisible(true);
					this.listTitulo.add(w.getTitulo());
					this.listDesc.add(w.getTitulo());
					this.listPath.add(f.getAbsolutePath());
				}
				owner.dispose();
			}
		});
		panelBtnFileChooser.add(btnFileChooser);

	}

	private void crearDragNDrop() {
		panelGeneral.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					if (mode == MODE_FOTO) {
						for (File file : droppedFiles) {
							mostrarFoto(file);
						}
						PrincipalGUI w = new PrincipalGUI(type);
						w.mostrarVentana();
						owner.dispose();
					} else {
						for (File f : droppedFiles) {
							ShowImageGUI w = new ShowImageGUI(owner, f, ShowImageGUI.MODE_ALBUM);
							w.setVisible(true);
							listTitulo.add(w.getTitulo());
							listDesc.add(w.getDescripcion());
							listPath.add(f.getAbsolutePath());
							AddFotoGUI.this.dispose();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private void mostrarFoto(File fi) {
		ShowImageGUI w = new ShowImageGUI(owner, fi);
		w.setVisible(true);
	}

	public List<String> getListTitulos() {
		return Collections.unmodifiableList(this.listTitulo);
	}
	
	public List<String> getListDescripciones(){
		return Collections.unmodifiableList(this.listDesc);
	}
	
	public List<String> getListPaths(){
		return Collections.unmodifiableList(this.listPath);
	}
}
