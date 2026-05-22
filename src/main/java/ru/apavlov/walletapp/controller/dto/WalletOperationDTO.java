package ru.apavlov.walletapp.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.apavlov.walletapp.service.entity.OperationTypeEnum;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletOperationDTO {
    @NotNull
    private UUID walletId;
    @NotNull
    private OperationTypeEnum operationType;
    @NotNull
    @PositiveOrZero
    private Long amount;
}
