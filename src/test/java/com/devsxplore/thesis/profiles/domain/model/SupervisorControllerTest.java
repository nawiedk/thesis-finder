//package com.devsxplore.thesis.profiles.domain.model;
//
//import com.devsxplore.thesis.profiles.adapter.in.web.SupervisorController;
//import com.devsxplore.thesis.profiles.adapter.in.web.dto.TopicForm;
//import com.devsxplore.thesis.profiles.application.port.in.command.CreateTopicCommand;
//import com.devsxplore.thesis.profiles.application.service.SupervisorService;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultMatcher;
//
//import static com.devsxplore.thesis.profiles.domain.model.TopicFactory.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(SupervisorController.class)
//public class SupervisorControllerTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    //Für normale Tests ohne MockMvc zum probieren
//    @Autowired
//    SupervisorController controller;
//
//    @MockitoBean
//    SupervisorService service;
//
//    //Resultmatcher um wiederholte andExpect() aufrufe an einer Stelle zu haben
//    //nur zur übung
//    private ResultMatcher successfullWithAttribute(String attributeName){
//        return result -> {
//            model().attributeExists(attributeName).match(result);
//            status().is2xxSuccessful().match(result);
//        };
//    }
//
//    private ResultMatcher redirectWithView(String viewName){
//        return result -> {
//            view().name(viewName).match(result);
//            status().is3xxRedirection().match(result);
//        };
//    }
//
//    @Test
//    @DisplayName("If we go on /supervisor/ we get status 200")
//    void test_1() throws Exception {
//        mvc.perform(get("/supervisor/"))
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    @DisplayName("If we go on /supervisor/ we get supervisormenu view")
//    void test_2() throws Exception {
//        mvc.perform(get("/supervisor/"))
//                .andExpect(view().name("supervisormenu"));
//    }
//
//    @Test
//    @DisplayName("If we create title with parameters we get status 300 redirect")
//    void test_3() throws Exception {
//        mvc.perform(post("/supervisor/createTopic")
//                        .param("title", "Bruce Wayne")
//                        .param("description", "Batman"))
//                .andExpect(status().is3xxRedirection());
//    }
//
//
//    @Test
//    @DisplayName("Title of title correctly shown on start page")
//    void test_4() throws Exception {
//        when(service.showTopicList(any())).thenReturn(createBruceAndAlfred());
//        MvcResult result = mvc.perform(get("/supervisor/")).andReturn();
//        Document html = Jsoup.parse(result.getResponse().getContentAsString());
//        assertThat(html.select("table tr:has(td:contains(Bruce Wayne))")).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("Description of title correctly shown on start page")
//    void test_5() throws Exception {
//        when(service.showTopicList(any())).thenReturn(createBruceAndAlfred());
//        MvcResult result = mvc.perform(get("/supervisor/")).andReturn();
//        Document html = Jsoup.parse(result.getResponse().getContentAsString());
//        assertThat(html.select("table tr:has(td:contains(Batman))")).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("If we go on /supervisor/ we get status supervisormenu view")
//    void test_6() throws Exception {
//        mvc.perform(get("/supervisor/"))
//                .andExpect(view().name("supervisormenu"));
//    }
//
//    @Test
//    @DisplayName("If we post on /supervisor/createTopic with parameters createTopic of service with same parameters is invoked")
//    void test_7() throws Exception {
//        mvc.perform(post("/supervisor/createTopic")
//               .param("title", "Bruce Wayne")
//                .param("description", "Batman"));
//
//        verify(service)
//                .createTopic(argThat(command -> command.title().equals("Bruce Wayne")
//                        && command.description().equals("Batman")));
//
//    }
//
//    @Test
//    @DisplayName("When clicking edit on any title the status is 200")
//    void test_8() throws Exception {
//        Topic topic = createBruce();
//        when(service.updateTopic(any())).thenReturn(topic);
//
//        mvc.perform(get("/supervisor/editTopic/2"))
//                .andExpect(successfullWithAttribute("title"));
//
//    }
//
//    @Test
//    @DisplayName("When clicking edit on any title the topicedit view is shown")
//    void test_9() throws Exception {
//        Topic topic = createBruce();
//        when(service.updateTopic(any())).thenReturn(topic);
//
//        mvc.perform(get("/supervisor/editTopic/2"))
//                .andExpect(view().name("topicedit"));
//    }
//
//    @Test
//    @DisplayName("When clicking edit on any title an attribute called 'title' exists")
//    void test_10() throws Exception {
//        Topic topic = createBruce();
//        when(service.updateTopic(any())).thenReturn(topic);
//
//        mvc.perform(get("/supervisor/editTopic/2"))
//                .andExpect(model().attributeExists("title"));
//    }
//
//    @Test
//    @DisplayName("When there is a get to editTopic/1 the editTopic of the service is invoked with the correct parameters ")
//    void test_11() throws Exception {
//        when(service.updateTopic(any())).thenReturn(createBruce());
//        mvc.perform(get("/supervisor/editTopic/1"));
//        verify(service)
//                .updateTopic(argThat(command -> command.supervisorId().equals(1L) && command.topicId().equals(1L)));
//    }
//
//    @Test
//    @DisplayName("If saveChange is invoked status is 300 redirect")
//    void test_12() throws Exception {
//        mvc.perform(post("/supervisor/saveChange"))
//                .andExpect(status().is3xxRedirection());
//    }
//
//    @Test
//    @DisplayName("If saveChange is invoked with parameters saveChangesToTopic in service is invoked with same parameters")
//    void test_13() throws Exception {
//        mvc.perform(post("/supervisor/saveChange")
//                .param("id", "1")
//                .param("title", "Bruce Wayne")
//                .param("description", "Batman"));
//
//        verify(service)
//                .saveChangesToTopic
//                        (argThat(command -> command.supervisorId().equals(1L)
//                                && command.topicId().equals(1L)
//                                && command.title().equals("Bruce Wayne")
//                                && command.description().equals("Batman")));
//    }
//
//    @Test
//    @DisplayName("Same test as 13 but without using MockMvc")
//    void test_14() throws Exception {
//        TopicForm form = mock(TopicForm.class);
//        when(form.id()).thenReturn(1L);
//        when(form.topic()).thenReturn("Bruce Wayne");
//        when(form.description()).thenReturn("Batman");
//
//        controller.saveChange(form);
//
//        verify(service)
//                .saveChangesToTopic(argThat(command -> command.topicId().equals(1L)
//                        && command.title().equals("Bruce Wayne")
//                        && command.description().equals("Batman")));
//    }
//
//    @Test
//    @DisplayName("If get request at /supervisor/topicform is made topiform view is shown and status is 200")
//    void test_15() throws Exception {
//        mvc.perform(get("/supervisor/topicform"))
//                .andExpect(view().name("topicerstellen"))
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    @DisplayName("If get request at /supervisor/topicform is made a form with method = 'post' exists")
//    void test_16() throws Exception {
//        MvcResult result = mvc.perform(get("/supervisor/topicform")).andReturn();
//        //Jsoup
//        Document html = Jsoup.parse(result.getResponse().getContentAsString());
//        assertThat(html.select("form[method=post]")).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("If get request at /supervisor/topicform is made a form exists with inputs 'id', 'title', 'description'")
//    void test_17() throws Exception {
//        MvcResult result = mvc.perform(get("/supervisor/topicform")).andReturn();
//        //Jsoup
//        Document html = Jsoup.parse(result.getResponse().getContentAsString());
//        assertThat(html.select("input[name=title],[name=description],[name=id]")).hasSize(3);
//    }
//}
