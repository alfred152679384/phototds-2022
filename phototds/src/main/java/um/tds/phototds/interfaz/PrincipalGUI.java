package um.tds.phototds.interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Scrollbar;

public class PrincipalGUI extends JFrame {
	private JFrame framePrincipal;
	private JTextField txtBuscador;

	/**
	 * Create the frame.
	 */
	public PrincipalGUI() {
		initialize();
	}
	
	public void mostrarVentana() {
		framePrincipal.setResizable(false);
		framePrincipal.setLocationRelativeTo(null);
		framePrincipal.setVisible(true);
	}

	private void initialize() {
		framePrincipal = new JFrame("Photo TDS");
		framePrincipal.setBounds(100, 100, 537, 600);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelGeneral = new JPanel();
		framePrincipal.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout(0, 0));

		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));

		JPanel panelTitulo = new JPanel();
		panelNorte.add(panelTitulo);

		JLabel lblTitulo = new JLabel("Photo TDS");
		lblTitulo.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		panelTitulo.add(lblTitulo);

		JPanel panelPadding0 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelPadding0.getLayout();
		flowLayout.setHgap(20);
		panelNorte.add(panelPadding0);

		JPanel panelBtnAdd = new JPanel();
		panelNorte.add(panelBtnAdd);

		JButton btnAddFoto = new JButton("+");
		btnAddFoto.addActionListener(e -> {
			AddFotoGUI w = new AddFotoGUI(this.framePrincipal);
			w.setVisible(true);
		});
		panelBtnAdd.add(btnAddFoto);

		JPanel panelBuscador = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelBuscador.getLayout();
		flowLayout_2.setVgap(10);
		panelNorte.add(panelBuscador);

		txtBuscador = new JTextField();
		panelBuscador.add(txtBuscador);
		txtBuscador.setColumns(10);

		JButton btnLupa = new JButton();
		try {
			Image imgLupa = ImageIO.read(getClass().getResource("E:\\UNIVERSIDAD\\3.º\\C1\\TDS\\workspace\\lupa.bmp"));
			btnLupa.setIcon(new ImageIcon(imgLupa));
		} catch (Exception ex) {
			System.out.println(ex);
		}	
		panelBuscador.add(btnLupa);

		JPanel panelPadding1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelPadding1.getLayout();
		flowLayout_1.setHgap(20);
		panelNorte.add(panelPadding1);

		JPanel panelAjustes = new JPanel();
		panelNorte.add(panelAjustes);

		JLabel lblFotoPerfil = new JLabel("Foto");
		lblFotoPerfil.setVerticalAlignment(SwingConstants.BOTTOM);
		lblFotoPerfil.setIcon(new ImageIcon("E:\\UNIVERSIDAD\\3.º\\C1\\TDS\\workspace\\fotoPerfil.ico"));
		panelAjustes.add(lblFotoPerfil);
		
		JButton btnNewButton = new JButton("Ajustes");
		panelAjustes.add(btnNewButton);
		
		JPanel panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);
		
		JList listFoto = new JList();
		panelCentral.add(listFoto);
		
		Scrollbar scrollbar = new Scrollbar();
		panelCentral.add(scrollbar);
	}

}
