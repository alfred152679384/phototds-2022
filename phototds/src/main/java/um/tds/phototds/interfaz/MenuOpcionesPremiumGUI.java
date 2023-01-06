package um.tds.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuOpcionesPremiumGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MenuOpcionesPremiumGUI dialog = new MenuOpcionesPremiumGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MenuOpcionesPremiumGUI() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		
		JMenu mnOpcionesPremium = new JMenu("Settings");
		panelCentral.add(mnOpcionesPremium);
		
		JMenuItem mntmPremium = new JMenuItem("Premium");
		mnOpcionesPremium.add(mntmPremium);
		
		JMenuItem mntmPdf = new JMenuItem("Generar PDF");
		mnOpcionesPremium.add(mntmPdf);
		
		JMenuItem mntmExcel = new JMenuItem("Generar Excel");
		mnOpcionesPremium.add(mntmExcel);
		
		JMenuItem mntmTopMG = new JMenuItem("Top Me gusta");
		mnOpcionesPremium.add(mntmTopMG);
		
		JButton btn = new JButton("Options");
		btn.addActionListener(ev -> {
			JPopupMenu menu = new JPopupMenu();
		});
		panelCentral.add(btn);
		
		JPopupMenu popupMenu = new JPopupMenu();
		panelCentral.add(popupMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		panelCentral.add(mntmNewMenuItem);
		
	}

}
