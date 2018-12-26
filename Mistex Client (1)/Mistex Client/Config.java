/**
 * @author Mistex Team
 */
public class Config {
	
	/**
	 * Client name
	 */
	public static String clientName = "Mistex";
	
	/**
	 * Mistex cache name
	 */
	public static String cacheName = "./MistexCachev1/";
	
	/**
	 * Checks if using local host
	 */
	public static boolean usingLocal;
	
	/**
	 * Local host IP address
	 */
	public static String LOCALHOST_IP = "127.0.0.1";
	
	/**
	 * VPS IP address
	 */
	public static String VPS_IP = "127.0.0.1";
	
	/**
	 * Determine the client running
	 * @return
	 */
	public static String determineClient() {
		if (debugMode == false) {
			usingLocal = false;
			System.out.println("Loading VPS client.");
		} else if (debugMode == true) {
			usingLocal = true;
			System.out.println("Loading local host client.");
		}
		return null;
	}
	
	/**
	 * Client version
	 */
	public static final double CLIENT_VERSION = 3.6;
	
	/**
	 * Client port
	 */
	public static int serverPort = 43594;
	
	/**
	 * Debug mode
	 */
	public static boolean debugMode = false;
	
	/**
	 * Other
	 */
	public static final int CAM_NEAR = 50;
	public static final int CAM_FAR = 4200;


}
