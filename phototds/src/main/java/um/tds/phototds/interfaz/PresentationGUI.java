package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import um.tds.phototds.controlador.Controlador;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class PresentationGUI extends JDialog {
	//Necesario para quitar warnings
	private static final long serialVersionUID = 1L;
	
	//Constantes
	public static final int MODE_PRESENTACION = 0;
	public static final int MODE_COMENTARIO= 1;
	public static final int MODE_UPDATE = 2;
	
	//Atributos
	private final JPanel contentPanel = new JPanel();
	private Optional<String> optPresentacion;
	private JTextArea txtPresentacion;
	private int mode;

	/**
	 * Create the dialog.
	 */
	public PresentationGUI(JFrame owner) {
		super(owner, "Registro de Usuario - Presentacion", true);
		this.mode = MODE_PRESENTACION;//Por defecto
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(400, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		initialize();
	}
	
	public PresentationGUI(JFrame owner, int mode) {
		super(owner, true);
		this.mode = mode; 
		if(mode == MODE_COMENTARIO)
			this.setTitle("Añadir Comentario");
		else
			this.setTitle("Registro de Usuario - Presentacion");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setBounds(400, 300, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		initialize();
	}
	
	public void mostrarVentana() {
		this.setVisible(true);
	}
	
	private void initialize() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		{//Panel Norte
			JPanel panelTitulo = new JPanel();
			contentPanel.add(panelTitulo, BorderLayout.NORTH);
			{//Panel título
				if(mode == MODE_PRESENTACION) {
					JLabel lblTitulo = new JLabel("Escribe una presentación sobre ti");
					panelTitulo.add(lblTitulo);
				}
				else {
					JLabel lblTitulo = new JLabel("Escribe un comentario");
					panelTitulo.add(lblTitulo);
				}
			}
		}
		{
			{
				txtPresentacion = new JTextArea();
				txtPresentacion.setBorder(new LineBorder(Color.black, 1));
				txtPresentacion.setWrapStyleWord(true);
				txtPresentacion.setLineWrap(true);
				if(mode == MODE_UPDATE)
					txtPresentacion.setText(Controlador.INSTANCE.getPresentacionUsuarioActual());
			}
			
			JScrollPane sc = new JScrollPane(txtPresentacion);
			sc.setBorder(new EmptyBorder(5, 5, 5, 5));			
			contentPanel.add(sc);
		}
		
		{//Panel sur: botones
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				addManejadorBtnOk(okButton);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				addManejadorBtnCancel(cancelButton);
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public Optional<String> getTexto() {
		return this.optPresentacion;
	}
	
	private void addManejadorBtnOk(JButton btn) {
		btn.addActionListener(ev -> {
			this.optPresentacion = Optional.ofNullable(this.txtPresentacion.getText());
			PresentationGUI.this.dispose();
		});
	}
	
	private void addManejadorBtnCancel(JButton btn) {
		btn.addActionListener(ev -> {
			this.optPresentacion = Optional.empty();
			PresentationGUI.this.dispose();
		});
	}

}
