package com.devsxplore.thesis.accounts.adapter.in.web;


import com.devsxplore.thesis.accounts.application.port.in.usecase.ListAccountsUseCase;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ListAccountsUseCase listAccountsUseCase;

    @GetMapping("/accounts")
    @ResponseBody
    public List<UserAccount> loadAllAccounts() {
        return listAccountsUseCase.loadAll();
    }

    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

