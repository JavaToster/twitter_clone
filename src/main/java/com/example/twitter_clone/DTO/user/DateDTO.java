package com.example.twitter_clone.DTO.user;

import lombok.Data;

@Data
public class DateDTO {
    private int day;
    private int month;
    private int year;

    public boolean isEmpty(){
        return day == 0 || month == 0 || year == 0;
    }
}
