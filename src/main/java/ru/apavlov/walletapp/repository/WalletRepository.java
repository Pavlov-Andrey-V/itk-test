package ru.apavlov.walletapp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.apavlov.walletapp.service.entity.WalletBalance;

import java.util.List;
import java.util.UUID;

@Repository
public class WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    private final WalletRowMapper walletRowMapper = new WalletRowMapper();

    public WalletRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean withdrawBalance(UUID walletId, Long amount) {
        int updated = jdbcTemplate.update(
                "UPDATE wallet_balances SET balance = balance - ? WHERE id = ? AND balance >= ?",
                amount, walletId, amount
        );
        return updated != 0;
    }

    public void depositBalance(UUID walletId, Long amount) {
        jdbcTemplate.update(
                "UPDATE wallet_balances SET balance = balance + ? WHERE id = ?",
                amount, walletId
        );
    }

    public WalletBalance getWalletBalance(UUID walletId) {
        List<WalletBalance> queryResult = jdbcTemplate.query("SELECT * FROM wallet_balances WHERE id = ?", walletRowMapper, walletId);
        if (queryResult.isEmpty()) {
            return null;
        }
        return queryResult.get(0);
    }
}
