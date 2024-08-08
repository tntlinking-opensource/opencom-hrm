package com.geeke.induction.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QRCodeGenerator {

    @SneakyThrows
    public static String generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        byte[] qrCodeBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(qrCodeBytes);
        return base64Image;
    }

    public static String generateQRCode(String url,int width,int height,int margin,String format) throws WriterException, Exception {
        String charset = "UTF-8";

        // 设置二维码纠错级别
        ErrorCorrectionLevel level = ErrorCorrectionLevel.H;
        // 设置二维码参数
        java.util.Map<EncodeHintType, Object> hints = new java.util.HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, charset);
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        hints.put(EncodeHintType.MARGIN, margin);

        // 生成二维码
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        // 填充背景色
        Graphics2D graphics = (Graphics2D)image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        // 画二维码图案
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i)) {
                    graphics.fillRect(j, i, 1, 1);
                }
            }
        }

        // 将图片转换为字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        byte[] imageBytes = baos.toByteArray();

        // 将字节数组转换为 Base64 字符串
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        return base64Image;
    }

}
