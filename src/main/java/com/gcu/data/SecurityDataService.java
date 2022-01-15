package com.gcu.data;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import com.gcu.model.LoginModel;
import com.gcu.model.RegisterModel;
import com.gcu.util.DatabaseException;

//import lombok.extern.slf4j.Slf4j;

@Service
public class SecurityDataService implements DataAccessInterface<LoginModel> {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public SecurityDataService(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Override
	public LoginModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<LoginModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<LoginModel> findAllById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Method used to authenticate a user by searching for the username and password combo
	 * returns an int to indicate success or fail
	 * @param LoginModel t
	 * @return int
	 * **/
	
	@Override
	public int findByUsernameAndPassword(LoginModel t) {
		//log.info("Entering SecurityDataService.findByUsernameAndPassword()");
		try {
			//sql query
			String search = "SELECT * FROM `users` WHERE `USERNAME` = ? AND `PASSWORD` = ?";
			
			//hold user found in srs
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(search, t.getUsername(), t.getPassword());
			
			//if the search returns null we will return 0
			if(srs.next() == false) {
				//log.info("Exit SecurityDataService.findByUsernameAndPassword() with incorrect username or password");
				return 0;
			}
			//otherwise return 1
			else {
				t.setUserId(srs.getInt("USER_ID"));
				//log.info("Exit SecurityDataService.findByUsernameAndPassword() with correct username and password");
				return 1;
			}
			//exception handling
		} catch(DatabaseException ex) {
			//log.error("Exit SecurityDataService.findByUsernameAndPassword() with exception - %s", ex);
			return -1;
		}
	}

	@Override
	public int create(LoginModel t) {
		//unneeded
		return 0;
	}

	@Override
	public int update(LoginModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LoginModel t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * This method finds a user by their username
	 * 
	 * @param String username - used to find a user by username
	 * @Return LoginModel - returning credentials from database 
	 * 
	 **/
	@Override
	public LoginModel findByUsername(String username) {
		//log.info("Entering SecurityDataService.findByUsernameAndPassword()");
		LoginModel user = new LoginModel();
		try {
			//sql query
			String search = "SELECT * FROM `users` WHERE `USERNAME` = ?";
			
			//hold user found srs
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(search, username);
			
			//if the search returns null we will return 0
			if(srs.next() == false) {
				//log.info("Exit SecurityDataService.findByUsernameAndPassword() with incorrect username or password");
				return user;
			}
			//otherwise return 1
			else {
				//set user credentials and id 
				user.setUserId(srs.getInt("USER_ID"));
				user.setPassword(srs.getString("PASSWORD"));
				user.setUsername(username);								
				//log.info("Exit SecurityDataService.findByUsername() with correct username ");
				return user;
			}
			//exception handling
		} catch(DatabaseException ex) {
			//log.error("Exit SecurityDataService.findByUsernameAndPassword() with exception - %s", ex);
			return user;
		}
	}
}