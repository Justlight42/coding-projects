package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Player;
import com.techelevator.custom.model.PlayerAction;
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
public class JdbcPlayerActionDao implements PlayerActionDao {

    private final String PLAYER_ACTION_SELECT = "SELECT * FROM player_action ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayerActionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PlayerAction getActionById(int actionId) {
        try {
            String sql = PLAYER_ACTION_SELECT + "WHERE action_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, actionId);
            if (rowSet.next()) {
                return mapRowToPlayerAction(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;
    }

    @Override
    public List<PlayerAction> getActionsByPlayerId(int playerId) {
        List<PlayerAction> actions = new ArrayList<>();
        try {
            String sql = PLAYER_ACTION_SELECT + "WHERE player_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, playerId);
            while (rowSet.next()) {
                actions.add(mapRowToPlayerAction(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return actions;
    }

    @Override
    public List<PlayerAction> getAllActionsInSession(int sessionId) {
        List<PlayerAction> actions = new ArrayList<>();
        try {
            String sql = PLAYER_ACTION_SELECT + "WHERE session_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, sessionId);
            while (rowSet.next()) {
                actions.add(mapRowToPlayerAction(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return actions;
    }

    @Override
    public PlayerAction createAction(PlayerAction playerAction) {
        try {
            String sql = "INSERT INTO player_action WHERE (session_id, player_id, action_type, " +
                    "amount, action_time, action_reverted) VALUES (?, ?, ?, ?, ?, ?)";
            int actionId = jdbcTemplate.queryForObject(sql, int.class, playerAction.getSessionId(),
                    playerAction.getPlayerId(), playerAction.getActionType(), playerAction.getAmount(),
                    playerAction.getActionTime(), playerAction.isActionReverted());
            return getActionById(actionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating a player action " + playerAction, e);
        }
    }

    @Override
    public PlayerAction revertAction(int actionId) {
        try {
            String sql = "UPDATE player_action SET action_reverted = true WHERE action_id = ?";
            jdbcTemplate.update(sql, actionId);
            return getActionById(actionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating a player action with ID: " + actionId, e);
        }
    }

//    @Override
//    public PlayerAction updateAction(PlayerAction playerAction) {
//        try {
//            String sql = "UPDATE player_action SET session_id = ?, player_id = ?, action_type = ?, " +
//                    "amount = ?, action_time = ?, action_reverted = ? WHERE action_id = ?";
//            jdbcTemplate.update(sql, playerAction.getSessionId(), playerAction.getPlayerId(),
//                    playerAction.getActionType(), playerAction.getAmount(), playerAction.getActionTime(),
//                    playerAction.isActionReverted(), playerAction.getActionId());
//            return getActionById(playerAction.getActionId());
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Error connecting to the server " + e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Error updating a player action " + playerAction, e);
//        }
//    }

    public static PlayerAction mapRowToPlayerAction(SqlRowSet rowSet) {
        int actionId = rowSet.getInt("action_id");
        int sessionId = rowSet.getInt("session_id");
        int playerId = rowSet.getInt("player_id");
        String actionType = rowSet.getString("action_type"); // Score or Health
        int amount = rowSet.getInt("amount");; // (+/- Score/Health)
        LocalDateTime actionTime = rowSet.getTimestamp("action_time").toLocalDateTime();
        boolean actionReverted = rowSet.getBoolean("action_reverted");
        return new PlayerAction(actionId, sessionId, playerId, actionType, amount, actionTime, actionReverted);
    }

}
