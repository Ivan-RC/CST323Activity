package com.gcu.data;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gcu.model.BlogModel;
import com.gcu.model.RegisterModel;
import com.gcu.util.DatabaseException;

//import lombok.extern.slf4j.Slf4j;

@Service
public class RegistrationDataService implements DataAccessInterface<RegisterModel> {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public RegistrationDataService(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Override
	public RegisterModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<RegisterModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<RegisterModel> findAllById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int findByUsernameAndPassword(RegisterModel t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Method used to create a new user
	 * Returns an int to indicate success or fail
	 * @param RegisterModel user
	 * @return int
	 * **/
	
	@Override
	public int create(RegisterModel user) {
		//log.info("Entering RegistrationDataService.create()");
		try {
			//sql statement 
			String sql = "INSERT INTO users(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ROLE) VALUES(?,?,?,?,?,?)";
			
			//hold number of rows inserted
			int rowInserted = jdbcTemplateObject.update(sql, user.getCredentials().getUsername(), user.getCredentials().getPassword(),
																user.getFirstName(), user.getLastName(), user.getEmail(), null);
			if(rowInserted == 0) {
				//if no row was inserted, return 0
				//log.info("Exit RegistrationDataService.create() with no new rows");
				return 0;
			}
		} catch(DatabaseException ex) {
			//log.error("Exit RegistrationDataService.create() with exception - %s", ex);
			return -1;
		}
		//if new row was inserted we return 1
		//log.info("Exit RegistrationDataService.create() with new row");
		return 1;
	}

	@Override
	public int update(RegisterModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(RegisterModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RegisterModel findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
