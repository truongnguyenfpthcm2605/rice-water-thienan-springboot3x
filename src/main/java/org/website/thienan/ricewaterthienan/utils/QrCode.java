package org.website.thienan.ricewaterthienan.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCode {
    public String generationQrCode(String data) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (!data.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            int HEIGHT_QRCODE_IMAGE = 300;
            int WIDTH_QRCODE_IMAGE = 300;
            BitMatrix bitMatrix =
                    qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, WIDTH_QRCODE_IMAGE, HEIGHT_QRCODE_IMAGE);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            stringBuilder.append("data:image/png;base64,");
            stringBuilder.append(new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray())));
            return stringBuilder.toString();
        }
        return "Nothing";
    }
}
