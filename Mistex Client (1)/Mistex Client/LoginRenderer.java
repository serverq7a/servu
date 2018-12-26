/**
 * Displays login screen elements.
 * 
 * @author Evan Bennett
 * 
 * Time 10:10am
 * Date: 8/05/2014
 */

public class LoginRenderer {
	
	/**
	 * Client instance.
	 */
	private final Client client;
	
	/**
	 * Login Renderer Constructor. 
	 * 
	 * @param client
	 */
	public LoginRenderer(Client client) {
		this.client = client;
	}
	
		/**
		 * Draws the login elements to the screen.
	 	*/
		public void displayLoginScreen() {
			final int centerX = Client.clientWidth / 2, centerY = Client.clientHeight / 2;
			client.resetImageProducers();
			client.checkSize();
			client.aRSImageProducer_1109.setCanvas();
			if(client.loginScreenState == 0) {
				DrawingArea.drawFilledPixels(0, 0, Client.clientWidth, Client.clientHeight, 0x040404);
				client.imageLoader[80].drawARGBSprite((Client.clientWidth / 2) - (client.imageLoader[80].myWidth / 2), (Client.clientHeight / 2) - (client.imageLoader[80].myHeight / 2));
				client.imageLoader[81].drawARGBSprite(centerX - 477/2, centerY - 240);//Logo
				client.imageLoader[70].drawARGBSprite(centerX - 429/2, centerY - 105);//Login box
				if(client.mouseInRegion(centerX + 130, centerY + 135, centerX + 200, centerY + 163)) {
					DrawingArea.fillRoundedRectangle(centerX + 130, centerY + 135, 70, 30, 5, 0x646468, 190);
				} else {
					DrawingArea.fillRoundedRectangle(centerX + 130, centerY + 135, 70, 30, 5, 0x000000, 190);
				}
				Client.fancyFont.method382(0xFFFFFF, centerX + 165, "Settings", centerY + 155, true);
				client.imageLoader[Client.isFixed() || client.mouseInRegion(centerX - 195, centerY - 10, centerX - 161, centerY + 33) ? 65 : 64].drawARGBSprite(centerX - 205 + 10, centerY - 10);//Fixed
				client.imageLoader[Client.isResize() || client.mouseInRegion(centerX - 128, centerY - 10, centerX - 74, centerY + 33) ? 67 : 66]
						.drawSprite(centerX - 138 + 10, centerY - 10);//Resizable
				client.imageLoader[Client.isFull() || client.mouseInRegion(centerX - 60, centerY - 10, centerX - 6, centerY + 33) ? 69 : 68]
						.drawSprite(centerX - 70 + 10, centerY - 10);//Fullscreen
				if (client.mouseInRegion(centerX + 128, centerY + 73, centerX + 128 + 74, centerY + 73 + 23)) {//Login Hover
					client.imageLoader[71].drawSprite(centerX + 134, centerY + 73);
				}
				if (client.mouseInRegion(centerX + 15, centerY - 11, centerX + 200, centerY + 20)) {//User Hover
					client.imageLoader[72].drawARGBSprite(centerX + 15, centerY - 11);
				}
				if (client.mouseInRegion(centerX + 15, centerY + 41, centerX + 200, centerY + 75)) {//Password Hover
					client.imageLoader[72].drawARGBSprite(centerX + 15, centerY + 41);
				}
				if (!client.mouseInRegion(centerX - 170, centerY + 80, centerX - 170 + 37, centerY + 118)) {//Facebook Hover
					client.imageLoader[77].drawARGBSprite(centerX - 170, centerY + 80);
				} else {
					client.drawTooltipBox("Like us on Facebook!", centerX - 210, centerY + 120, 0xFFFFA0);
				}
				if (!client.mouseInRegion(centerX - 126, centerY + 80, centerX - 126 + 37, centerY + 118)) {//Twitter Hover
					client.imageLoader[78].drawARGBSprite(centerX - 126, centerY + 80);
				} else {
					client.drawTooltipBox("Follow us on Twitter!", centerX - 165, centerY + 120, 0xFFFFA0);
				}
				if (!client.mouseInRegion(centerX - 83, centerY + 80, centerX - 83 + 37, centerY + 118)) {//Youtube Hover
					client.imageLoader[79].drawARGBSprite(centerX - 83, centerY + 80);
				} else {
					client.drawTooltipBox("Check out our YouTube channel!", centerX - 160, centerY + 120, 0xFFFFA0);
				}
				if (!client.mouseInRegion(centerX - 83, centerY + 80, centerX - 83 + 37, centerY + 118)) {//Bubble Hover
					client.imageLoader[79].drawARGBSprite(centerX - 83, centerY + 80);
				} else {
					client.drawTooltipBox("Check out our YouTube channel!", centerX - 160, centerY + 120, 0xFFFFA0);
				}
				
				
				Client.fancyFont.method382(0x6F6F6F, centerX - 103, "Screen Mode", centerY - 20, true);
				Client.fancyFont.method382(0x6F6F6F, centerX - 168, "Fixed", centerY + 55, true);
				Client.fancyFont.method382(0x6F6F6F, centerX - 104, "Resizable", centerY + 55, true);
				Client.fancyFont.method382(0x6F6F6F, centerX - 33, "Fullscreen", centerY + 55, true);
				Client.fancyFont.method382(0x6F6F6F, centerX + 50, "Username:", centerY - 12, true);
				Client.fancyFont.method382(0x6F6F6F, centerX + 60, "Password:", centerY + 40, true);
				Client.fancyFont.method389(true, centerX + 27, 0xB0AFAB, TextClass.capitalize(client.myUsername) + ((client.loginScreenCursorPos == 0) & (Client.loopCycle % 40 < 20) ? "|" : ""), centerY + 12);//Username field
				Client.fancyFont.method389(true, centerX + 27, 0xB0AFAB, TextClass.passwordAsterisks(client.myPassword) + ((client.loginScreenCursorPos == 1) & (Client.loopCycle % 40 < 20) ? "|" : ""), centerY + 63);//Password field
				if (client.loginMessage1.length() > 0) {
					client.aTextDrawingArea_1271.method382(0xffffff, centerX + 110, client.loginMessage1, centerY + 110, true);
					client.aTextDrawingArea_1271.method382(0xffffff, centerX + 110, client.loginMessage2, centerY + 126, true);
				} else {
					client.aTextDrawingArea_1271.method382(0xffffff, centerX + 110, client.loginMessage2, centerY + 110, true);
				}
			}
			if(client.loginScreenState == 1) {
				/* Main Drawing */
				DrawingArea.fillRectangle(0, 0, Client.clientWidth, Client.clientHeight, 0x040404, 200);
				client.imageLoader[80].drawARGBSprite((Client.clientWidth / 2) - (client.imageLoader[80].myWidth / 2), (Client.clientHeight / 2) - (client.imageLoader[80].myHeight / 2));
				client.imageLoader[81].drawARGBSprite(centerX - 477/2, centerY - 240);//Logo
				DrawingArea.fillRoundedRectangle(centerX - 303, centerY - 10, 606, 250, 13, 0x000000, 200);
				if(client.mouseInRegion(centerX + 230, centerY - 45, centerX + 300, centerY - 17)) {
					DrawingArea.fillRoundedRectangle(centerX + 230, centerY - 45, 70, 30, 5, 0x646468, 190);
				} else {
					DrawingArea.fillRoundedRectangle(centerX + 230, centerY - 45, 70, 30, 5, 0x000000, 190);
				}
				DrawingArea.fillRoundedRectangle(centerX - 175, centerY - 45, 270, 30, 5, 0x000000, 200);
				Client.fancyFont.method382(0xFFFFFF, centerX - 35, "Mistex Client Configurations", centerY - 26, true);
				Client.fancyFont.method382(0xFFFFFF, centerX + 265, "Back", centerY - 26, true);
				/* Social Networks */
				DrawingArea.drawSection(centerX - 65, centerY + 118, 100, 350, "Social Networks");
				client.imageLoader[client.mouseInRegion(centerX - 30, centerY + 153, centerX + 25, centerY + 203) ? 109 : 108].drawARGBSprite(centerX - 30, centerY + 153);//Facebook
				client.imageLoader[client.mouseInRegion(centerX + 95, centerY + 153, centerX + 150, centerY + 203) ? 111 : 110].drawARGBSprite(centerX + 95, centerY + 153);//Twitter
				client.imageLoader[client.mouseInRegion(centerX + 210, centerY + 153,centerX + 265, centerY + 203) ? 113 : 112].drawARGBSprite(centerX + 210, centerY + 153);//Youtube
				
				/* Client Configurations */
				DrawingArea.drawSection(centerX - 280, centerY + 8, 215, 200, "Client Configurations");
				client.smallText.method382(0xFFFFFF, centerX - 185, "@red@Red = Toggled off, @gre@Green = Toggled on", centerY + 2, true);
				for(int button = 0; button < 8; button++) {
					drawTitleButton("Toggle", centerX - 124, centerY + 39 + (button * 23), 0x646468);
				}
				client.smallText.method382(0xFFFFFF, centerX - 230, (Client.lowMem ? "@gre@" : "@red@") + "Low Memory Usage", centerY + 48, true);
				client.smallText.method382(0xFFFFFF, centerX - 234, (GameConfiguration.MOVING_TEXTURES ? "@gre@" : "@red@") + "Moving Textures", centerY + 73, true);
				client.smallText.method382(0xFFFFFF, centerX - 228, (GameConfiguration.GROUND_DECORATION ? "@gre@" : "@red@") + "Ground Decorations", centerY + 95, true);
				client.smallText.method382(0xFFFFFF, centerX - 242, (Client.enableBuffering ? "@gre@" : "@red@") + "Advanced Sky", centerY + 116, true);
				client.smallText.method382(0xFFFFFF, centerX - 244, (GameConfiguration.VISIBLE_LEVELS ? "@gre@" : "@red@") + "Visible Roofs", centerY + 141, true);
				client.smallText.method382(0xFFFFFF, centerX - 255, (Client.enableTweening ? "@gre@" : "@red@") + "Tweening", centerY + 164, true);
				client.smallText.method382(0xFFFFFF, centerX - 246, (Client.showSkillOrbs ? "@gre@" : "@red@") + "Skilling Orbs", centerY + 189, true);
				client.smallText.method382(0xFFFFFF, centerX - 257, (Client.enableBubbles ? "@gre@" : "@red@") + "Bubbles", centerY + 214, true);
				
				/* Resizing */
				DrawingArea.drawSection(centerX - 65, centerY + 8, 100, 350, "Screen Mode");
				Client.fancyFont.method382(0x646468, centerX - 5, "Fixed", centerY + 101, true);
				Client.fancyFont.method382(0x646468, centerX + 115, "Resizable", centerY + 101, true);
				Client.fancyFont.method382(0x646468, centerX + 235, "Fullscreen", centerY + 101, true);
				client.imageLoader[Client.isFixed() || client.mouseInRegion(centerX - 30, centerY + 40, centerX + 24, centerY + 83) ? 65 : 64]
						.drawARGBSprite(centerX - 30, centerY + 40);//Fixed
				client.imageLoader[Client.isResize() || client.mouseInRegion(centerX + 85, centerY + 40, centerX + 139, centerY + 83) ? 67 : 66]
						.drawARGBSprite(centerX + 85, centerY + 40);//Resizable
				client.imageLoader[Client.isFull() || client.mouseInRegion(centerX + 205, centerY + 40, centerX + 259, centerY + 83) ? 69 : 68]
						.drawARGBSprite(centerX + 205, centerY + 40);//Fullscreen
			}
			if (Client.enableBubbles) {
				for (Bubble bubble : Client.bubbles) {
					bubble.draw(Bubble.BUBBLES);
				}
			}
			if(Config.debugMode) {
				final Runtime runtime = Runtime.getRuntime();
				final int usedMemory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
				client.boldFont.method382(0xffffff, centerX - 345, "Fps: " + client.fps, centerY - 230, true);
				client.boldFont.method382(0xffffff, centerX - 325, "Memory: " + usedMemory / 1000L + "M", centerY - 215, true);
				client.boldFont.method382(0xffffff, centerX - 320, "Mouse: " + (client.mouseX - centerX) + "," + (client.mouseY - centerY), centerY - 200, true);
		}
		client.aRSImageProducer_1109.drawGraphics(0, client.graphics, 0);
	}
	
	
	/**
	 * Handles login screen clicking/text.
	 * 
	 * @return
	 */
	public void processLoginScreen() {
		int centerX = Client.clientWidth / 2, centerY = Client.clientHeight / 2;
        if (client.loginScreenState == 0) {
            if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 205, centerY - 20, centerX - 151, centerY + 23))//Fixed Mode
            	client.toggleSize(0);
            if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 138, centerY - 20, centerX - 84, centerY + 23))//Resizable Mode
            	client.toggleSize(1);
            if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 70, centerY - 20, centerX - 16, centerY + 23))//Fullscreen Mode
            	client.toggleSize(2);
            if (client.clickMode3 == 1 && client.clickInRegion(centerX + 18, centerY - 15, centerX + 198, centerY + 12))//Username
            	client.loginScreenCursorPos = 0;
            if (client.clickMode3 == 1 && client.clickInRegion(centerX + 18, centerY + 37, centerX + 198, centerY + 65))//Password
            	client.loginScreenCursorPos = 1;
            if (client.clickMode3 == 1 && client.clickInRegion(centerX - 169, centerY + 70, centerX - 143, centerY + 96))//Facebook
            	client.launchURL("https://www.facebook.com/pages/Mistex/839756339385413");
            if(client.clickMode3 == 1 && client.clickInRegion(centerX - 129, centerY + 70, centerX - 99, centerY + 96))//Twitter
            	client.launchURL("www.twitter.com/MistexCommunity");
            if(client.clickMode3 == 1 && client.clickInRegion(centerX - 82, centerY + 70, centerX - 56, centerY + 96))//Youtube
            	client.launchURL("www.youtube.com/user/MistexServer");
            if(client.clickMode3 == 1 && client.clickInRegion(centerX + 130, centerY + 135, centerX + 200, centerY + 163))//Settings Button
            	client.loginScreenState = 1;
            if (client.clickMode3 == 1 && client.clickInRegion(centerX + 127, centerY + 77, centerX + 192, centerY + 100)) {//Login
                if (client.myUsername.length() > 0 && client.myPassword.length() > 0) {
                	client.loginFailures = 0;
                	client.login(TextClass.capitalize(client.myUsername), client.myPassword, false);
                    if (client.loggedIn) {
                        return;
                    }
                } else {
                	client.loginMessage1 = "Please enter a username and password.";
                }
            }
            do {
                int l1 = client.readChar(-796);
                if (l1 == -1)
                    break;
                boolean flag1 = false;
                for (int i2 = 0; i2 < client.validUserPassChars.length(); i2++) {
                    if (l1 != client.validUserPassChars.charAt(i2))
                        continue;
                    flag1 = true;
                    break;
                }
                if (client.loginScreenCursorPos == 0) {
                    if (l1 == 8 && client.myUsername.length() > 0)
                    	client.myUsername = client.myUsername.substring(0, client.myUsername.length() - 1);
                    if (l1 == 9 || l1 == 10 || l1 == 13)
                    	client.loginScreenCursorPos = 1;
                    if (flag1)
                    	client.myUsername += (char) l1;
                    if (client.myUsername.length() > 12)
                    	client.myUsername = TextClass.capitalize(client.myUsername.substring(0, 12));
                } else if (client.loginScreenCursorPos == 1) {
                    if (l1 == 8 && client.myPassword.length() > 0)
                    	client.myPassword = client.myPassword.substring(0, client.myPassword.length() - 1);
                    if (l1 == 9 || l1 == 10 || l1 == 13)
                    	client.login(client.myUsername, client.myPassword, false);
                    if (flag1)
                    	client.myPassword += (char) l1;
                    if (client.myPassword.length() > 20)
                    	client.myPassword = client.myPassword.substring(0, 20);
                }
            } while (true);
            return;
        }
        if(client.loginScreenState == 1) {
    		if (client.clickMode3 == 1 && client.mouseInRegion(centerX + 229, centerY - 45, centerX + 300, centerY - 17))//Back Button
            	client.loginScreenState = 0;
    		
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 30, centerY + 40, centerX + 24, centerY + 83))//Fixed
    			client.toggleSize(0);
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX + 85, centerY + 40, centerX + 139, centerY + 83))//Resizable
    			client.toggleSize(1);
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX + 205, centerY + 40, centerX + 259, centerY + 83))//Fullscreen
    			client.toggleSize(2);
    		
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 39, centerX - 82, centerY + 53))//Low memory usage
    			Client.lowMem = !Client.lowMem;
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 62, centerX - 82, centerY + 77))//Moving textures
    			GameConfiguration.MOVING_TEXTURES = !GameConfiguration.MOVING_TEXTURES;
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 131, centerY + 75, centerX - 82, centerY + 100))//Ground decorations
    			GameConfiguration.GROUND_DECORATION = !GameConfiguration.GROUND_DECORATION;
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 98, centerX - 82, centerY + 123))//Fog
    			Client.enableBuffering = !Client.enableBuffering;
    		if(client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 121, centerX - 82, centerY + 146))//Visible roofs
    			GameConfiguration.VISIBLE_LEVELS = !GameConfiguration.VISIBLE_LEVELS;
    		if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 144, centerX - 82, centerY + 169))//Tweening
    			Client.enableTweening = !Client.enableTweening;
    		if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 167, centerX - 82, centerY + 192))//Skill orbs
        		Client.showSkillOrbs = !Client.showSkillOrbs;
    		if (client.clickMode3 == 1 && client.mouseInRegion(centerX - 124, centerY + 200, centerX - 82, centerY + 215))//Bubbles
        		Client.enableBubbles = !Client.enableBubbles;
    		
            if (client.clickMode3 == 1 && client.clickInRegion(centerX - 30, centerY + 153, centerX + 25, centerY + 203))//Facebook
            	client.launchURL("https://www.facebook.com/pages/Mistex/839756339385413");
            if(client.clickMode3 == 1 && client.clickInRegion(centerX + 95, centerY + 153, centerX + 150, centerY + 203))//Twitter
            	client.launchURL("www.twitter.com/MistexCommunity");
            if(client.clickMode3 == 1 && client.clickInRegion(centerX + 210, centerY + 153, centerX + 265, centerY + 203))//Youtube
            	client.launchURL("www.youtube.com/user/MistexServer");
    		}
		}
	
		public void drawTitleButton(String text, int x, int y, int color) {
			DrawingArea.fillRectangle(x, y, client.smallText.getTextWidth(text) + 10, 15, color, 100);
			if(client.mouseInRegion(x, y, x + client.smallText.getTextWidth(text) + 10, y + 15)) {
				DrawingArea.fillRectangle(x, y, client.smallText.getTextWidth(text) + 10, 15, color, 100);
			}
			client.smallText.method382(0xFFFFFF, x + 22, text, y + 13, true);
		}
	}
