package com.project.SEP_BE_EmployeeManagement.scheduled;

import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExecuteAttendance {

    @Autowired
    HolidayRepository holidayRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    SignRepository signRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LogCheckInOutRepository logCheckInOutRepository;

    @Autowired
    NoteCatergoryRepository noteCatergoryRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    public void ExecuteAttendance(Integer day, Integer month, Integer year){
        WorkingTime morningShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
        WorkingTime afternoonShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();
        LocalTime ruleTimeIn = LocalTime.of(8,30,00);
        LocalTime ruleTimeOut = LocalTime.of(17,30,00);
        int breakTime = 1;

        LocalDate currentDate = LocalDate.now();
        LocalDate executeDate = currentDate.minusDays(0);
        if(day!=null && month!=null && year!=null){
            executeDate = LocalDate.of(year, month, day);
        }
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        // Holiday CASE
        Holiday holidayYesterday = holidayRepository.findExecuteAttendanceDate(executeDate.toString());
        if (holidayYesterday != null) {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                System.out.println(user.getUserCode() + " Add Log HOLIDAY");
                // create log
                Attendance attendance = new Attendance();
                Set<NoteLog> noteCatergorySet = attendance.getNoteLogSet();
                if (noteCatergorySet == null)
                    noteCatergorySet = new HashSet<>();
                NoteLog noteLog = new NoteLog();
                noteLog.setAttendance(attendance);
                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_HOLIDAY));
                noteLog.setContent(holidayYesterday.getHolidayName());
                noteLog.setCreateDate(LocalDateTime.now());
                noteLog.setSignChange(signRepository.findByName(ESign.L));
                noteCatergorySet.add(noteLog);
                attendance.setNoteLogSet(noteCatergorySet);

                attendance.setSigns(signRepository.findByName(ESign.L));
                attendance.setDateLog(executeDate);
                attendance.setUser(user);
//                logDetail.setRequestActive(true);
                attendanceRepository.save(attendance);
            }
            return;
        }

        // NT CASE
        if (executeDate.getDayOfWeek().toString() == "SATURDAY" ||
                executeDate.getDayOfWeek().toString() == "SUNDAY") {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                System.out.println(user.getUserCode() + " Add Log SS");
                // create log
                Attendance attendance = new Attendance();
//                Set<NoteLog> noteCatergorySet = logDetail.getNoteLogSet();
//                if (noteCatergorySet == null)
//                    noteCatergorySet = new HashSet<>();
//                NoteLog noteLog = new NoteLog();
//                noteLog.setLogDetail(logDetail);
//                noteLog.setNoteCatergory(noteCatergoryRepository.findByName(ENoteCatergory.E_WEEKEND));
////                noteLog.setContent(holidayYesterday.getHolidayName());
//                noteLog.setCreateDate(LocalDateTime.now());
//                noteLog.setSignChange(signRepository.findByName(ESign.NT));
//                noteCatergorySet.add(noteLog);
//                logDetail.setNoteLogSet(noteCatergorySet);

                attendance.setSigns(signRepository.findByName(ESign.NT));
                attendance.setDateLog(executeDate);
                attendance.setUser(user);
//                logDetail.setRequestActive(true);
                attendanceRepository.save(attendance);
            }
            return;
        }

        // check check in/ check out từ LogCheckInOut
        List<LogCheckInOut> checkInOuts = logCheckInOutRepository.findByDate(executeDate);

        for (LogCheckInOut log: checkInOuts) {

            if(log.getUser()!=null){
                Attendance attendance = new Attendance();
                Attendance attendanceExist = attendanceRepository.findByUserCodeAndDate(log.getUser().getUserCode(), log.getDateCheck());

                //check log Detail đã tồn tại
                if(attendanceExist!=null){
                    // time out == null  => setTimeOut
                    if(attendanceExist.getTimeOut()==null){
                        attendanceExist.setTimeOut(log.getTimeCheck());
                        Duration duration = Duration.between(attendanceExist.getTimeIn(), attendanceExist.getTimeOut());

                        LocalTime total = LocalTime.of((int)duration.toHours(),
                                (int)(duration.toMinutes() % 60),
                                (int)(duration.toSeconds() % 60));
                        attendanceExist.setTotalWork(total.minusHours(breakTime));
                    }
                    // nếu time check sau TimeOut của log thì set lại
                    else {
                        if(attendanceExist.getTimeIn()==null ){
                            attendanceExist.setTimeOut(log.getTimeCheck());
                        }
                        else if(attendanceExist.getTimeOut().isBefore(log.getTimeCheck())){
                            attendanceExist.setTimeOut(log.getTimeCheck());
                            Duration duration = Duration.between(attendanceExist.getTimeIn(), attendanceExist.getTimeOut());
                            LocalTime total = LocalTime.of((int)duration.toHours(),
                                    (int)(duration.toMinutes() % 60),
                                    (int)(duration.toSeconds() % 60));
                            attendanceExist.setTotalWork(total.minusHours(breakTime));
                        }
                    }
                    attendanceRepository.save(attendanceExist);

                }
                else {
                    attendance = new Attendance();
                    attendance.setDateLog(executeDate);
                    if(log.getUser()!=null)  attendance.setUser(log.getUser());

                    if(log.getTimeCheck().isAfter(LocalTime.of(15,0,0))){
                        attendance.setTimeOut(log.getTimeCheck());
                    }else{
                        if(attendance.getTimeIn()==null){
                            attendance.setTimeIn(log.getTimeCheck());
                        }
                    }
                    attendanceRepository.save(attendance);
                }
            }
        }
        for (User user: userRepository.findAll()) {
            Attendance attendanceExist = attendanceRepository.findByUserCodeAndDate(user.getUserCode(), executeDate);
            if( attendanceExist ==null){
                Attendance attendance = new Attendance();
                attendance.setDateLog(executeDate);
                attendance.setSigns(signRepository.findByName(ESign.KL));
                attendance.setUser(user);
//                logDetail.setRequestActive(true);
                attendanceRepository.save(attendance);
            }
        }

//        List<Request> requests = requestRepository.findByStatusAndDateList(2,firstDayOfMonth, yesterday);
        List<Attendance> attendances = attendanceRepository.findByDate(executeDate);

        for (Attendance attendance : attendances) {
            Sign currentSign = attendance.getSigns();
            if (holidayYesterday != null) {
                attendance.setSigns(signRepository.findByName(ESign.L));
//                Set<NoteLog> noteCatergorySet = logDetail.getNoteLogSet();
//                if (noteCatergorySet == null)
//                    noteCatergorySet = new HashSet<>();
//                NoteLog noteLog = new NoteLog();
//                noteLog.setLogDetail(logDetail);
//                noteLog.setNoteCatergory(noteCatergoryRepository.findByName(ENoteCatergory.E_HOLIDAY));
//                noteLog.setContent(holidayYesterday.getHolidayName());
//                noteLog.setLastSign(logDetail.getSigns());
//                noteLog.setCreateDate(LocalDateTime.now());
//                noteLog.setSignChange(signRepository.findByName(ESign.L));
//                noteCatergorySet.add(noteLog);
//                logDetail.setNoteLogSet(noteCatergorySet);
                attendance.setSigns(signRepository.findByName(ESign.L));
                attendanceRepository.save(attendance);
                continue;
            }

            DayOfWeek dayOfWeek = attendance.getDateLog().getDayOfWeek();
            Integer hourIn = null;
            Integer hourOut = null;
            Integer minuteIn = null;
            Integer minuteOut = null;
            Integer secondIn = null;
            Integer secondOut = null;
            if (attendance.getTimeIn() != null) {
                hourIn = attendance.getTimeIn().getHour();
                minuteIn = attendance.getTimeIn().getMinute();
                secondIn = attendance.getTimeIn().getSecond();
                System.out.println("Hour in : " + hourIn);
                System.out.println("Minute in : " + minuteIn);
                System.out.println("Second in : " + secondIn);

            }
            if (attendance.getTimeOut() != null) {
                hourOut = attendance.getTimeOut().getHour();
                minuteOut = attendance.getTimeOut().getMinute();
                secondOut = attendance.getTimeOut().getSecond();

                System.out.println("Hour out : " + hourOut);
                System.out.println("Minute out : " + minuteOut);
                System.out.println("Second out : " + secondOut);

            }
            System.out.println(attendance.getDateLog() + "-" + dayOfWeek);

            if (hourOut == null && hourIn == null) {
                if (!dayOfWeek.toString().equals("SUNDAY") && !dayOfWeek.toString().equals("SATURDAY")) {
                    attendance.setSigns(signRepository.findByName(ESign.KL));
                }
            }
            if (hourOut != null && hourIn != null) {
                if ((hourIn == null && hourOut < 17) || (hourIn >= 10 && hourOut == null) || ((hourIn >= 8 && minuteIn >30) && hourOut < 17)) {
                    attendance.setSigns(signRepository.findByName(ESign.KL));
                }

                if (hourIn >= 9  ) {
                    if(hourOut >= 17) attendance.setSigns(signRepository.findByName(ESign.KL_H));
                    else if(hourOut < 17) attendance.setSigns(signRepository.findByName(ESign.KL));
                }
                if (hourIn < 9) {
                    if(hourOut >= 17 && minuteOut >= 0){
                        attendance.setSigns(signRepository.findByName(ESign.H));
                    } else if (hourOut >= 12 && hourOut < 17) {
                        attendance.setSigns(signRepository.findByName(ESign.H_KL));
                    } else if (hourOut < 12) {
                        attendance.setSigns(signRepository.findByName(ESign.KL));
                    }
                }
//                if(hourIn<10 &&  hourOut>15) {
//                    logDetail.setSigns(signRepository.findByName(ESign.H));
//                }

            }
            if (hourIn == null && hourOut != null) {
                if (hourOut < 17) {
                    attendance.setSigns(signRepository.findByName(ESign.KL));
                }
                if (hourOut >= 17) {
                    attendance.setSigns(signRepository.findByName(ESign.KL_H));
                }
            }
            if (hourIn != null && hourOut == null) {
                if (hourIn >= 9) {
                    attendance.setSigns(signRepository.findByName(ESign.KL));
                }
                if (hourIn < 9) {
                    attendance.setSigns(signRepository.findByName(ESign.H_KL));
                }
            }
//            Set<NoteLog> noteCatergorySet = logDetail.getNoteLogSet();
//            if (noteCatergorySet == null)
//                noteCatergorySet = new HashSet<>();
//            NoteLog noteLog = new NoteLog();
//            noteLog.setLogDetail(logDetail);
//            noteLog.setNoteCatergory(noteCatergoryRepository.findByName(ENoteCatergory.E_CHECKINOUT));
//            noteLog.setLastSign(currentSign);
//            noteLog.setCreateDate(LocalDateTime.now());
//            noteLog.setSignChange(logDetail.getSigns());
////            noteCatergorySet.add(noteLog);
//            logDetail.setNoteLogSet(noteCatergorySet);
            attendanceRepository.save(attendance);
        }
    }
}
