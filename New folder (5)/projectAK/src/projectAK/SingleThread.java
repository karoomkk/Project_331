package projectAK;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SingleThread {
    // Entry point for template matching using a single thread
    public static void main(String[] args) {
        // Define the file paths for source, template, and output images
        String sourceImagePath = "TenCardG.jpg";
        String templateImagePath = "Template.jpg";
        String outputImagePath = "SingleThreadResult.jpg";

        // Load and process source and template images
        ImageProcessor.loadAndConvertImage(sourceImagePath);
        short[][] sourceImage = ImageProcessor.getGrayscaleImage();
        ImageProcessor.loadAndConvertImage(templateImagePath);
        short[][] templateImage = ImageProcessor.getGrayscaleImage();

        // Perform template matching
        double[][] matchingResults = TemplateMatcher.matchTemplate(templateImage, sourceImage);


        // Draw rectangles on the source image based on the matching results
        BufferedImage sourceBufferedImage = ImageProcessor.createBufferedImage(sourceImage);
        drawMatchingRectangles(sourceBufferedImage, matchingResults, templateImage.length, templateImage[0].length);

        try {
           
            ImageProcessor.saveImage(sourceBufferedImage, outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to draw rectangles on the image based on the matching results
    static void drawMatchingRectangles(BufferedImage image, double[][] results, int templateHeight, int templateWidth) {
        // Find the minimum value in the matching results matrix
        double minDifference = Double.MAX_VALUE;
        for (double[] row : results) {
            for (double value : row) {
                if (value < minDifference) {
                    minDifference = value;
                }
            }
        }

        // Define the threshold for drawing rectangles
        double threshold = 10 * minDifference;

        // Draw rectangles on the image where the matching result is below the threshold
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[0].length; j++) {
                if (results[i][j] <= threshold) {
                    drawRectangle(image, j, i, templateWidth, templateHeight);
                }
            }
        }
    }

    // Method to draw a single rectangle on the image
    private static void drawRectangle(BufferedImage image, int x, int y, int width, int height) {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.RED);
        graphics.drawRect(x, y, width, height);
        graphics.dispose();
    }
}
