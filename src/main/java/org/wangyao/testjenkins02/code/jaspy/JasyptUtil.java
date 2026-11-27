package org.wangyao.testjenkins02.code.jaspy;

/**
 *
 * @author WangYao
 * date: 2026/11/27/17:00
 * description:
 **         <dependency>
 *             <groupId>org.jasypt</groupId>
 *             <artifactId>jasypt</artifactId>
 *             <version>1.9.3</version>
 *         </dependency>
 */
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;

public class JasyptUtil {

    // 和你环境变量保持一致
    private static final String PASSWORD = "yVsnNkeBMaQVSxvvTkzj";
    private static final String ALGORITHM = "PBEWithHmacSHA512AndAES_128";
    private static final String IV_GENERATOR_CLASS = "org.jasypt.iv.RandomIvGenerator";

    private static final StandardPBEStringEncryptor ENCRYPTOR;

    static {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD);
        config.setAlgorithm(ALGORITHM);
        config.setIvGenerator(new RandomIvGenerator());
        // 配套默认盐生成器（spring-jasypt默认）
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        // 线程池，默认1
        config.setPoolSize("1");
        ENCRYPTOR = new StandardPBEStringEncryptor();
        ENCRYPTOR.setConfig(config);
    }

    /**
     * 加密
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return plainText;
        }
        return ENCRYPTOR.encrypt(plainText);
    }

    /**
     * 解密
     */
    public static String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) {
            return cipherText;
        }
        return ENCRYPTOR.decrypt(cipherText);
    }

    public static void main(String[] args) {
        String raw = "1TmpAv0Eqp2mDRmiHRzzjOMzDnlesgHx/egaDre6VDJKMXjgSXDu+T4Vo2NB+ss9";
        String enc = encrypt(raw);
        String dec = decrypt(raw);

        System.out.println("原文：" + raw);
        System.out.println("密文：" + enc);
        System.out.println("解密：" + dec);
//        System.out.println("校验：" + raw.equals(dec));
    }
}
