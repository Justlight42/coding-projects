package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.GameMode;
import com.techelevator.custom.model.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSessionDao implements SessionDao {

    private final String SESSIONS_SELECT = "SELECT * FROM sessions ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcSessionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Session getSessionById(int sessionId) {
        try {
            String sql = SESSIONS_SELECT + "WHERE session_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, sessionId);
            if (rowSet.next()) {
                return mapRowToFinishedSessions(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;
    }

    @Override
    public List<Session> getSessionByUserId(int userId) {
        List<Session> sessions = new ArrayList<>();
        try {
            String sql = SESSIONS_SELECT + "WHERE user_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                sessions.add(mapRowToFinishedSessions(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return sessions;
    }

    @Override
    public Session createSession(Session session) {
        try {
            String sql = "INSERT INTO sessions WHERE (game_mode, created_by_user_id, start_time) VALUES (?, ?, ?) RETURNING session_id";
            int sessionId = jdbcTemplate.queryForObject(sql, int.class, session.getGameMode(), session.getCreatedById(), session.getStartTime());
            return getSessionById(sessionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating a session " + session, e);
        }
    }

    @Override
    public Session endSession(Session session) {
        try {
            String sql = "UPDATE sessions SET game_mode = ?, created_by_user_id = ?, " +
                    "start_time = ?, end_time = ?, session_duration = ? WHERE session_id = ?";
            jdbcTemplate.update(sql, session.getGameMode(), session.getCreatedById(),
                    session.getStartTime(), session.getEndTime(), session.getSessionDuration(), session.getSessionId());
            return getSessionById(session.getSessionId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating a session " + session, e);
        }
    }

    @Override
    public int deleteSession(int sessionId) {
        try {
            String sql = "DELETE FROM sessions WHERE session_id = ?";
            return jdbcTemplate.update(sql, sessionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting a session with ID: " + sessionId, e);
        }
    }

    public static Session mapRowToFinishedSessions(SqlRowSet rowSet) {
        int sessionId = rowSet.getInt("session_id");
        int gameModeValue = rowSet.getInt("game_mode");
        GameMode gameMode = GameMode.fromIntToMode(gameModeValue);
        int createdById = rowSet.getInt("created_by_user_id");
        LocalDateTime startTime = rowSet.getTimestamp("start_time").toLocalDateTime();
        LocalDateTime endTime = rowSet.getTimestamp("end_time").toLocalDateTime();;
        int sessionDuration = rowSet.getInt("session_duration");
        return new Session(sessionId, gameMode, createdById, startTime);
    }

    public static Session mapRowToStartSessions(SqlRowSet rowSet) {
        int sessionId = rowSet.getInt("session_id");
        int gameModeValue = rowSet.getInt("game_mode");
        GameMode gameMode = GameMode.fromIntToMode(gameModeValue);
        int createdById = rowSet.getInt("created_by_user_id");
        LocalDateTime startTime = rowSet.getTimestamp("start_time").toLocalDateTime();
        return new Session(sessionId, gameMode, createdById, startTime);
    }

}
