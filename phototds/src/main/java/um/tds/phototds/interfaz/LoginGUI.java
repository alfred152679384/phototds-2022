package um.tds.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import um.tds.phototds.controlador.Controlador;

public class LoginGUI{

	private JFrame frameLogin;
	private JTextField txtrNombreDeUsuario;
	private JPasswordField passwordField;
	//	//Repositorios
	//	private RepoUsuarios repoUsers;
	//	//Ventanas
	//	private RegisterUserPanel registerPanel;

	public LoginGUI() {
		initialize();
	}

	public void mostrarVentana() {
		frameLogin.setResizable(false);
		frameLogin.setLocationRelativeTo(null);
		frameLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameLogin = new JFrame("Inicio de Sesión");
		frameLogin.setBounds(100, 100, 537, 600);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(new BoxLayout(frameLogin.getContentPane(), BoxLayout.Y_AXIS));

		JPanel panelSuperior = new JPanel();
		panelSuperior.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 20, 10), new LineBorder(new Color(0, 0, 0), 2)));
		frameLogin.getContentPane().add(panelSuperior);
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(25, 10, 10, 10));
		panelSuperior.add(panelTitulo);

		JLabel lblNewLabel = new JLabel("Photo TDS");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 30));
		panelTitulo.add(lblNewLabel);

		JPanel panelTexto = new JPanel();
		panelSuperior.add(panelTexto);
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));

		JPanel panelUsuario = new JPanel();
		panelTexto.add(panelUsuario);

		txtrNombreDeUsuario = new JTextField();
		txtrNombreDeUsuario.setText("nombre de usuario o email");
		txtrNombreDeUsuario.setColumns(44);
		txtrNombreDeUsuario.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null));

		panelUsuario.add(txtrNombreDeUsuario);

		JPanel panelCont = new JPanel();
		panelTexto.add(panelCont);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("contraseña\r\n");
		passwordField.setColumns(44);
		panelCont.add(passwordField);


		JButton btnInicSes;
		btnInicSes = new JButton("Iniciar Sesión\r\n");
		btnInicSes.setForeground(new Color(0, 0, 255));
		btnInicSes.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnInicSes.setAlignmentX(Component.CENTER_ALIGNMENT);
		addManejadorBtnLogin(btnInicSes);
		panelSuperior.add(btnInicSes);

		JPanel panel_relleno = new JPanel();
		panelSuperior.add(panel_relleno);

		JPanel panelInferior = new JPanel();
		panelInferior.setBorder(new CompoundBorder(new EmptyBorder(10, 5, 20, 5), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		frameLogin.getContentPane().add(panelInferior);
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

		JPanel pTexto = new JPanel();
		panelInferior.add(pTexto);

		JTextArea txtRegistro = new JTextArea();
		txtRegistro.setEditable(false);
		txtRegistro.setColumns(22);
		txtRegistro.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtRegistro.setForeground(new Color(255, 0, 0));
		txtRegistro.setBackground(new Color(241, 241, 241));
		txtRegistro.setText("¿No tienes una cuenta?");
		txtRegistro.setAlignmentY(50);
		pTexto.add(txtRegistro);

		JPanel panel_btn = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_btn.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panelInferior.add(panel_btn);

		JPanel panelBorderBtn = new JPanel();
		panelBorderBtn.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		FlowLayout flowLayout_1 = (FlowLayout) panelBorderBtn.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panel_btn.add(panelBorderBtn);


		JButton btnRegister;
		btnRegister = new JButton("Crear una cuenta");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addManejadorBtnRegister(btnRegister);
		panelBorderBtn.add(btnRegister);

	}


	private void addManejadorBtnLogin(JButton btnLogin) {
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean login = Controlador.INSTANCE.loginUser(txtrNombreDeUsuario.getText(), passwordField.getText());
				if (login) {
					PrincipalGUI window = new PrincipalGUI();
					window.mostrarVentana();
					System.out.println("ok");
					frameLogin.dispose();
				} 
				else {
					JOptionPane.showMessageDialog(frameLogin, "Nombre de usuario o contraseña no valido",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void addManejadorBtnRegister(JButton btnRegister) {
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI v = new RegisterGUI(frameLogin);
				v.setVisible(true);
			}			
		});
	}
}
