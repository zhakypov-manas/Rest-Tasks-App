package com.example.laba.dto;

import com.example.laba.model.Status;
import com.example.laba.model.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String header;
    private Status status;
    private LocalDate date;
}
