package um.tds.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

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

		crearPanelInicioSesion();
		crearPanelNuevaCuenta();

	}

	private void crearPanelInicioSesion() {
		JPanel panelSuperior = new JPanel();//Panel Inicio de Sesión
		panelSuperior.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 20, 10), new LineBorder(new Color(0, 0, 0), 2)));
		frameLogin.getContentPane().add(panelSuperior);
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(25, 10, 10, 10));
		panelSuperior.add(panelTitulo);

		JLabel lblTitulo = new JLabel("Photo TDS");
		lblTitulo.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 30));
		panelTitulo.add(lblTitulo);

		JPanel panelDatos = new JPanel();
		panelSuperior.add(panelDatos);
		panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));

		JPanel panelIntroducirUsuario = new JPanel();
		panelDatos.add(panelIntroducirUsuario);

		txtrNombreDeUsuario = new JTextField();
		txtrNombreDeUsuario.setText("nombre de usuario o email");
		txtrNombreDeUsuario.setColumns(44);
		txtrNombreDeUsuario.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null));

		panelIntroducirUsuario.add(txtrNombreDeUsuario);

		JPanel panelPassword = new JPanel();
		panelDatos.add(panelPassword);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("contraseña\r\n");
		passwordField.setColumns(44);
		panelPassword.add(passwordField);


		JButton btnInicSes;
		btnInicSes = new JButton("Iniciar Sesión\r\n");
		btnInicSes.setForeground(Color.WHITE);
		btnInicSes.setBackground(Color.BLUE);
		btnInicSes.setOpaque(true);
		btnInicSes.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnInicSes.setAlignmentX(Component.CENTER_ALIGNMENT);
		addManejadorBtnLogin(btnInicSes);
		frameLogin.getRootPane().setDefaultButton(btnInicSes);
		panelSuperior.add(btnInicSes);

		JPanel panelPadding0 = new JPanel();
		panelSuperior.add(panelPadding0);
	}
	
	private void crearPanelNuevaCuenta() {
		JPanel panelInferior = new JPanel();//Panel crear nueva cuenta
		panelInferior.setBorder(new CompoundBorder(new EmptyBorder(10, 5, 20, 5), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		frameLogin.getContentPane().add(panelInferior);
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

		JPanel panelTexto = new JPanel();
		panelInferior.add(panelTexto);

		JTextArea txtRegistro = new JTextArea();
		txtRegistro.setEditable(false);
		txtRegistro.setColumns(22);
		txtRegistro.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtRegistro.setForeground(Color.RED);
		txtRegistro.setBackground(new Color(240, 240, 240));
		txtRegistro.setText("¿No tienes una cuenta?");
		txtRegistro.setAlignmentY(50);
		panelTexto.add(txtRegistro);

		JPanel panelBtnCreaCuenta = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBtnCreaCuenta.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panelInferior.add(panelBtnCreaCuenta);

		JPanel panelBorderBtnRegistrar = new JPanel();
		panelBorderBtnRegistrar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		FlowLayout flowLayout_1 = (FlowLayout) panelBorderBtnRegistrar.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panelBtnCreaCuenta.add(panelBorderBtnRegistrar);


		JButton btnRegister;
		btnRegister = new JButton("Crear una cuenta");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.RED);
		btnRegister.setOpaque(true);
		addManejadorBtnRegister(btnRegister);
		panelBorderBtnRegistrar.add(btnRegister);
	}

	private void addManejadorBtnLogin(JButton btnLogin) {
		btnLogin.addActionListener(e -> {
			boolean login = Controlador.INSTANCE.loginUser(txtrNombreDeUsuario.getText(), passwordField.getText());
			if (login) {
				PrincipalGUI window = new PrincipalGUI();
				window.mostrarVentana();
				frameLogin.dispose();
			} 
			else {
				JOptionPane.showMessageDialog(frameLogin, "Nombre de usuario o contraseña no valido",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void addManejadorBtnRegister(JButton btnRegister) {
		btnRegister.addActionListener(ev ->{
			RegisterGUI v = new RegisterGUI(frameLogin);
			v.setVisible(true);
		});
	}
}
