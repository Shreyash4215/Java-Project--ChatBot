import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javaswingdev.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class ChatScreen extends JPanel {
	
    private MigLayout layout;
    private MigLayout layoutLayered;
    private JLayeredPane layeredPane;
    private JPanel body;
    private JScrollPane scrollBody;
    private Animator animator;
	private TimingTarget target;    
    
    public ChatScreen() {
        setOpaque(false);
        layout = new MigLayout("fill, wrap", "[fill]", "[fill]");
        body = createBody();
        layeredPane = createLayeredPane();
        scrollBody = createScroll();
        scrollBody.setViewportView(body);
        scrollBody.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.getViewport().setOpaque(false);
        layeredPane.add(scrollBody);
        setLayout(layout);
        add(layeredPane);
    }
  
    private JPanel createBody() {
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(60,60,80));
        panel.setLayout(new MigLayout("wrap,fillx"));
        return panel;
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        return scroll;
    }

    public void addChatBox(String message, String name,ChatBox.BoxType type) {
        if (type == ChatBox.BoxType.LEFT) {
            body.add(new ChatBox(type, message,name), "width ::80%");
        } else {
            body.add(new ChatBox(type, message,name), "al right,width ::80%");
        }
        scrollToBottom();
    }

    private void scrollToBottom() {
    
		 animator = new Animator(350);
		 animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void begin() {
                body.revalidate();
            }
        });
		
		animator.stop();
		animator.removeTarget(target);
        target = new PropertySetter(scrollBody.getVerticalScrollBar(), "value",  scrollBody.getVerticalScrollBar().getMaximum());
        animator.addTarget(target);
        animator.start();
        body.revalidate();
    }
}