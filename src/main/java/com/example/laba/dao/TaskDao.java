package com.example.laba.dao;

import com.example.laba.dto.TaskDetailDto;
import com.example.laba.dto.TaskDto;
import com.example.laba.model.Status;
import com.example.laba.model.Task;
import com.example.laba.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public TaskDetailDto taskDetail(String email, Long task_id){
        try {
            User user = userDao.getUserByEmail(email);
            String query = "select t.id, t.header, t.description, t.date, u.name, t.status from tasks as t " +
                    "join users u ON t.user_id = u.id " +
                    "where t.id = ? and t.user_id = ?";
            TaskDetailDto task = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(TaskDetailDto.class), task_id, user.getId());
            return task;

        }catch (Exception exc){
            return null;
        }
    }

    public List<TaskDto> getMyTasks(String email){
        User user = userDao.getUserByEmail(email);
        String sql = "select * from tasks where user_id = ?";
        List<TaskDto> tasks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TaskDto.class), user.getId());
        return tasks;
    }

    public String createTask(Task task, String email){
        if (check(task)){
            User user = userDao.getUserByEmail(email);
            LocalDate localDate = LocalDate.parse(task.getDate().toString());
            String query = "INSERT INTO tasks (header, description, date, user_id, status)" +
                    " VALUES (?,?,?,?,?)";
            jdbcTemplate.update(query,
                    task.getHeader(), task.getDescription(), localDate, user.getId(), Status.New.toString());
            return "Успешно заполнено";
        }
        return "Вы что то не заполнили";
    }

    private Boolean check(Task task){
        if (task.getHeader() == null){
            return false;
        }else if (task.getDescription() == null){
            return false;
        }else if (task.getDate() == null){
            return false;
        }
        return true;
    }

    public String changeStatus(Long task_id, String email){
        String query = "select * from tasks where id = ? and user_id = ?";
        try {
            User user = userDao.getUserByEmail(email);
            Task task = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Task.class), task_id, user.getId());
            if (task != null){
                Task updateTask = chooseStatusUpdate(task);
                String sql = "update tasks Set status = ? where id = ?";
                jdbcTemplate.update(sql,task.getStatus().name(),task_id);
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
