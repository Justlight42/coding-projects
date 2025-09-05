package custom.dao;

import custom.exception.DaoException;
import custom.model.Subscription;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSubscriptionDao implements SubscriptionDao {

    private final JdbcTemplate jdbcTemplate;
    private final String SUB_SQL = "SELECT * FROM subscriptions ";

    public JdbcSubscriptionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Subscription getSubById(int subId) {
        try {
            String sql = SUB_SQL + "WHERE sub_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, subId);
            if (rowSet.next()) {
                return mapToSubRowSet(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public List<Subscription> getAllSubsByUserId(int userId, boolean orderByDate) {
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            String sql = SUB_SQL + "WHERE user_id = ? ";
            if (orderByDate) {
                sql += "ORDER BY next_billing_date ASC";
            }
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                subscriptions.add(mapToSubRowSet(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return subscriptions;
    }

    @Override
    public Subscription createSub(Subscription sub) {
        try {
            String sql = "INSERT INTO subscriptions (user_id, sub_name, cost, billing_cycle, next_billing_date, site_url) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING sub_id";
            int subId = jdbcTemplate.queryForObject(sql, int.class, sub.getUserId(), sub.getSubName(),
                    sub.getCost(), sub.getBillingCycle(), sub.getNextBilling(), sub.getSiteURL());
            return getSubById(subId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Subscription updateSub(Subscription sub) {
        try {
            String sql = "UPDATE subscriptions SET user_id = ?, sub_name = ?, cost = ?, billing_cycle = ?, " +
                    "next_billing_date = ?, site_url = ? WHERE sub_id = ?";
            jdbcTemplate.update(sql, sub.getUserId(), sub.getSubName(), sub.getCost(), sub.getBillingCycle(),
                    sub.getNextBilling(), sub.getSiteURL(), sub.getSubId());
            return getSubById(sub.getSubId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Subscription updateBillingDate(int subId, LocalDate updatedDate) {
        try {
            String sql = "UPDATE subscriptions SET next_billing_date = ? WHERE sub_id = ?";
            int rowsAffected = jdbcTemplate.update(sql, updatedDate, subId);
            if (rowsAffected > 0) {
                return getSubById(subId);
            } else {
                throw new DaoException("No subscription found with the ID " + subId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public int deleteSub(int subId) {
        try {
            String sql = "DELETE FROM subscriptions WHERE sub_id = ?";
            return jdbcTemplate.update(sql, subId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<Subscription> getSubsNeedRefresh() {
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            String sql = SUB_SQL + "WHERE next_billing_date <= ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, LocalDate.now());
            while (rowSet.next()) {
                subscriptions.add(mapToSubRowSet(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return List.of();
    }

    public static Subscription mapToSubRowSet(SqlRowSet rowSet) {
        int subId = rowSet.getInt("sub_id");
        int userId = rowSet.getInt("user_id");
        String subName = rowSet.getString("sub_name");
        BigDecimal cost = rowSet.getBigDecimal("cost");
        String billingCycle = rowSet.getString("billing_cycle");
        LocalDate nextBilling = rowSet.getDate("next_billing_date").toLocalDate();
        String siteURL = rowSet.getString("site_url");
        return new Subscription(subId, userId, subName, cost, billingCycle, nextBilling, siteURL);
    }

}
