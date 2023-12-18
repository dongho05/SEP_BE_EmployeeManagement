package com.project.SEP_BE_EmployeeManagement.dto.response.connector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


//@Entity
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Table(name = "tmpCHECKINOUT")
public class TmpCheckInOut {
    private String BadgeNumber;
    private Integer InOutMode;
    private Integer VerifyCode;
    private Integer WorkCode;
    private Date CheckTime;
    private String SN;
    private Integer LoginID;
    private String EventType;
    private Integer MachineNumber;
    private String PhotoImage;
    private Float temperature;
    private Integer maskflag;
//    private String CardNo;


//    @Column(name = "BadgeNumber")
//    private Long badgeNumber;
//
//    @Column(name = "InOutMode")
//    private Integer inOutMode;
//
//    @Column(name = "VerifyCode")
//    private Integer verifyCode;
//
//    @Column(name = "WorkCode")
//    private Integer workCode;
//
//    @Column(name = "CheckTime")
//    private Date checkTime;
//
//    @Column(name = "SN")
//    private String SN;
//
//    @Column(name = "LoginID")
//    private Integer loginID;
//
//    @Column(name = "EventType")
//    private String eventType;
//
//    @Column(name = "MachineNumber")
//    private Integer machineNumber;
//
//    @Column(name = "PhotoImage")
//    private String photoImage;
//
//    @Column(name = "temperature")
//    private Integer temperature;
//
//    @Column(name = "maskflag")
//    private String maskFlag;
//
//    @Column(name = "CardNo")
//    private String cardNo;
}
