package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Team;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTeamDao implements TeamDao {

    private final String TEAM_SELECT = "SELECT * FROM team ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcTeamDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Team getTeamId(int teamId) {
        try {
            String sql = TEAM_SELECT + "WHERE team_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, teamId);
            if (rowSet.next()) {
                return mapRowToTeam(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;
    }

    @Override
    public List<Team> getTeamsBySessionId(int sessionId) {
        List<Team> teams = new ArrayList<>();
        try {
            String sql = TEAM_SELECT + "WHERE session_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, sessionId);
            while (rowSet.next()) {
                teams.add(mapRowToTeam(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return teams;
    }

    @Override
    public Team createTeam(Team team) {
        try {
            if (teamNameExist(team.getSessionId(), team.getTeamName())) {
                throw new DaoException("Can not update that team name because it already exist in your session");
            }
            String sql = "INSERT INTO team (session_id, team_name) VALUES (?, ?) RETURNING team_id";
            int teamId = jdbcTemplate.queryForObject(sql, int.class, team.getSessionId(), team.getTeamName());
            return getTeamId(teamId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating a team " + team, e);
        }
    }

    @Override
    public Team updateTeamName(Team team) {
        try {
            if (teamNameExist(team.getSessionId(), team.getTeamName())) {
                throw new DaoException("Can not update that team name because it already exist in your session");
            }
            String sql = "UPDATE team SET session_id = ?, team_name = ? WHERE team_id = ?";
            jdbcTemplate.update(sql, team.getSessionId(), team.getTeamName(), team.getTeamId());
            return getTeamId(team.getTeamId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating a team " + team, e);
        }
    }

    @Override
    public int deleteTeam(int teamId) {
        try {
            String sqlPlayer = "DELETE FROM player WHERE team_id = ?;";
            return jdbcTemplate.update(sqlPlayer, teamId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting team with ID: " + teamId, e);
        }
    }

    @Override
    public boolean teamNameExist(int sessionId, String teamName) {
        try {
            String sql = "SELECT COUNT(*) FROM team WHERE session_id = ? and team_name = ?";
            int dupe = jdbcTemplate.queryForObject(sql, int.class, sessionId, teamName);
            if (dupe >= 1) {
                throw new DaoException("There is already a team with the name: " + teamName);
            } else {
                return false;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataAccessException e) {
            throw new DaoException("Error checking if the team name already exist for the session ID: "
                    + sessionId + "and the team name: " + teamName);
        }
    }

    public static Team mapRowToTeam(SqlRowSet rowSet) {
        int teamId = rowSet.getInt("team_id");
        int sessionId = rowSet.getInt("session_id");
        String teamName = rowSet.getString("team_name");
        return new Team(teamId, sessionId, teamName);
    }

}
