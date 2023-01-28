package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import um.tds.phototds.controlador.Controlador;

import java.awt.Font;

public class CrearAlbumGUI extends JDialog {
	// Necesario para eliminar Warnings
	private static final long serialVersionUID = 1L;

	// Atributos
	private JFrame owner;
	private JPanel panelCentral;
	private JTextField txtTitulo;
	private JTextArea txtDescripcion;
	private boolean ok;

	/**
	 * Create the dialog.
	 */
	public CrearAlbumGUI(JFrame owner) {
		super(owner, true);
		this.owner = owner;
		this.ok = false;
		setBounds(500, 100, 300, 300);
		getContentPane().setLayout(new BorderLayout());
		initialize();
	}

	private void initialize() {
		crearPanelNorte();
		crearPanelCentral();
		crearPanelSur();
	}

	private void crearPanelNorte() {
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 5, 5), 
						new EtchedBorder(EtchedBorder.RAISED, null, null)));
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		{
			JLabel lblTituloPantalla = new JLabel("Crea un nuevo Álbum");
			lblTituloPantalla.setFont(new Font("Tahoma", Font.BOLD, 20));
			panelNorte.add(lblTituloPantalla);
		}
	}

	private void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		{
			JPanel panelAddTitulo = new JPanel();
			panelCentral.add(panelAddTitulo);
			{
				JLabel lblAddTitulo = new JLabel("Introduce un título");
				panelAddTitulo.add(lblAddTitulo);
			}
		}
		{
			txtTitulo = new JTextField();
			panelCentral.add(txtTitulo);
		}
		{
			JPanel panelAddDesc = new JPanel();
			panelCentral.add(panelAddDesc);
			{
				JLabel lblDescripcion = new JLabel("Añade una descripcion");
				panelAddDesc.add(lblDescripcion);
			}
		}
		{
			txtDescripcion = new JTextArea();
			txtDescripcion.setRows(5);
			txtDescripcion.setColumns(18);
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setWrapStyleWord(true);
			panelCentral.add(txtDescripcion);
		}

	}

	private void crearPanelSur() {
		JPanel panelSur = new JPanel();
		panelSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(panelSur, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			addManejadorBtnOK(okButton);
			panelSur.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(ev -> {
				CrearAlbumGUI.this.dispose();
				this.ok = false;
			});
			panelSur.add(cancelButton);
		}
	}

	private void addManejadorBtnOK(JButton okButton) {
		okButton.addActionListener(ev -> {
			// No añade título
			String titulo = txtTitulo.getText();
			if (titulo.equals("")) {
				JOptionPane.showMessageDialog(CrearAlbumGUI.this, "No ha introducido un "
						+ "título", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Titulo ya existe
			if (Controlador.INSTANCE.comprobarTituloAlbum(titulo)) {
				JOptionPane.showMessageDialog(CrearAlbumGUI.this, "Ya existe una publicación"
						+ " con ese título", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Cogemos las fotos
			this.ok = true;
			AddFotoGUI w = new AddFotoGUI(owner, AddFotoGUI.MODE_ALBUM);
			w.setVisible(true);
			Controlador.INSTANCE.addAlbum(titulo,txtDescripcion.getText(),w.getListTitulos(),
					w.getListDescripciones(), w.getListPaths());
			CrearAlbumGUI.this.dispose();
		});
	}

	public boolean getOk() {
		return this.ok;
	}

}
