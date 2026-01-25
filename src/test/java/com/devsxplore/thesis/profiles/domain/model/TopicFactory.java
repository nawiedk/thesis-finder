package com.devsxplore.thesis.profiles.domain.model;

import java.util.List;


//factory mother
public class TopicFactory {
    public static List<Topic> createBatFamily(){
        Topic topic1 = new TopicBuilder()
                .addTitle("Bruce Wayne")
                .addDescription("Batman")
                .addId(1L)
                .build();

        Topic topic2 = new TopicBuilder()
                .addTitle("Alfred Pennyworth")
                .addDescription("Butler")
                .addId(1L)
                .build();

        Topic topic3 = new TopicBuilder()
                .addTitle("Dick Grayson")
                .addDescription("Nightwing")
                .addId(1L)
                .build();

        Topic topic4 = new TopicBuilder()
                .addTitle("Barbara Gordon")
                .addDescription("Batgirl")
                .addId(1L)
                .build();

        return List.of(topic1,topic2,topic3,topic4);
    }

    public static List<Topic> createBruceAndAlfred(){
        Topic topic1 = new TopicBuilder()
                .addTitle("Bruce Wayne")
                .addDescription("Batman")
                .addId(1L)
                .build();

        Topic topic2 = new TopicBuilder()
                .addTitle("Alfred Pennyworth")
                .addDescription("Butler")
                .addId(1L)
                .build();

        return List.of(topic1,topic2);
    }

    public static Topic createBruce(){
        return new TopicBuilder()
                .addTitle("Bruce Wayne")
                .addDescription("Batman")
                .addId(1L)
                .build();
    }
}
