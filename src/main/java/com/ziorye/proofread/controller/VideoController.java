package com.ziorye.proofread.controller;

import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VideoController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("/videos")
    String index(Model model,
                 @RequestParam("page") Optional<Integer> page,
                 @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Collection> pageContent = collectionService.findAllPublishedVideos(currentPage, pageSize);
        model.addAttribute("page", pageContent);
        return "collection/video/index";
    }
}
