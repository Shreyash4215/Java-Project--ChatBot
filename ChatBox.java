import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBox extends JComponent {

    private final BoxType boxType;
    private final String message;
    private final String name;
	JLabel labelDate;
	SimpleDateFormat df;
	String date;

    public ChatBox(BoxType boxType, String message,String name) {
        this.boxType = boxType;
        this.message = message;
        this.name = name;
        initBox();
    }

    private void initBox() {
		Font font1 = new Font("SansSerif", Font.PLAIN, 14);
		df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
		date = df.format(new Date());
        String rightToLeft = boxType == BoxType.RIGHT ? ",rtl" : "";
        setLayout(new MigLayout( rightToLeft, ""));
        JTextPane text = new JTextPane();
		text.setText(message);
		text.setFont(font1);
        text.setBackground(new Color(100, 100, 100, 100));
        text.setForeground(new Color(50, 50, 50));
        text.setSelectionColor(new Color(255, 255, 255, 255));
        text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setOpaque(false);
        text.setEditable(false);
        labelDate = new JLabel(name + " | " + date);
        labelDate.setForeground(new Color(127, 127, 127));
        add(text, "gapy 20, wrap");
        add(labelDate, "gapx 20,span 2");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        if (boxType == BoxType.LEFT) {
            Area area = new Area(new RoundRectangle2D.Double(5, 25, width - 15, height - 25 - 16 - 10, 25, 25));
            g2.setColor(new Color(138, 236, 255));
			add(labelDate, "gapx 5,span 2");
            g2.fill(area);
        } else {
            Area area = new Area(new RoundRectangle2D.Double(0, 25, width - 10, height - 25 - 16 - 10,25,25));
            g2.setColor(new Color(255,215,141));
            g2.fill(area);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    public static enum BoxType {
        LEFT, RIGHT
    }
}
