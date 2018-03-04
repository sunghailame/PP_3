package whiteboard.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import whiteboard.login.Person;


@Configuration
public class AdminConfiguration {
	private static final String listUsers = 
			"SELECT " + 
				"ID, " + 
				"Name, " + 
				"Email, " + 
				"Role," + 
				"Username" +
			"FROM PERSON " +
			"ORDER BY Role ASC";
	
	@Bean
	ItemReader<Person> databaseReader (DataSource dataSource) {
		JdbcCursorItemReader<Person> databaseReader = new JdbcCursorItemReader<>();
		 
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(listUsers);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Person.class));
 
        return databaseReader;
	}

}
