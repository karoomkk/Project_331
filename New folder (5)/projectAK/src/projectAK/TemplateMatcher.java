package projectAK;

public class TemplateMatcher {
    // This class provides a method for performing template matching

    // Method to perform template matching between a template and a source image
    public static double[][] matchTemplate(short[][] template, short[][] source) {
        // Retrieve the dimensions of the source and template images
        int sourceRows = source.length;
        int sourceCols = source[0].length;
        int templateRows = template.length;
        int templateCols = template[0].length;
        int templateSize = templateRows * templateCols;

        // Initialize the result matrix and minimum difference variable
        double minDifference = Double.MAX_VALUE;
        double[][] resultMatrix = new double[sourceRows - templateRows + 1][sourceCols - templateCols + 1];

        // Iterate over the source image to perform template matching
        for (int i = 0; i <= sourceRows - templateRows; i++) {
            for (int j = 0; j <= sourceCols - templateCols; j++) {
                double totalDifference = 0;
                // Calculate the total absolute difference between the template and the corresponding region in the source image
                for (int m = 0; m < templateRows; m++) {
                    for (int n = 0; n < templateCols; n++) {
                        totalDifference += Math.abs(source[i + m][j + n] - template[m][n]);
                    }
                }
                // Normalize the total difference and store it in the result matrix
                totalDifference /= templateSize;
                resultMatrix[i][j] = totalDifference;
                // Update the minimum difference if a new minimum is found
                if (totalDifference < minDifference) {
                    minDifference = totalDifference;
                }
            }
        }

        // Return the result matrix containing normalized differences
        return resultMatrix;
    }
}
