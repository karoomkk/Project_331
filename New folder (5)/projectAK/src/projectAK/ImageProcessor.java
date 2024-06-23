package projectAK;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    // Class for handling image reading and processing

    // Array to store the grayscale image
    private static short[][] grayscaleImage;

    // Image dimensions
    private static int imgWidth;
    private static int imgHeight;

    // Buffered image object to hold the image
    private static BufferedImage bufferedImage;

    // Method to read a color image and convert it to grayscale
    public static void loadAndConvertImage(String fileName) {
        try {
            byte[] pixelData;
            File inputFile = new File(fileName);
            // Read the image from file
            bufferedImage = ImageIO.read(inputFile);
            // Get image dimensions
            imgWidth = bufferedImage.getWidth();
            imgHeight = bufferedImage.getHeight();

            // Extract pixel data from the image
            pixelData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            System.out.println("Image dimensions: Width=" + imgWidth + ", Height=" + imgHeight + " | Number of pixels: " + pixelData.length);

            // Convert the color image to grayscale
            int red, green, blue;
            grayscaleImage = new short[imgHeight][imgWidth];
            int index;
            for (int y = 0; y < imgHeight; y++) {
                for (int x = 0; x < imgWidth; x++) {
                    index = 3 * (y * imgWidth + x);
                    red = (pixelData[index] & 0xff);
                    green = (pixelData[index + 1] & 0xff);
                    blue = (pixelData[index + 2] & 0xff);
                    grayscaleImage[y][x] = (short) Math.round(0.299 * red + 0.587 * green + 0.114 * blue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write an image to a file
    public static void saveImage(BufferedImage image, String filePath) throws IOException {
        ImageIO.write(image, "jpg", new File(filePath));
    }

    // Method to convert the grayscale image to BufferedImage
    public static BufferedImage createBufferedImage(short[][] grayscaleImage) {
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                int grayValue = grayscaleImage[y][x];
                int rgbValue = grayValue | (grayValue << 8) | (grayValue << 16);
                image.setRGB(x, y, rgbValue);
            }
        }
        return image;
    }

    // Getter for grayscale image
    public static short[][] getGrayscaleImage() {
        return grayscaleImage;
    }

    // Getter for image width
    public static int getImgWidth() {
        return imgWidth;
    }

    // Getter for image height
    public static int getImgHeight() {
        return imgHeight;
    }
}
