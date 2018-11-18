package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.BanEvent;
import com.gibgab.service.database.repository.BanEventRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class BanUserController {

    @Autowired
    private BanEventRepository banEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Data
    private static class BanInfo {
        String emailToBan;
        String banStart;
        String bannedUntil;
    }

    @PostMapping("/moderator/ban_user")
    public String banUser(Principal principal, @RequestBody BanInfo banInfo) throws ParseException {
        ApplicationUser banner = userRepository.findByEmail(principal.getName());
        ApplicationUser banned = userRepository.findByEmail(banInfo.emailToBan);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Timestamp startTime = new Timestamp(dateFormat.parse(banInfo.getBanStart()).getTime());
        Timestamp endTime = new Timestamp(dateFormat.parse(banInfo.getBannedUntil()).getTime());

        BanEvent banEvent = BanEvent.builder()
                .bannedId(banned.getId())
                .bannerId(banner.getId())
                .startTime(startTime)
                .endTime(endTime).build();

        banned.setActive(false);
        userRepository.save(banned);

        banEventRepository.save(banEvent);

        return "Banned";
    }
}
