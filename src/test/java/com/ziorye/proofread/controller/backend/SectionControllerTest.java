package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.controller.WithMockUserForAdminBaseTest;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.Section;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.repository.CollectionRepository;
import com.ziorye.proofread.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

class SectionControllerTest extends WithMockUserForAdminBaseTest {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    SectionRepository sectionRepository;

    @Test
    void store() throws Exception {
        String collectionTitle = UUID.randomUUID().toString();
        Collection collection = new Collection();
        collection.setTitle(collectionTitle);
        collection.setSlug(UUID.randomUUID().toString());
        collection.setType("doc");
        collection.setUser(new User(1L));
        collectionRepository.save(collection);


        String sectionTitle = "title-" + UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders
                        .post("/backend/sections/store")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("title", sectionTitle)
                        .param("sortOrder", "8")
                        .param("collection_id", collection.getId().toString())
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections/edit/" + collection.getId()))
        ;

        Optional<Section> se = sectionRepository.findFirstByTitle(sectionTitle);
        Assertions.assertTrue(se.isPresent());
        sectionRepository.delete(se.get());

        Optional<Collection> co = collectionRepository.findFirstByTitle(collectionTitle);
        Assertions.assertTrue(co.isPresent());
        collectionRepository.delete(co.get());
    }

    @Test
    void update() throws Exception {
        String collectionTitle = UUID.randomUUID().toString();
        Collection collection = new Collection();
        collection.setTitle(collectionTitle);
        collection.setSlug(UUID.randomUUID().toString());
        collection.setType("doc");
        collection.setUser(new User(1L));
        collectionRepository.save(collection);

        String sectionTitle = UUID.randomUUID().toString();
        Section section = new Section();
        section.setTitle(sectionTitle);
        section.setCollection(new Collection(collection.getId()));
        sectionRepository.save(section);

        String illegalSortOrderValue = "100000";
        mvc.perform(MockMvcRequestBuilders.put("/backend/sections/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", section.getId().toString())
                        .param("title", section.getTitle())
                        .param("sortOrder", illegalSortOrderValue)
                        .param("collection_id", collection.getId().toString())
                )
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("section", "sortOrder", "Digits"))
        ;

        String descriptionUpdated = "description--updated";
        mvc.perform(MockMvcRequestBuilders.put("/backend/sections/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", section.getId().toString())
                        .param("title", section.getTitle())
                        .param("sortOrder", "0")
                        .param("description", descriptionUpdated)
                        .param("collection_id", collection.getId().toString())
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections/edit/" + collection.getId()))
        ;

        Section sectionUpdated = sectionRepository.findById(section.getId()).get();
        Assertions.assertEquals(descriptionUpdated, sectionUpdated.getDescription());

        sectionRepository.delete(section);
        collectionRepository.delete(collection);

        Assertions.assertTrue(sectionRepository.findById(section.getId()).isEmpty());
        Assertions.assertTrue(collectionRepository.findById(collection.getId()).isEmpty());
    }
}