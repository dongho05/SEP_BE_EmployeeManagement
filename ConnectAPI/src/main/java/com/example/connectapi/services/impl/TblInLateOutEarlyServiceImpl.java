package com.example.connectapi.services.impl;

import com.example.connectapi.models.TblInLateOutEarly;
import com.example.connectapi.services.TblInLateOutEarlyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TblInLateOutEarlyServiceImpl implements TblInLateOutEarlyService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<TblInLateOutEarly> findByDate(LocalDate checkDate) {
        Integer year = 2023;
        Integer month = 10;
        Integer day = 3;

//        Integer year = checkDate.getYear();
//        Integer month = checkDate.getMonthValue();
//        Integer day = checkDate.getDayOfMonth();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, year); // Đặt giá trị của tham số
                preparedStatement.setInt(2, month); // Đặt giá trị của tham số
                preparedStatement.setInt(3, day); // Đặt giá trị của tham số
            }
        };
//        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
//
//        MapSqlParameterSource parameters = new MapSqlParameterSource();
//        parameters.addValue("badgeNumber", badgeNumber);
        String sql = "select * from tblInLateOutEarly where  YEAR(IODate) = ? and   Month(IODate) = ? and   DAY(IODate) = ? order by IODate desc";

//        List<TmpCheckInOut> tmpCheckInOutList = tmpCheckInOutRepository.findByBadgeNumber(badgeNumber);
//        List<TmpCheckInOut> tmpCheckInOutList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TmpCheckInOut.class)) ;
        List<TblInLateOutEarly> tmpCheckInOutList = jdbcTemplate.query(sql,pss, BeanPropertyRowMapper.newInstance(TblInLateOutEarly.class)) ;
//        List<TmpCheckInOut> tmpCheckInOutList = namedParameterJdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(TmpCheckInOut.class)) ;
        if(tmpCheckInOutList.isEmpty()){
//            throw new RuntimeException("Empty");
            return tmpCheckInOutList;
        }
        return tmpCheckInOutList;
    }
}
