package com.nseit.dominos.repositary;

import com.nseit.dominos.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
