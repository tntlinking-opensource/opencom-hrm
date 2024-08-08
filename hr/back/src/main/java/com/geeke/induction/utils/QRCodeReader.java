//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import com.google.zxing.BinaryBitmap;
//import com.google.zxing.MultiFormatReader;
//import com.google.zxing.NotFoundException;
//import com.google.zxing.Result;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
//import com.google.zxing.common.HybridBinarizer;
//
//public class QRCodeReader {
//    public static void readQRCode(String filePath) throws IOException, NotFoundException {
//        File file = new File(filePath);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
//        Result result = new MultiFormatReader().decode(bitmap);
//        System.out.println("QR code text: " + result.getText());
//    }
//
//    public static void main(String[] args) {
//        String filePath = "example_qrcode.png
//
//    }
