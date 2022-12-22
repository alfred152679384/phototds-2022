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
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JTextArea;

import um.tds.phototds.controlador.Controlador;

import java.awt.Component;

public class ShowImageGUI extends JDialog {
	//Constantes
	private static final double DEFAULT_X = 350.0;
	private static final double DEFAULT_Y = 325.0;
	private static final int BOUND_X = 600;
	private static final int BOUND_Y = 300; 	

	public ShowImageGUI(JFrame owner, File imageFile) {
		super(owner, "Añadir Foto", true);
		BufferedImage foto;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		try {
			foto = ImageIO.read(imageFile);
			getContentPane().setLayout(new BorderLayout(5, 5));
			JPanel panelCentral = new JPanel();
			getContentPane().add(panelCentral, BorderLayout.CENTER);

			panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));

			JPanel panelFoto = new JPanel();
			panelCentral.add(panelFoto);	

			JLabel picLabel = new JLabel();
			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProp(size);
			if(size[1] < BOUND_Y-100) {
				setBounds(100, 100, BOUND_X, BOUND_Y);
			}else { 
				setBounds(100, 100, BOUND_X, (int)(size[1]+100));
			}

			Image resizedImage;
			resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(resizedImage));
			panelFoto.add(picLabel);

			JPanel panelEste = new JPanel();
			getContentPane().add(panelEste, BorderLayout.EAST);

			JPanel panelDescripcion = new JPanel();
			panelEste.add(panelDescripcion);
			panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
			
			JPanel panelTitulo = new JPanel();
			panelDescripcion.add(panelTitulo);
			panelTitulo.setLayout(new BorderLayout(0, 0));
			
			JPanel panelNorteTitulo = new JPanel();
			panelTitulo.add(panelNorteTitulo, BorderLayout.NORTH);
			
			JLabel lblTitulo = new JLabel("Escribe un titulo ");
			panelNorteTitulo.add(lblTitulo);
			
			JPanel panelCentroTitulo = new JPanel();
			panelTitulo.add(panelCentroTitulo, BorderLayout.CENTER);
			
			JTextArea textTitulo = new JTextArea();
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
			
			JLabel lblComentario = new JLabel("Escribe un comentario");
			panelDescNorte.add(lblComentario);
			
			JPanel panelDescCentro = new JPanel();
			panelComentario.add(panelDescCentro);
			
			JTextArea textDesc = new JTextArea(7, 20);
			textDesc.setWrapStyleWord(true);
			textDesc.setTabSize(4);
			textDesc.setLineWrap(true);
			panelDescCentro.add(textDesc);
			
			JPanel panelSur = new JPanel();	
			FlowLayout flowLayout = (FlowLayout) panelSur.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			getContentPane().add(panelSur, BorderLayout.SOUTH);
			
			JPanel panelBtnComp = new JPanel();
			panelSur.add(panelBtnComp);
			
			JButton btnCompartir = new JButton("Compartir");
			btnCompartir.addActionListener(e -> {
				Controlador.INSTANCE.addFoto(imageFile.getAbsolutePath(), textTitulo.getText(), textDesc.getText());
				this.dispose();
			});
			panelBtnComp.add(btnCompartir);
			
			JPanel panelBtnCancel = new JPanel();
			panelSur.add(panelBtnCancel);
			
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(e -> {
				this.dispose();
			});
			panelBtnCancel.add(btnCancelar);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void setProp(double[] size) {//SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0]; 
		newSize[1] = size[1]; 		
		double prop;
		if(size[0]>= DEFAULT_X) {
			prop = size[0]/size[1];
			newSize[0] = DEFAULT_X;
			newSize[1] = DEFAULT_X/prop;
			//			System.out.println("1:new size = ["+newSize[0]+"]["+size[1]+"]");
		}
		if(newSize[1]>= DEFAULT_Y){
			prop = size[1]/size[0];
			newSize[1] = DEFAULT_Y;
			newSize[0] = DEFAULT_Y/prop;			
			//			System.out.println("2 new size = ["+size[0]+"]["+size[1]+"]");
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
		//		System.out.println("new size = ["+size[0]+"]["+size[1]+"]");
	}

}
