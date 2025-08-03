package com.techelevator.custom.dao;

import com.techelevator.custom.model.GameMode;
import com.techelevator.custom.model.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcSessionDao implements SessionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSessionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Session getSessionById(int sessionId) {
        return null;
    }

    @Override
    public List<Session> getSessionByUserId(int userId) {
        return List.of();
    }

    @Override
    public Session createSession(GameMode gameMode, int createdByUserId) {
        return null;
    }

    @Override
    public Session endSession(Session session) {
        return null;
    }

    @Override
    public int deleteSession(int sessionId) {
        return 0;
    }

}
