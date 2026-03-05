package com.Project.PlacementCell.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private String error;
    private int statuscode;

    public ApiError(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String error,int statuscode){
        this();
        this.error=error;
        this.statuscode=statuscode;
    }
}
