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
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class PresentationGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Optional<String> optPresentacion;
	private JTextArea txtPresentacion;

	/**
	 * Create the dialog.
	 */
	public PresentationGUI(JFrame owner) {
		super(owner, "Registro de Usuario", true);
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
		{
			JPanel panelTitulo = new JPanel();
			contentPanel.add(panelTitulo, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("Escribe una presentación sobre ti");
				panelTitulo.add(lblNewLabel);
			}
		}
		{
			{
				txtPresentacion = new JTextArea();
				txtPresentacion.setBorder(new LineBorder(Color.black, 1));
				txtPresentacion.setWrapStyleWord(true);
				txtPresentacion.setLineWrap(true);
			}
			
			JScrollPane sc = new JScrollPane(txtPresentacion);
			sc.setBorder(new EmptyBorder(5, 5, 5, 5));			
			contentPanel.add(sc);
		}
		{
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
	
	public Optional<String> getPresentacion() {
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
