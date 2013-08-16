import java.awt.Graphics;


public class FilledRect {
	public int X, Y, Width, Height;
	
	public void draw(Graphics graphics) {
		graphics.fillRect(X, Y, Width, Height);
	}
	
	public FilledRect(int x, int y, int width, int height) {
		X = x;
		Y = y;
		Width = width;
		Height = height;
	}
}
