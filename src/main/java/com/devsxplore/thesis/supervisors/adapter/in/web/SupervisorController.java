package com.devsxplore.thesis.supervisors.adapter.in.web;

import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.FieldTagAddDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.supervisors.application.port.in.command.supervisor.FieldAddCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicShowListUseCase;
import com.devsxplore.thesis.supervisors.domain.model.Supervisor;
import com.devsxplore.thesis.supervisors.domain.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.request.SupervisorRequestMapper.generateSupervisorUpdateCommand;
import static com.devsxplore.thesis.supervisors.adapter.in.web.mapper.request.SupervisorRequestMapper.generateSupervisorDeleteCommand;

@Controller
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorCreateUseCase supervisorCreateUseCase;
    private final SupervisorUpdateUseCase supervisorUpdateUseCase;
    private final SupervisorShowAllUseCase supervisorShowAllUseCase;
    private final SupervisorDeleteUseCase supervisorDeleteUseCase;
    private final TopicShowListUseCase topicListUseCase;
    private final FieldAddUseCase fieldAddUseCase;


//TODO:@ControllerAdvice aus Woche 4 Exceptions etc machen


    @GetMapping("/")
    public String showStartPage(Model model) {
        ShowTopicListCommand command = new ShowTopicListCommand(1L);
        List<Topic> topics = topicListUseCase.loadTopicsBySupervisor(command);
        model.addAttribute("topics", topics);
        return "supervisormenu";
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Supervisor> loadAllSupervisors() {
        return supervisorShowAllUseCase.showAllSupervisors();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public List<Supervisor> loadSupervisorById(@PathVariable("id") Long supervisorId) {
        return supervisorShowAllUseCase.showAllSupervisors();
    }

    @ResponseBody
    @PostMapping("/create")
    public Supervisor createSupervisor(SupervisorCreateDTO dto) {
        return supervisorCreateUseCase.createSupervisor(
                generateSupervisorUpdateCommand(dto)
        );
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Supervisor updateProfile(@PathVariable("id") Long supervisorId, SupervisorUpdateDTO dto) {
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
    public void addFieldToProfile(@PathVariable("id") Long supervisorId, FieldTagAddDTO dto){
        fieldAddUseCase.addFieldToSupervisor(
                new FieldAddCommand(supervisorId, dto.fieldName())
        );
    }


}
