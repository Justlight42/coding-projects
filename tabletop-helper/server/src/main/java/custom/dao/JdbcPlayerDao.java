package custom.dao;

import custom.exception.DaoException;
import custom.model.Player;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlayerDao implements PlayerDao {

    private final String PLAYER_SELECT = "SELECT * FROM player ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            String sql = PLAYER_SELECT + "WHERE player_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, playerId);
            if (rowSet.next()) {
                return mapRowToPlayer(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;
    }

    @Override
    public List<Player> getAllPlayersBySessionId(int sessionId) {
        List<Player> players = new ArrayList<>();
        try {
            String sql = PLAYER_SELECT + "JOIN player_action pa ON player.player_id = pa.player_id JOIN sessions se ON pa.session_id = se.session_id WHERE se.session_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, sessionId);
            while (rowSet.next()) {
                players.add(mapRowToPlayer(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return players;
    }

    @Override
    public Player createPlayer(Player player) {
        try {
            String sql = "INSERT INTO player (team_id, user_id, player_name, health, score) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING player_id";
            int playerId = jdbcTemplate.queryForObject(sql, int.class, player.getTeamId(), player.getUserId(),
                    player.getName(), player.getHealth(), player.getScore());
            return getPlayerById(playerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating a player " + player, e);
        }
    }

    @Override
    public Player updatePlayer(Player player) {
        try {
            String sql = "UPDATE player SET team_id = ?, user_id = ?, player_name = ?, " +
                    "health = ?, score = ? WHERE player_id = ?";
            jdbcTemplate.update(sql, player.getTeamId(), player.getUserId(), player.getName(),
                    player.getHealth(), player.getScore(), player.getPlayerId());
            return getPlayerById(player.getPlayerId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating a player " + player, e);
        }
    }

    @Override
    public int deletePlayer(int playerId) {
        try {
            String sqlPlayer = "DELETE FROM player WHERE player_id = ?";
            String sqlPlayerAction = "DELETE FROM player_action WHERE player_id = ?";
            jdbcTemplate.update(sqlPlayerAction, playerId);
            return jdbcTemplate.update(sqlPlayer, playerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting a player with ID: " + playerId, e);
        }
    }

    public static Player mapRowToPlayer(SqlRowSet rowSet) {
        int playerId = rowSet.getInt("player_id");
        Integer teamId = checkIfNull(rowSet, "team_id");
        Integer userId = checkIfNull(rowSet, "user_id");
        String name = rowSet.getString("player_name");
        Integer health = checkIfNull(rowSet, "health");;
        Integer score =checkIfNull(rowSet, "score");;
        return new Player(playerId, teamId, userId, name, health, score);
    }

    public static Integer checkIfNull(SqlRowSet rowSet, String name) {
        int value = rowSet.getInt(name);
        return rowSet.wasNull() ? null : value;
    }

}
