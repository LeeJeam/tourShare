package com.xmyy.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.logging.log4j.LogManager;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class QRCodeUtils {

	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();
	public static String createQrcode(String content) {
		 return createQrcode(content, 300, 300);
	}

	public static String createQrcode(String content, int width, int height) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String qrcodeFormat = "png";
			HashMap<EncodeHintType, String> hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			//File file = new File(dir, UUID.randomUUID().toString() + "." + qrcodeFormat);
			//MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, file.toPath());
			MatrixToImageWriter.writeToStream(bitMatrix, qrcodeFormat,out);
			//return file.getAbsolutePath();

			return Base64.getEncoder().encodeToString(out.toByteArray());//new StringBuilder().append("data:image/png;base64,").append(
		} catch (Exception var8) {
			logger.error("", var8);
		}
		return content;
	}

	public static void main(String[] args) {

		System.out.println(QRCodeUtils.createQrcode("11fuck"));
	}
}
