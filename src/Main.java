import java.applet.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends Applet implements MouseListener, MouseMotionListener, MouseWheelListener {
	private static final long serialVersionUID = 4844855624430486548L;
	Graphics graphicsBuffer;
	Image offscreen;
	int X, Y, toolX, toolY, toolWidth = 40, toolHeight = 40;
	List<FilledRect> filledRect = new ArrayList<FilledRect>();

	public void init() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width - 128, screenSize.height - 128);
		setBackground(new Color(117, 178, 221, 255));
		offscreen = createImage(getSize().width, getSize().height);
		graphicsBuffer = offscreen.getGraphics();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}

	public void mouseClicked(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
	
	public void mouseWheelMoved(MouseWheelEvent event) {
		if (event.getWheelRotation() == -1) {
			toolWidth += event.getScrollAmount();
			toolHeight += event.getScrollAmount();
		} else {
			toolWidth -= event.getScrollAmount();
			toolHeight -= event.getScrollAmount();
		}
		if (event.getY() - toolHeight / 2 > 72) {
			toolX = event.getX() - toolWidth / 2;
			toolY = event.getY() - toolHeight / 2;
		} else {
			toolY = 72;
		}
		repaint();
	}

	public void mouseDragged(MouseEvent event) {
		if (event.getY() - toolHeight / 2 > 72) {
			toolX = event.getX() - toolWidth / 2;
			toolY = event.getY() - toolHeight / 2;
		} else {
			toolY = 72;
		}
		X = event.getX();
		Y = event.getY();
		if (X >= 4 + toolWidth / 2 && X <= 804 - toolWidth / 2 && Y >= 76 + toolHeight / 2 && Y <= 676 - toolHeight / 2) filledRect.add(new FilledRect(toolX, toolY, toolWidth, toolHeight));
		repaint();
	}

	public void mouseMoved(MouseEvent event) {
		if (event.getY() - toolHeight / 2 > 72) {
			toolX = event.getX() - toolWidth / 2;
			toolY = event.getY() - toolHeight / 2;
		} else {
			toolY = 72;
		}
		X = event.getX();
		Y = event.getY();
		if (X >= 4 + toolWidth / 2 && X <= 804 - toolWidth / 2 && Y >= 76 + toolHeight / 2 && Y <= 676 - toolHeight / 2) {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (X >= 804 && X <= 808 && Y >= 676 && Y <= 680) {
			setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
		} else if (X >= 804 && X <= 808 && Y >= 376 && Y <= 380) {
			setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} else if (X >= 404 && X <= 408 && Y >= 676 && Y <= 680) {
			setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		} else if (Y <= 72) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		repaint();
	}

	public void paint(Graphics graphics) {
		graphicsBuffer.clearRect(0, 0, getSize().width, getSize().height);
		// Canvas
		graphicsBuffer.setColor(Color.gray);
		graphicsBuffer.fillRect(6, 78, 800, 600);
		graphicsBuffer.setColor(Color.white);
		graphicsBuffer.fillRect(4, 76, 800, 600);
		// Canvas resizer
		graphicsBuffer.setColor(Color.white);
		graphicsBuffer.fillRect(804, 676, 4, 4);
		graphicsBuffer.fillRect(804, 376, 4, 4);
		graphicsBuffer.fillRect(404, 676, 4, 4);
		graphicsBuffer.setColor(Color.black);
		graphicsBuffer.drawRect(804, 676, 4, 4);
		graphicsBuffer.drawRect(804, 376, 4, 4);
		graphicsBuffer.drawRect(404, 676, 4, 4);
		// Toolbar
		graphicsBuffer.setColor(Color.white);
		graphicsBuffer.fillRect(0, 0, getSize().width, 72);
		graphicsBuffer.setColor(Color.gray);
		graphicsBuffer.fillRect(4, 4, 64, 64);
		graphicsBuffer.setColor(Color.orange);
		graphicsBuffer.fillRect(16, 16, 40, 40);
		// Text
		graphicsBuffer.setColor(Color.white);
		graphicsBuffer.setFont(new Font("Calibri", Font.PLAIN, 16));
		graphicsBuffer.drawString("Double-buffered paint studio by Reece Aaron Lecrivain 2012", 10, (int) (getSize().getHeight() - 32));
		// Tool
		graphicsBuffer.setColor(Color.orange);
		if (X >= 4 + toolWidth / 2 && X <= 804 - toolWidth / 2 && Y >= 76 + toolHeight / 2 && Y <= 676 - toolHeight / 2) graphicsBuffer.fillRect(toolX, toolY, toolWidth, toolHeight);
		// Shapes
		for (FilledRect rect : filledRect) rect.draw(graphicsBuffer);
		graphics.drawImage(offscreen, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}
}
