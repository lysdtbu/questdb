package org.questdb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeatmapUtils {
    private static final int MAX = 184;

    public static void main(String[] args) {
        int[] values = new int[100 * 100];
        for (int i = 0; i < values.length; i++) {
            values[i] = i / 100;
        }
        writeToPngFile(values, "heatmap.png");
    }

    public static void writeToPngFile(int[] values, String filename) {
        int width = (int) Math.sqrt(values.length);
        int height = values.length / width + (values.length % width == 0 ? 0 : 1);
        int[] pixels = new int[width * height];
        int max = getMaxValue(values);
//        if (max > MAX) {
//            throw new IllegalArgumentException("Max value is " + max + ", but max value for heatmap is " + MAX);
//        }
        max = 80;

        int grayColor = 0xff888888;
        int[] greenColor = {0, 255, 0};
        int[] redColor = {255, 0, 0};
        int[] blackColor = {0, 0, 0};
        int[] startColor = greenColor;
        int[] endColor = blackColor;


        for (int i = 0; i < values.length; i++) {
            int val = values[i];
            if (val == -1) {
                pixels[i] = 0xffffffff; // white
            } else {
                int[] interpolatedColor = HeatmapUtils.logInterpolateColor(startColor, endColor, 0, max, val);
                pixels[i] = 0xff000000 | (interpolatedColor[0] << 16) | (interpolatedColor[1] << 8) | interpolatedColor[2];
            }
        }

        writeToPngFile2(pixels, width, height, filename);
    }

    public static int[] logInterpolateColor(int[] colorStart, int[] colorEnd, int minimum, int maximum, int value) {
        // Adjust for values outside the range
        if (value < minimum) {
            value = minimum;
        }
        if (value > maximum) {
            value = maximum;
        }

        // Calculate the logarithmic ratio
        double logMin = Math.log1p(minimum - minimum + 1); // Avoid log(0) which is undefined
        double logMax = Math.log1p(maximum - minimum + 1);
        double logValue = Math.log1p(value - minimum + 1);
        float ratio = (float) ((logValue - logMin) / (logMax - logMin));

        // Interpolate each color component
        int red = (int) (colorStart[0] + ratio * (colorEnd[0] - colorStart[0]));
        int green = (int) (colorStart[1] + ratio * (colorEnd[1] - colorStart[1]));
        int blue = (int) (colorStart[2] + ratio * (colorEnd[2] - colorStart[2]));

        return new int[]{clamp(red), clamp(green), clamp(blue)};
    }


    public static int[] linearInterpolateColor(int[] colorStart, int[] colorEnd, int minimum, int maximum, int value) {
        if (value < minimum) {
            value = minimum;
        }
        if (value > maximum) {
            value = maximum;
        }

        float ratio = (float) (value - minimum) / (maximum - minimum);
        int red = (int) (colorStart[0] + ratio * (colorEnd[0] - colorStart[0]));
        int green = (int) (colorStart[1] + ratio * (colorEnd[1] - colorStart[1]));
        int blue = (int) (colorStart[2] + ratio * (colorEnd[2] - colorStart[2]));

        return new int[]{clamp(red), clamp(green), clamp(blue)};
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }


    private static void writeToPngFile2(int[] pixels, int width, int height, String filename) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, pixels, 0, width);
            ImageIO.write(image, "png", new File(filename));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private static int getMaxValue(int[] values) {
        int max = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
