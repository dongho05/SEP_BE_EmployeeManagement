package com.example.connectapi.controllers;

import com.example.connectapi.models.TblInLateOutEarly;
import com.example.connectapi.models.TmpCheckInOut;
import com.example.connectapi.services.TblInLateOutEarlyService;
import com.example.connectapi.services.TmpCheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {
    @Autowired
    private TmpCheckInOutService tmpCheckInOutService;
    @Autowired
    private TblInLateOutEarlyService tblInLateOutEarlyService;

    @GetMapping("logCheckInOutByBadgeNumber/{badgeNumber}")
    public List<TmpCheckInOut> listCheckInOut (@PathVariable String badgeNumber) {
        List<TmpCheckInOut> list = tmpCheckInOutService.findByBadgeNumber(badgeNumber);
        return list ;
    }

    @GetMapping("logCheckInOutByToday")
    public List<TmpCheckInOut> listCheckInOutByDate () {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);
        List<TmpCheckInOut> list = tmpCheckInOutService.findByDate(yesterday);
        return list ;
    }

    @GetMapping("logInLateOutEarlyByToday")
    public List<TblInLateOutEarly> logInLateOutEarlyByToday () {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);
        List<TblInLateOutEarly> list = tblInLateOutEarlyService.findByDate(yesterday);
        return list ;
    }
}
