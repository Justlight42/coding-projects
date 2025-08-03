package com.techelevator.custom.dao;

import com.techelevator.custom.model.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcTeamDao implements TeamDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTeamDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Team getTeamId(int teamId) {
        return null;
    }

    @Override
    public List<Team> getTeamBySessionId(int sessionId) {
        return List.of();
    }

    @Override
    public Team createTeam(int sessionId, String teamName) {
        return null;
    }

    @Override
    public Team updateTeamName(int teamId, String teamName) {
        return null;
    }

    @Override
    public int deleteTeam(int teamId) {
        return 0;
    }

    @Override
    public boolean teamNameExist(int sessionId, String teamName) {
        return false;
    }

}
