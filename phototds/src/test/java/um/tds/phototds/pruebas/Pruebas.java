package um.tds.phototds.pruebas;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import um.tds.phototds.controlador.Controlador;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

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
		panelEntradaFoto.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 5, 0), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		panelEntradaFoto.setPreferredSize(new Dimension(frame.getWidth()-10, DEFAULT_Y+20));
		panelCentral.add(panelEntradaFoto);
		panelEntradaFoto.setLayout(new BoxLayout(panelEntradaFoto, BoxLayout.X_AXIS));
		
		JPanel panelFoto = new JPanel();
		panelEntradaFoto.add(panelFoto);
		
		BufferedImage foto;
		try {
			File f = new File("C:\\Users\\Usuario\\Downloads\\Telegram Desktop\\castillo.jpg");
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
		panelInfo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelEntradaFoto.add(panelInfo);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		
		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelBotones);
		
		JPanel panelBtnMG = new JPanel();
		panelBotones.add(panelBtnMG);
		
		JButton btnMG = new JButton("Me Gusta");
		panelBtnMG.add(btnMG);
		
		JPanel panelBtnComentarios = new JPanel();
		panelBotones.add(panelBtnComentarios);
		
		JButton btnComentarios = new JButton("Comentarios");
		panelBtnComentarios.add(btnComentarios);
		
		JPanel panelContadorMG = new JPanel();
		panelBotones.add(panelContadorMG);
		
		JLabel lblContadorMG = new JLabel(/*get Megusta ????*/"X Me gusta");
		panelContadorMG.add(lblContadorMG);
		
		JPanel panelPerfilUsuario = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelPerfilUsuario.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelPerfilUsuario);
		
		JLabel lblFotoUsuario = new JLabel("Foto");
		panelPerfilUsuario.add(lblFotoUsuario);
		
		JLabel lblNombreUsuario = new JLabel(/*Controlador.INSTANCE.getUsuarioActual()*/"user");
		panelPerfilUsuario.add(lblNombreUsuario);
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

