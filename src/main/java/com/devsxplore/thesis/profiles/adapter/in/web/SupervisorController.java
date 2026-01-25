package com.devsxplore.thesis.profiles.adapter.in.web;

import com.devsxplore.thesis.profiles.adapter.in.web.dto.SupervisorCreateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.TopicForm;
import com.devsxplore.thesis.profiles.application.port.in.command.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.*;
import com.devsxplore.thesis.profiles.domain.model.Supervisor;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.devsxplore.thesis.profiles.domain.model.Contact.contactFromPrimitive;
import static com.devsxplore.thesis.profiles.domain.model.Name.nameFromPrimitive;

@Controller
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final CreateSupervisorUseCase supervisorUseCase;
    private final CreateTopicUseCase topicUseCase;
    private final ShowTopicListUseCase topicListUseCase;
    private final EditTopicUseCase editTopicUseCase;
    private final SaveChangesTopicUseCase saveChangesTopicUseCase;


//TODO:@ControllerAdvice aus Woche 4 Exceptions etc machen


    @GetMapping("/")
    public String showStartPage(Model model){
        ShowTopicListCommand command = new ShowTopicListCommand(1L);
        List<Topic> topics = topicListUseCase.showTopicList(command);
        model.addAttribute("topics", topics);
        return "supervisormenu";
    }

    @GetMapping("/editTopic/{topicId}")
    public String editTopic(@PathVariable Long topicId, Model model){
        EditTopicCommand command = new EditTopicCommand(1L, topicId);
        Topic topic = editTopicUseCase.editTopic(command);
        TopicForm topicForm = new TopicForm(topic.getId(), topic.getTitle(), topic.getDescription());
        model.addAttribute("topic", topicForm);
        return "topicedit";
    }

    @PostMapping("/saveChange")
    public String saveChange(TopicForm form){
        SaveChangesTopicCommand command = new SaveChangesTopicCommand(1L, form.id(), form.topic(), form.description());
        saveChangesTopicUseCase.saveChangesToTopic(command);
        return "redirect:/supervisor/";

    }

    @GetMapping("/topicform")
    public String showForm() {
        return "topicerstellen";
    }

    @ResponseBody
    @PostMapping("/create")
    public String createSupervisor(SupervisorCreateDTO dto) {
        var name = nameFromPrimitive(
                dto.firstName(),
                dto.lastName(),
                dto.title()
        );
        var contactDetails = contactFromPrimitive(
                dto.email(),
                dto.office(),
                dto.phone()
        );
        CreateSupervisorCommand command = new CreateSupervisorCommand(name, contactDetails);
        Supervisor supervisor = supervisorUseCase.createSupervisor(command);
        return "Supervisor ID: " + supervisor.getId();
    }

    @PostMapping("/createTopic")
    public String createTopic(TopicForm form, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        CreateTopicCommand command = new CreateTopicCommand(1L, form.topic(), form.description());
        Topic topic = topicUseCase.createTopic(command);
        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
        return "redirect:/supervisor/";
    }

}
