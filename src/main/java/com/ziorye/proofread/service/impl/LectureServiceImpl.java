package com.ziorye.proofread.service.impl;

import com.ziorye.proofread.dto.LectureDto;
import com.ziorye.proofread.entity.Block;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.Lecture;
import com.ziorye.proofread.entity.Section;
import com.ziorye.proofread.repository.BlockRepository;
import com.ziorye.proofread.repository.LectureRepository;
import com.ziorye.proofread.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    BlockRepository blockRepository;

    @Value("${custom.block.separator}")
    String blockSeparator;

    @Override
    public Lecture save(LectureDto lectureDto) {
        Lecture lecture = new Lecture();

        if (lectureDto.getId() != null) {
            lecture = lectureRepository.findById(lectureDto.getId()).get();
            lecture.setUpdatedAt(LocalDateTime.now());
        } else {
            lecture.setCreatedAt(LocalDateTime.now());
        }

        lecture.setTitle(lectureDto.getTitle());
        lecture.setContent(lectureDto.getContent());
        lecture.setPublished(lectureDto.isPublished());
        lecture.setFree(lectureDto.isFree());
        lecture.setRequiresLogin(lectureDto.isRequiresLogin());
        lecture.setSortOrder(lectureDto.getSortOrder());
        lecture.setDescription(lectureDto.getDescription());
        lecture.setSection(new Section(lectureDto.getSection_id()));
        lecture.setCollection(new Collection(lectureDto.getCollection_id()));
        return lectureRepository.save(lecture);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureRepository.findById(id);
    }

    @Override
    public void destroy(Long id) {
        lectureRepository.deleteById(id);
    }

    @Override
    public void destroyAllById(List<Long> ids) {
        lectureRepository.deleteAllById(ids);
    }

    @Override
    public void saveBlocks(Long lectureId, LectureDto lectureDto) {
        String[] ss = lectureDto.getContent().split(blockSeparator);
        for (String s : ss) {
            Block block = new Block();
            block.setContent(s.trim());
            block.setLecture(new Lecture(lectureId));
            block.setCollection(new Collection(lectureDto.getCollection_id()));
            blockRepository.save(block);
        }
    }
}
