package com.example.connectapi.services;

import com.example.connectapi.models.TblInLateOutEarly;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface TblInLateOutEarlyService {
    List<TblInLateOutEarly> findByDate(LocalDate checkDate);
}
