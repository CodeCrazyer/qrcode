package com.dzcx.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

/**
 * 图片工具类
 * 
 * @author allen
 *
 */
public class ImageUtil {

	/**
	 * 读取指定路径的图片并返回图片对象
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getBufferedImage(String filePath) throws IOException {

		File f = new File(filePath);
		return ImageIO.read(f);
	}

	/**
	 * 读取文件对象并返回图片对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getBufferedImage(File file) throws IOException {

		return ImageIO.read(file);
	}

	/**
	 * 按比例放大指定图片
	 * 
	 * @param image
	 *            图片
	 * @param scale
	 *            放大比例
	 * @return
	 */
	public static BufferedImage scale(BufferedImage image, double scale) {

		int width = image.getWidth();
		int height = image.getHeight();
		int _width = (int) (width * scale);
		int _height = (int) (height * scale);

		BufferedImage _image = new BufferedImage(_width, _height, image.getType());
		Graphics2D g2 = _image.createGraphics();
		g2.drawImage(image, 0, 0, _width, _height, null);
		g2.dispose();
		return _image;
	}

	/**
	 * 放大指定图片图到指定尺寸
	 * 
	 * @param image
	 *            图片
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return
	 */
	public static BufferedImage scale(BufferedImage image, int width, int height) {

		BufferedImage _image = new BufferedImage(width, height, image.getType());
		Graphics2D g2 = _image.createGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
		return _image;
	}
	
	/**
	 * 自定义白边边框宽度
	 * 
	 * @param matrix
	 * @param margin
	 * @return
	 */
	public static BitMatrix updateBit(final BitMatrix matrix, final int margin) {
		int tempM = margin * 2;
		// 获取二维码图案的属性
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		// 按照自定义边框生成新的BitMatrix
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		// 循环，将二维码图案绘制到新的bitMatrix中
		for (int i = margin; i < resWidth - margin; i++) {
			for (int j = margin; j < resHeight - margin; j++) {
				if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}
	
}
