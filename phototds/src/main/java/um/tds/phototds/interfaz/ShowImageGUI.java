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
import javax.swing.Icon;
import javax.swing.JTextArea;
import java.awt.Component;

public class ShowImageGUI extends JDialog {
	//Constantes
	private static final double DEFAULT_X = 300.0;
	private static final double DEFAULT_Y = 300.0;
	private static final int BOUND_X = 600;
	private static final int BOUND_Y = 400; 
	
	//Atributos
	private BufferedImage fotoDimensionada = null;
	
	
	public ShowImageGUI(JFrame owner, File imageFile) {
		super(owner, "Añadir Foto", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		try {
			fotoDimensionada = ImageIO.read(imageFile);
			//Image foto = ImageIO.read(imageFile);
			double[] size = new double[2];
			size[0] = fotoDimensionada.getWidth(null);
			size[1] = fotoDimensionada.getHeight(null);
			setProp(size);
			fotoDimensionada.getScaledInstance((int)size[0], (int)size[1], 0);
			//fotoDimensionada = new BufferedImage((int)size[0], (int)size[1], BufferedImage.);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		getContentPane().setLayout(new BorderLayout(5, 5));
		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		setBounds(100, 100, BOUND_X, BOUND_Y);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));
		
		JPanel panelFoto = new JPanel();
		panelCentral.add(panelFoto);
		
		JLabel picLabel = new JLabel();
		picLabel.setIcon(new ImageIcon(this.fotoDimensionada));
		panelFoto.add(picLabel);
		
		JPanel panelSur = new JPanel();	
		FlowLayout flowLayout = (FlowLayout) panelSur.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JPanel panelBtnCancel = new JPanel();
		panelSur.add(panelBtnCancel);
		
		JButton btnCompartir = new JButton("Compartir");
		panelBtnCancel.add(btnCompartir);
		
		JPanel panelBtnComp = new JPanel();
		panelSur.add(panelBtnComp);
		
		JButton btnCancelar = new JButton("Cancelar");
		panelBtnComp.add(btnCancelar);
		
		JPanel panelEste = new JPanel();
		getContentPane().add(panelEste, BorderLayout.EAST);
		
		JPanel panelDescripcion = new JPanel();
		panelEste.add(panelDescripcion);
		panelDescripcion.setLayout(new BorderLayout(0, 0));
	}
	
	private static void setProp(double[] size) {//SetProporcion
		double prop;
		if(size[0]>= DEFAULT_X) {
			prop = size[0]/size[1];
			size[0] = DEFAULT_X;
			size[1] = DEFAULT_X/prop;
		}
		if(size[1]>= DEFAULT_Y){
			prop = size[1]/size[0];
			size[1] = DEFAULT_Y;
			size[0] = DEFAULT_Y/prop;			
		}
	}

}
