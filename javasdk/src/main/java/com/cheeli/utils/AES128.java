package com.cheeli.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

public class AES128 {


    public static byte[] encrypt(String key, byte[] origData) throws GeneralSecurityException {

        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, keyBytes.length > buf.length ? keyBytes.length : buf.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(origData);

    }

    public static byte[] decrypt(String key, byte[] crypted) throws GeneralSecurityException {
        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, keyBytes.length > buf.length ? keyBytes.length : buf.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(crypted);
    }

    private static byte[] getKeyBytes(String key) {
        byte[] bytes = key.getBytes();
        return bytes.length == 16 ? bytes : Arrays.copyOf(bytes, 16);
    }

    public static String encrypt(String key, String val) throws GeneralSecurityException {
        byte[] origData = val.getBytes();
        byte[] crypted = encrypt(key, origData);
        return Base64.getUrlEncoder().encodeToString(crypted);
    }

    public static String decrypt(String key, String val) throws GeneralSecurityException {


//        byte[] crypted = Base64.Decoder.RFC4648_URLSAFE.decode(val);
        byte[] crypted = Base64.getUrlDecoder().decode(val);


        byte[] origData = decrypt(key, crypted);
        return new String(origData);
    }



}
