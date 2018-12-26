import java.awt.Color;


public class DepthDebugFilter implements FilterPostProcessor {

	@Override
	public void postRender(int[] colorBuffer, float[] depthBuffer, int width, int height) {
		for (int i = 0; i < colorBuffer.length; i++) {
			float abs = depthBuffer[i] / 3500f;
			if (abs > 1f) 
				abs = 1f;
			if (abs < 0f) {
				abs = 0f;
			}
			int absRGB = new Color(abs, abs, abs).getRGB();
			colorBuffer[i] = absRGB;
		}
	}
}
