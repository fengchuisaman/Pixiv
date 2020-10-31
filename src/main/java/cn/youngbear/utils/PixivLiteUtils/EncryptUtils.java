package cn.youngbear.utils.PixivLiteUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

    private static final char[] HEX_DIGITS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };

    public static String encryptMD5ToString(String paramString) {
        return (paramString == null || paramString.length() == 0) ? "" : encryptMD5ToString(paramString.getBytes());
    }
    public static String encryptMD5ToString(byte[] paramArrayOfbyte) {
        return bytes2HexString(encryptMD5(paramArrayOfbyte));
    }
    private static String bytes2HexString(byte[] paramArrayOfbyte) {
        if (paramArrayOfbyte == null)
            return "";
        int k = paramArrayOfbyte.length;
        if (k <= 0)
            return "";
        char[] arrayOfChar = new char[k << 1];
        int i = 0;
        int j = 0;
        while (i < k) {
            int m = j + 1;
            char[] arrayOfChar1 = HEX_DIGITS;
            arrayOfChar[j] = arrayOfChar1[paramArrayOfbyte[i] >> 4 & 0xF];
            j = m + 1;
            arrayOfChar[m] = arrayOfChar1[paramArrayOfbyte[i] & 0xF];
            i++;
        }
        return new String(arrayOfChar);
    }
    public static byte[] encryptMD5(byte[] paramArrayOfbyte) {
        return hashTemplate(paramArrayOfbyte, "MD5");
    }
    private static byte[] hashTemplate(byte[] paramArrayOfbyte, String paramString) {
        if (paramArrayOfbyte != null) {
            if (paramArrayOfbyte.length <= 0)
                return null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(paramString);
                messageDigest.update(paramArrayOfbyte);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        }
        return null;
    }
}
