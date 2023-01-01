package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import um.tds.phototds.controlador.Controlador;

public class RegisterGUI extends JDialog {
	// Constantes
	private static final long serialVersionUID = 1L;//Necesaria para quitar warnigns
	private static final int DEFAULT_H = 600;
	private static final int DEFAULT_W = 400;
	private static final int DEFAULT_COLUMNS = 30;

	// variables
	private JFrame owner;
	private JDateChooser chooser;
	private JTextField txtEmail;
	private JTextField txtNombre;
	private JTextField txtUsuario;
	private JTextField txtContras;
	private Optional<String> fotoPerfil;
	private Optional<String> presentacion;

	/**
	 * Create the application.
	 */
	public RegisterGUI(JFrame owner) {
		super(owner, "Registro de Usuario", true);
		this.owner = owner;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(owner.getX(), owner.getY(), DEFAULT_W, DEFAULT_H);
		this.fotoPerfil = Optional.empty();
		this.presentacion = Optional.empty();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Creamos el panel general
		JPanel panelGeneral = new JPanel();
		this.getContentPane().add(panelGeneral);
		panelGeneral.setLayout(new BorderLayout());

		// Creamos elementos dentro del border-layout
		crearPanelTitulo(panelGeneral);
		crearPanelDatos(panelGeneral);
		crearPanelInferior(panelGeneral);

		// panel este -> Panel Padding
		JPanel panelEste = new JPanel();
		panelGeneral.add(panelEste, BorderLayout.EAST);

		// panel oeste -> Panel Padding
		JPanel panelOeste = new JPanel();
		panelGeneral.add(panelOeste, BorderLayout.WEST);
	}

	private void crearPanelInferior(JPanel panelGeneral) {
		// panel sur
		JPanel panelSur = new JPanel();
		panelGeneral.add(panelSur, BorderLayout.SOUTH);

		// --->Elementos del panel sur
		panelSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton btnOK = new JButton("OK");
		addManejadorBtnOK(btnOK);
		JButton btnCancel = new JButton("Cancel");
		addManejadorBtnCancel(btnCancel);
		panelSur.add(btnOK);
		panelSur.add(btnCancel);
	}

	private void crearPanelTitulo(JPanel panelGeneral) {
		// panel norte
		JPanel panelNorte = new JPanel();
		panelGeneral.add(panelNorte, BorderLayout.NORTH);

		// --->Elementos del panel norte
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

		// ------->Dentro del box-layout tenemos que meter paneles
		JPanel panelTitulo = new JPanel();
		JLabel lblTitulo = new JLabel("Photo TDS");
		lblTitulo.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 30));
		panelTitulo.add(lblTitulo);
		panelNorte.add(new JPanel());
		panelNorte.add(panelTitulo);

		JPanel panelSubtitulo = new JPanel();
		panelSubtitulo.setBorder(new EmptyBorder(10, 10, 0, 10));
		JTextArea txtSubtitulo = new JTextArea("Si te registras podrás compartir fotos y ver las fotos de tus amigos");
		txtSubtitulo.setEditable(false);
		txtSubtitulo.setBackground(new Color(240, 240, 240));
		txtSubtitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSubtitulo.setLineWrap(true);
		txtSubtitulo.setWrapStyleWord(true);
		txtSubtitulo.setRows(2);
		txtSubtitulo.setColumns(DEFAULT_COLUMNS);

		panelSubtitulo.add(txtSubtitulo);
		panelNorte.add(new JPanel());// Panel Padding
		panelNorte.add(panelSubtitulo);
	}

	private void crearPanelDatos(JPanel panelGeneral) {
		// panel central
		JPanel panelCentral = new JPanel();
		panelGeneral.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

		panelCentral.add(new JPanel());// Panel Padding

		// --->Elementos panel central

		JPanel panelEmail = new JPanel();
		txtEmail = new JTextField("Email");
		txtEmail.setColumns(DEFAULT_COLUMNS);
		panelEmail.add(txtEmail);
		panelCentral.add(panelEmail);

		JPanel panelNombre = new JPanel();
		txtNombre = new JTextField("Nombre Completo");
		txtNombre.setColumns(DEFAULT_COLUMNS);
		panelNombre.add(txtNombre);
		panelCentral.add(panelNombre);

		JPanel panelUsuario = new JPanel();
		txtUsuario = new JTextField("Nombre de Usuario");
		txtUsuario.setColumns(DEFAULT_COLUMNS);
		panelUsuario.add(txtUsuario);
		panelCentral.add(panelUsuario);

		JPanel panelContras = new JPanel();
		txtContras = new JTextField("Contraseña");
		txtContras.setColumns(DEFAULT_COLUMNS);
		panelContras.add(txtContras);
		panelCentral.add(panelContras);

		// Campo Fecha de Nacimiento
		JPanel panelFechaNacimiento = new JPanel();
		panelFechaNacimiento.setLayout(new FlowLayout());
		panelFechaNacimiento.setBorder(new LineBorder(Color.black, 1));
		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento ");
		panelFechaNacimiento.add(lblFechaNacimiento);
		crearCalendario(panelFechaNacimiento);
		panelCentral.add(panelFechaNacimiento);

		// Panel Padding
		panelCentral.add(new JPanel());

		// Campo Foto de perfil
		JPanel panelFotoPerfil = new JPanel();
		panelFotoPerfil.setLayout(new FlowLayout());
		JLabel lblFotoPerfil = new JLabel("Añadir foto del usuario (opcional) ");
		panelFotoPerfil.add(lblFotoPerfil);
		JButton btnFotoPerfil = new JButton("+");
		addManejadorBtnFotoPerfil(btnFotoPerfil);
		panelFotoPerfil.setBorder(new LineBorder(Color.black, 1));
		panelFotoPerfil.add(btnFotoPerfil);
		panelCentral.add(panelFotoPerfil);

		// Panel Padding
		panelCentral.add(new JPanel());

		// Campo Presentación del usuario
		JPanel panelPresentacion = new JPanel();
		panelPresentacion.setLayout(new FlowLayout());
		JLabel lblPresentacion = new JLabel("Añadir presentación (opcional) ");
		panelPresentacion.add(lblPresentacion);
		JButton btnPresentacion = new JButton("...");
		panelPresentacion.setBorder(new LineBorder(Color.black, 1));
		addManejadorBtnPresentacion(btnPresentacion);
		panelPresentacion.add(btnPresentacion);
		panelCentral.add(panelPresentacion);

	}

	private void addManejadorBtnPresentacion(JButton btn) {
		btn.addActionListener(ev -> {
			PresentationGUI w = new PresentationGUI(owner);
			w.mostrarVentana();
			this.presentacion = w.getTexto();
		});
	}

	private void addManejadorBtnFotoPerfil(JButton btn) {
		btn.addActionListener(ev -> {
			JFileChooser fc = new JFileChooser();
			int retVal = fc.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				this.fotoPerfil = Optional.ofNullable(fc.getSelectedFile().getAbsolutePath());
			}
		});
	}

	private void crearCalendario(JPanel p) {
		this.chooser = new JDateChooser();
		chooser.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chooser.setDateFormatString("yyyy-MM-dd");
		p.add(chooser);
	}

	private void addManejadorBtnOK(JButton btnOK) {
		btnOK.addActionListener(ev -> {
			// Comprobación de errores
			// Campo fecha vacío
			Date date = chooser.getDate();
			if (date == null) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Debe introducir la fecha de nacimiento", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			DateFormat df = DateFormat.getDateInstance();

			// Campo Email vacio
			String email = txtEmail.getText();
			if (email.equals("") || email.equals("Email")) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Debe introducir un Email", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Campo Nombre vacío
			String nombre = txtNombre.getText();
			if (nombre.equals("") || nombre.equals("Nombre Completo")) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Debe introducir su nombre completo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Campo Username vacío
			String username = txtUsuario.getText();
			if (username.equals("") || username.equals("Nombre de Usuario")) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Debe introducir un nombre de usuario", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Campo contraseña vacío
			String password = txtContras.getText();
			if (password.equals("") || password.equals("Contraseña")) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Debe introducir una contraseña", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Caso de todos los campos necesarios completos --> Intenta registrar
			if (Controlador.INSTANCE.registerUser(username, nombre, email, password, df.format(date), fotoPerfil,
					presentacion)) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Usuario registrado correctamente");
				RegisterGUI.this.dispose();
			} else {
				JOptionPane.showMessageDialog(RegisterGUI.this, "El usuario ya existe", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		});
	}

	private void addManejadorBtnCancel(JButton btnCancel) {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI.this.dispose();
			}
		});
	}
}
