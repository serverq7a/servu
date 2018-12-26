import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
final class RSFrame extends Frame {
	
	public RSFrame(RSApplet rsapplet, int width, int height, boolean fullscreen, boolean resizable) {
		rsApplet = rsapplet;
		setTitle("Mistex - Your Adventure | " + (fullscreen ? "Fullscreen" : resizable ? "Resizable" : "Fixed")); // Sets the frame's title.
		setUndecorated(fullscreen);
		setResizable(resizable);
		setVisible(true);
		insets = getInsets();
		if(resizable) {
			setMinimumSize(new Dimension(777 + insets.left + insets.right, 565 + insets.top + insets.bottom));
		}
		setFocusTraversalKeysEnabled(false);
		Insets insets = this.getInsets();
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);//28
		Client.getClient();
		setLocation((screenWidth - width) / 2, ((screenHeight - height) / 2) - screenHeight == Client.getMaxHeight() ? 0 : fullscreen ? 0 : 70);
		requestFocus();
		toFront();
		setBackground(Color.BLACK);
	}

	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		Insets insets = this.getInsets();
		g.translate(insets.left ,insets.top);
		return g;
	}
	
	public int getFrameWidth() {
		Insets insets = this.getInsets();
		return getWidth() - (insets.left + insets.right);
	}
	
	public int getFrameHeight() {
		Insets insets = this.getInsets();
		return getHeight() - (insets.top + insets.bottom);
	}

	public void update(Graphics g) {
		rsApplet.update(g);
	}

	public void paint(Graphics g) {
		rsApplet.paint(g);
	}
	
	public static void takeScreenshot() {
		try {
			Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
			Point point = window.getLocationOnScreen();
			int xPos = (int)point.getX();
			int yPos = (int)point.getY();
			int width = window.getWidth();
			int height = window.getHeight();
			Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
			Rectangle captureSize = new Rectangle(xPos, yPos, width, height);
			BufferedImage bufferedimage = robot.createScreenCapture(captureSize);
			String imageName = JOptionPane.showInputDialog(frame, "Image Name:", "Screenshot Name", JOptionPane.OK_CANCEL_OPTION);
	        File directory = new File(Signlink.findcachedir() + "Screenshots");
	        if (!directory.exists()) {
	            directory.mkdir();
	        }
			if(!imageName.equals("null"))
				ImageIO.write(bufferedimage, "png", new File(Signlink.findcachedir()+"Screenshots/" + imageName + ".png"));
		} catch (Exception e) {}
	}

	private final RSApplet rsApplet;
	private static RSFrame frame;
	protected final Insets insets;
	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	public Dimension screenSize = toolkit.getScreenSize();
	public int screenWidth = (int)screenSize.getWidth();
	public int screenHeight = (int)screenSize.getHeight();
}
