package network.base.com.network;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apaches.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

/**
 * @Author: wb.caobaohe
 * @Date: 2018/11/1
 * @Description:
 */
public class LoginUtil {

    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    public static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getEncyData(String username, String password) {
        // 将军令字段允许为空
        String generalToken = "";
        String str1 = DigestUtils.md5DigestAsHex(password.getBytes());
        int time = (int)((System.currentTimeMillis() / 1000) / 60);
        String str2 = getRandomString(8);
        String content = username + "," + str2 + str1 + "," + generalToken + "," + time;

        try {
            String keyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuJKchro8zP43dnxGZ7RKzJ21Q" +
                    "gkvAj+Yu6KIKQ3UIBhKz7vNksnj6kV39efAonMU/Hr/z1LpkC04qzDlwuCtGhQiP" +
                    "aFUzvrzLr2fOrHsoOQ5waGu2xxqt6QLLwDv7r3I3Ow42TDyjnxmERoyUWYADZtMu" +
                    "jhdpIWPd/6oF72evdwIDAQAB";
            return org.apaches.commons.codec.binary.Base64.encodeBase64String(encryptByPublicKey(content.getBytes(), org.apaches.commons.codec.binary.Base64.decodeBase64(keyString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getRSAEncyData(String content) {
        try {
            //content = org.apaches.commons.codec.binary.Base64.encodeBase64String(content.getBytes());

            String keyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApvNVSycsNgoMwiUfmYSdRwQrTGKvn/iNAz7kXZU+MK2Ou1nPzMehKGfaXop6m6iWmDXH89d00YkP+cJUzYFen6n5YwHUoMQkEHKaZUynAtvEcgdHPYRRIPdlFfNIHbWqzJypjtyPixO7YxJy3gWAEw2cik/JDKFgRHlC/plQfI41dRJItz/dEDjD8DTobDpnGeJjt6gthDytCPUsI/T/3aGaJmIzUuCNK44pvnl2B7klqlLUWd3aUYBTh0rk6XkMHNVvzKyVd+TTtS4lqziUiuJj/WM9ulqA6A2Yc+N/nL5OdoAkT5y9Ni5dEvK6AGhjb3ReVT7RrEFUK0pqV47zVwIDAQAB";
            return org.apaches.commons.codec.binary.Base64.encodeBase64String(encryptByPublicKey(content.getBytes(), org.apaches.commons.codec.binary.Base64.decodeBase64(keyString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 随机生成指定长度的字符串
     *
     * @param length
     * @return
     */
    private static String getRandomString(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        java.util.Random random = new java.util.Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 用公钥对字符串进行分段加密
     *
     */
   public static byte[] encryptByPublicKeyForSpilt(byte[] data, byte[] publicKey) throws Exception {
        int dataLen = data.length;
        if (dataLen <= DEFAULT_BUFFERSIZE) {
            return encryptByPublicKey(data, publicKey);
        }
        List<Byte> allBytes = new ArrayList<Byte>(2048);
        int bufIndex = 0;
        int subDataLoop = 0;
        byte[] buf = new byte[DEFAULT_BUFFERSIZE];
        for (int i = 0; i < dataLen; i++) {
            buf[bufIndex] = data[i];
            if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
                subDataLoop++;
                if (subDataLoop != 1) {
                    for (byte b : DEFAULT_SPLIT) {
                        allBytes.add(b);
                    }
                }
                byte[] encryptBytes = encryptByPublicKey(buf, publicKey);
                for (byte b : encryptBytes) {
                    allBytes.add(b);
                }
                bufIndex = 0;
                if (i == dataLen - 1) {
                    buf = null;
                } else {
                    buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
                }
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return bytes;
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

}
