package com.fjs.api2dextra.repository;

import java.util.List;

import com.fjs.api2dextra.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM student WHERE house_id = ?1", nativeQuery = true)
    List<Student> findCharactersByHouse(String houseID);
    
}
