package com.example.laba.controller;

import com.example.laba.dto.TaskDetailDto;
import com.example.laba.dto.TaskDto;
import com.example.laba.model.Task;
import com.example.laba.model.User;
import com.example.laba.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks/")
    public ResponseEntity<?> getMyTask(Authentication authentication){
        String email = authentication.getName();
        return new ResponseEntity<List<TaskDto>>(taskService.getMyTask(email), HttpStatus.OK);
    }

    @GetMapping("/tasks/detail/{id}")
    public ResponseEntity<?> taskDetail(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();
        return new ResponseEntity<TaskDetailDto>(taskService.taskDetail(email,id), HttpStatus.OK);
    }

    @PostMapping("/tasks/changeStatus/{id}")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();
        return new ResponseEntity<String>(taskService.changeTaskStatus(id,email), HttpStatus.OK);
    }

    @PostMapping("/tasks/create/")
    public ResponseEntity<?> createTask(@RequestBody Task task, Authentication authentication){
        String email = authentication.getName();
        return new ResponseEntity<String>(taskService.createTask(task,email), HttpStatus.OK);
    }
}
