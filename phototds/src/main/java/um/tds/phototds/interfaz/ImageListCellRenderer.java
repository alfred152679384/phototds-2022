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
		Component component = (Component) value;
		component.setForeground(Color.white);
		component.setBackground(isSelected ? Color.GRAY : Color.white);
		return component;
	}

}
