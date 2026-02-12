package com.devsxplore.thesis.profiles.adapter.in.web;

import com.devsxplore.thesis.profiles.adapter.in.web.dto.topic.*;
import com.devsxplore.thesis.profiles.application.port.in.command.topic.*;
import com.devsxplore.thesis.profiles.application.port.in.usecase.topic.*;
import com.devsxplore.thesis.profiles.domain.model.Topic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

@SessionAttributes("{topic}")
@Controller
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicCreateUseCase topicCreateUseCase;
    private final TopicLoadUseCase topicLoadUseCase;
    private final TopicDeleteUseCase topicDeleteUseCase;
    private final TopicUpdateUseCase updateUseCase;
    //ich benutze erstmal saveChanges wieder
    private final TopicSaveChangesUseCase saveChangesUseCase;

    //Controller Advice Errors dings da machen
//Supervisor ui machen für supervisorcontroller und resultbinding etc wiederholen
//mehr Topic attribute machen und dann HttpSession bzw Zustand benutzen
//wenn ich Topic attribute erweitere auch tabelle etc erweitern
//Locking einführen zb optimistisch mit @Transactional etc
//Aggregate Reference
//Derived Queries probieren
//gradle submodule???

    @GetMapping("/all")
    @ResponseBody
    public List<Topic> loadAllTopics(){
        return topicLoadUseCase.loadAllTopics();
    }

    @PostMapping(value = "/create", params = "create")
    public String createTopic(HttpSession session){
//        if(result.hasFieldErrors()){
//            return "topicerstellen";
//        } else {
//            CreateTopicCommand command = new CreateTopicCommand(dto.supervisorId(), dto.title(), dto.description());
//            topicCreateUseCase.createTopic(command);
//            status.setComplete();
//            redirectAttributes.addFlashAttribute("erfolgreichErstellt", "Thema wurde erstellt");
//            return "redirect:/supervisor/";
//        }

        //füge später wieder bindingresult hinzu

        TopicCreateDTO dto = (TopicCreateDTO) session.getAttribute("topic");
        CreateTopicCommand command = new CreateTopicCommand(dto.supervisorId(), dto.title(), dto.description(), dto.links(), dto.fields());
        topicCreateUseCase.createTopic(command);
        return "redirect:/supervisor/";



    }

    @PostMapping(value="/create", params="addLink")
    public String addLink(@RequestParam("links")String link, Model model, HttpSession session, RedirectAttributes redirectAttributes){
        TopicCreateDTO dto = (TopicCreateDTO) session.getAttribute("topic");
        dto.links().add(link);
        session.setAttribute("topic", dto);
        redirectAttributes.addFlashAttribute("topic", dto);
        return"redirect:/topic/create/nextStep";
    }

    @PostMapping(value="/create", params="addField")
    public String addReq(@RequestParam("field") String field, HttpSession session, RedirectAttributes redirectAttributes){
        TopicCreateDTO dto = (TopicCreateDTO) session.getAttribute("topic");
        dto.fields().add(field);
        session.setAttribute("topic", dto);
        redirectAttributes.addFlashAttribute("topic", dto);
        return "redirect:/topic/create/nextStep";
    }


    //hier werden die informationen aus dem ersten schritt nach titel und beschreibung in die http session gepackt und weitergegeben
    @PostMapping("/create/extra")
    public String extraTopicInformation(@ModelAttribute("topic") TopicCreateDTO dto, RedirectAttributes redirectAttributes,HttpSession session){
        session.setAttribute("topic", dto);
        redirectAttributes.addFlashAttribute("topic", dto);
        return "redirect:/topic/create/nextStep";
    }

    //hier wird das nächste formular mit den weiteren infos angezeigt
    //ich weiß verwirrende namensgebung
    @GetMapping("/create/nextStep")
    public String showExtraForm(@ModelAttribute("topic") TopicCreateDTO dto){
        return "topicExtraInformation";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public boolean deleteTopic(@PathVariable("id") Long topicId, RedirectAttributes redirectAttributes){
        return topicDeleteUseCase.deleteTopic(new TopicDeleteCommand(topicId));
    }

    @PostMapping("/update")
    public String updateTopic(@ModelAttribute("topic") @Valid TopicSaveChangesDTO dto, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasFieldErrors()){
            return "topicedit";
        }else{
            SaveChangesTopicCommand command = new SaveChangesTopicCommand(dto.supervisorId(), dto.topicId(), dto.title(), dto.description());
            saveChangesUseCase.saveChangesToTopic(command);
            redirectAttributes.addFlashAttribute("editSuccess","Thema wurde erfolgreich bearbeitet");
            return "redirect:/supervisor/";
        }
    }

    @GetMapping("/topicform")
    public String showCreateForm(Model model) {
        model.addAttribute("topic", new TopicCreateDTO(1L, "","",new HashSet<>(), new HashSet<>()));
        return "topicerstellen";
    }

//    @PutMapping("/update/{id}")
//    @ResponseBody
//    public Topic updateTopic(@PathVariable("id") Long topicId, TopicUpdateDTO dto){
//        TopicUpdateCommand command = new TopicUpdateCommand(dto.supervisorId(), topicId, dto.title(), dto.description());
//        return updateUseCase.updateTopic(command);
//    }

    @GetMapping("/edit/{supervisorId}/{topicId}")
    public String showEditForm(@PathVariable Long supervisorId, @PathVariable Long topicId, Model model){
        LoadSingleTopicCommand command = new LoadSingleTopicCommand(supervisorId, topicId);
        Topic topic = topicLoadUseCase.loadSingleTopicById(command);
        TopicSaveChangesDTO dto = TopicWebMapper.toSaveChangesDTO(topic, supervisorId);
        model.addAttribute("topic", dto);
        return "topicedit";
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
