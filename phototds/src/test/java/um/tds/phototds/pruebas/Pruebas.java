package um.tds.phototds.pruebas;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pruebas {
	//Constantes
	private static final int DEFAULT_X = 100;
	private static final int DEFAULT_Y = 100;
	
	//Atributos
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pruebas window = new Pruebas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Pruebas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		JPanel panelEntradaFoto = new JPanel();
		panelCentral.add(panelEntradaFoto);
		panelEntradaFoto.setLayout(new BoxLayout(panelEntradaFoto, BoxLayout.X_AXIS));
		
		JPanel panelFoto = new JPanel();
		panelEntradaFoto.add(panelFoto);
		
		BufferedImage foto;
		try {
			File f = new File("C:\\Users\\Usuario\\Downloads\\Telegram Desktop\\fondo.jpg");
			foto = ImageIO.read(f);
			JLabel picLabel = new JLabel();
			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProp(size);

			Image resizedImage;
			resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(resizedImage));
			panelFoto.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JPanel panelInfo = new JPanel();
		panelEntradaFoto.add(panelInfo);
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

