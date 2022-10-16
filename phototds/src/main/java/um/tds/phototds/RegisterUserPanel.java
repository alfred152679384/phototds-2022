package um.tds.phototds;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.DomainCombiner;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RegisterUserPanel implements ActionListener {
	//Constantes
	private static int DEFAULT_X_Y = 0 ;	
	private static int DEFAULT_H = 600;
	private static int DEFAULT_W = 400;
	private static int DEFAULT_COLUMNS = 30;
	//variables
	private PhotoTDS_App mainP;
	private JFrame frame;
	private JButton btnOK;
	private JButton btnCancel;
	private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JTextField txtCont;
	private JTextField txtFeNa;
	private Usuario u;

	/**
	 * Create the application.
	 */
	//	public static void main(String[] args) {
	//		new RegisterUserPanel();
	//	}
	public RegisterUserPanel(PhotoTDS_App mainP) {
		this.mainP = mainP;
		initialize();
		frame.setVisible(true);

	}

	public Usuario getUsuario() {
		return this.u;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Registro de Usuario");
		frame.setBounds(100, 100, DEFAULT_W, DEFAULT_H);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Creamos el panel general
		JPanel panelGeneral = new JPanel();
		frame.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout());

		//Creamos elementos dentro del border-layout

		//panel norte
		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);

		//--->Elementos del panel norte
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

		//------->Dentro del box-layout tenemos que meter paneles
		JPanel pTitulo = new JPanel();
		JLabel lbTitulo = new JLabel("Photo TDS");
		lbTitulo.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 30));
		pTitulo.add(lbTitulo);
		panelNorte.add(new JPanel());
		panelNorte.add(pTitulo);

		JPanel pSubTit = new JPanel();
		pSubTit.setBorder(new EmptyBorder(10, 10, 0, 10));
		JTextArea jtSubTit = new JTextArea("Si te registras podrás compartir fotos y"
				+ " ver las fotos de tus amigos");
		jtSubTit.setEditable(false);
		jtSubTit.setBackground(new Color(240, 240, 240));
		jtSubTit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jtSubTit.setWrapStyleWord(true);
		jtSubTit.setRows(2);
		jtSubTit.setColumns(DEFAULT_COLUMNS);
		jtSubTit.setLineWrap(true);
		//JLabel lbSubTit = new JLabel("Si te registras podrás compartir fotos y"
		//		+ " ver las fotos de tus amigos");
		pSubTit.add(jtSubTit);
		panelNorte.add(new JPanel());
		panelNorte.add(pSubTit);

		//panel central
		JPanel panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral,BoxLayout.Y_AXIS));
		panelCentral.add(new JPanel());//Panel para separación

		//--->Elementos panel central
		JPanel pEmail = new JPanel();
		JPanel pNombre = new JPanel();
		JPanel pUsuario = new JPanel();
		JPanel pCont = new JPanel();

		txtEmail = new JTextField("Email");
		txtNombre = new JTextField("Nombre Completo");
		txtUsuario = new JTextField("Nombre de Usuario");
		txtCont = new JTextField("Contraseña");
		txtEmail.setColumns(DEFAULT_COLUMNS);
		txtNombre.setColumns(DEFAULT_COLUMNS);
		txtUsuario.setColumns(DEFAULT_COLUMNS);
		txtCont.setColumns(DEFAULT_COLUMNS);
		pEmail.add(txtEmail);
		pNombre.add(txtNombre);
		pUsuario.add(txtUsuario);
		pCont.add(txtCont);
		panelCentral.add(pEmail);
		panelCentral.add(pNombre);
		panelCentral.add(pUsuario);
		panelCentral.add(pCont);

		JPanel pFeNa = new JPanel();
		JPanel pFoto = new JPanel();
		JPanel pPres = new JPanel();
		pFeNa.setLayout(new FlowLayout());
		pFoto.setLayout(new FlowLayout());
		pPres.setLayout(new FlowLayout());
		JLabel lFeNa = new JLabel("Fecha de Nacimiento ");
		JLabel lFoto = new JLabel("Añadir foto del usuario (opcional) ");
		JLabel lPres = new JLabel("Añadir presentación (opcional) ");
		txtFeNa = new JTextField(LocalDate.now().toString());
		txtFeNa.setToolTipText("yyyy-mm-dd");
		//tFeNa.s
		//
		JButton bFeNa = new JButton();
		bFeNa.setIcon(new ImageIcon("E:/UNIVERSIDAD/3.%BA/C1/TDS/workspace/TDS_practicas/calendar.ico"));
		JButton bFoto = new JButton("+");
		JButton bPres = new JButton("...");
		pFeNa.setBorder(new LineBorder(Color.black, 1));
		pFoto.setBorder(new LineBorder(Color.black, 1));
		pPres.setBorder(new LineBorder(Color.black, 1));
		pFeNa.add(lFeNa);
		pFoto.add(lFoto);
		pPres.add(lPres);
		pFeNa.add(txtFeNa);
		pFeNa.add(bFeNa);
		pFoto.add(bFoto);
		pPres.add(bPres);
		panelCentral.add(pFeNa);
		panelCentral.add(new JPanel());
		panelCentral.add(pFoto);
		panelCentral.add(new JPanel());
		panelCentral.add(pPres);

		//panel este
		JPanel panelEste = new JPanel();
		panelGeneral.add(panelEste, BorderLayout.EAST);

		//panel oeste
		JPanel panelOeste = new JPanel();
		panelGeneral.add(panelOeste, BorderLayout.WEST);

		//panel sur
		JPanel panelSur = new JPanel();
		panelGeneral.add(panelSur, BorderLayout.SOUTH);

		//--->Elementos del panel sur
		panelSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnOK = new JButton("OK");
		btnCancel = new JButton("Cancel");
		panelSur.add(btnOK);
		panelSur.add(btnCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOK) {
			this.u = new Usuario(txtEmail.getText(), txtNombre.getText(), txtUsuario.getText(), txtCont.getText(), LocalDate.parse(txtFeNa.getText()), null);
			if(!mainP.registrarUsuario(u)) {
				JOptionPane.showMessageDialog(
						frame, "Nombre de usuario o contraseña no valido",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(
						frame, "usuario valido",
						"Exito", JOptionPane.OK_OPTION);
			}
		}
		else if(e.getSource() == btnCancel) {
			this.u = null;
			this.frame.setVisible(false);
		}

	}

}
