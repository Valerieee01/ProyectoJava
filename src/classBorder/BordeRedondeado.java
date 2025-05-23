package classBorder;

import java.awt.*;
import javax.swing.border.AbstractBorder;

public class BordeRedondeado extends AbstractBorder {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int arcWidth;
    private int arcHeight;
    private Color color;

    public BordeRedondeado(int arcWidth, int arcHeight, Color color) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x, y, width - 1, height - 1, arcWidth, arcHeight);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8); // espacio interno
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}
