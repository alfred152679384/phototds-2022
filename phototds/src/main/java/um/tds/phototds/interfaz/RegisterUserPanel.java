package um.tds.phototds.interfaz;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import um.tds.phototds.controlador.Controlador;
import um.tds.phototds.dominio.Usuario;

public class RegisterUserPanel extends JDialog{
	//Constantes
	private static int DEFAULT_H = 600;
	private static int DEFAULT_W = 400;
	private static int DEFAULT_COLUMNS = 30;
	//variables
	private JDialog registerUserInterfaz;
	//private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JTextField txtCont;
	private JTextField txtFeNa;

	/**
	 * Create the application.
	 */
	public RegisterUserPanel(JFrame owner) {
		super(owner,"Registro de Usuario",true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initialize();
	}

	//	public void mostrarVentana() {
	//		frame.setVisible(true);
	//	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(500, 100, DEFAULT_W, DEFAULT_H);
		//Creamos el panel general
		JPanel panelGeneral = new JPanel();
		this.getContentPane().add(panelGeneral);
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
		JButton btnOK = new JButton("OK");
		addManejadorBtnOK(btnOK);
		JButton btnCancel = new JButton("Cancel");
		panelSur.add(btnOK);
		panelSur.add(btnCancel);
	}

	private void addManejadorBtnOK(JButton btnOK) {
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( Controlador.INSTANCE.registerUser(txtUsuario.getText(),txtNombre.getText(),txtEmail.getText(), txtCont.getText(), txtFeNa.getText(), null, null)) {
					JOptionPane.showMessageDialog(RegisterUserPanel.this, "Usuario registrado correctamente");
					RegisterUserPanel.this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(RegisterUserPanel.this, "El usuario ya existe",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	private void addManejadorBtnCancel(JButton btnCancel) {

	}

}
