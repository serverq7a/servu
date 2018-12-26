public class DrawingArea extends NodeSub {
	
	public static void initDrawingArea(int i, int j, int ai[], float[] depth)
	{
		canvasRaster = ai;
		depthBuffer = depth;
		canvasWidth = j;
		canvasHeight = i;
		setDrawingArea(i, 0, j, 0);
	}
	
	public static void drawVerticalLine(int x, int y, int h, int colour) {
		if (x < clipStartX || x >= clipEndX) {
			return;
		}
		if (y < clipStartY) {
			h -= clipStartY - y;
			y = clipStartY;
		}
		if (y + h > clipEndY) {
			h = clipEndY - y;
		}
		int pixel = x + y * canvasWidth;
		for (int index = 0; index < h; index++) {
			canvasRaster[pixel + index * canvasWidth] = colour;
		}
	}/*	public static void drawHorizontalLine(int drawX, int drawY, int lineWidth, int i_62_) {
        if (drawY >= topY && drawY < bottomY) {
            if (drawX < topX) {
                lineWidth -= topX - drawX;
                drawX = topX;
            }
            if (drawX + lineWidth > bottomX) {
                lineWidth = bottomX - drawX;
            }
            int i_63_ = drawX + drawY * width;
            for (int i_64_ = 0; i_64_ < lineWidth; i_64_++) {
                pixels[i_63_ + i_64_] = i_62_;
            }
        }
    }*/
	
	public static void drawUnfilledPixels(int x, int y, int width, int height, int colour) {
		drawHorizontalLine(x, y, width, colour);
		drawHorizontalLine(x, (y + height) - 1, width, colour);
		drawVerticalLine(x, y, height, colour);
		drawVerticalLine((x + width) - 1, y, height, colour);
	}
	
	public static void drawAlphaGradient(int x, int y, int gradientWidth, 
            int gradientHeight, int startColor, int endColor, int alpha) { 
        int k1 = 0; 
        int l1 = 0x10000 / gradientHeight; 
        if (x < clipStartX) { 
            gradientWidth -= clipStartX - x; 
            x = clipStartX; 
        } 
        if (y < clipStartY) { 
            k1 -= (clipStartY - y) * l1; 
            gradientHeight -= clipStartY - y; 
            y = clipStartY; 
        } 
        if (x + gradientWidth > clipEndX) 
            gradientWidth = clipEndX - x; 
        if (y + gradientHeight > clipEndY) 
            gradientHeight = clipEndY - y; 
        int i2 = canvasWidth - gradientWidth; 
        int total_pixels = x + y * canvasWidth; 
        for (int k2 = -gradientHeight; k2 < 0; k2++) { 
            int alpha2 = (gradientHeight+k2)*(gradientHeight/alpha); 
            int result_alpha = 256 - alpha2; 
            int gradient1 = 0x10000 - k1 >> 8; 
            int gradient2 = k1 >> 8; 
            int gradient_color = ((startColor & 0xff00ff) * gradient1 
            		+ (endColor & 0xff00ff) * gradient2 & 0xff00ff00) 
                    + ((startColor & 0xff00) * gradient1 + (endColor & 0xff00) 
                            * gradient2 & 0xff0000) >>> 8; 
            int color = ((gradient_color & 0xff00ff) * alpha >> 8 & 0xff00ff) 
                    + ((gradient_color & 0xff00) * alpha >> 8 & 0xff00); 
            for (int k3 = -gradientWidth; k3 < 0; k3++) { 
                int colored_pixel = canvasRaster[total_pixels]; 
                colored_pixel = ((colored_pixel & 0xff00ff) * result_alpha >> 8 & 0xff00ff) 
                        + ((colored_pixel & 0xff00) * result_alpha >> 8 & 0xff00); 
                canvasRaster[total_pixels++] = color + colored_pixel; 
            } 
            total_pixels += i2; 
            k1 -= l1; 
        } 
    } 
	
	
    public static void drawBubble(int x, int y, int radius, int colour, int initialAlpha) {
            fillCircleAlpha(x, y, radius, colour, initialAlpha);
            fillCircleAlpha(x, y, radius + 2, colour, 8);
            fillCircleAlpha(x, y, radius + 4, colour, 6);
            fillCircleAlpha(x, y, radius + 6, colour, 4);
            fillCircleAlpha(x, y, radius + 8, colour, 2);
    }

    public static void fillCircleAlpha(int posX, int posY, int radius, int colour, int alpha)
    {
            int dest_intensity = 256 - alpha;
            int src_red = (colour >> 16 & 0xff) * alpha;
            int src_green = (colour >> 8 & 0xff) * alpha;
            int src_blue = (colour & 0xff) * alpha;
            int i3 = posY - radius;
            if(i3 < 0)
                    i3 = 0;
            int j3 = posY + radius;
            if(j3 >= canvasHeight)
                    j3 = canvasHeight - 1;
            for(int y = i3; y <= j3; y++)
            {
                    int l3 = y - posY;
                    int i4 = (int)Math.sqrt(radius * radius - l3 * l3);
                    int x = posX - i4;
                    if(x < 0)
                            x = 0;
                    int k4 = posX + i4;
                    if(k4 >= canvasWidth)
                            k4 = canvasWidth - 1;
                    int pixel_offset = x + y * canvasWidth;
                    for(int i5 = x; i5 <= k4; i5++)
                    {
                            int dest_red = (canvasRaster[pixel_offset] >> 16 & 0xff) * dest_intensity;
                            int dest_green = (canvasRaster[pixel_offset] >> 8 & 0xff) * dest_intensity;
                            int dest_blue = (canvasRaster[pixel_offset] & 0xff) * dest_intensity;
                            int result_rgb = ((src_red + dest_red >> 8) << 16) + ((src_green + dest_green >> 8) << 8) + (src_blue + dest_blue >> 8);
                            canvasRaster[pixel_offset++] = result_rgb;
                    }
            }
    }
	
	private static int[] getPixels(int x, int y, int width, int height) {		
		int[] pixels = new int[width * height];
		if (x < DrawingArea.clipStartX) {
			width -= DrawingArea.clipStartX - x;
			x = DrawingArea.clipStartX;
		}
		if (y < DrawingArea.clipStartY) {
			height -= DrawingArea.clipStartY - y;
			y = DrawingArea.clipStartY;
		}
		if (x + width > DrawingArea.clipEndX) {
			width = DrawingArea.clipEndX - x;
		}
		if (y + height > DrawingArea.clipEndY) {
			height = DrawingArea.clipEndY - y;
		}
		int pixelOffset = DrawingArea.canvasWidth - width;
		int pixel = x + y * DrawingArea.canvasWidth;
		int newPixel = 0;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				pixels[newPixel++] = DrawingArea.canvasRaster[pixel++];
			}
			pixel += pixelOffset;
		}
		return pixels;
	}
	
	private static void setPixels(int x, int y, int width, int height, int[] newPixels) {
		if (x < DrawingArea.clipStartX) {
			width -= DrawingArea.clipStartX - x;
			x = DrawingArea.clipStartX;
		}
		if (y < DrawingArea.clipStartY) {
			height -= DrawingArea.clipStartY - y;
			y = DrawingArea.clipStartY;
		}
		if (x + width > DrawingArea.clipEndX) {
			width = DrawingArea.clipEndX - x;
		}
		if (y + height > DrawingArea.clipEndY) {
			height = DrawingArea.clipEndY - y;
		}
		int pixelOffset = DrawingArea.canvasWidth - width;
		int pixel = x + y * DrawingArea.canvasWidth;
		int newPixel = 0;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				DrawingArea.canvasRaster[pixel++] = newPixels[newPixel++];
			}
			pixel += pixelOffset;
		}
	}
	
	private static int[] applyBoxBlurFilter(int[] pixels, int width, int height, float[] kernel, float alpha, float red, float green, float blue) {
		int[] temp = new int[width * height];
		float denominator = 0.0f;
		int ialpha, ired, igreen, iblue, indexOffset, rgb;
		float a, r, g, b;
		int[] indices = {-width - 1, -width, -width + 1, -1, 0, +1, width - 1, width, width + 1};
		for (int i = 0; i < kernel.length; i++) {
			denominator += kernel[i];
		}
		if (denominator == 0.0f)
			denominator = 1.0f;
		for (int i = 1; i < height-1; i++) {
			for (int j = 1; j < width-1; j++) {
				a = alpha;
				r = red;
				g = green;
				b = blue;
				indexOffset = j + i * width;
				for (int k = 0; k < kernel.length; k++) {
					rgb = pixels[indexOffset + indices[k]];
					a += ((rgb & 0xff000000) >> 24) * kernel[k];
					r += ((rgb & 0xff0000) >> 16) * kernel[k];
					g += ((rgb & 0xff00) >> 8) * kernel[k];
					b += (rgb & 0xff) * kernel[k];
				}
				ialpha = (int) (a / denominator);
				ired = (int) (r / denominator);
				igreen = (int) (g / denominator);
				iblue = (int) (b / denominator);
				
				if (ired > 0xff)
					ired = 0xff;
				else if (ired < 0)
					ired = 0;
				if (igreen > 0xff)
					igreen = 0xff;
				else if (igreen < 0)
					igreen = 0;
				if (iblue > 0xff) 
					iblue = 0xff;
				else if (iblue < 0)
					iblue = 0;
				if (ialpha > 0xff)
					ialpha = 0xff;
				else if (ialpha < 0)
					ialpha = 0;
					
				temp[indexOffset] = ((ialpha << 24) & 0xff000000) | ((ired << 16) & 0xff0000) | ((igreen << 8) & 0xff00) | (iblue & 0xff);
			}
		}
		return temp;
	}
	
	public static void drawBoxBlur(int x, int y, int width, int height, float[] kernel, int colour, int speed) {
		float a = (colour & 0xff000000) >> 24;
		float r = (colour & 0xff0000) >> 16;
		float g = (colour & 0xff00) >> 8;
		float b = (colour & 0xff);
		for (int index = 0; index < speed; index++) {
			setPixels(x, y, width, height, applyBoxBlurFilter(getPixels(x, y, width, height), width, height, kernel, a, r, g, b));
		}
	}

	public static void defaultDrawingAreaSize()
	{
			clipStartX = 0;
			clipStartY = 0;
			clipEndX = canvasWidth;
			clipEndY = canvasHeight;
			centerX = clipEndX;
			centerY = clipEndX / 2;
	}
	
	public static void drawFilledPixels(int x, int y, int pixelWidth,
			int pixelHeight, int color) {// method578
		if (x < clipStartX) {
			pixelWidth -= clipStartX - x;
			x = clipStartX;
		}
		if (y < clipStartY) {
			pixelHeight -= clipStartY - y;
			y = clipStartY;
		}
		if (x + pixelWidth > clipEndX)
			pixelWidth = clipEndX - x;
		if (y + pixelHeight > clipEndY)
			pixelHeight = clipEndY - y;
		int j1 = canvasWidth - pixelWidth;
		int k1 = x + y * canvasWidth;
		for (int l1 = -pixelHeight; l1 < 0; l1++) {
			for (int i2 = -pixelWidth; i2 < 0; i2++)
				canvasRaster[k1++] = color;
			k1 += j1;
		}
	}
	
	
	public static void drawAlphaFilledPixels(int xPos, int yPos,
		    int pixelWidth, int pixelHeight, int color, int alpha) { // method586
		    if (xPos < clipStartX) {
		        pixelWidth -= clipStartX - xPos;
		        xPos = clipStartX;
		    }
		    if (yPos < clipStartY) {
		        pixelHeight -= clipStartY - yPos;
		        yPos = clipStartY;
		    }
		    if (xPos + pixelWidth > clipEndX)
		        pixelWidth = clipEndX - xPos;
		    if (yPos + pixelHeight > clipEndY)
		        pixelHeight = clipEndY - yPos;
		    color = ((color & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((color & 0xff00) * alpha >> 8 & 0xff00);
		    int k1 = 256 - alpha;
		    int l1 = canvasWidth - pixelWidth;
		    int i2 = xPos + yPos * canvasWidth;
		    for (int j2 = 0; j2 < pixelHeight; j2++) {
		        for (int k2 = -pixelWidth; k2 < 0; k2++) {
		            int l2 = canvasRaster[i2];
		            l2 = ((l2 & 0xff00ff) * k1 >> 8 & 0xff00ff) + ((l2 & 0xff00) * k1 >> 8 & 0xff00);
		            canvasRaster[i2++] = color + l2;
		        }
		        i2 += l1;
		    }
		}
	
	public static void drawHorizontalLine(int drawX, int drawY, int lineWidth, int i_62_) {
        if (drawY >= clipStartY && drawY < clipEndY) {
            if (drawX < clipStartX) {
                lineWidth -= clipStartX - drawX;
                drawX = clipStartX;
            }
            if (drawX + lineWidth > clipEndX) {
                lineWidth = clipEndX - drawX;
            }
            int i_63_ = drawX + drawY * canvasWidth;
            for (int i_64_ = 0; i_64_ < lineWidth; i_64_++) {
                canvasRaster[i_63_ + i_64_] = i_62_;
            }
        }
    }	

	public static void setDrawingArea(int i, int j, int k, int l)
	{
		if(j < 0)
			j = 0;
		if(l < 0)
			l = 0;
		if(k > canvasWidth)
			k = canvasWidth;
		if(i > canvasHeight)
			i = canvasHeight;
		clipStartX = j;
		clipStartY = l;
		clipEndX = k;
		clipEndY = i;
		centerX = clipEndX;
		centerY = clipEndX / 2;
		anInt1387 = clipEndY / 2;
	}

	public static void setAllPixelsToZero()
	{
		int i = canvasWidth * canvasHeight;
		for(int j = 0; j < i; j++) {
			canvasRaster[j] = 0;
			depthBuffer[j] = Float.MAX_VALUE;
		}

	}
	
	public static void method336(int i, int j, int k, int l, int i1) {
		if (k < clipStartX) {
			i1 -= clipStartX - k;
			k = clipStartX;
		}
		if (j < clipStartY) {
			i -= clipStartY - j;
			j = clipStartY;
		}
		if (k + i1 > clipEndX)
			i1 = clipEndX - k;
		if (j + i > clipEndY)
			i = clipEndY - j;
		int k1 = canvasWidth - i1;
		int l1 = k + j * canvasWidth;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++)
				canvasRaster[l1++] = l;

			l1 += k1;
		}

	}	

	public static void method335(int i, int j, int k, int l, int i1, int k1)
	{
		if(k1 < clipStartX)
		{
			k -= clipStartX - k1;
			k1 = clipStartX;
		}
		if(j < clipStartY)
		{
			l -= clipStartY - j;
			j = clipStartY;
		}
		if(k1 + k > clipEndX)
			k = clipEndX - k1;
		if(j + l > clipEndY)
			l = clipEndY - j;
		int l1 = 256 - i1;
		int i2 = (i >> 16 & 0xff) * i1;
		int j2 = (i >> 8 & 0xff) * i1;
		int k2 = (i & 0xff) * i1;
		int k3 = canvasWidth - k;
		int l3 = k1 + j * canvasWidth;
		for(int i4 = 0; i4 < l; i4++)
		{
			for(int j4 = -k; j4 < 0; j4++)
			{
				int l2 = (canvasRaster[l3] >> 16 & 0xff) * l1;
				int i3 = (canvasRaster[l3] >> 8 & 0xff) * l1;
				int j3 = (canvasRaster[l3] & 0xff) * l1;
				int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8) + (k2 + j3 >> 8);
				canvasRaster[l3++] = k4;
			}

			l3 += k3;
		}
	}
	//int i, int y, int x, int color, int width
	public static void drawPixels(int i, int j, int k, int l, int i1)
	{
		if(k < clipStartX)
		{
			i1 -= clipStartX - k;
			k = clipStartX;
		}
		if(j < clipStartY)
		{
			i -= clipStartY - j;
			j = clipStartY;
		}
		if(k + i1 > clipEndX)
			i1 = clipEndX - k;
		if(j + i > clipEndY)
			i = clipEndY - j;
		int k1 = canvasWidth - i1;
		int l1 = k + j * canvasWidth;
		for(int i2 = -i; i2 < 0; i2++)
		{
			for(int j2 = -i1; j2 < 0; j2++)
				canvasRaster[l1++] = l;

			l1 += k1;
		}

	}

	public static void fillPixels(int i, int j, int k, int l, int i1)
	{
		method339(i1, l, j, i);
		method339((i1 + k) - 1, l, j, i);
		method341(i1, l, k, i);
		method341(i1, l, k, (i + j) - 1);
	}

	public static void method338(int i, int j, int k, int l, int i1, int j1)
	{
		method340(l, i1, i, k, j1);
		method340(l, i1, (i + j) - 1, k, j1);
		if(j >= 3)
		{
			method342(l, j1, k, i + 1, j - 2);
			method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
		}
	}

	public static void method339(int i, int j, int k, int l)
	{
		if(i < clipStartY || i >= clipEndY)
			return;
		if(l < clipStartX)
		{
			k -= clipStartX - l;
			l = clipStartX;
		}
		if(l + k > clipEndX)
			k = clipEndX - l;
		int i1 = l + i * canvasWidth;
		for(int j1 = 0; j1 < k; j1++)
			canvasRaster[i1 + j1] = j;

	}

	private static void method340(int i, int j, int k, int l, int i1)
	{
		if(k < clipStartY || k >= clipEndY)
			return;
		if(i1 < clipStartX)
		{
			j -= clipStartX - i1;
			i1 = clipStartX;
		}
		if(i1 + j > clipEndX)
			j = clipEndX - i1;
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * canvasWidth;
		for(int j3 = 0; j3 < j; j3++)
		{
			int j2 = (canvasRaster[i3] >> 16 & 0xff) * j1;
			int k2 = (canvasRaster[i3] >> 8 & 0xff) * j1;
			int l2 = (canvasRaster[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			canvasRaster[i3++] = k3;
		}

	}

	public static void method341(int i, int j, int k, int l)
	{
		if(l < clipStartX || l >= clipEndX)
			return;
		if(i < clipStartY)
		{
			k -= clipStartY - i;
			i = clipStartY;
		}
		if(i + k > clipEndY)
			k = clipEndY - i;
		int j1 = l + i * canvasWidth;
		for(int k1 = 0; k1 < k; k1++)
			canvasRaster[j1 + k1 * canvasWidth] = j;

	}
	
	 public static void drawLine(int i, int j, int k, int l) 
	    { 
	        if(i < clipStartY || i >= clipEndY) 
	            return; 
	        if(l < clipStartX) 
	        { 
	            k -= clipStartX - l; 
	            l = clipStartX; 
	        } 
	        if(l + k > clipEndX) 
	            k = clipEndX - l; 
	        int i1 = l + i * canvasWidth; 
	        for(int j1 = 0; j1 < k; j1++) 
	        	canvasRaster[i1 + j1] = j; 
	  
	    } 

	static void method342(int i, int j, int k, int l, int i1) {
		if(j < clipStartX || j >= clipEndX)
			return;
		if(l < clipStartY) {
			i1 -= clipStartY - l;
			l = clipStartY;
		}
		if(l + i1 > clipEndY)
			i1 = clipEndY - l;
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * canvasWidth;
		for(int j3 = 0; j3 < i1; j3++) {
			int j2 = (canvasRaster[i3] >> 16 & 0xff) * j1;
			int k2 = (canvasRaster[i3] >> 8 & 0xff) * j1;
			int l2 = (canvasRaster[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			canvasRaster[i3] = k3;
			i3 += canvasWidth;
		}
	}
	
	public static void drawSection(int x, int y, int height, int width, String name) {
		fillRectangle(x - 5, y - 5, width + 10, height + 5, 0xFFFFFF, 30);
		fillRectangle(x - 2, y - 2, width + 4, height, 0x000000, 90);
		fillRectangle(x, y, width, 23, 0x646468, 90);
		Client.fancyFont.method382(0xFFFFFF, x + width / 2, name, y + 17, true);
	}
	
	public static void drawHorizontalLine(int x, int y, int length, int colour, int alpha) {
		if(y < clipEndY || y >= centerY) {
			return;
		}
		if(x < clipEndX) {
			length -= clipEndX - x;
			x = clipEndX;
		}
		if(x + length > centerX) {
			length = centerX - x;
		}
		final int j1 = 256 - alpha;
		final int k1 = (colour >> 16 & 0xff) * alpha;
		final int l1 = (colour >> 8 & 0xff) * alpha;
		final int i2 = (colour & 0xff) * alpha;
		int i3 = x + y * canvasWidth;
		for(int j3 = 0; j3 < length; j3++) {
			final int j2 = (canvasRaster[i3] >> 16 & 0xff) * j1;
			final int k2 = (canvasRaster[i3] >> 8 & 0xff) * j1;
			final int l2 = (canvasRaster[i3] & 0xff) * j1;
			final int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			canvasRaster[i3++] = k3;
		}
	}
	
	 public static void fillCircle(int x, int y, int radius, int color) {
	        int y1 = y - radius;
	        if (y1 < 0) {
	            y1 = 0;
	        }
	        int y2 = y + radius;
	        if (y2 >= canvasHeight) {
	            y2 = canvasHeight - 1;
	        }
	        for (int iy = y1; iy <= y2; iy++) {
	            int dy = iy - y;
	            int dist = (int) Math.sqrt(radius * radius - dy * dy);
	            int x1 = x - dist;
	            if (x1 < 0) {
	                x1 = 0;
	            }
	            int x2 = x + dist;
	            if (x2 >= canvasWidth) {
	                x2 = canvasWidth - 1;
	            }
	            int pos = x1 + iy * canvasWidth;
	            for (int ix = x1; ix <= x2; ix++) {
	                canvasRaster[pos++] = color;
	            }
	        }
	    }
	
	public static void fillCircle(int x, int y, int radius, int color, int alpha) {
        int a2 = 256 - alpha;
        int r1 = (color >> 16 & 0xff) * alpha;
        int g1 = (color >> 8 & 0xff) * alpha;
        int b1 = (color & 0xff) * alpha;
        int y1 = y - radius;
        if (y1 < 0) {
            y1 = 0;
        }
        int y2 = y + radius;
        if (y2 >= canvasHeight) {
            y2 = canvasHeight - 1;
        }
        for (int iy = y1; iy <= y2; iy++) {
            int dy = iy - y;
            int dist = (int) Math.sqrt(radius * radius - dy * dy);
            int x1 = x - dist;
            if (x1 < 0) {
                x1 = 0;
            }
            int x2 = x + dist;
            if (x2 >= canvasWidth) {
                x2 = canvasWidth - 1;
            }
            int pos = x1 + iy * canvasWidth;
            for (int ix = x1; ix <= x2; ix++) {
                int r2 = (canvasRaster[pos] >> 16 & 0xff) * a2;
                int g2 = (canvasRaster[pos] >> 8 & 0xff) * a2;
                int b2 = (canvasRaster[pos] & 0xff) * a2;
                canvasRaster[pos++] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
            }
        }
    }
	
	public static void fillRoundedRectangle(int x, int y, int width, int height, int radius, int color, int alpha) {
		if (x >= clipEndX || y >= clipEndY || x + width < clipStartX || y + height < clipStartY) {
			return;
		}
		if (width == height) {
			if (radius > width >> 1) {
				fillCircle(x + radius, y + radius,radius, color);
				return;
			}
		} else if (width < height) {
			if (radius > width >> 1) {
				radius = width >> 1;
			}
		} else {
			if (radius > height >> 1) {
				radius = height >> 1;
			}
		}
        int a2 = 256 - alpha;
        int r1 = (color >> 16 & 0xff) * alpha;
        int g1 = (color >> 8 & 0xff) * alpha;
        int b1 = (color & 0xff) * alpha;
        int pos;
		for (int i = 0; i < radius; i++) {
			int dist = (int) Math.sqrt(radius * radius - i * i);
			if (y + (radius - i) - 1 >= clipStartY) {
				for (int n = dist; n > 0; n--) {
					if (x + radius - n >= clipStartX) {
						pos = (x + radius - n) + (y + (radius - i) - 1) * canvasWidth;
		                int r2 = (canvasRaster[pos] >> 16 & 0xff) * a2;
		                int g2 = (canvasRaster[pos] >> 8 & 0xff) * a2;
		                int b2 = (canvasRaster[pos] & 0xff) * a2;
		                canvasRaster[pos] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
					}
					if (x + width - (radius - n) - 1 < clipEndX) {
						pos = (x + width - (radius - n) - 1) + (y + (radius - i) - 1) * canvasWidth;
		                int r2 = (canvasRaster[pos] >> 16 & 0xff) * a2;
		                int g2 = (canvasRaster[pos] >> 8 & 0xff) * a2;
		                int b2 = (canvasRaster[pos] & 0xff) * a2;
		                canvasRaster[pos] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
					}
				}
			}
			if (y + height - (radius - i) < clipEndY) {
				for (int n = dist; n > 0; n--) {
					if (x + (radius - n) >= clipStartX) {
						pos = (x + (radius - n)) + (y + height - (radius - i)) * canvasWidth;
		                int r2 = (canvasRaster[pos] >> 16 & 0xff) * a2;
		                int g2 = (canvasRaster[pos] >> 8 & 0xff) * a2;
		                int b2 = (canvasRaster[pos] & 0xff) * a2;
		                canvasRaster[pos] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
					}
					if (x + width - (radius - n) - 1 < clipEndX) {
						pos = (x + width - (radius - n) - 1) + (y + height - (radius - i)) * canvasWidth;
		                int r2 = (canvasRaster[pos] >> 16 & 0xff) * a2;
		                int g2 = (canvasRaster[pos] >> 8 & 0xff) * a2;
		                int b2 = (canvasRaster[pos] & 0xff) * a2;
		                canvasRaster[pos] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
					}
				}
			}
		}
		fillRectangle(x + radius, y, width - (radius << 1), radius, color, alpha);
		fillRectangle(x, y + radius, radius, height - (radius << 1), color, alpha);
		fillRectangle(x + radius, y + radius, width - (radius << 1), height - (radius << 1), color, alpha);
		fillRectangle(x + width - radius, y + radius, radius, height - (radius << 1), color, alpha);
		fillRectangle(x + radius, y + height - radius, width - (radius << 1), radius, color, alpha);
	}
	
	public static void fillRectangle(int x, int y, int width, int height, int color, int alpha) {
		if(x < clipStartX) {
			width -= clipStartX - x;
			x = clipStartX;
		}
		if(y < clipStartY) {
			height -= clipStartY - y;
			y = clipStartY;
		}
		if(x + width > clipEndX) {
			width = clipEndX - x;
		}
		if(y + height > clipEndY) {
			height = clipEndY - y;
		}
		final int l1 = 256 - alpha;
		final int i2 = (color >> 16 & 0xff) * alpha;
		final int j2 = (color >> 8 & 0xff) * alpha;
		final int k2 = (color & 0xff) * alpha;
		final int k3 = canvasWidth - width;
		int l3 = x + y * canvasWidth;
		for(int i4 = 0; i4 < height; i4++) {
			for(int j4 = -width; j4 < 0; j4++) {
				final int l2 = (canvasRaster[l3] >> 16 & 0xff) * l1;
				final int i3 = (canvasRaster[l3] >> 8 & 0xff) * l1;
				final int j3 = (canvasRaster[l3] & 0xff) * l1;
				final int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
				canvasRaster[l3++] = k4;
			}
			l3 += k3;
		}
	}

	DrawingArea() {}

	public static int canvasRaster[];
	public static float depthBuffer[];
	public static int canvasWidth;
	public static int canvasHeight;
	public static int clipStartY;
	public static int clipEndY;
	public static int clipStartX;
	public static int clipEndX;
	public static int centerX;
	public static int centerY;
	public static int anInt1387;
	public static Client client;
	public static final float[] SMOOTH = new float[] {1, 1, 1, 1, 1, 1, 1, 1, 1};

}
