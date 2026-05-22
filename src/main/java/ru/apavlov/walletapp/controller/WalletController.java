package ru.apavlov.walletapp.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.apavlov.walletapp.controller.dto.WalletBalanceDTO;
import ru.apavlov.walletapp.controller.dto.WalletOperationDTO;
import ru.apavlov.walletapp.service.WalletService;
import ru.apavlov.walletapp.service.entity.WalletBalance;
import ru.apavlov.walletapp.service.entity.WalletOperation;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(path = "/wallet")
    public void changeWalletBalance(@Validated @RequestBody WalletOperationDTO walletDTO) {
        walletService.changeBalance(
                new WalletOperation(walletDTO.getWalletId(), walletDTO.getOperationType(), walletDTO.getAmount())
        );
    }

    @GetMapping(path = "/wallets/{walletUuid}")
    public WalletBalanceDTO walletBalance(@PathVariable UUID walletUuid) {
        WalletBalance balance = walletService.getBalance(walletUuid);
        return new WalletBalanceDTO(balance.getId(), balance.getBalance());
    }
}
