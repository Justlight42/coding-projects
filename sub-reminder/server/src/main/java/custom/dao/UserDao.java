package custom.dao;

import custom.model.User;
import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User createUser(User newUser);

    int deleteUser(int userId);

}
