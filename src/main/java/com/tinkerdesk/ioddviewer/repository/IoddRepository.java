package com.tinkerdesk.ioddviewer.repository;

import com.tinkerdesk.ioddviewer.model.Iodd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IoddRepository extends CrudRepository<Iodd, String> {
}
