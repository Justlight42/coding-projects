package com.techelevator.custom.controller;


import com.techelevator.custom.dao.ExerciseDao;
import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.dao.UserExerciseListDao;
import com.techelevator.custom.dto.ExerciseListEntry;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Exercise;
import com.techelevator.custom.model.User;
import com.techelevator.custom.model.UserExerciseList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/userExiList")
public class UserExerciseListController {

    private final ExerciseDao exerciseDao;
    private final UserDao userDao;
    private final UserExerciseListDao userExerciseListDao;

    public UserExerciseListController(ExerciseDao exerciseDao, UserDao userDao, UserExerciseListDao userExerciseListDao) {
        this.exerciseDao = exerciseDao;
        this.userDao = userDao;
        this.userExerciseListDao = userExerciseListDao;
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(path = "/list/{listId}")
    public UserExerciseList getListById(@PathVariable int listId) {
        try {
            UserExerciseList list = userExerciseListDao.getListById(listId);
            if (list == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not get list with the Id: " + listId);
            }
            return list;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get list: " + listId + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/list")
    public UserExerciseList createList(@RequestBody UserExerciseList userExerciseList, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return userExerciseListDao.createList(user.getId(), userExerciseList.getListName());
    }

    @GetMapping(path = "/exerciseList/{listId}")
    public List<Exercise> getAllExercisesInList(@PathVariable int listId) {
        try {
            List<Exercise> exerciseList = userExerciseListDao.getAllExercisesInList(listId);
            if (exerciseList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not get exercise from the list: " + listId);
            }
            return exerciseList;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get exercises in the list: " + listId + e.getMessage());
        }
    }

    @GetMapping(path = "/userList/{userId}")
    public List<UserExerciseList> getListByUser(@PathVariable int userId) {
        try {
            List<UserExerciseList> userList = userExerciseListDao.getListByUserId(userId);
            if (userList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find any list from the user: " + userId);
            }
            return userList;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error fetching the list from user: " + userId + e.getMessage());
        }
    }

    @GetMapping(path = "/lists/{exerciseId}")
    public List<ExerciseListEntry> getListsByExerciseId(@PathVariable int exerciseId) {
        try {
            return userExerciseListDao.getListsByExerciseId(exerciseId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error fetching the list with the exerciseId: " + exerciseId + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/list/addExercise")
    public Exercise addExerciseToList(@RequestBody ExerciseListEntry entry, Principal principal) {
        listOwner(entry, principal);
        if (userExerciseListDao.exerciseExistInList(entry.getListId(), entry.getExerciseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already have that exercise in this list.");
        }
        return userExerciseListDao.addExerciseToList(entry);
    }

    private void listOwner(ExerciseListEntry entry, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        UserExerciseList listId = userExerciseListDao.getListById(entry.getListId());
        if (listId.getUserId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this list.");
        }
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/list/{listId}")
    public UserExerciseList updateListName(@RequestBody UserExerciseList userExerciseList, @PathVariable int listId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        UserExerciseList list = userExerciseListDao.getListById(listId);
        if (list == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the list to update: " + listId);
        }
        if (list.getUserId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this list.");
        }
        UserExerciseList updatedList = new UserExerciseList(listId, user.getId(), userExerciseList.getListName());
        return userExerciseListDao.updateListName(updatedList);
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/list/{listId}/deleteExercise/{exerciseId}")
    public int removeExerciseFromList(@PathVariable int listId, @PathVariable int exerciseId, Principal principal) {
        ExerciseListEntry entry = new ExerciseListEntry(listId, exerciseId);
        listOwner(entry, principal);
        if (!userExerciseListDao.exerciseExistInList(entry.getListId(), entry.getExerciseId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This exercise does not exist in this list.");
        }
        return userExerciseListDao.removeExerciseFromList(listId, exerciseId);
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/list/{listId}")
    public int deleteList(@PathVariable int listId, Principal principal) {
        try {
            User user = userDao.getUserByUsername(principal.getName());
            UserExerciseList userList = userExerciseListDao.getListById(listId);
            if (user.getId() != userList.getUserId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this list.");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This list does not exist.");
        }
        return userExerciseListDao.deleteList(listId);
    }

}
