package com.zf.springboot.dao;

import com.zf.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by zhife on 2017/6/20.
 */
@Repository
@Qualifier("MongoData")
public class MongoStudentDaoImpl implements StudentDao {

    @Override
    public Collection<Student> getAllStudents() {
        return null;
    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }

    @Override
    public Student removeStudentById(int id) {
        return null;
    }

    @Override
    public void updateStudentById(int id, Student ss) {

    }
}
