package um.tds.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class ImageListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof JPanel) {
			Component component = (Component) value;
			component.setForeground(Color.white);
			component.setBackground(isSelected ? UIManager.getColor("Table.focusCellForeground") : Color.white);
			return component;
		} else {
			// TODO - I get one String here when the JList is first rendered; proper way to
			// deal with this?
			// System.out.println("Got something besides a JPanel: " +
			// value.getClass().getCanonicalName());
			return new JLabel("???");
		}
	}

}
