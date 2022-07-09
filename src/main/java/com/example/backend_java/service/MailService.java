package com.example.backend_java.service;


import com.example.backend_java.domain.dto.DataMailDto;

import javax.mail.MessagingException;

public interface MailService {
    void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException;
}
