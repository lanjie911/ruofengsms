package sms;

import static java.lang.System.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegPressure {

    public static void main(String[] args) {
        // test 1000 times validate
    	//尊敬的用户[$]，您好！恭喜您已成为优普的用户。现赠送您芒果TV VIP注册码：[$]。您可登录芒果TV，进入用户中心，在兑换卡中开通VIP服务。
    	//^尊敬的企业版用户，您的手机动态码为(\S)+。30分钟内有效，请勿泄露。
        Pattern p = Pattern.compile(
                "^尊敬的(\\S)+，您已转入(\\S)+元到活期宝，当前余额(\\S)+元，有问题请及时联系客服400-800-3121。$");

        out.println(System.currentTimeMillis());

        for (int i = 0; i < 10000; i++) {
            String msg = new String("尊敬的陈琦，您已转入1000.86元到活期宝，当前余额56元，有问题请及时联系客服400-800-3121。");
            Matcher m = p.matcher(msg);
            System.out.println(m.find());
        }
        
        out.println(System.currentTimeMillis());

    }

}