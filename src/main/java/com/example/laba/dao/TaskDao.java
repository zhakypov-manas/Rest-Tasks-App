package com.example.laba.dao;

import com.example.laba.dto.TaskDto;
import com.example.laba.model.Status;
import com.example.laba.model.Task;
import com.example.laba.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskDao {
    private final JdbcTemplate jdbcTemplate;

    private Task taskDetail(User user, Long task_id){
        try {
            String query = "select * from tasks where id = ? and user_id = ?";
            Task task = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Task.class), task_id, user.getId());
            return task;
        }catch (Exception exc){
            return null;
        }
    }

    private List<TaskDto> getMyTasks(User user){
        String sql = "select * from tasks where user_id != ?";
        List<TaskDto> tasks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TaskDto.class), user.getId());
        return tasks;
    }

    private String createTask(Task task, User user){
        String query = "INSERT INTO tasks (header, description, date, user_id, status)" +
                " VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query,
                task.getHeader(), task.getDescription(), task.getDate(), user.getId(), Status.New);
        return "Что то произошло";
    }

    private String changeStatus(Long task_id, User user){
        String query = "select * from tasks where id = ? and user_id = ?";
        try {
            Task task = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Task.class), task_id, user.getId());
            if (task != null){
                Task updateTask = chooseStatusUpdate(task);
                String sql = "update tasks Set status = ?, where id = ?";
                jdbcTemplate.update(sql,task.getStatus(),task_id);
                return "Всё получилось";
            }else {
                return "Ошибка";
            }

        }catch (Exception exc){
            return "Что то пошло не так";
        }
    }

    private Task chooseStatusUpdate(Task task){
        switch (task.getStatus()) {
            case New -> {
                task.setStatus(Status.At_Work);
                break;
            }
            case At_Work -> {
                task.setStatus(Status.Closed);
                break;
            }
        }
        return task;
    }
}
