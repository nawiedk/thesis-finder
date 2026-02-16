package com.devsxplore.thesis.supervisors.adapter.in.web;

import com.devsxplore.thesis.accounts.adapter.in.security.CurrentUser;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.FieldTagAddDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorResponseMapper;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.FieldAddCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.LoadByUserIdCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicShowListUseCase;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import com.devsxplore.thesis.supervisors.domain.model.Topic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper.generateSupervisorDeleteCommand;
import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper.generateSupervisorUpdateCommand;

@Controller
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorRequestMapper requestMapper;
    private final SupervisorResponseMapper responseMapper;
    private final SupervisorCreateUseCase supervisorCreateUseCase;
    private final SupervisorUpdateUseCase supervisorUpdateUseCase;
    private final SupervisorShowAllUseCase supervisorShowAllUseCase;
    private final SupervisorDeleteUseCase supervisorDeleteUseCase;
    private final TopicShowListUseCase topicListUseCase;
    private final FieldAddUseCase fieldAddUseCase;
    private final LoadByUserIdUseCase loadByUserIdUseCase;


//TODO:@ControllerAdvice aus Woche 4 Exceptions etc machen

    @GetMapping()
    public String showStartPage(@AuthenticationPrincipal CurrentUser user, Model model) {
        ShowTopicListCommand command = new ShowTopicListCommand(user.githubId());
        List<Topic> topics = topicListUseCase.loadTopicsBySupervisor(command);
        model.addAttribute("topics", topics);
        return "supervisor/supervisormenu";
    }

    @GetMapping(("/profile"))
    public String showProfile(@AuthenticationPrincipal CurrentUser user, Model model) {
        Supervisor supervisor = loadByUserIdUseCase.loadSupervisor(new LoadByUserIdCommand(user.githubId()));
        model.addAttribute("supervisor", responseMapper.mapToSupervisorProfileDTO(
                supervisor
        ));
        return "supervisor/profile";
    }

    @GetMapping("/register")
    public String showRegisterSupervisorForm() {
        return "supervisor/supervisor-register";
    }

    @PostMapping("/register")
    public String createSupervisor(@AuthenticationPrincipal CurrentUser user, @Valid SupervisorCreateDTO dto) {
        supervisorCreateUseCase.createSupervisor(
                generateSupervisorUpdateCommand(user.githubId(), dto)
        );
        return "redirect:/supervisor/profile";
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Supervisor> loadAllSupervisors() {
        return supervisorShowAllUseCase.showAllSupervisors();
    }

    @ResponseBody
    @GetMapping("/{uuid}")
    public List<Supervisor> loadSupervisorById(@PathVariable("uuid") Long supervisorId) {
        return supervisorShowAllUseCase.showAllSupervisors();
    }


    @PutMapping("/update/{id}")
    @ResponseBody
    public Supervisor updateProfile(@PathVariable("id") Long supervisorId, @Valid SupervisorUpdateDTO dto) {
        return supervisorUpdateUseCase.updateSupervisorProfile(
                generateSupervisorUpdateCommand(supervisorId, dto)
        );
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public boolean deleteProfile(@PathVariable("id") Long supervisorId) {
        return supervisorDeleteUseCase.deleteSupervisor(
                generateSupervisorDeleteCommand(supervisorId)
        );
    }

    @GetMapping("/topicform")
    public String showForm() {
        return "topicerstellen";
    }

    @PostMapping("/fields/{id}")
    @ResponseBody
    public void addFieldToProfile(@PathVariable("id") Long supervisorId, FieldTagAddDTO dto) {
        fieldAddUseCase.addFieldToSupervisor(
                new FieldAddCommand(supervisorId, dto.fieldName())
        );
    }


}
