package com.techelevator.custom.dao;

import com.techelevator.custom.model.GameMode;
import com.techelevator.custom.model.Session;

import java.util.List;

public interface SessionDao {

    Session getSessionById(int sessionId);

    List<Session> getSessionByUserId(int userId);

    Session createSession(GameMode gameMode, int createdByUserId);

    Session endSession(Session session); // Puts the end timer to now and calculates session duration

    int deleteSession(int sessionId);

}
