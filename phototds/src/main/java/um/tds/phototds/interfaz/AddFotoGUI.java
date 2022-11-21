package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Color;

public class AddFotoGUI extends JDialog {//Añadir fotos por drag and drop o del sistema de ficheros

	private final JEditorPane panelGeneral = new JEditorPane();
	private JFrame owner;

	/**
	 * Create the dialog.
	 */
	public AddFotoGUI(JFrame owner) { 
		super(owner, "Añadir Foto", true);
		this.owner = owner;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(panelGeneral);
		panelGeneral.setContentType("text/html");
		panelGeneral.setText("<h1>Agregar Foto</h1><p>Anímate a compartir una foto con "
				+ "tus amigos. <br> Puedes arrastrar el fichero aquí. </p>");
				panelGeneral.setEditable(false);
				panelGeneral.setDropTarget(new DropTarget() {
					private static final long serialVersionUID = 1L;

					public synchronized void drop(DropTargetDropEvent evt) {
						try {
							evt.acceptDrop(DnDConstants.ACTION_COPY);
							List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
							for (File file : droppedFiles) {
								//Tenemos el fichero
								ShowImageGUI w = new ShowImageGUI(owner, file);
								w.setVisible(true);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				
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
						System.out.println(f.getAbsolutePath());
					}
				});
				panelBtnFileChooser.add(btnFileChooser);

	}
	
	public static void main(String[] args) {
		AddFotoGUI w = new AddFotoGUI(new JFrame());
		w.setVisible(true);
	}

}
