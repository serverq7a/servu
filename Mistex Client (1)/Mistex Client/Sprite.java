import java.awt.*;
import java.awt.image.PixelGrabber;
import javax.swing.ImageIcon;

public final class Sprite extends DrawingArea {
	int drawOffsetX;
	int drawOffsetY;

	public Sprite(int i, int j) {
		myPixels = new int[i * j];
		myWidth = anInt1444 = i;
		myHeight = anInt1445 = j;
		anInt1442 = anInt1443 = 0;
	}
	
	public void setAlphaTransparency(int a) {
	      for (int pixel = 0; pixel < myPixels.length; pixel++){
	         if (((myPixels[pixel] >> 24) & 255) == a)
	            myPixels[pixel] = 0;
	      }
	   }
	
	public void drawSpriteOpacity(int i, int j, int k) {
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.canvasWidth;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.canvasWidth - l1;
		int j2 = 0;
		if(j < DrawingArea.clipStartY) {
			int k2 = DrawingArea.clipStartY - j;
			k1 -= k2;
			j = DrawingArea.clipStartY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.canvasWidth;
		}
		if(j + k1 > DrawingArea.clipEndY)
			k1 -= (j + k1) - DrawingArea.clipEndY;
		if(i < DrawingArea.clipStartX) {
			int l2 = DrawingArea.clipStartX - i;
			l1 -= l2;
			i = DrawingArea.clipStartX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if(i + l1 > DrawingArea.clipEndX) {
			int i3 = (i + l1) - DrawingArea.clipEndX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if(!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.canvasRaster, myPixels, j2, k1, i2, k, i1);
		}
	}
	
	public void drawTransparentSprite(int x, int y) {
		drawTransparentSprite(x, y, 256);
	}
	
    public void drawTransparentSprite(int i, int j, int opacity)
    {
            int k = opacity;
            i += anInt1442;
            j += anInt1443;
            int i1 = i + j * DrawingArea.canvasWidth;
            int j1 = 0;
            int k1 = myHeight;
            int l1 = myWidth;
            int i2 = DrawingArea.canvasWidth - l1;
            int j2 = 0;
            if(j < DrawingArea.clipStartY)
            {
                    int k2 = DrawingArea.clipStartY - j;
                    k1 -= k2;
                    j = DrawingArea.clipStartY;
                    j1 += k2 * l1;
                    i1 += k2 * DrawingArea.canvasWidth;
            }
            if(j + k1 > DrawingArea.clipEndY)
                    k1 -= (j + k1) - DrawingArea.clipEndY;
            if(i < DrawingArea.clipStartX)
            {
                    int l2 = DrawingArea.clipStartX - i;
                    l1 -= l2;
                    i = DrawingArea.clipStartX;
                    j1 += l2;
                    i1 += l2;
                    j2 += l2;
                    i2 += l2;
            }
            if(i + l1 > DrawingArea.clipEndX)
            {
                    int i3 = (i + l1) - DrawingArea.clipEndX;
                    l1 -= i3;
                    j2 += i3;
                    i2 += i3;
            }
            if(!(l1 <= 0 || k1 <= 0))
            {
                    method351(j1, l1, DrawingArea.canvasRaster, myPixels, j2, k1, i2, k, i1);
            }
    }
    
    public void drawAdvancedSprite(int i, int j) {
        int k = 256;
        i += anInt1442;
        j += anInt1443;
        int i1 = i + j * DrawingArea.canvasWidth;
        int j1 = 0;
        int k1 = myHeight;
        int l1 = myWidth;
        int i2 = DrawingArea.canvasWidth - l1;
        int j2 = 0;
        if (j < DrawingArea.clipStartY) {
           int k2 = DrawingArea.clipStartY - j;
           k1 -= k2;
           j = DrawingArea.clipStartY;
           j1 += k2 * l1;
           i1 += k2 * DrawingArea.canvasWidth;
        }
        if (j + k1 > DrawingArea.clipEndY)
           k1 -= (j + k1) - DrawingArea.clipEndY;
        if (i < DrawingArea.clipStartX) {
           int l2 = DrawingArea.clipStartX - i;
           l1 -= l2;
           i = DrawingArea.clipStartX;
           j1 += l2;
           i1 += l2;
           j2 += l2;
           i2 += l2;
        }
        if (i + l1 > DrawingArea.clipEndX) {
           int i3 = (i + l1) - DrawingArea.clipEndX;
           l1 -= i3;
           j2 += i3;
           i2 += i3;
        }
        if (!(l1 <= 0 || k1 <= 0)) {
           drawAlphaSprite(j1, l1, DrawingArea.canvasRaster, myPixels, j2, k1, i2, k, i1);
        }
     }
    
    private void drawAlphaSprite(int i, int j, int ai[], int ai1[], int l, int i1, int j1, int k1, int l1) {
        int k;// was parameter
        int j2;
        for (int k2 = -i1; k2 < 0; k2++) {
           for (int l2 = -j; l2 < 0; l2++) {
              k1 = ((myPixels[i] >> 24) & 255);
              j2 = 256 - k1;
              k = ai1[i++];
              if (k != 0) {
                 int i3 = ai[l1];
                 ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00) + ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
              } else {
                 l1++;
              }
           }
           
           l1 += j1;
           i += l;
        }
     }
	
	public void drawARGBSprite(int xPos, int yPos) {
		drawARGBSprite(xPos, yPos, 256);
	}

	public void drawARGBSprite(int xPos, int yPos, int alpha) {
		int alphaValue = alpha;
		xPos += anInt1442;
		yPos += anInt1443;
		int i1 = xPos + yPos * DrawingArea.canvasWidth;
		int j1 = 0;
		int spriteHeight = myHeight;
		int spriteWidth = myWidth;
		int i2 = DrawingArea.canvasWidth - spriteWidth;
		int j2 = 0;
		if (yPos < DrawingArea.clipStartY) {
			int k2 = DrawingArea.clipStartY - yPos;
			spriteHeight -= k2;
			yPos = DrawingArea.clipStartY;
			j1 += k2 * spriteWidth;
			i1 += k2 * DrawingArea.canvasWidth;
		}
		if (yPos + spriteHeight > DrawingArea.clipEndY)
			spriteHeight -= (yPos + spriteHeight) - DrawingArea.clipEndY;
			if (xPos < DrawingArea.clipStartX) {
			int l2 = DrawingArea.clipStartX - xPos;
			spriteWidth -= l2;
			xPos = DrawingArea.clipStartX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (xPos + spriteWidth > DrawingArea.clipEndX) {
			int i3 = (xPos + spriteWidth) - DrawingArea.clipEndX;
			spriteWidth -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(spriteWidth <= 0 || spriteHeight <= 0)) {
			renderARGBPixels(spriteWidth, spriteHeight, myPixels, DrawingArea.canvasRaster, i1, alphaValue, j1, j2, i2);
		}
	}

    private void renderARGBPixels(int spriteWidth, int spriteHeight, int spritePixels[], int renderAreaPixels[], int pixel, int alphaValue, int i, int l, int j1) {
    	int pixelColor;
    	int alphaLevel;
    	int alpha = alphaValue;
    	for (int height = -spriteHeight; height < 0; height++) {
    		for (int width = -spriteWidth; width < 0; width++) {
    			alphaValue = ((myPixels[i] >> 24) & (alpha - 1));
    			alphaLevel = 256 - alphaValue;
    			if (alphaLevel > 256) {
    				alphaValue = 0;
    			}
    			if (alpha == 0) {
    				alphaLevel = 256;
    				alphaValue = 0;
    			} 
    			pixelColor = spritePixels[i++];
    			if (pixelColor != 0) {
    				int pixelValue = renderAreaPixels[pixel];
    				renderAreaPixels[pixel++] = ((pixelColor & 0xff00ff) * alphaValue + (pixelValue & 0xff00ff) * alphaLevel & 0xff00ff00) + ((pixelColor & 0xff00) * alphaValue + (pixelValue & 0xff00) * alphaLevel & 0xff0000) >> 8;
    			} else {
    				pixel++;
    			}
    		}
    		pixel += j1;
    		i += l;
    	}
    }
		
	public String location = Signlink.findcachedir() + "Interfaces/";

	public Sprite(byte abyte0[], Component component) {
		try {
			MediaTracker mediatracker = new MediaTracker(component);
			mediatracker.waitForAll();
			anInt1444 = myWidth;
			anInt1445 = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
		} catch(Exception _ex) {
			System.out.println("Error converting jpg");
		}
	}
	
	public Sprite(byte spriteData[]) {
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(spriteData);
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			anInt1444 = myWidth;
			anInt1445 = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
			setTransparency(255, 0, 255);
		} catch (Exception _ex) {
			System.out.println(_ex);
		}
	}

	/*public Sprite(String img, int width, int height) {
		try {
			myWidth = width;
			myHeight = height;
			anInt1444 = myWidth;
			anInt1445 = myHeight;
			anInt1442 = 0;
			anInt1443 = 0;
			myPixels = new int[myWidth * myHeight];
		} catch(Exception _ex) {
			System.out.println(_ex);
		}
	}*/
	
	public Sprite(String img, int i)
    {
        ImageIcon imageicon = new ImageIcon(img);
        imageicon.getIconHeight();
        imageicon.getIconWidth();
        try {
	    Image image = Toolkit.getDefaultToolkit().createImage(Utility.ReadFile(img));
	    myWidth = imageicon.getIconWidth();
	    myHeight = imageicon.getIconHeight();
            anInt1444 = myWidth;
            anInt1445 = myHeight;
            anInt1442 = 0;
            anInt1443 = 0;
            myPixels = new int[myWidth * myHeight];
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
            pixelgrabber.grabPixels();
	    image = null;
        } catch(Exception _ex) {
            System.out.println(_ex);
        }
    }
	
	public Sprite(String img, int width, int height)
    {
        try {
	    Image image = Toolkit.getDefaultToolkit().createImage(Utility.ReadFile(img));
	    myWidth = width;
	    myHeight = height;
            anInt1444 = myWidth;
            anInt1445 = myHeight;
            anInt1442 = 0;
            anInt1443 = 0;
            myPixels = new int[myWidth * myHeight];
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
            pixelgrabber.grabPixels();
	    image = null;
        } catch(Exception _ex) {
            System.out.println(_ex);
        }
    }

	 public Sprite(String img) {
			try {			
				Image image = Toolkit.getDefaultToolkit().getImage(location + img + ".png");
				ImageIcon sprite = new ImageIcon(image);
				myWidth = sprite.getIconWidth();
				myHeight = sprite.getIconHeight();
				anInt1444 = myWidth;
				anInt1445 = myHeight;
				anInt1442 = 0;
				anInt1443 = 0;
				myPixels = new int[myWidth * myHeight];
				PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
				pixelgrabber.grabPixels();
				image = null;
				setTransparency(255, 0, 255);
				setAlphaTransparency(0);
			} catch(Exception _ex) {
				System.out.println(_ex);
			}
		}
	 
	 
	
	public void draw24BitSprite(int x, int y) {
		int alpha = 256;
		x += this.anInt1442;// offsetX
		y += this.anInt1443;// offsetY
		int destOffset = x + y * DrawingArea.canvasWidth;
		int srcOffset = 0;
		int height = this.myHeight;
		int width = this.myWidth;
		int destStep = DrawingArea.canvasWidth - width;
		int srcStep = 0;
		if (y < DrawingArea.clipStartY) {
			int trimHeight = DrawingArea.clipStartY - y;
			height -= trimHeight;
			y = DrawingArea.clipStartY;
			srcOffset += trimHeight * width;
			destOffset += trimHeight * DrawingArea.canvasWidth;
		}
		if (y + height > DrawingArea.clipEndY) {
			height -= (y + height) - DrawingArea.clipEndY;
		}
		if (x < DrawingArea.clipStartX) {
			int trimLeft = DrawingArea.clipStartX - x;
			width -= trimLeft;
			x = DrawingArea.clipStartX;
			srcOffset += trimLeft;
			destOffset += trimLeft;
			srcStep += trimLeft;
			destStep += trimLeft;
		}
		if (x + width > DrawingArea.clipEndX) {
			int trimRight = (x + width) - DrawingArea.clipEndX;
			width -= trimRight;
			srcStep += trimRight;
			destStep += trimRight;
		}
		if (!((width <= 0) || (height <= 0))) {
			set24BitPixels(width, height, DrawingArea.canvasRaster, myPixels, alpha, destOffset,
				srcOffset, destStep, srcStep);
		}
	}
	

	private void set24BitPixels(int width, int height, int destPixels[], int srcPixels[],
		int srcAlpha, int destOffset, int srcOffset, int destStep, int srcStep) {
		int srcColor;
		int destAlpha;
		for (int loop = -height; loop < 0; loop++) {
			for (int loop2 = -width; loop2 < 0; loop2++) {
				srcAlpha = ((this.myPixels[srcOffset] >> 24) & 255);
				destAlpha = 256 - srcAlpha;
				srcColor = srcPixels[srcOffset++];
				if (srcColor != 0 && srcColor != 0xffffff) {
					int destColor = destPixels[destOffset];
					destPixels[destOffset++] = ((srcColor & 0xff00ff) * srcAlpha
						+ (destColor & 0xff00ff) * destAlpha & 0xff00ff00)
						+ ((srcColor & 0xff00) * srcAlpha + (destColor & 0xff00) * destAlpha & 0xff0000) >> 8;
				} else {
					destOffset++;
				}
			}
			destOffset += destStep;
			srcOffset += srcStep;
		}
	}	
	
	public void setTransparency(int transRed, int transGreen, int transBlue)
	{
		for(int index = 0; index < myPixels.length; index++)
			if(((myPixels[index] >> 16) & 255) == transRed && ((myPixels[index] >> 8) & 255) == transGreen && (myPixels[index] & 255) == transBlue)
				myPixels[index] = 0;
	}
	
	public Sprite(Archive jagexArchive, String s, int i)
	{
		Buffer buffer = new Buffer(jagexArchive.getDataForName(s + ".dat"));
		Buffer stream_1 = new Buffer(jagexArchive.getDataForName("index.dat"));
		stream_1.currentOffset = buffer.readUnsignedWord();
		anInt1444 = stream_1.readUnsignedWord();
		anInt1445 = stream_1.readUnsignedWord();
		int j = stream_1.readUnsignedByte();
		int ai[] = new int[j];
		for(int k = 0; k < j - 1; k++)
		{
			ai[k + 1] = stream_1.read3Bytes();
			if(ai[k + 1] == 0)
				ai[k + 1] = 1;
		}

		for(int l = 0; l < i; l++)
		{
			stream_1.currentOffset += 2;
			buffer.currentOffset += stream_1.readUnsignedWord() * stream_1.readUnsignedWord();
			stream_1.currentOffset++;
		}

		anInt1442 = stream_1.readUnsignedByte();
		anInt1443 = stream_1.readUnsignedByte();
		myWidth = stream_1.readUnsignedWord();
		myHeight = stream_1.readUnsignedWord();
		int i1 = stream_1.readUnsignedByte();
		int j1 = myWidth * myHeight;
		myPixels = new int[j1];
		if(i1 == 0)
		{
			for(int k1 = 0; k1 < j1; k1++)
				myPixels[k1] = ai[buffer.readUnsignedByte()];
			setTransparency(255,0,255);
			return;
		}
		if(i1 == 1)
		{
			for(int l1 = 0; l1 < myWidth; l1++)
			{
				for(int i2 = 0; i2 < myHeight; i2++)
					myPixels[l1 + i2 * myWidth] = ai[buffer.readUnsignedByte()];
			}

		}
		setTransparency(255,0,255);
	}

	public void method343()
	{
		DrawingArea.initDrawingArea(myHeight, myWidth, myPixels, null);
	}

	public void method344(int i, int j, int k)
	{
		for(int i1 = 0; i1 < myPixels.length; i1++)
		{
			int j1 = myPixels[i1];
			if(j1 != 0)
			{
				int k1 = j1 >> 16 & 0xff;
				k1 += i;
				if(k1 < 1)
					k1 = 1;
				else
				if(k1 > 255)
					k1 = 255;
				int l1 = j1 >> 8 & 0xff;
				l1 += j;
				if(l1 < 1)
					l1 = 1;
				else
				if(l1 > 255)
					l1 = 255;
				int i2 = j1 & 0xff;
				i2 += k;
				if(i2 < 1)
					i2 = 1;
				else
				if(i2 > 255)
					i2 = 255;
				myPixels[i1] = (k1 << 16) + (l1 << 8) + i2;
			}
		}

	}

	public void method345()
	{
		int ai[] = new int[anInt1444 * anInt1445];
		for(int j = 0; j < myHeight; j++)
		{
			System.arraycopy(myPixels, j * myWidth, ai, j + anInt1443 * anInt1444 + anInt1442, myWidth);
		}

		myPixels = ai;
		myWidth = anInt1444;
		myHeight = anInt1445;
		anInt1442 = 0;
		anInt1443 = 0;
	}

	public void method346(int i, int j)
	{
		i += anInt1442;
		j += anInt1443;
		int l = i + j * DrawingArea.canvasWidth;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = DrawingArea.canvasWidth - k1;
		int i2 = 0;
		if(j < DrawingArea.clipStartY)
		{
			int j2 = DrawingArea.clipStartY - j;
			j1 -= j2;
			j = DrawingArea.clipStartY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.canvasWidth;
		}
		if(j + j1 > DrawingArea.clipEndY)
			j1 -= (j + j1) - DrawingArea.clipEndY;
		if(i < DrawingArea.clipStartX)
		{
			int k2 = DrawingArea.clipStartX - i;
			k1 -= k2;
			i = DrawingArea.clipStartX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if(i + k1 > DrawingArea.clipEndX)
		{
			int l2 = (i + k1) - DrawingArea.clipEndX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if(k1 <= 0 || j1 <= 0)
		{
		} else
		{
			method347(l, k1, j1, i2, i1, l1, myPixels, DrawingArea.canvasRaster);
		}
	}

	private void method347(int i, int j, int k, int l, int i1, int k1,
						   int ai[], int ai1[])
	{
		int l1 = -(j >> 2);
		j = -(j & 3);
		for(int i2 = -k; i2 < 0; i2++)
		{
			for(int j2 = l1; j2 < 0; j2++)
			{
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
			}

			for(int k2 = j; k2 < 0; k2++)
				ai1[i++] = ai[i1++];

			i += k1;
			i1 += l;
		}
	}

	public void drawSprite1(int i, int j)
	{
		int k = 128;//was parameter
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.canvasWidth;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.canvasWidth - l1;
		int j2 = 0;
		if(j < DrawingArea.clipStartY)
		{
			int k2 = DrawingArea.clipStartY - j;
			k1 -= k2;
			j = DrawingArea.clipStartY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.canvasWidth;
		}
		if(j + k1 > DrawingArea.clipEndY)
			k1 -= (j + k1) - DrawingArea.clipEndY;
		if(i < DrawingArea.clipStartX)
		{
			int l2 = DrawingArea.clipStartX - i;
			l1 -= l2;
			i = DrawingArea.clipStartX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if(i + l1 > DrawingArea.clipEndX)
		{
			int i3 = (i + l1) - DrawingArea.clipEndX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if(!(l1 <= 0 || k1 <= 0))
		{
			method351(j1, l1, DrawingArea.canvasRaster, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawSprite(int i, int k)
	{
		i += anInt1442;
		k += anInt1443;
		int l = i + k * DrawingArea.canvasWidth;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = DrawingArea.canvasWidth - k1;
		int i2 = 0;
		if(k < DrawingArea.clipStartY)
		{
			int j2 = DrawingArea.clipStartY - k;
			j1 -= j2;
			k = DrawingArea.clipStartY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.canvasWidth;
		}
		if(k + j1 > DrawingArea.clipEndY)
			j1 -= (k + j1) - DrawingArea.clipEndY;
		if(i < DrawingArea.clipStartX)
		{
			int k2 = DrawingArea.clipStartX - i;
			k1 -= k2;
			i = DrawingArea.clipStartX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if(i + k1 > DrawingArea.clipEndX)
		{
			int l2 = (i + k1) - DrawingArea.clipEndX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if(!(k1 <= 0 || j1 <= 0))
		{
			method349(DrawingArea.canvasRaster, myPixels, i1, l, k1, j1, l1, i2);
		}
	}

	public void drawSprite(int i, int k, int color) {
		int tempWidth = myWidth + 2;
		int tempHeight = myHeight + 2;
		int[] tempArray = new int[tempWidth * tempHeight];
		for(int x = 0; x < myWidth; x++) {
			for(int y = 0; y < myHeight; y++) {
				if(myPixels[x + y * myWidth] != 0)
					tempArray[(x + 1) + (y + 1) * tempWidth] = myPixels[x + y * myWidth];
			}
		}
		for(int x = 0; x < tempWidth; x++) {
			for(int y = 0; y < tempHeight; y++) {
				if(tempArray[(x) + (y) * tempWidth] == 0) {
					if(x < tempWidth - 1 && tempArray[(x + 1) + ((y) * tempWidth)] > 0 && tempArray[(x + 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if(x > 0 && tempArray[(x - 1) + ((y) * tempWidth)] > 0 && tempArray[(x - 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if(y < tempHeight - 1 && tempArray[(x) + ((y + 1) * tempWidth)] > 0 && tempArray[(x) + ((y + 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if(y > 0 && tempArray[(x) + ((y - 1) * tempWidth)] > 0 && tempArray[(x) + ((y - 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
				}
			}
		}
		i--;
		k--;
		i += anInt1442;
		k += anInt1443;
		int l = i + k * DrawingArea.canvasWidth;
		int i1 = 0;
		int j1 = tempHeight;
		int k1 = tempWidth;
		int l1 = DrawingArea.canvasWidth - k1;
		int i2 = 0;
		if (k < DrawingArea.clipStartY) {
			int j2 = DrawingArea.clipStartY - k;
			j1 -= j2;
			k = DrawingArea.clipStartY;
			i1 += j2 * k1;
			l += j2 * DrawingArea.canvasWidth;
		}
		if (k + j1 > DrawingArea.clipEndY) {
			j1 -= (k + j1) - DrawingArea.clipEndY;
		}
		if (i < DrawingArea.clipStartX) {
			int k2 = DrawingArea.clipStartX - i;
			k1 -= k2;
			i = DrawingArea.clipStartX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > DrawingArea.clipEndX) {
			int l2 = (i + k1) - DrawingArea.clipEndX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			method349(DrawingArea.canvasRaster, tempArray, i1, l, k1, j1, l1, i2);
		}
	}
	
	
	public void drawSprite2(int i, int j) {
		int k = 225;//was parameter
		i += anInt1442;
		j += anInt1443;
		int i1 = i + j * DrawingArea.canvasWidth;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = DrawingArea.canvasWidth - l1;
		int j2 = 0;
		if(j < DrawingArea.clipStartY) {
			int k2 = DrawingArea.clipStartY - j;
			k1 -= k2;
			j = DrawingArea.clipStartY;
			j1 += k2 * l1;
			i1 += k2 * DrawingArea.canvasWidth;
		}
		if(j + k1 > DrawingArea.clipEndY)
			k1 -= (j + k1) - DrawingArea.clipEndY;
		if(i < DrawingArea.clipStartX) {
			int l2 = DrawingArea.clipStartX - i;
			l1 -= l2;
			i = DrawingArea.clipStartX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if(i + l1 > DrawingArea.clipEndX) {
			int i3 = (i + l1) - DrawingArea.clipEndX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if(!(l1 <= 0 || k1 <= 0)) {
			method351(j1, l1, DrawingArea.canvasRaster, myPixels, j2, k1, i2, k, i1);
		}
	}

	private void method349(int ai[], int ai1[], int j, int k, int l, int i1, int j1, int k1) {
		int i;//was parameter
		int l1 = -(l >> 2);
		l = -(l & 3);
		for(int i2 = -i1; i2 < 0; i2++) {
			for(int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if(i != 0 && i != -1)
				{
					ai[k++] = i;
				} else {
				k++;
			}
			i = ai1[j++];
			if(i != 0 && i != -1) {
				ai[k++] = i;
			} else {
				k++;
			}
			i = ai1[j++];
			if(i != 0 && i != -1) {
				ai[k++] = i;
			} else {
				k++;
			}
			i = ai1[j++];
			if(i != 0 && i != -1) {
				ai[k++] = i;
			} else {
				k++;
			}
		}

		for(int k2 = l; k2 < 0; k2++) {
			i = ai1[j++];
			if(i != 0 && i != -1) {
				ai[k++] = i;
			} else {
				k++;
			}
		}
		k += j1;
		j += k1;
		}
	}

	private void method351(int i, int j, int ai[], int ai1[], int l, int i1,
						   int j1, int k1, int l1)
	{
		int k;//was parameter
		int j2 = 256 - k1;
		for(int k2 = -i1; k2 < 0; k2++)
		{
			for(int l2 = -j; l2 < 0; l2++)
			{
				k = ai1[i++];
				if(k != 0)
				{
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00) + ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else
				{
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public void method352(int i, int j, int ai[], int k, int ai1[], int i1,
						  int j1, int k1, int l1, int i2)
	{
		try
		{
			int j2 = -l1 / 2;
			int k2 = -i / 2;
			int l2 = (int)(Math.sin((double)j / 326.11000000000001D) * 65536D);
			int i3 = (int)(Math.cos((double)j / 326.11000000000001D) * 65536D);
			l2 = l2 * k >> 8;
			i3 = i3 * k >> 8;
			int j3 = (i2 << 16) + (k2 * l2 + j2 * i3);
			int k3 = (i1 << 16) + (k2 * i3 - j2 * l2);
			int l3 = k1 + j1 * DrawingArea.canvasWidth;
			for(j1 = 0; j1 < i; j1++)
			{
				int i4 = ai1[j1];
				int j4 = l3 + i4;
				int k4 = j3 + i3 * i4;
				int l4 = k3 - l2 * i4;
				for(k1 = -ai[j1]; k1 < 0; k1++)
				{
					DrawingArea.canvasRaster[j4++] = myPixels[(k4 >> 16) + (l4 >> 16) * myWidth];
					k4 += i3;
					l4 -= l2;
				}

				j3 += l2;
				k3 += i3;
				l3 += DrawingArea.canvasWidth;
			}

		}
		catch(Exception _ex)
		{
		}
	}

	public void method353(int i,
						  double d, int l1)
	{
		//all of the following were parameters
		int j = 15;
		int k = 20;
		int l = 15;
		int j1 = 256;
		int k1 = 20;
		//all of the previous were parameters
		try
		{
			int i2 = -k / 2;
			int j2 = -k1 / 2;
			int k2 = (int)(Math.sin(d) * 65536D);
			int l2 = (int)(Math.cos(d) * 65536D);
			k2 = k2 * j1 >> 8;
			l2 = l2 * j1 >> 8;
			int i3 = (l << 16) + (j2 * k2 + i2 * l2);
			int j3 = (j << 16) + (j2 * l2 - i2 * k2);
			int k3 = l1 + i * DrawingArea.canvasWidth;
			for(i = 0; i < k1; i++)
			{
				int l3 = k3;
				int i4 = i3;
				int j4 = j3;
				for(l1 = -k; l1 < 0; l1++)
				{
					int k4 = myPixels[(i4 >> 16) + (j4 >> 16) * myWidth];
					if(k4 != 0)
						DrawingArea.canvasRaster[l3++] = k4;
					else
						l3++;
					i4 += l2;
					j4 -= k2;
				}

				i3 += k2;
				j3 += l2;
				k3 += DrawingArea.canvasWidth;
			}

		}
		catch(Exception _ex)
		{
		}
	}

	public void method354(Background background, int i, int j)
	{
		j += anInt1442;
		i += anInt1443;
		int k = j + i * DrawingArea.canvasWidth;
		int l = 0;
		int i1 = myHeight;
		int j1 = myWidth;
		int k1 = DrawingArea.canvasWidth - j1;
		int l1 = 0;
		if(i < DrawingArea.clipStartY)
		{
			int i2 = DrawingArea.clipStartY - i;
			i1 -= i2;
			i = DrawingArea.clipStartY;
			l += i2 * j1;
			k += i2 * DrawingArea.canvasWidth;
		}
		if(i + i1 > DrawingArea.clipEndY)
			i1 -= (i + i1) - DrawingArea.clipEndY;
		if(j < DrawingArea.clipStartX)
		{
			int j2 = DrawingArea.clipStartX - j;
			j1 -= j2;
			j = DrawingArea.clipStartX;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if(j + j1 > DrawingArea.clipEndX)
		{
			int k2 = (j + j1) - DrawingArea.clipEndX;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if(!(j1 <= 0 || i1 <= 0))
		{
			method355(myPixels, j1, background.aByteArray1450, i1, DrawingArea.canvasRaster, 0, k1, k, l1, l);
		}
	}

	private void method355(int ai[], int i, byte abyte0[], int j, int ai1[], int k,
						   int l, int i1, int j1, int k1)
	{
		int l1 = -(i >> 2);
		i = -(i & 3);
		for(int j2 = -j; j2 < 0; j2++)
		{
			for(int k2 = l1; k2 < 0; k2++)
			{
				k = ai[k1++];
				if(k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if(k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if(k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if(k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			for(int l2 = i; l2 < 0; l2++)
			{
				k = ai[k1++];
				if(k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			i1 += l;
			k1 += j1;
		}

	}

	public int myPixels[];
	public int myWidth;
	public int myHeight;
	int anInt1442;
	int anInt1443;
	public int anInt1444;
	public int anInt1445;

	public void drawSpriteCC(int x, int y) {
        setTransparency(255, 0, 255);
        x += anInt1442;
        y += anInt1443;
        int l = x + y * canvasWidth;
        int i1 = 0;
        int j1 = myHeight;
        int k1 = myWidth;
        int l1 = canvasWidth - k1;
        int i2 = 0;
        if (y < clipStartY) {
            int j2 = clipStartY - y;
            j1 -= j2;
            y = clipStartY;
            i1 += j2 * k1;
            l += j2 * canvasWidth;
        }
        if (y + j1 > clipEndY)
            j1 -= (y + j1) - clipEndY;
        if (x < clipStartX) {
            int k2 = clipStartX - x;
            k1 -= k2;
            x = clipStartX;
            i1 += k2;
            l += k2;
            i2 += k2;
            l1 += k2;
        }
        if (x + k1 > clipEndX) {
            int l2 = (x + k1) - clipEndX;
            k1 -= l2;
            i2 += l2;
            l1 += l2;
        }
        if (!(k1 <= 0 || j1 <= 0)) {
            method349(canvasRaster, myPixels, i1, l, k1, j1, l1, i2);
        }
    }
}
