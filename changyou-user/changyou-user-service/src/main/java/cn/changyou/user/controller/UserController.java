package cn.changyou.user.controller;

import cn.changyou.user.pojo.User;
import cn.changyou.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author xgl
 * @create 2019-12-27 0:35
 */
@Controller
@Api("用户服务接口")
public class UserController {
    @Autowired
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验。
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    @ApiOperation(value = "用户校验接口,返回Boolean类型",notes = "用户校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data",required = true,value = "用户填写的数据,data可以是手机号或是用户名",type = "String"),
            @ApiImplicitParam(name = "type",required = true,value = "校验类型,1为用户名,2为手机号",type = "Integer")
    })
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data,@PathVariable("type") Integer type){
        log.info("即将进行用户校验,数据为{},类型为{}",data,type);
        Boolean result = userService.checkUser(data,type);
        if (result == null){
            log.info("参数错误");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 发送验证码d
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone){
        log.info("马上要发送验证码,收到的手机号为{}",phone);
        Boolean boo = userService.sendCode(phone);
        if (boo == null || !boo){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        log.info("这里是注册Controller,前台传来的信息是,{},{}",user,code);
        userService.register(user,code);
        log.info("注册成功,即将返回数据给前端");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,@RequestParam("password") String password){
        log.info("即将进行登录判断,收到的用户名为{},密码为{}",username,password);
        User user = userService.queryUser(username, password);
        if (user == null) {
            log.info("登录失败");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
}
