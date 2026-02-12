package com.devsxplore.thesis.profiles.adapter.in.web;

import com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor.FieldTagAddDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor.SupervisorCreateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.supervisor.SupervisorUpdateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.TopicCreateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.TopicShowDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.TopicWebMapper;
import com.devsxplore.thesis.profiles.application.port.in.command.supervisor.FieldAddCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.ShowTopicListCommand;
import com.devsxplore.thesis.profiles.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.TopicShowListUseCase;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.devsxplore.thesis.profiles.adapter.in.web.mapper.request.SupervisorRequestMapper.generateSupervisorUpdateCommand;
import static com.devsxplore.thesis.profiles.adapter.in.web.mapper.request.SupervisorRequestMapper.generateSupervisorDeleteCommand;

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
        List<Topic> topicsDomain = topicListUseCase.loadTopicsBySupervisor(command);
        List<TopicShowDTO> topics = TopicWebMapper.toTopicShowDTOList(topicsDomain);
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

    @PostMapping("/fields/{id}")
    @ResponseBody
    public void addFieldToProfile(@PathVariable("id") Long supervisorId, FieldTagAddDTO dto){
        fieldAddUseCase.addFieldToSupervisor(
                new FieldAddCommand(supervisorId, dto.fieldName())
        );
    }


}
