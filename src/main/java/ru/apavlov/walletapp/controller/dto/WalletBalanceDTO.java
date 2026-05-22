package ru.apavlov.walletapp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletBalanceDTO {
    private UUID walletId;
    private Long balance;
}
