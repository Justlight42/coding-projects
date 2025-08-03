package com.techelevator.custom.controller;

import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllUser() {
        return userDao.getUsers();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable int userId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        if (userId != user.getId() && !user.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have authorization to delete this user.");
        }
        int delete = userDao.deleteUser(userId);
        if (delete == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user you are trying to delete does not exist.");
        }
    }












}
