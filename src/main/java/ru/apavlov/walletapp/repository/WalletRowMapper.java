package ru.apavlov.walletapp.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.apavlov.walletapp.service.entity.WalletBalance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WalletRowMapper implements RowMapper<WalletBalance> {
    @Override
    public WalletBalance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WalletBalance(
                UUID.fromString(rs.getString("id")),
                rs.getLong("balance")
        );
    }
}
