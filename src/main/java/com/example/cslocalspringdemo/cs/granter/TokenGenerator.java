package com.example.cslocalspringdemo.cs.granter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author: Song
 * @Desc: 生成MD5的Token
 * @Data: 2024/4/22
 */
public class TokenGenerator {

    public static String generateToken(String secretKey) {
        try {
            // 生成随机因素
            SecureRandom random = new SecureRandom();
            byte[] randomBytes = new byte[16];
            random.nextBytes(randomBytes);
            // 结合密钥和随机因素生成 token
            String data = secretKey + System.currentTimeMillis() + Base64.getEncoder().encodeToString(randomBytes);
            // 创建 MessageDigest 实例并指定算法为 MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算数据的摘要
            byte[] digest = md.digest(data.getBytes());
            // 将摘要转换成十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b & 0xff));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
