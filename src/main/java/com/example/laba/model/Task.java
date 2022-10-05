package com.example.laba.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class Task {
    private Long id;
    private String header;
    private String description;
    private LocalDate date;
    private User user;
    private Status status;
}
