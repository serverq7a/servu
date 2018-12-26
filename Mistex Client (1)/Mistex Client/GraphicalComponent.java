import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public final class GraphicalComponent {

	public final int[] raster;
	public final float[] depth;
	public final int width;
	public final int height;
	private final BufferedImage bufferedImage;

	public GraphicalComponent(int width, int height) {
		this.width = width;
		this.height = height;
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		raster = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		depth = new float[width*height];
		setCanvas();
	}

	public void drawGraphics(int x, Graphics graphics, int y) {
		graphics.drawImage(bufferedImage, y, x, null);
	}

	public void setCanvas() {
		DrawingArea.initDrawingArea(height, width, raster, depth);
	}
}