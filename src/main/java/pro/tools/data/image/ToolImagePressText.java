package pro.tools.data.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片水印处理
 *
 * @author SeanDragon
 */
@SuppressWarnings("restriction")
public final class ToolImagePressText {

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg  -- 水印文件
     * @param targetImg -- 目标文件
     * @param x
     * @param y
     */
    public static void pressImage(String pressImg, String targetImg, int x, int y) throws IOException {
        File _file = new File(targetImg);
        Image src = ImageIO.read(_file);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);

        // 水印文件
        File file = new File(pressImg);
        Image read = ImageIO.read(file);
        int readWidth = read.getWidth(null);
        int readHeight = read.getHeight(null);
        g.drawImage(read, width - readWidth - x, height - readHeight - y, readWidth, readHeight, null);
        g.dispose();
        try (FileOutputStream out = new FileOutputStream(targetImg);) {
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
        }
    }

    /**
     * 打印文字水印图片，右下角计算坐标
     *
     * @param pressText --文字
     * @param targetImg -- 目标图片
     * @param fontName  -- 字体名
     * @param fontStyle -- 字体样式
     * @param color     -- 字体颜色
     * @param fontSize  -- 字体大小
     * @param x         -- 偏移量
     * @param y
     */
    public static void pressText(String pressText, String targetImg,
                                 String fontName, int fontStyle, Color color, int fontSize, int x, int y) throws IOException {
        File _file = new File(targetImg);
        Image src = ImageIO.read(_file);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        g.setColor(color);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.drawString(pressText, width - fontSize - x, height - fontSize / 2 - y);
        g.dispose();
        try (FileOutputStream out = new FileOutputStream(targetImg);) {
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
        }
    }


}