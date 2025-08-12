package custom.dao;

import custom.exception.DaoException;
import custom.model.GameMode;
import custom.model.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
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
            String sql = SESSIONS_SELECT + "WHERE created_by_user_id = ?";
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
            String sql = "INSERT INTO sessions (game_mode, created_by_user_id) VALUES (?, ?) RETURNING session_id";
            int sessionId = jdbcTemplate.queryForObject(sql, int.class, session.getGameMode().getValue(), session.getCreatedById());
            return getSessionById(sessionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating a session " + session, e);
        }
    }

    @Override
    public Session endSession(Session session) {
        if (session.getEndTime() == null) {
            session.setEndTime(LocalDateTime.now());
        } else {
            throw new DaoException("That session has already ended. Session ID: " + session.getSessionId());
        }
        try {
            String sql = "UPDATE sessions SET end_time = ?, session_duration = ? WHERE session_id = ?";
            jdbcTemplate.update(sql, session.getEndTime(), session.getSessionDuration(), session.getSessionId());
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
        Timestamp entTs =  rowSet.getTimestamp("end_time");
        LocalDateTime endTime = (entTs != null) ? entTs.toLocalDateTime() : null;
        String sessionDuration = rowSet.getString("session_duration");
        return new Session(sessionId, gameMode, createdById, startTime, endTime, sessionDuration);
    }

}
