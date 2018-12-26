public class Bubble {

	public Client client;
	private int xPos;
	private int yPos;
	private byte radius;
	private int speed;
	private int xChange;
	public static final byte BUBBLES = 1;
	public static final byte BOUNCING_BALLS = 0;
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public byte getRadius() {
		return radius;
	}
	
	public Bubble() {
		radius = (byte) (Math.random() * 60.0d);
		if (radius < 30) {
			radius += 15;
		}
		xChange = ((Math.random()) == 1 ? -1 : 1) * (int) Math.round(Math.random());
		xPos = radius + (int)(Math.random() * (Client.clientWidth - radius * 2)); 
		yPos = Client.clientHeight + radius + (int) (Math.random() * 50.0d);
		speed = (int) (Math.random() * 3.0d);
		if (speed == 0) {
			speed = 1;
		}
		xSpeed = speed;
		ySpeed = speed;
	}
	
	private boolean setToRandomY = false;
	private int xSpeed;
	private int ySpeed;
	
	public void draw(byte state) {
		if (this != null) {
			switch (state) {
			case BUBBLES:
				this.yPos -= speed;
				this.xPos += xChange;
				if (this.yPos < 0 - radius) {
					radius = (byte) (Math.random() * 60.0d);
					if (radius < 30) {
						radius += 15;
					}
					xChange = (Math.round(Math.random()) == 1 ? -1 : 1) * (int) Math.round(Math.random());
					xPos = radius + (int)(Math.random() * (Client.clientWidth - radius));
					yPos = Client.clientHeight + radius + (int) (Math.random() * (Math.random() * 50.0d));
					speed = (int) (Math.random() * 3.0d);
					if (speed == 0) {
						speed = 1;
					}
				}
				DrawingArea.drawBubble(this.xPos, this.yPos, (int) this.radius, 0xD1D1D1, 20);
				break;
			case BOUNCING_BALLS:
				if (!setToRandomY) {
					yPos = (radius + 1) + (int)(Math.random() * (Client.clientHeight - radius + 1));
					setToRandomY = true;
				}
				if (setToRandomY) {
					if (yPos - radius <= 0) {
						ySpeed *= -1;
					}
					if (yPos + radius >= Client.clientHeight) {
						ySpeed *= -1;
					}
					
					if (xPos - radius <= 0) {
						xSpeed *= -1;
					}
					if (xPos + radius >= Client.clientWidth) {
						xSpeed *= -1;
					}
				}
				yPos += ySpeed;
				xPos += xSpeed;
				DrawingArea.drawBubble(this.xPos, this.yPos, (int) this.radius, 0xFFFFFF, 20);
				break;
			}
		}
	}
}