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

public class ShowImageGUI extends JDialog {
	private static final int DEFAULT_X = 250;
//	private BufferedImage foto;
	private Image foto;
	public ShowImageGUI(JFrame owner, File imageFile) {
		super(owner, "Añadir Foto", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		try {
			this.foto = ImageIO.read(imageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		getContentPane().setLayout(new BorderLayout(5, 5));
		int[] size = new int[2];
		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		JLabel picLabel = new JLabel(new ImageIcon(foto.getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		picLabel.setSize(100, 100);
		setBounds(100, 100, 400, 350);
		panelCentral.add(picLabel);
	}
	
	private void setSize(int[] size) {
		int prop = size[0]/size[1];
		size[0] = 250;
		size[1] = 250/prop;
	}

}
