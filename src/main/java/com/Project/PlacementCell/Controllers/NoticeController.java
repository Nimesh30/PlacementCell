package com.Project.PlacementCell.Controllers;

import com.Project.PlacementCell.DTO.AdminDTO.NoticeDTO;
import com.Project.PlacementCell.Entity.Notice;
import com.Project.PlacementCell.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // ADMIN
    @PostMapping("/admin/notices")
    public Notice createNotice(@RequestBody NoticeDTO dto) {
        return noticeService.createNotice(dto);
    }

    @PutMapping("/admin/notices/{id}")
    public Notice updateNotice(@PathVariable Long id, @RequestBody NoticeDTO dto) {
        return noticeService.updateNotice(id, dto);
    }

    @DeleteMapping("/admin/notices/{id}")
    public ResponseEntity<Map<String, String>> deleteNotice(@PathVariable Long id) {

        noticeService.deleteNotice(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Deleted Successfully");

        return ResponseEntity.ok(response);
    }

    // STUDENT
    @GetMapping("/students/notices")
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }
}