package com.techelevator.custom.model;

public class UserExerciseList {

    private int listId;
    private int userId;
    private String listName;

    public UserExerciseList(int listId, int userId, String listName) {
        this.listId = listId;
        this.userId = userId;
        this.listName = listName;
    }

    public int getListId() {
        return listId;
    }

    public int getUserId() {
        return userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

}
