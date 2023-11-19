package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.LectureDto;
import com.ziorye.proofread.entity.Lecture;
import com.ziorye.proofread.service.CollectionService;
import com.ziorye.proofread.service.LectureService;
import com.ziorye.proofread.service.SectionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller("backendLectureController")
@RequestMapping("/backend/lectures/")
public class LectureController {
    @Autowired
    LectureService lectureService;
    @Autowired
    SectionService sectionService;
    @Autowired
    CollectionService collectionService;

    @Value("${custom.block.separator}")
    String blockSeparator;

    @GetMapping("create")
    String create(@RequestParam("collection_id") Long collectionId, @RequestParam("section_id") Long sectionId, Model model) {
        model.addAttribute("collection", collectionService.findById(collectionId).get());
        model.addAttribute("section", sectionService.findById(sectionId).get());
        model.addAttribute("lecture", new Lecture());
        return "backend/lecture/create";
    }

    @PostMapping("store")
    @Transactional
    public String store(@Valid @ModelAttribute("lecture") LectureDto lectureDto,
                        BindingResult result) {
        if (result.hasErrors()) {
            return "backend/lecture/create";
        }
        Lecture lecture = lectureService.save(lectureDto);
        if (lectureDto.getContent()!=null && lectureDto.getContent().contains(blockSeparator)) {
            lectureService.saveBlocks(lecture.getId(), lectureDto);
        }
        return "redirect:/backend/collections/edit/" + lectureDto.getCollection_id();
    }

    @GetMapping("edit/{id}")
    String edit(
            @PathVariable Long id,
            @RequestParam("collection_id") Long collectionId,
            @RequestParam("section_id") Long sectionId,
            Model model
    ) {
        Optional<Lecture> optionalLecture = lectureService.findById(id);
        if (optionalLecture.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecture Not Found");
        } else {
            Lecture lecture = optionalLecture.get();
            model.addAttribute("collection", collectionService.findById(collectionId).get());
            model.addAttribute("section", sectionService.findById(sectionId).get());
            model.addAttribute("lecture", lecture);
            return "backend/lecture/edit";
        }
    }

    @PutMapping("update")
    String update(
            @Validated @ModelAttribute("lecture") LectureDto lectureDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("lecture", lectureDto);
            model.addAttribute("collection", collectionService.findById(lectureDto.getCollection_id()).get());
            model.addAttribute("section", sectionService.findById(lectureDto.getSection_id()).get());

            return "backend/lecture/edit";
        }

        lectureService.save(lectureDto);

        return "redirect:/backend/collections/edit/" + lectureDto.getCollection_id();
    }

    @DeleteMapping("destroy/{id}")
    @Transactional
    public String destroy(@PathVariable Long id) {
        Lecture lecture = lectureService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecture Not Found"));
        lectureService.destroy(id);
        if (!lecture.getBlocks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are also blocks under the lecture, which are not allowed to be deleted directly.");
        }
        return "redirect:/backend/collections/edit/" + lecture.getCollection().getId();
    }
}
