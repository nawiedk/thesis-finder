package com.devsxplore.thesis;

import com.devsxplore.thesis.profiles.application.port.in.usecase.CreateSupervisorUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final CreateSupervisorUseCase useCase;

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


//    @PostMapping("/sendtopic")
//    public String sendTopic(TopicForm form, RedirectAttributes redirectAttributes){
//        MyCommand command = new MyCommand(form.getTopic(), form.getDescription());
//        userCase.createTopic(command);
//        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
//        return "redirect:/";
//    }

}
