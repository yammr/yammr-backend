package com.gibgab.service.database.repository;

import com.gibgab.service.database.entity.BanEvent;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface BanEventRepository extends CrudRepository<BanEvent, Integer> {
    BanEvent findByBannedId(Integer bannedId);
    BanEvent findByBannedIdAndEndTimeAfter(Integer bannedId, Timestamp endTime);
}
