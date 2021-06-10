package com.fjs.api2dextra.repository;

import com.fjs.api2dextra.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM student WHERE house_id = ?1", nativeQuery = true)
    Student findCharactersByHouse(String houseID);
    
}
