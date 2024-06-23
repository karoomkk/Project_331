package projectAK;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MultiThread {
    // Main method to perform template matching using multiple threads
    public static void main(String[] args) {
        // Define file paths for the source, template, and output images
        String sourceImagePath = "TenCardG.jpg";
        String templateImagePath = "Template.jpg";
        String outputImagePath = "MultiThreadResult.jpg";

        // Load and convert source and template images to grayscale
        ImageProcessor.loadAndConvertImage(sourceImagePath);
        short[][] sourceImage = ImageProcessor.getGrayscaleImage();
        ImageProcessor.loadAndConvertImage(templateImagePath);
        short[][] templateImage = ImageProcessor.getGrayscaleImage();

        // Perform template matching
        double[][] matchingResults = TemplateMatcher.matchTemplate(templateImage, sourceImage);


        // Create a result image based on the template matching results
        BufferedImage resultImage = ImageProcessor.createBufferedImage(sourceImage);
        // Placeholder for drawing rectangles, if needed
        // SingleThreadProcessor.drawRectangles(resultImage, matchingResult, templateImage.length, templateImage[0].length);

        // Save the resulting image to the specified output path
        try {
            ImageProcessor.saveImage(resultImage, outputImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
