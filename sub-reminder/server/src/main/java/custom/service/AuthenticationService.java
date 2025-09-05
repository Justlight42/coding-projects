package custom.service;

import custom.dao.UserDao;
import custom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    public int getCurrentUserId(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return user.getId();
    }
}
