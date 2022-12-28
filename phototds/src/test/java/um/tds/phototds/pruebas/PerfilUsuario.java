package um.tds.phototds.pruebas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

public class PerfilUsuario{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PerfilUsuario window = new PerfilUsuario();
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
	public PerfilUsuario() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panelCentral = new JPanel();
		frame.getContentPane().add(panelCentral);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		JPanel panelInfoUser = new JPanel();
		panelCentral.add(panelInfoUser);
		panelInfoUser.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFotoPerfil = new JPanel();
		panelInfoUser.add(panelFotoPerfil, BorderLayout.WEST);
		
		JLabel lblFotoPerfil = new JLabel("(Foto Perfil) not button");
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		panelFotoPerfil.add(lblFotoPerfil);
		
		JPanel panelInfo = new JPanel();
		panelInfoUser.add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		
		JPanel panelUsername = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelUsername.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelUsername);
		
		JLabel lblUsername = new JLabel("Correo");
		panelUsername.add(lblUsername);
		
		JPanel panelPadding0 = new JPanel();
		panelUsername.add(panelPadding0);
		
		JButton btnEditPerfil = new JButton("Editar perfil");
		btnEditPerfil.setForeground(Color.WHITE);
		btnEditPerfil.setBackground(new Color(0, 0, 255));
		btnEditPerfil.setOpaque(true);
		panelUsername.add(btnEditPerfil);
		
		JPanel panelEstadisticas = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelEstadisticas.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelEstadisticas);
		
		JLabel lblNumPublics = new JLabel("X publicaciones");
		panelEstadisticas.add(lblNumPublics);
		
		JPanel panelPadding1 = new JPanel();
		panelEstadisticas.add(panelPadding1);
		
		JLabel lblNumSeguidores = new JLabel("Y seguidores");
		panelEstadisticas.add(lblNumSeguidores);
		
		JPanel panelPadding2 = new JPanel();
		panelEstadisticas.add(panelPadding2);
		
		JLabel lblNumSeguidos = new JLabel("Z seguidos");
		panelEstadisticas.add(lblNumSeguidos);
		
		JPanel panelNombre = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelNombre.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panelInfo.add(panelNombre);
		
		JLabel lblNombre = new JLabel("Nombre Completo Usuario");
		panelNombre.add(lblNombre);
		
		JPanel panelFotos = new JPanel();
		panelCentral.add(panelFotos);
		panelFotos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFotoAlbumes = new JPanel();
		panelFotos.add(panelFotoAlbumes, BorderLayout.NORTH);
		
		JButton btnFotos = new JButton("FOTOS");
		btnFotos.setBackground(Color.WHITE);
		btnFotos.setOpaque(true);
		panelFotoAlbumes.add(btnFotos);
		
		JButton btnAlbumes = new JButton("ALBUMES");
		btnAlbumes.setBackground(Color.WHITE);
		btnAlbumes.setOpaque(true);
		panelFotoAlbumes.add(btnAlbumes);
		
		cargarFotos(panelFotos);
	}
	
	private void cargarFotos(JPanel panelFotos) {
		int cont = 0;
		int width = frame.getWidth()/3-12;
		DefaultListModel<ImageIcon> model = new DefaultListModel<>();
		JList<ImageIcon> lista;
		BufferedImage foto;
		
		try {//Cargamos las imágenes en el modelo
			//new image
			File fi= new File("resources\\photos\\photo18.jpg");
			foto = ImageIO.read(fi);

			double[] size = new double[2];
			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProfileProp(size, width, width);

			Image resizedImage;
			resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(resizedImage);
			
			model.add(cont, icon);
			cont++;
			
			//new image
			fi= new File("resources\\photos\\photo0.jpg");
			foto = ImageIO.read(fi);

			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProfileProp(size, width, width);

			resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			icon = new ImageIcon(resizedImage);
			
			model.add(cont, icon);
			cont++;			
			
			//new image
			fi= new File("resources\\photos\\photo2.jpg");
			foto = ImageIO.read(fi);

			size[0] = foto.getWidth(null);
			size[1] = foto.getHeight(null);
			setProfileProp(size, width, width);

			resizedImage = foto.getScaledInstance((int)size[0], (int)size[1], Image.SCALE_SMOOTH);
			icon = new ImageIcon(resizedImage);
			
			model.add(cont, icon);
			cont++;		
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		lista = new JList<>(model);
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setFixedCellWidth(width);
		lista.setFixedCellHeight(width);
		lista.setBorder(new LineBorder(Color.black));
		
		JScrollPane scrollFotos = new JScrollPane(lista);
		panelFotos.add(scrollFotos, BorderLayout.CENTER);
	}
	
	private static void setProfileProp(double[] size, double x, double y) {//SetProporcion
		double[] newSize = new double[2];
		newSize[0] = size[0]; 
		newSize[1] = size[1]; 		
		double prop;
		if(size[1]>= size[0]) {//fotos verticales
			prop = size[0]/size[1];
			newSize[0] = x;
			newSize[1] = x/prop;
		}
		else {//fotos horizontales
			prop = size[1]/size[0];
			newSize[1] = y;
			newSize[0] = y/prop;			
		}

		size[0] = newSize[0];
		size[1] = newSize[1];
	}
}
