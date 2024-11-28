package com.example.ankiapp.service;

public interface MailSendService {

    boolean sendMail(String mailAddress, String mailSubject, String mailText);

}
