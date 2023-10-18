package com.project.SEP_BE_EmployeeManagement.scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TblInLateOutEarly;
import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TmpCheckInOut;
import com.project.SEP_BE_EmployeeManagement.model.LogCheckInOut;
import com.project.SEP_BE_EmployeeManagement.model.LogInLateOutEarly;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.LogCheckInOutRepository;
import com.project.SEP_BE_EmployeeManagement.repository.LogInLateOutEarlyRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CallApi {
    @Autowired
    private LogCheckInOutRepository logCheckInOutRepository;

    @Autowired
    private LogInLateOutEarlyRepository logInLateOutEarlyRepository;

    @Autowired
    private UserRepository userRepository;
    @Value("${connect.api}")
    private String URL;

//    @Scheduled(cron = "0 0 */2 * * *")
    @Scheduled(cron = "0 0/15 * * * ?") // 15'
//    @Scheduled(cron = "0 0 * * * ?") //1 tieng
    public List<TmpCheckInOut> getLogCheckInOut() {
        try {
            String apiUrl = URL + "logCheckInOutByToday";

            // Tạo URL từ đường dẫn API
            URL url = new URL(apiUrl);

            // Mở kết nối HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức GET
            connection.setRequestMethod("GET");

            // Lấy dữ liệu từ API
            ObjectMapper objectMapper = new ObjectMapper();
            TmpCheckInOut[] tmpCheckInOuts = objectMapper.readValue(connection.getInputStream(), TmpCheckInOut[].class);

            // Chuyển mảng thành danh sách
            List<TmpCheckInOut> tmpCheckInOutList = new ArrayList<>();
            for (TmpCheckInOut checkInOut : tmpCheckInOuts) {
                tmpCheckInOutList.add(checkInOut);
            }

            // In danh sách người dùng
            for (TmpCheckInOut checkInOut : tmpCheckInOutList) {
                System.out.println(checkInOut.getBadgeNumber());
                System.out.println(checkInOut.getCheckTime());
                LogCheckInOut logCheckInOut = new LogCheckInOut();

                String userCode = checkInOut.getBadgeNumber().toString();
                int length = userCode.length();
                if (length == 1) userCode = "FPT_0000" + checkInOut.getBadgeNumber();
                if (length == 2) userCode = "FPT_000" + checkInOut.getBadgeNumber();
                if (length == 3) userCode = "FPT_00" + checkInOut.getBadgeNumber();
                if (length == 4) userCode = "FPT_0" + checkInOut.getBadgeNumber();
                if (length == 5) userCode = "FPT_" + checkInOut.getBadgeNumber();
                User user = userRepository.findByUserCode(userCode);
                if (user != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkInOut.getCheckTime());

                    Integer year = checkInOut.getCheckTime().getYear() + 1900;
                    Integer month = calendar.get(Calendar.MONTH) + 1;
                    Integer day = calendar.get(Calendar.DAY_OF_MONTH);
                    Integer hour = checkInOut.getCheckTime().getHours();
                    Integer minutes = checkInOut.getCheckTime().getMinutes();
                    Integer second = checkInOut.getCheckTime().getSeconds();


                    LocalDate dateCheck = LocalDate.of(year, month, day);
                    LocalTime timeCheck = LocalTime.of(hour, minutes, second);
                    Optional<LogCheckInOut> logCheckInOut1 = logCheckInOutRepository.checkExist(dateCheck,timeCheck,user.getId());
                    if(!logCheckInOut1.isPresent()){
                        logCheckInOut.setDateCheck(dateCheck);
                        logCheckInOut.setTimeCheck(timeCheck);
                        logCheckInOut.setBadgeNumber(checkInOut.getBadgeNumber());
                        logCheckInOut.setUser(user);
                        logCheckInOutRepository.save(logCheckInOut);
                    }
                }



            }
            System.out.println("get Check In Outttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
            // Đóng kết nối
            connection.disconnect();
            return tmpCheckInOutList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(cron = "0 01 9 * * *")
    public List<TblInLateOutEarly> getLogInLateOutEarly() {
        try {
            String apiUrl = URL + "logInLateOutEarlyByToday";

            // Tạo URL từ đường dẫn API
            URL url = new URL(apiUrl);

            // Mở kết nối HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức GET
            connection.setRequestMethod("GET");

            // Lấy dữ liệu từ API
            ObjectMapper objectMapper = new ObjectMapper();
            TblInLateOutEarly[] tmpCheckInOuts = objectMapper.readValue(connection.getInputStream(), TblInLateOutEarly[].class);

            // Chuyển mảng thành danh sách
            List<TblInLateOutEarly> tblInLateOutEarlies = new ArrayList<>();
            for (TblInLateOutEarly inLateOutEarly : tmpCheckInOuts) {
                tblInLateOutEarlies.add(inLateOutEarly);
            }

            // In danh sách người dùng
            for (TblInLateOutEarly inLateOutEarly : tblInLateOutEarlies) {
                System.out.println(inLateOutEarly.getEmployeeId());
                System.out.println(inLateOutEarly.getIOStart());
                LogInLateOutEarly logInLateOutEarly = new LogInLateOutEarly();

                String userCode = inLateOutEarly.getEmployeeId().toString();
                int length = userCode.length();
                if (length == 1) userCode = "FPT_0000" + inLateOutEarly.getEmployeeId();
                if (length == 2) userCode = "FPT_000" + inLateOutEarly.getEmployeeId();
                if (length == 3) userCode = "FPT_00" + inLateOutEarly.getEmployeeId();
                if (length == 4) userCode = "FPT_0" + inLateOutEarly.getEmployeeId();
                if (length == 5) userCode = "FPT_" + inLateOutEarly.getEmployeeId();
                User user = userRepository.findByUserCode(userCode);
                if (user != null) {
                    logInLateOutEarly.setUser(user);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(inLateOutEarly.getIODate());
                    Integer year = calendar.get(Calendar.YEAR);
                    Integer month = calendar.get(Calendar.MONTH) + 1;
                    Integer day = calendar.get(Calendar.DAY_OF_MONTH);
                    LocalDate dateCheck = LocalDate.of(year, month, day);
                    logInLateOutEarly.setDateCheck(dateCheck);

                    Integer hourStart = inLateOutEarly.getIOStart().getHours();
                    Integer minutesStart = inLateOutEarly.getIOStart().getMinutes();
                    Integer secondStart = inLateOutEarly.getIOStart().getSeconds();
                    LocalTime timeStart = LocalTime.of(hourStart, minutesStart, secondStart);


                    Integer hourEnd = inLateOutEarly.getIOEnd().getHours();
                    Integer minutesEnd = inLateOutEarly.getIOEnd().getMinutes();
                    Integer secondEnd = inLateOutEarly.getIOEnd().getSeconds();
                    LocalTime timeEnd = LocalTime.of(hourEnd, minutesEnd, secondEnd);

                    Optional<LogInLateOutEarly> checkExist = null;
                    if(inLateOutEarly.getIOKind()==1 ){
                        checkExist = logInLateOutEarlyRepository.checkExist(dateCheck, timeEnd, user.getId());
                    }else if(inLateOutEarly.getIOKind()==2 ){
                        checkExist = logInLateOutEarlyRepository.checkExist(dateCheck, timeStart, user.getId());
                    }

                    if (!checkExist.isPresent()) {
                        if (inLateOutEarly.getIOKind() == 1) {
                            if (timeEnd.isAfter(LocalTime.of(8, 30, 00))) {
                                logInLateOutEarly.setIOKind("Đến muộn");
                                Duration duration = Duration.between(LocalTime.of(8, 30, 00), timeEnd);
                                LocalTime duration1 = LocalTime.of((int)duration.toHours(),
                                        (int)(duration.toMinutes() % 60),
                                        (int)(duration.toSeconds() % 60));
                                logInLateOutEarly.setDuration(duration1);
                                logInLateOutEarly.setTimeEnd(timeEnd);
                            }
                        } else if (inLateOutEarly.getIOKind() == 2) {
                            if (timeStart.isBefore(LocalTime.of(17, 0, 0))) {
                                logInLateOutEarly.setIOKind("Về sớm");
                                Duration duration = Duration.between(timeStart,LocalTime.of(17, 0, 0));
                                LocalTime duration1 = LocalTime.of((int)duration.toHours(),
                                        (int)(duration.toMinutes() % 60),
                                        (int)(duration.toSeconds() % 60));
                                logInLateOutEarly.setDuration(duration1);
                                logInLateOutEarly.setTimeStart(timeStart);
                            }
                        }
                        logInLateOutEarly.setEmployeeId(inLateOutEarly.getEmployeeId());
                        logInLateOutEarly.setReason(inLateOutEarly.getReason());
                        if(logInLateOutEarly.getIOKind()!=null && logInLateOutEarly.getIOKind()!=""){
                            logInLateOutEarlyRepository.save(logInLateOutEarly);
                        }
                    }

                }
            }
            // Đóng kết nối
            connection.disconnect();
            return tblInLateOutEarlies;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
