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
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;

import um.tds.phototds.controlador.ComunicacionConGUI;
import um.tds.phototds.controlador.Controlador;

public class ShowImageGUI extends JDialog {
	// Necesario para eliminar warnings
	private static final long serialVersionUID = 1L;
	// Constantes
	private static final double DEFAULT_X = 400.0;
	private static final double DEFAULT_Y = 400.0;
	private static final int BOUND_X = 600;
	private static final int BOUND_Y = 300;
	private static final int DEFAULT_PROP = 100;

	public static final int MODE_FOTO_ONLY = 0;
	public static final int MODE_PUBLICAR_FOTO = 1;
	public static final int MODE_FOTO_COMENTARIO = 2;
	public static final int MODE_ALBUM = 3;

	// Atributos
	private File imageFile;
	private JTextArea textTitulo;
	private JTextArea textDesc;
	private ImageIcon iconImage;
	private ComunicacionConGUI com;
	private int mode;
	private boolean ok;

	// Constructores
	public ShowImageGUI(JFrame owner, File imageFile) {
		super(owner, "Mostrar Foto", true);
		this.imageFile = imageFile;
		this.mode = MODE_PUBLICAR_FOTO;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initialize();
	}

	public ShowImageGUI(JFrame owner, ComunicacionConGUI com , int mode) {
		super(owner, "Mostrar Foto", true);
		this.imageFile = new File(com.getPathFoto());
		this.com = com;
		this.mode = mode;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initialize();
	}
	
	public ShowImageGUI(JFrame owner, File imageFile, int mode) {
		super(owner, "Mostrar Foto", true);
		this.imageFile = imageFile;
		this.mode = mode;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.ok = false;
		initialize();
	}
	
	private void initialize() {
		getContentPane().setLayout(new BorderLayout(5, 5));
		crearPanelFoto();
		if (mode == MODE_PUBLICAR_FOTO || mode == MODE_FOTO_COMENTARIO || mode == MODE_ALBUM) {
			crearPanelComentarios();
			crearBotones();
		}
	}

	private void crearBotones() {
		JPanel panelSur = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSur.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelSur, BorderLayout.SOUTH);

		JPanel panelBtnComp = new JPanel();
		panelSur.add(panelBtnComp);

		// Botón para compartir
		if(mode == MODE_PUBLICAR_FOTO) {
			JButton btnCompartir = new JButton("Compartir");
			btnCompartir.addActionListener(e -> {
				Controlador.INSTANCE.addFoto(imageFile.getAbsolutePath(), textTitulo.getText(), textDesc.getText());
				this.dispose();
			});
			panelBtnComp.add(btnCompartir);
		}else if (mode == MODE_FOTO_COMENTARIO){
			JButton btnCompartir = new JButton("OK");
			btnCompartir.addActionListener(ev -> {
				Controlador.INSTANCE.escribirComentario(com.getIdPublicacion(), textDesc.getText());
				this.dispose();
			});
			panelBtnComp.add(btnCompartir);
		}else if(mode == MODE_ALBUM) {
			JButton btnCompartir = new JButton("Añadir");
			btnCompartir.addActionListener(ev -> {
				this.ok = true;
				this.dispose();
			});
			panelBtnComp.add(btnCompartir);
		}

		// Botón para cancelar
		JPanel panelBtnCancel = new JPanel();
		panelSur.add(panelBtnCancel);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> {
			this.textTitulo.setText("");
			this.textDesc.setText("");
			this.ok = false;
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

		if (mode == MODE_PUBLICAR_FOTO || mode == MODE_ALBUM) {
			// Titulo de foto
			JPanel panelTitulo = new JPanel();
			panelDescripcion.add(panelTitulo);
			panelTitulo.setLayout(new BorderLayout(0, 0));

			JPanel panelNorteTitulo = new JPanel();
			panelTitulo.add(panelNorteTitulo, BorderLayout.NORTH);

			JLabel lblTitulo = new JLabel("Escribe un titulo ");
			panelNorteTitulo.add(lblTitulo);

			// Panel para el comentario (texto)
			JPanel panelCentroTitulo = new JPanel();
			panelTitulo.add(panelCentroTitulo, BorderLayout.CENTER);

			// Titulo de foto
			textTitulo = new JTextArea();
			textTitulo.setRows(1);
			textTitulo.setColumns(20);
			textTitulo.setWrapStyleWord(true);
			textTitulo.setLineWrap(true);
			panelCentroTitulo.add(textTitulo);
		}

		JPanel panelComentario = new JPanel();
		panelDescripcion.add(panelComentario);
		panelComentario.setLayout(new BorderLayout(0, 0));

		JPanel panelDescNorte = new JPanel();
		panelComentario.add(panelDescNorte, BorderLayout.NORTH);

		// Pie de foto -> descripción
		JLabel lblComentario;
		if(mode == MODE_PUBLICAR_FOTO || mode == MODE_ALBUM)
			lblComentario = new JLabel("Escribe una descripción");
		else
			lblComentario = new JLabel("Escribe un comentario");
		panelDescNorte.add(lblComentario);

		JPanel panelDescCentro = new JPanel();
		panelComentario.add(panelDescCentro);

		textDesc = new JTextArea(15, 20);
		textDesc.setWrapStyleWord(true);
		textDesc.setTabSize(4);
		textDesc.setLineWrap(true);
		
		JScrollPane scrollText = new JScrollPane(textDesc);
		panelDescCentro.add(scrollText);
	}

	private void crearPanelFoto() {
		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));

		JPanel panelFoto = new JPanel();
		panelCentral.add(panelFoto);

		// Foto original
		BufferedImage foto;
		try {
			// Leemos la foto
			foto = ImageIO.read(imageFile);
			JLabel picLabel = new JLabel();

			// Dimensionamos
			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProp(size);

			if (size[1] < BOUND_Y - DEFAULT_PROP) {
				setBounds(100, 100, BOUND_X, BOUND_Y);
			} else {
				setBounds(100, 100, BOUND_X, (int) (size[1] + DEFAULT_PROP));
			}

			// Creamos el JLabel con la nueva foto
			Image resizedImage;
			resizedImage = foto.getScaledInstance((int) size[0], (int) size[1], Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(resizedImage));
			panelFoto.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public String getTitulo() {
		return this.textTitulo.getText();
	}
	
	public String getDescripcion() {
		return this.textDesc.getText();
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
