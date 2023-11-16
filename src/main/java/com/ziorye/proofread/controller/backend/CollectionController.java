package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.CollectionDto;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/backend/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("")
    String index(Model model,
                 @RequestParam("page") Optional<Integer> page,
                 @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Collection> pageContent = collectionService.findAll(currentPage, pageSize);
        model.addAttribute("page", pageContent);
        return "backend/collection/index";
    }

    @DeleteMapping("destroy/{id}")
    String destroy(@PathVariable Long id) {
        collectionService.destroy(id);
        return "redirect:/backend/collections";
    }

    @DeleteMapping("destroy")
    @ResponseBody
    String destroyBatch(@RequestParam(value = "ids[]") List<Long> ids) {
        collectionService.destroyAllById(ids);
        return "DONE";
    }

    @GetMapping("create")
    String create(Model model) {
        model.addAttribute("collection", new Collection());
        return "backend/collection/create";
    }

    @PostMapping("store")
    String store(@Valid @ModelAttribute("collection") CollectionDto collectionDto,
                 BindingResult result) {
        if (result.hasErrors()) {
            return "backend/collection/create";
        }
        collectionService.save(collectionDto);
        return "redirect:/backend/collections";
    }
}
