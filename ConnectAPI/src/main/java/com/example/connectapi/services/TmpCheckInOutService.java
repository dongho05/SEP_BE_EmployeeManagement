package com.example.connectapi.services;

import com.example.connectapi.models.TmpCheckInOut;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface TmpCheckInOutService {
    List<TmpCheckInOut> findByBadgeNumber(String badgeNumber);
    List<TmpCheckInOut> findByDate(LocalDate checkDate, Integer day, Integer month, Integer year);
}
