package cn.changyou.auth.service;

import cn.changyou.auth.client.UserClient;
import cn.changyou.auth.config.JwtProperties;
import cn.changyou.common.pojo.UserInfo;
import cn.changyou.common.utils.JwtUtils;
import cn.changyou.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xgl
 * @create 2020-01-03 23:35
 */
@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;
    private Logger log = LoggerFactory.getLogger(AuthService.class);

    public String accredit(String username, String password) {
        //1.根据用户名和密码进行查询
        log.info("准备调用feign");
        User user = userClient.queryUser(username, password);
        log.info("通过feign获取的用户为{}",user);
        //2.判断user是否为空
        if (user == null) {
            return null;
        }
        //3.通过jwtUtil生成token
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        try {
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
