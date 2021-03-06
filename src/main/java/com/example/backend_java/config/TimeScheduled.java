package com.example.backend_java.config;

import com.example.backend_java.service.HopDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TimeScheduled {
    @Autowired
    HopDongService hopDongService;
    @Scheduled(cron = "0 01 00 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void task(){
        hopDongService.UpdateTime();
    }
}