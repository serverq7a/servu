import java.awt.Color;

public class FilterUtils {
	public static Color mix(Color source, Color dest, float factor) {
		int dR = dest.getRed() - source.getRed();
		int dG = dest.getGreen() - source.getGreen();
		int dB = dest.getBlue() - source.getBlue();
		return new Color((int)(source.getRed() + (dR * factor)), (int)(source.getGreen() + (dG * factor)), (int)(source.getBlue() + (dB * factor)));
	}
	public static float clamp(float val, float min, float max) {
		float val2 = val;
		if (val2 < min) val2 = min;
		if (val2 > max) val2 = max;
		return val2;
	}
}
