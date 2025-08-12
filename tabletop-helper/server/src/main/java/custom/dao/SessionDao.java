package custom.dao;

import custom.model.Session;

import java.util.List;

public interface SessionDao {

    Session getSessionById(int sessionId);

    List<Session> getSessionByUserId(int userId);

    Session createSession(Session session);

    Session endSession(Session session); // Puts the end timer to now and calculates session duration

    int deleteSession(int sessionId);

}
