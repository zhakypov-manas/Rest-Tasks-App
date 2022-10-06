package com.example.laba.model;

import com.example.laba.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    private Long id;
    private String header;
    private String description;
    private LocalDate date;
    private UserDto user;
    private Status status;
}
