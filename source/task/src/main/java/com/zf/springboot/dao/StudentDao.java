package com.zf.springboot.dao;

import com.zf.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by zhife on 2017/6/20.
 */
@Repository
public interface StudentDao {
    Collection<Student> getAllStudents();

    Student getStudentById(int id);

    Student removeStudentById(int id);

    void updateStudentById(int id, Student ss);
}
