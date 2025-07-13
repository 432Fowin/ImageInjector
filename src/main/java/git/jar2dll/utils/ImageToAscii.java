package git.jar2dll.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToAscii {
    private static final String ASCII_CHARS = "@#S%?*+;:,. ";
    private static final int DEFAULT_WIDTH = 100;

    public static String convertImageToAscii(File imageFile) throws IOException {
        return convertImageToAscii(imageFile, -1);
    }

    public static String convertImageToAsciiOriginalSize(File imageFile) throws IOException {
        return convertImageToAscii(imageFile, -1);
    }

    public static String convertImageToAscii(File imageFile, int width) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException("Could not read image file: " + imageFile.getName());
        }

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        final int MAX_DIMENSION = 800;
        final int MAX_TOTAL_CHARS = 400000;

        int targetWidth, targetHeight;
        if (width > 0) {
            targetWidth = width;
            targetHeight = (int) ((double) originalHeight * width / originalWidth);
        } else {
            targetWidth = originalWidth;
            targetHeight = originalHeight;

            if (targetWidth > MAX_DIMENSION || targetHeight > MAX_DIMENSION) {
                double scale = Math.min((double) MAX_DIMENSION / targetWidth,
                                      (double) MAX_DIMENSION / targetHeight);
                targetWidth = (int) (targetWidth * scale);
                targetHeight = (int) (targetHeight * scale);
            }

            if (targetWidth * targetHeight > MAX_TOTAL_CHARS) {
                double scale = Math.sqrt((double) MAX_TOTAL_CHARS / (targetWidth * targetHeight));
                targetWidth = (int) (targetWidth * scale);
                targetHeight = (int) (targetHeight * scale);
            }
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        StringBuilder ascii = new StringBuilder();

        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int rgb = resizedImage.getRGB(x, y);

                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) +
                                 0.587 * ((rgb >> 8) & 0xFF) +
                                 0.114 * (rgb & 0xFF));

                gray = 255 - gray;

                int charIndex = (gray * (ASCII_CHARS.length() - 1)) / 255;
                ascii.append(ASCII_CHARS.charAt(charIndex));
            }
            ascii.append("\n");
        }
        
        return ascii.toString();
    }

    public static String[] convertImagesToAsciiArray(File[] imageFiles) throws IOException {
        String[] asciiImages = new String[imageFiles.length];
        for (int i = 0; i < imageFiles.length; i++) {
            asciiImages[i] = convertImageToAscii(imageFiles[i]);
        }
        return asciiImages;
    }
}
