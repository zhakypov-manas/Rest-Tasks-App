package com.example.laba.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    New("Новая"),
    At_Work("В работе"),
    Closed("Закрыта");

    private String description;
}
