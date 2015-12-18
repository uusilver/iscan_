package com.tmind.iscan.task;

import com.tmind.iscan.controller.LoginController;
import com.tmind.iscan.service.QrCodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
/**
 * Created by lijunying on 15/12/17.
 */
@Component("iScanUserTask")
public class IScanUserTask {

    @Resource(name="qrCodeService")
    private QrCodeService qrCodeService;
    /**
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
     * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */

    /**
     * 定时卡点计算。每天凌晨 02:00 执行一次
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void secondScan() {
        System.out.println("每天2点检测任务... " + new Date());
    }

    /**
     * 心跳更新。启动时执行一次，之后每隔200分钟执行一次
     */
    @Scheduled(fixedRate = 1000*60*200)
    public void findIpAndUpdateAddress() {
        System.out.println("将ip地址转成物理地址");
        qrCodeService.queryQrCode4UpdatePhysicalAddress();

    }

    /**
     * 卡点持久化。启动时执行一次，之后每隔2分钟执行一次
     */
//    @Scheduled(fixedRate = 1000*60*2)
//    public void persistRecord() {
//        System.out.println("卡点持久化... " + new Date());
//    }
}
