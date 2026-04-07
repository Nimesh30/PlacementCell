package com.Project.PlacementCell.Service;

import com.Project.PlacementCell.DTO.AdminDTO.NoticeDTO;
import com.Project.PlacementCell.Entity.Notice;
import com.Project.PlacementCell.Repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    // CREATE
    public Notice createNotice(NoticeDTO dto) {
        Notice notice = new Notice();

        notice.setTitle(dto.getTitle());
        notice.setMessage(dto.getMessage());
        notice.setFileUrl(dto.getFileUrl());
        notice.setPinned(dto.getPinned());
        notice.setCreatedAt(LocalDateTime.now());

        return noticeRepository.save(notice);
    }

    // GET ALL
    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByPinnedDescCreatedAtDesc();
    }

    // UPDATE
    public Notice updateNotice(Long id, NoticeDTO dto) {
        Notice notice = noticeRepository.findById(id).orElseThrow();

        notice.setTitle(dto.getTitle());
        notice.setMessage(dto.getMessage());
        notice.setFileUrl(dto.getFileUrl());
        notice.setPinned(dto.getPinned());
        notice.setUpdatedAt(LocalDateTime.now());

        return noticeRepository.save(notice);
    }

    // DELETE
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}