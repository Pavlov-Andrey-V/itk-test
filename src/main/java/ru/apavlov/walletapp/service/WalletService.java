package ru.apavlov.walletapp.service;

import org.springframework.stereotype.Service;
import ru.apavlov.walletapp.controller.dto.WalletBalanceDTO;
import ru.apavlov.walletapp.service.entity.OperationTypeEnum;
import ru.apavlov.walletapp.service.entity.WalletBalance;
import ru.apavlov.walletapp.service.entity.WalletOperation;
import ru.apavlov.walletapp.service.exception.NotEnoughBalanceException;
import ru.apavlov.walletapp.service.exception.WalletNotFoundException;
import ru.apavlov.walletapp.repository.WalletRepository;

import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void changeBalance(WalletOperation walletOperation) {
        getWalletBalanceWithCheck(walletOperation.getId());
        switch(walletOperation.getOperationType()) {
            case WITHDRAW -> {
                boolean updateIsSuccess = walletRepository.withdrawBalance(walletOperation.getId(), walletOperation.getAmount());
                if (!updateIsSuccess) {
                    throw new NotEnoughBalanceException("Not enough balance");
                }
            }
            case DEPOSIT ->
                walletRepository.depositBalance(walletOperation.getId(), walletOperation.getAmount());
        }
    }

    public WalletBalance getBalance(UUID walletUuid) {
        return getWalletBalanceWithCheck(walletUuid);
    }

    private WalletBalance getWalletBalanceWithCheck(UUID walletUuid) {
        WalletBalance currentWalletBalance = walletRepository.getWalletBalance(walletUuid);
        if (currentWalletBalance == null) {
            throw new WalletNotFoundException("Wallet not found. ID: " + walletUuid);
        }
        return currentWalletBalance;
    }
}
