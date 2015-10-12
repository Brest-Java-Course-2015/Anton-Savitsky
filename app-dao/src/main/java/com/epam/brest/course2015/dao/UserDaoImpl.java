package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by antonsavitsky on 09.10.15.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${user.select}")
    private String userSelect;

    @Value("${user.selectById}")
    private String userSelectById;

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(userSelect, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer id) {
        LOGGER.info("id: {}", id);
        return jdbcTemplate.queryForObject(userSelectById, new Object[]{id}, new UserRowMapper());
    }

    private class UserRowMapper implements RowMapper<User> {

        public static final String USER_ID = "userId";
        public static final String LOGIN = "login";

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt(USER_ID)); //Ctrl+Alt+C
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }
}
