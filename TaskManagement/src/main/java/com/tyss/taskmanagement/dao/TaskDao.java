package com.tyss.taskmanagement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tyss.taskmanagement.model.Employee1;
import com.tyss.taskmanagement.model.Task;
import com.tyss.taskmanagement.repository.EmployeeRepository;
import com.tyss.taskmanagement.repository.TaskRespository;

@Service
@Transactional
public class TaskDao {

	@Autowired
	TaskRespository taskrepository;

	@Autowired
	EmployeeRepository employeerepository;

	@Autowired
	private JdbcTemplate template;

	public void save(Task task) {
		
		taskrepository.save(task);
	}

	public void saveemployee(Employee1 employee) {
		employeerepository.save(employee);
	}

	public int getcount(String email, String password) {
		String sql = "select count(*) from employee1 where user_email=? and employee_password=?";
		int count=0;
		count = template.queryForObject(sql, new Object[] { email, password }, int.class);
		return count;

	}

	public List<Task> getAllTask(String email) {
		return template.query("select * from task where assigned_to_email=?",new Object[] {email}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(3));
				e.setEmail(rs.getString(9));
				e.setAssignedBy(rs.getString(4));
			
				return e;
			}
		});
	}

	public List<Task> getAll(String email) {
		return template.query("select * from task where assigned_by=?",new Object[] {email}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(9));
				e.setEmail(rs.getString(9));
				e.setDate(rs.getDate(7));
			
				return e;
			}
		});
	}
	
	public List<Task> getCompleted() {
		return template.query("select * from task where status=3",new Object[] {}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(4));
				e.setEmail(rs.getString(9));
				e.setDate(rs.getDate(7));
			
				return e;
			}
		});
	}
	
	public List<Task> getToDo(String email) {
		return template.query("select * from task where assigned_to_email=? and status=1",new Object[] {email}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(4));
				e.setDate(rs.getDate(7));
			
				return e;
			}
		});
	}
	
	public List<Task> getInProgress(String email) {
		return template.query("select * from task where assigned_to_email=? and status=2",new Object[] {email}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(4));
				e.setEmail(rs.getString(9));
				e.setDate(rs.getDate(7));
				e.setAssignedBy(rs.getString(4));
			
				return e;
			}
		});
	}
	
	public List<Task> getBlocked() {
		return template.query("select * from task where status=4",new Object[] {}, new RowMapper<Task>() {
			public Task mapRow(ResultSet rs, int row) throws SQLException {
				Task e = new Task();
				e.setId(rs.getInt(1));
				e.setDescription(rs.getString(8));
				e.setCategory(rs.getString(5));
				e.setPriority(rs.getString(10));
				e.setAssignTo(rs.getString(4));
				e.setEmail(rs.getString(9));
				e.setDate(rs.getDate(7));
				e.setAssignedBy(rs.getString(4));
			
				return e;
			}
		});
	}
	
	public void acceptTask(Task task) {
		taskrepository.save(task);
	}
	
	public int todoTable() {
		String sql = "insert into to_do_task  (id, addcategory_column, assign_to, assigned_by, category_column, task_created_at, date_column, description_column, assigned_to_email, priority, status)select id, addcategory_column, assign_to, assigned_by, category_column, created_at, date_column, description_column, assigned_to_email, priority, status from task where status=2";
				
		return template.update(sql);
	}
	
	public Optional<Task> findById(Integer id) {
		return taskrepository.findById(id);
	}
}
