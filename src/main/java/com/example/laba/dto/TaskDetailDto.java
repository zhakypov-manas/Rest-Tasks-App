package com.example.laba.dto;

import com.example.laba.model.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDetailDto {
    private Long id;
    private String header;
    private String description;
    private LocalDate date;
    private String name;
    private Status status;
}
