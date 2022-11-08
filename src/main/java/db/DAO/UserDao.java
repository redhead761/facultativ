package db.DAO;


import db.DataSource;
import db.entity.Role;
import db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class UserDao {

    private static final String SELECT_FROM_USERS = "SELECT * FROM users";
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_FROM_USERS);
            while (rs.next()) {
                UserMapper userMapper = new UserMapper();
                User user = userMapper.mapRow(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    static class UserMapper {

        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.of(rs.getInt("role_id")));

                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }


}
