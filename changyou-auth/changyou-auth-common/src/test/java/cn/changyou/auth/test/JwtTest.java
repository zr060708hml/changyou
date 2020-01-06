package cn.changyou.auth.test;

import cn.changyou.common.pojo.UserInfo;
import cn.changyou.common.utils.JwtUtils;
import cn.changyou.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "D:\\temp\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\temp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3ODA2NDg0OH0.RZiIf4uy3PspaVv2nuj5g03P1phjwyL2RvXI22TV5v_axtHkwsAimHcciZ5MR0oQR441E1cX3n3bPUThnrb57Bwrvh4bku2rdiNGeWLHZ8zS7oRBkKH8Xj0q2_lgi3ioCLpLci_BYoUb16dABQQASIwrhmC6I1fc3H_nC9O2NEs";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}