package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

import um.tds.phototds.controlador.Controlador;

public class ShowImageGUI extends JDialog {
	// Necesario para eliminar warnings
	private static final long serialVersionUID = 1L;
	// Constantes
	private static final double DEFAULT_X = 350.0;
	private static final double DEFAULT_Y = 325.0;
	private static final int BOUND_X = 600;
	private static final int BOUND_Y = 300;
	private static final int DEFAULT_PROP = 100;
	
	//Atributos
	private File imageFile;
	private JTextArea textTitulo;
	private JTextArea textDesc;

	public ShowImageGUI(JFrame owner, File imageFile) {
		super(owner, "Añadir Foto", true);
		this.imageFile = imageFile;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initialize();
	}

	private void initialize() {
		getContentPane().setLayout(new BorderLayout(5, 5));
		crearPanelFoto();
		crearPanelComentarios();
		crearBotones();
	}
	
	private void crearBotones() {
		JPanel panelSur = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSur.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JPanel panelBtnComp = new JPanel();
		panelSur.add(panelBtnComp);

		//Botón para compartir
		JButton btnCompartir = new JButton("Compartir");
		btnCompartir.addActionListener(e -> {
			Controlador.INSTANCE.addFoto(imageFile.getAbsolutePath(), textTitulo.getText(), textDesc.getText());
			this.dispose();
		});
		panelBtnComp.add(btnCompartir);

		//Botón para cancelar
		JPanel panelBtnCancel = new JPanel();
		panelSur.add(panelBtnCancel);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> {
			this.dispose();
		});
		panelBtnCancel.add(btnCancelar);
	}

	private void crearPanelComentarios() {
		JPanel panelEste = new JPanel();
		getContentPane().add(panelEste, BorderLayout.EAST);

		JPanel panelDescripcion = new JPanel();
		panelEste.add(panelDescripcion);
		panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));

		//Titulo de comentario
		JPanel panelTitulo = new JPanel();
		panelDescripcion.add(panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));

		JPanel panelNorteTitulo = new JPanel();
		panelTitulo.add(panelNorteTitulo, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("Escribe un titulo ");
		panelNorteTitulo.add(lblTitulo);

		//Panel para el comentario (texto)
		JPanel panelCentroTitulo = new JPanel();
		panelTitulo.add(panelCentroTitulo, BorderLayout.CENTER);

		//Titulo de foto
		textTitulo = new JTextArea();
		textTitulo.setRows(1);
		textTitulo.setColumns(20);
		textTitulo.setWrapStyleWord(true);
		textTitulo.setLineWrap(true);
		panelCentroTitulo.add(textTitulo);

		JPanel panelComentario = new JPanel();
		panelDescripcion.add(panelComentario);
		panelComentario.setLayout(new BorderLayout(0, 0));

		JPanel panelDescNorte = new JPanel();
		panelComentario.add(panelDescNorte, BorderLayout.NORTH);

		//Pie de foto
		JLabel lblComentario = new JLabel("Escribe un comentario");
		panelDescNorte.add(lblComentario);

		JPanel panelDescCentro = new JPanel();
		panelComentario.add(panelDescCentro);

		textDesc = new JTextArea(7, 20);
		textDesc.setWrapStyleWord(true);
		textDesc.setTabSize(4);
		textDesc.setLineWrap(true);
		panelDescCentro.add(textDesc);
	}
	
	private void crearPanelFoto() {
		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));
		
		JPanel panelFoto = new JPanel();
		panelCentral.add(panelFoto);

		//Foto original
		BufferedImage foto;
		try {
			//Leemos la foto
			foto = ImageIO.read(imageFile);
			JLabel picLabel = new JLabel();
			
			//Dimensionamos
			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProp(size);
			
			if (size[1] < BOUND_Y - DEFAULT_PROP) {
				setBounds(100, 100, BOUND_X, BOUND_Y);
			} else {
				setBounds(100, 100, BOUND_X, (int) (size[1] + DEFAULT_PROP));
			}

			//Creamos el JLabel con la nueva foto
			Image resizedImage;
			resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(resizedImage));
			panelFoto.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setProp(double[] size) {// SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0];
		newSize[1] = size[1];
		double prop;
		if (size[0] >= DEFAULT_X) {
			prop = size[0] / size[1];
			newSize[0] = DEFAULT_X;
			newSize[1] = DEFAULT_X / prop;
		}
		if (newSize[1] >= DEFAULT_Y) {
			prop = size[1] / size[0];
			newSize[1] = DEFAULT_Y;
			newSize[0] = DEFAULT_Y / prop;
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}

}
