package ru.apavlov.walletapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String EXISTING_WALLET = "13b44463-22a8-4569-b05b-6707894160e2";
    private static final String LOW_BALANCE_WALLET = "bb78c379-a2a9-4983-86c0-b87e54e2cfd1";

    @Test
    public void testGetWalletIsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/wallets/13b44463-22a8-4569-b05b-6707894160e6"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorType").value("WalletNotFoundException"));
    }

    @Test
    public void testGetWalletIsOk() throws Exception {
        mockMvc.perform(get("/api/v1/wallets/" + EXISTING_WALLET))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(EXISTING_WALLET))
                .andExpect(jsonPath("$.balance").isNumber());
    }

    @Test
    public void testPostWalletIsNotFound() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"bb78c379-a2a9-4983-86c0-b87e54e2cfd8\",\"operationType\":\"DEPOSIT\",\"amount\":6000}")
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorType").value("WalletNotFoundException"));
    }

    @Test
    public void testPostWithdrawNotEnoughBalance() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + LOW_BALANCE_WALLET + "\",\"operationType\":\"WITHDRAW\",\"amount\":6000}")
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough balance"))
                .andExpect(jsonPath("$.errorType").value("NotEnoughBalanceException"));
    }

    @Test
    public void testPostWithdrawNegativeAmount() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + LOW_BALANCE_WALLET + "\",\"operationType\":\"WITHDRAW\",\"amount\":-6000}")
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorType").value("MethodArgumentNotValidException"));
    }

    @Test
    public void testPostDepositToNonExistentWallet() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"13b44463-22a8-4569-b05b-6707894160e6\",\"operationType\":\"DEPOSIT\",\"amount\":100}")
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorType").value("WalletNotFoundException"));
    }

    @Test
    public void testFullCycle() throws Exception {
        mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"walletId\":\"" + LOW_BALANCE_WALLET + "\",\"operationType\":\"DEPOSIT\",\"amount\":500}")
        ).andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/wallets/" + LOW_BALANCE_WALLET))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(600));

        mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"walletId\":\"" + LOW_BALANCE_WALLET + "\",\"operationType\":\"WITHDRAW\",\"amount\":50}")
        ).andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/wallets/" + LOW_BALANCE_WALLET))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(550));
    }
}
