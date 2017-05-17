package com.dzcx.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dzcx.utils.ImageUtil;
import com.dzcx.utils.ResourceUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QRCodeService {

	private static final int LOGO_DEFAULT_WIDTH = 70;
	private static final int LOGO_DEFAULT_HEIGHT = 70;
	private static final String FILE_DIR = ResourceUtil.get("config", "file.path");
	public BufferedImage createQRCode(final String url) throws WriterException, IOException {
		BufferedImage image = null;
		// 二维码图片输出流
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		//H = ~30% L = ~7% M = ~15% Q = ~25% 
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		
		BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
		//生成的二维码确实是400x400的尺寸，到时存在很大的白边。我们为了美化需要把白边变小
		bitMatrix = ImageUtil.updateBit(bitMatrix, 10);
		image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		BufferedImage logo = ImageUtil.getBufferedImage(FILE_DIR + "IMG_0408.JPG");
		image = addLog(image, logo);
		return image;
	}

	
	private static BufferedImage addLog(BufferedImage image, BufferedImage logo){
		
		int w1 = image.getWidth();
		int h1 = image.getHeight();
		//图片缩放
		logo = ImageUtil.scale(logo, LOGO_DEFAULT_WIDTH, LOGO_DEFAULT_HEIGHT);
		int w2 = logo.getWidth();
		int h2 = logo.getHeight();
		int w_margin = (w1 - w2) / 2;
		int h_margin = (h1 - h2) / 2;
		
		BufferedImage destImage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = destImage.createGraphics();
		/**第一种合并图片方式start*/
//		int[] image_array = new int[w1 * h1];
//		int[] logo_array = new int[w2 * h2];
//		image_array = image.getRGB(0, 0, w1, h1, image_array, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
//		logo_array = logo.getRGB(0, 0, w2, h2, logo_array, 0, w2); // 逐行扫描图像中各个像素的RGB到数组中
//		destImage.setRGB(0, 0, w1, h1, image_array, 0, w1);
//		destImage.setRGB(w_margin, h_margin, w2, h2, logo_array, 0, w2);
		/**第一种合并图片方式end*/
		
		/**第二种合并图片方式start*/
		g2.drawImage(image, 0, 0, w1, h1, null);//绘制logo
		g2.drawImage(logo, w_margin, h_margin, w2, h2, null);//绘制logo
		/**第二种合并图片方式end*/
		
		BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(w_margin, h_margin, w2, h2, 20, 20);
        g2.setColor(Color.WHITE);  
        g2.draw(round2);// 绘制圆弧矩形  
		g2.dispose();
		
		return destImage;
	}
	
}
