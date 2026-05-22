package ru.apavlov.walletapp.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletBalance {
    private UUID id;
    private Long balance;
}
