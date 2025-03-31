package com.example.ankiapp.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


/**
 * メール送信Serviceクラス
 */
@Service
@RequiredArgsConstructor
//@Transactional
public class MailSendServiceImpl implements MailSendService {

    /**メール送信用クラス*/
    private final MailSender mailSender;
    
    @Override
    public boolean sendMail(String mailTo, String mailSubject, String mailText) {
        var smm = new SimpleMailMessage();
        smm.setTo(mailTo);
        smm.setSubject(mailSubject);
        smm.setText(mailText);
        
        try {
            mailSender.send(smm);
        } catch (Exception e) {
            return false;
        }
        // TODO 自動生成されたメソッド・スタブ
        return true;
    }

}
