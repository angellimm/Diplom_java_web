package com.diary.demo.Repository;

import com.diary.demo.Models.Tracker;
import org.springframework.data.repository.CrudRepository;

public interface TrackerRepository extends CrudRepository<Tracker, Long> {
}
