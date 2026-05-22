package ru.apavlov.walletapp.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletOperation {
    private UUID id;
    private OperationTypeEnum operationType;
    private Long amount;
}
