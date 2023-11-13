package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.SepBeEmployeeManagementApplication;
import org.springframework.boot.SpringApplication;

public class MainTest {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7};
        boolean swaped;
        do{
            swaped = false;
            for(int i = 0; i < array.length-1; i++){
                if(array[i] > array[i+1]){
                    int temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    swaped = true;
                }
            }
        }while (swaped);

//        return array;


        // lấy index ký tự lặp lại đầu tiên trong array
//        String[] array = {"a", "b", "c", "d", "a", "e", "b"};
//
//        // Sử dụng một HashSet để lưu trữ các chữ cái đã xuất hiện
//        Set<String> seenLetters = new HashSet<>();
//
//        for (int i = 0; i < array.length; i++) {
//            String letter = array[i];
//
//            // Kiểm tra xem chữ cái đã xuất hiện trước đó chưa
//            if (seenLetters.contains(letter)) {
//                // Nếu đã xuất hiện, in ra index và chữ cái
//                System.out.println("Chữ cái lặp lại đầu tiên: " + letter + " tại index " + i);
//                return "Chữ cái lặp lại đầu tiên: " + letter + " tại index " + i;
////                break; // Dừng vòng lặp sau khi tìm thấy chữ cái lặp lại
//            } else {
//                // Nếu chưa xuất hiện, thêm chữ cái vào HashSet
//                seenLetters.add(letter);
//            }
//        }
//        return null;
    }
}
