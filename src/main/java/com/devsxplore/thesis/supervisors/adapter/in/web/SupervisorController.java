package com.devsxplore.thesis.supervisors.adapter.in.web;

import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper.generateSupervisorDeleteCommand;
import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper.generateSupervisorUpdateCommand;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.devsxplore.thesis.accounts.adapter.in.security.CurrentUser;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.response.SupervisorProfileDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.FieldTagAddDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorResponseMapper;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.FieldAddCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.LoadByPublicIdCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.LoadByUserIdCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.SupervisorCreateCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicShowListUseCase;
import com.devsxplore.thesis.supervisors.domain.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

	private final SupervisorRequestMapper requestMapper;

	private final SupervisorResponseMapper responseMapper;

	private final SupervisorCreateUseCase supervisorCreateUseCase;

	private final SupervisorUpdateUseCase updateByUserIdUseCase;

	private final SupervisorShowAllUseCase supervisorShowAllUseCase;

	private final SupervisorDeleteUseCase deleteByUserIdUseCase;

	private final TopicShowListUseCase topicListUseCase;

	private final FieldAddUseCase fieldAddUseCase;

	private final LoadByUserIdUseCase loadByUserIdUseCase;

	private final LoadByPublicIdUseCase loadByPublicIdUseCase;

	// TODO:@ControllerAdvice aus Woche 4 Exceptions etc machen

	@GetMapping
	public String showStartPage(@AuthenticationPrincipal CurrentUser user, Model model) {
		ShowTopicListCommand command = new ShowTopicListCommand(user.githubId());
		List<Topic> topics = this.topicListUseCase.loadTopicsBySupervisor(command);
		model.addAttribute("topics", topics);
		return "supervisor/supervisormenu";
	}

	@GetMapping("/test")
	public String test(@AuthenticationPrincipal CurrentUser user) {
		this.supervisorCreateUseCase.createSupervisor(new SupervisorCreateCommand(user.githubId(),
				new Name("Nawied", "Khaleqi", AcademicTitle.NONE),
				Contact.contactFromPrimitive("nawied@hhu.de", "24", "0123456789")));
		return "redirect:/supervisor/all";
	}

	@GetMapping(("/profile"))
	public String showProfile(@AuthenticationPrincipal CurrentUser user, Model model) {
		Supervisor supervisor = this.loadByUserIdUseCase.loadByUserId(new LoadByUserIdCommand(user.githubId()));
		model.addAttribute("supervisor", this.responseMapper.mapToSupervisorProfile(supervisor));
		return "supervisor/profile";
	}

	@GetMapping("/register")
	public String showRegisterSupervisorForm() {
		return "supervisor/supervisor-register";
	}

	@PostMapping("/register")
	public String createSupervisor(@AuthenticationPrincipal CurrentUser user, @Valid SupervisorCreateDTO dto) {
		this.supervisorCreateUseCase.createSupervisor(generateSupervisorUpdateCommand(user.githubId(), dto));
		return "redirect:/supervisor/profile";
	}

	@GetMapping("/all")
	public String loadAllSupervisors(Model model) {
		Set<SupervisorProfileDTO> supervisors = this.responseMapper.mapToSupervisorProfiles(
				this.supervisorShowAllUseCase.showAllSupervisors()
		);
		model.addAttribute("supervisors", supervisors);
		return "supervisor/show-all";
	}

	@GetMapping("/{uuid}")
	public String loadSupervisorById(@PathVariable("uuid") UUID publicId, Model model) {
		Supervisor supervisor = this.loadByPublicIdUseCase.load(new LoadByPublicIdCommand(publicId));
		model.addAttribute("supervisor", this.responseMapper.mapToSupervisorProfile(supervisor));
		return "supervisor/supervisor-profile";
	}

	@PutMapping("/update")
	public String updateProfile(@AuthenticationPrincipal CurrentUser user, @Valid SupervisorUpdateDTO dto) {
		this.updateByUserIdUseCase.update(generateSupervisorUpdateCommand(user.githubId(), dto));
		return "redirect:/supervisor/profile";
	}

	@DeleteMapping("/delete")
	public String deleteProfile(@AuthenticationPrincipal CurrentUser user) {
		this.deleteByUserIdUseCase.delete(generateSupervisorDeleteCommand(user.githubId()));
		return "redirect:/";
	}

	@GetMapping("/topicform")
	public String showForm() {
		return "topicerstellen";
	}

	@PostMapping("/fields/{id}")
	@ResponseBody
	public void addFieldToProfile(@PathVariable("id") Long supervisorId, FieldTagAddDTO dto) {
		this.fieldAddUseCase.addFieldToSupervisor(new FieldAddCommand(supervisorId, dto.fieldName()));
	}

}
