package menuInicialCliente;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;

public class mainMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public mainMenu() {
		setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 820, 756);
		add(panel_2);
		panel_2.setLayout(new CardLayout(0, 0));

	}
}
