package com.devsxplore.thesis.profiles.adapter.in.web;

import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.TopicCreateDTO;
import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.TopicUpdateDTO;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.TopicDeleteCommand;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.TopicCreateUseCase;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.TopicDeleteUseCase;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.TopicLoadUseCase;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.TopicUpdateUseCase;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicCreateUseCase topicCreateUseCase;
    private final TopicLoadUseCase topicLoadUseCase;
    private final TopicDeleteUseCase topicDeleteUseCase;
    private final TopicUpdateUseCase updateUseCase;



    @GetMapping("/all")
    @ResponseBody
    public List<Topic> loadAllTopics(){
        return topicLoadUseCase.loadAllTopics();
    }

    @PostMapping("/create")
    @ResponseBody
    public Topic createTopic(TopicCreateDTO dto, RedirectAttributes redirectAttributes){
        CreateTopicCommand command = new CreateTopicCommand(dto.supervisorId(), dto.title(), dto.description());
        Topic topic = topicCreateUseCase.createTopic(command);
        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
        return topic;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public boolean deleteTopic(@PathVariable("id") Long topicId, RedirectAttributes redirectAttributes){
        return topicDeleteUseCase.deleteTopic(new TopicDeleteCommand(topicId));
    }

    @PutMapping("/update/{id}")
    public Topic updateTopic(@PathVariable("id") Long topicId, TopicUpdateDTO dto){
        TopicUpdateCommand command = new TopicUpdateCommand(dto.supervisorId(), topicId, dto.topic(), dto.description());
        return updateUseCase.updateTopic(command);
    }

    /*
    1.Controller methode erstellen
    2.Command erstellen
    3.Use Case aufrufen
    4.Service implementiert Usecase
    5.Service ruft port auf
    6.Adapter implementiert port
    7.adapter ruft repo auf
    8.Repository implementiert sql query
     */



//    @GetMapping("")
//    @ResponseBody
//    public String loadTopics(){
//        List<Topic> topics = loadTopicsUseCase.loadTopics();
//        return "";
//    }

//    @PostMapping("/create")
//    public String createTopic(TopicCreateDTO dto, RedirectAttributes redirectAttributes){
//        CreateTopicCommand command = new CreateTopicCommand(dto.supervisorId(), dto.title(), dto.description());
//        Topic title = createTopicUseCase.createTopic(command);
//        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
//        return "redirect:/supervisor/";
//    }

//    @DeleteMapping("/delete/{supervisorId}")
//    public String deleteTopic(@PathVariable Long supervisorId, RedirectAttributes redirectAttributes){
//        createTopicUseCase.deleteTopic(supervisorId);
//        redirectAttributes.addFlashAttribute("erfolgreichGeloescht", "Thema wurde gelöscht");
//        return "redirect:/supervisor/";
//    }

}
