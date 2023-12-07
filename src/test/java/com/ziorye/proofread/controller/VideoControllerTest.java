package com.ziorye.proofread.controller;

import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.repository.CollectionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    CollectionRepository collectionRepository;

    private static final String COLLECTION_TYPE = "video";

    @Test
    void index() throws Exception {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Collection collection = new Collection();
            collection.setTitle(UUID.randomUUID().toString());
            collection.setSlug(UUID.randomUUID().toString());
            collection.setType(COLLECTION_TYPE);
            collection.setPublished(true);
            collection.setUser(new User(1L));
            collectionRepository.save(collection);
            ids.add(collection.getId());
        }

        String currentPageNumber = "2";
        mvc.perform(MockMvcRequestBuilders
                        .get("/" +  COLLECTION_TYPE + "s")
                        .param("page", currentPageNumber)
                        .param("size", "1")
                )
                .andExpect(MockMvcResultMatchers.view().name("collection/" + COLLECTION_TYPE + "/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("page"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("当前第 " + currentPageNumber + " 页")))
        ;

        collectionRepository.deleteAllById(ids);
    }
}