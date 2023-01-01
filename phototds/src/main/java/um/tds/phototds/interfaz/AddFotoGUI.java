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
import java.util.List;
import java.awt.Color;

public class AddFotoGUI extends JDialog {//Añadir fotos por drag and drop o del sistema de ficheros
	//Necesario para quitar warnings
	private static final long serialVersionUID = 1L;

	//Atributos
	private final JEditorPane panelGeneral = new JEditorPane();
	private JFrame owner;
	private boolean type;

	/**
	 * Create the dialog.
	 */
	public AddFotoGUI(JFrame owner, boolean type) { 
		super(owner, "Añadir Foto", true);
		this.type = type;
		this.owner = owner;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 400, 300);
		initialize();
	}

	private void initialize() {
		//Ponemos el texto en la ventana
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(panelGeneral);
		panelGeneral.setContentType("text/html");
		panelGeneral.setText("<h1> Agregar Foto</h1><p> Anímate a compartir una foto con "
				+ "tus amigos. <br> <h2>Arrastra la foto aquí.<h2> </p>");
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
			if(retVal == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				System.out.println(f);
				mostrarFoto(f);
				PrincipalGUI w = new PrincipalGUI(type);
				w.mostrarVentana();
				owner.dispose();
			}
		});
		panelBtnFileChooser.add(btnFileChooser);

	}
	
	private void crearDragNDrop(){
		panelGeneral.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						mostrarFoto(file);
					}
					PrincipalGUI w = new PrincipalGUI(type);
					w.mostrarVentana();
					owner.dispose();
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
}
