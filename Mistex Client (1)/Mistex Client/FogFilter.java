import java.awt.Color;

public class FogFilter implements FilterPostProcessor {
	private float playerDistance = 0f;
	public float getPlayerDistance() {
		return playerDistance;
	}
	public void setPlayerDistance(float playerDistance) {
		this.playerDistance = playerDistance;
	}
	private float fogStartDistance = 500f;
	private float fogEndDistance = 2850f;
	private Color fogColor = new Color(200, 192, 168);
	@Override
	public void postRender(int[] colorBuffer, float[] depthBuffer, int width, int height) {
		float realStartDistance = fogStartDistance + playerDistance;
		float realFogEndDistance = fogEndDistance + playerDistance;
		for (int i = 0; i < depthBuffer.length; i++) {
			float depth = depthBuffer[i];
			depth -= realStartDistance;
			depth /= realFogEndDistance;
			if (depth < 0) continue; // Don't bother.
			if (depth >= 1) {
				colorBuffer[i] = fogColor.getRGB();
				continue;
			}
			int rgb = colorBuffer[i];
			Color inCol = new Color(rgb);
			Color outCol = FilterUtils.mix(inCol, fogColor, depth);
			colorBuffer[i] = outCol.getRGB();
		}
	}
	public FogFilter(float fogStartDistance, float fogEndDistance, Color fogColor) {
		super();
		this.fogStartDistance = fogStartDistance;
		this.fogEndDistance = fogEndDistance;
		this.fogColor = fogColor;
	}
	public float getFogStartDistance() {
		return fogStartDistance;
	}
	public void setFogStartDistance(float fogStartDistance) {
		this.fogStartDistance = fogStartDistance;
	}
	public float getFogEndDistance() {
		return fogEndDistance;
	}
	public void setFogEndDistance(float fogEndDistance) {
		this.fogEndDistance = fogEndDistance;
	}
	public Color getFogColor() {
		return fogColor;
	}
	public void setFogColor(Color fogColor) {
		this.fogColor = fogColor;
	}

}
