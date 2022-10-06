package com.example.laba.service;

import com.example.laba.dao.TaskDao;
import com.example.laba.dto.TaskDetailDto;
import com.example.laba.dto.TaskDto;
import com.example.laba.model.Task;
import com.example.laba.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskDao taskDao;

    public List<TaskDto> getMyTask(String email){
        return taskDao.getMyTasks(email);
    }

    public TaskDetailDto taskDetail(String email, Long task_id){
        return taskDao.taskDetail(email, task_id);
    }

    public String changeTaskStatus(Long task_id, String email){
        return taskDao.changeStatus(task_id,email);
    }

    public String createTask(Task task, String email){
        return taskDao.createTask(task, email);
    }
}
