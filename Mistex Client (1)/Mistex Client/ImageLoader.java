import java.io.File;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

/**
 * 
 * @author Evan Bennett
 * Date: 13/03/2014
 * Time: 11:02 AM
 */

public class ImageLoader {

	/**
     * Loads the sprite data and index files from the cache location.
     * @param jagexArchive
     */
    public static void loadImageData(Archive jagexArchive) {
        try {
            Buffer index = new Buffer(Utility.readFile(Signlink.findcachedir() + "main_file_cache_sprites.idx"));
            Buffer data = new Buffer(Utility.readFile(Signlink.findcachedir() + "main_file_cache_sprites.dat"));
            DataInputStream indexFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(index.buffer)));
            DataInputStream dataFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(data.buffer)));
            int totalImages = indexFile.readInt();
            if (imageLoader == null) {
                imageLoader = new ImageLoader[totalImages];
                image = new Sprite[totalImages];
            }
            for (int i = 0; i < totalImages; i++) {
                int spriteId = indexFile.readInt();
                if (imageLoader[spriteId] == null) {
                    imageLoader[spriteId] = new ImageLoader();
                }
                imageLoader[spriteId].readValues(indexFile, dataFile);
                createImages(imageLoader[spriteId]);
            }
            indexFile.close();
            dataFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the information from the index and data files.
     *
     * @param index holds the sprite indices
     * @param data holds the sprite data per index
     * @throws IOException
     */
    public void readValues(DataInputStream index, DataInputStream data) throws IOException {
        do {
            int value = data.readByte();
            if (value == 0) {
                break;
            }
            if (value == 1) {
                imageId = data.readShort();
            } else if (value == 2) {
                imageName = data.readUTF();
            } else if (value == 3) {
                drawOffsetX = data.readShort();
            } else if (value == 4) {
                drawOffsetY = data.readShort();
            } else if (value == 5) {
                int indexLength = index.readInt();
                byte[] readData = new byte[indexLength];
                data.readFully(readData);
                imageData = readData;
            }
        } while (true);
    }

    /**
     * Gets the name of a specified sprite index.
     * 
     * @param index
     * @return
     */
    public static String getImageName(int index) {
        if (imageLoader[index].imageName != null) {
            return imageLoader[index].imageName;
        } else {
            return "null";
        }
    }

    /**
     * Gets the offsetX of a specified sprite index.
     *
     * @param index
     * @return
     */
    public static int offSetX(int index) {
        return imageLoader[index].drawOffsetX;
    }

    /**
     * Gets the offsetY of a specified sprite index.
     *
     * @param index
     * @return
     */
    public static int offSetY(int index) {
        return imageLoader[index].drawOffsetY;
    }
    
    /**
     * Dumps all images from data file.
     * 
     * @param sprite
     */
    public static void dumpImages(ImageLoader sprite) {
        File directory = new File(Signlink.findcachedir() + "dumpimages");
        if (!directory.exists()) {
            directory.mkdir();
        }
        Utility.writeFile(new File(directory.getAbsolutePath() + System.getProperty("file.separator") + ImageLoader.imageId + ".png"), sprite.imageData);
        System.out.println("Dumping Image: "+imageId);
    }

    /**
     * Creates a sprite out of the spriteData.
     * 
     * @param sprite
     */
    public static void createImages(ImageLoader sprite) {
        image[ImageLoader.imageId] = new Sprite(sprite.imageData);
        image[ImageLoader.imageId].anInt1442 = sprite.drawOffsetX;
        image[ImageLoader.imageId].anInt1443 = sprite.drawOffsetY;
    }

    /**
     * Sets the default values.
     * 
     */
    public ImageLoader() {
        imageName = "name";
        imageId = -1;
        drawOffsetX = 0;
        drawOffsetY = 0;
        imageData = null;
    }

    public static ImageLoader[] imageLoader;
    public static Sprite[] image = null;
    public static int totalImages;
    public String imageName;
    public static int imageId;
    public int drawOffsetX;
    public int drawOffsetY;
    public byte[] imageData;
}

