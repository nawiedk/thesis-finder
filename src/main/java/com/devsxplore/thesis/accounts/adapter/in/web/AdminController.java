package com.devsxplore.thesis.accounts.adapter.in.web;

import com.devsxplore.thesis.accounts.adapter.in.security.CurrentUser;
import com.devsxplore.thesis.accounts.application.port.in.command.AssignUserRoleCommand;
import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.accounts.application.port.in.usecase.ListAccountsUseCase;
import com.devsxplore.thesis.accounts.domain.model.UserAccount;
import com.devsxplore.thesis.accounts.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private final ListAccountsUseCase listAccountsUseCase;

	private final AssignUserRoleUseCase assignUserRoleUseCase;

	@GetMapping("/accounts")
	@ResponseBody
	public List<UserAccount> loadAllAccounts() {
		return listAccountsUseCase.loadAll();
		// return "/admin/accounts";
	}

	@GetMapping("/self")
	@ResponseBody
	public CurrentUser loadSelf(@AuthenticationPrincipal CurrentUser user) {
		assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(user.githubId(), UserRole.ADMIN));
		return user;
		// return "/admin/accounts";
	}

	@ResponseBody
	@GetMapping("/")
	public String index() {

		return "index";
	}

	@PostMapping("/role")
	@ResponseBody
	public String assignRoleToSelf(@AuthenticationPrincipal CurrentUser user, @RequestParam("role") UserRole role) {
		assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(user.githubId(), role));
		return "redirect:/admin/accounts";
	}

	@PostMapping("/{githubId}/role")
	@ResponseBody
	public String assignRole(@PathVariable("githubId") Long githubId, @RequestParam("role") UserRole role,
			RedirectAttributes redirectAttributes) {
		assignUserRoleUseCase.assignRole(new AssignUserRoleCommand(githubId, role));
		redirectAttributes.addFlashAttribute("statusMessage", "Role updated.");
		return "redirect:/admin/accounts";
	}

}
