package com.devsxplore.thesis.supervisors.adapter.in.web;

import com.devsxplore.thesis.supervisors.adapter.in.web.dto.topic.TopicCreateDTO;
import com.devsxplore.thesis.supervisors.adapter.in.web.dto.topic.TopicUpdateDTO;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.CreateTopicCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicDeleteCommand;
import com.devsxplore.thesis.supervisors.application.port.in.command.topic.TopicUpdateCommand;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicCreateUseCase;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicDeleteUseCase;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicLoadUseCase;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicUpdateUseCase;
import com.devsxplore.thesis.supervisors.domain.model.Topic;
import jakarta.validation.Valid;
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
    public List<Topic> loadAllTopics() {
        return topicLoadUseCase.loadAllTopics();
    }

    @PostMapping("/create")
    @ResponseBody
    public Topic createTopic(@Valid TopicCreateDTO dto, RedirectAttributes redirectAttributes) {
        CreateTopicCommand command = new CreateTopicCommand(dto.supervisorId(), dto.title(), dto.description());
        Topic topic = topicCreateUseCase.createTopic(command);
        redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
        return topic;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public boolean deleteTopic(@PathVariable("id") Long topicId, RedirectAttributes redirectAttributes) {
        return topicDeleteUseCase.deleteTopic(new TopicDeleteCommand(topicId));
    }

    @PutMapping("/update/{id}")
    public Topic updateTopic(@PathVariable("id") Long topicId, @Valid TopicUpdateDTO dto) {
        TopicUpdateCommand command = new TopicUpdateCommand(dto.supervisorId(), topicId, dto.topic(), dto.description());
        return updateUseCase.updateTopic(command);
    }


}
