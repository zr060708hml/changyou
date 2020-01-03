package cn.changyou.sms.listener;

import cn.changyou.sms.config.SmsProperties;
import cn.changyou.sms.utils.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author xgl
 * @create 2019-12-27 1:19
 */
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties smsProperties;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "changyou.sms.queue",durable = "true"),
            exchange = @Exchange(value = "changyou.sms.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"code.sms"}
    ))
    public void sendSms(Map<String,String> map) throws ClientException {
        if (CollectionUtils.isEmpty(map)){
            return;
        }
        String phone = map.get("phone");
        System.out.println(phone);
        String code = map.get("code");
        System.out.println(code);
        if (StringUtils.isNoneBlank(phone) && StringUtils.isNoneBlank(code)){
            smsUtils.sendSms(phone,code,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate());
        }
    }
}
