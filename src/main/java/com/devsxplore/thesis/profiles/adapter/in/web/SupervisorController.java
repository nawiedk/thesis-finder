package com.devsxplore.thesis.profiles.adapter.in.web;

import com.devsxplore.thesis.profiles.adapter.in.web.dto.TopicForm;
import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.usecase.CreateSupervisorUseCase;
import com.devsxplore.thesis.profiles.application.port.in.usecase.CreateTopicUseCase;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final CreateSupervisorUseCase supervisorUseCase;
    private final CreateTopicUseCase topicUseCase;

    @GetMapping("/")
    public String showForm() {
        return "topiccreate";
    }

    @ResponseBody
    @PostMapping("/create")
    public String createSupervisor() {
//        CreateSupervisorCommand command = new CreateSupervisorCommand(null, null);
//        Supervisor supervisor = useCase.createSupervisor(command);
//        return "Supervisor ID: " + supervisor.getId();
        return "Supervisor ID: ";
    }


    @PostMapping("/createTopic")
    public String createTopic(TopicForm form, RedirectAttributes redirectAttributes){
        CreateTopicCommand command = new CreateTopicCommand(1L, form.getTopic(), form.getDescription());
        Topic topic = topicUseCase.createTopic(command);
        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
        return "redirect:/";
    }

}
