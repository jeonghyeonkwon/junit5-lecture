package site.metacoding.junitproject.util;

import org.springframework.stereotype.Component;



/*
* 테스트용 가짜 클래스 싱글톤이기 때문에 테스트가 끝나면 Component 주석 처리하면됨
* */
@Component
public class MailSenderStub implements MailSender{
    @Override
    public boolean send() {
        return true;
    }
}
