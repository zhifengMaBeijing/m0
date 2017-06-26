package com.zf.springboot.service;

import com.zf.springboot.dao.FakeStudentDaoImpl;
import com.zf.springboot.dao.StudentDao;
import com.zf.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by zhife on 2017/6/18.
 */

@Service
public class StudentService {
    @Autowired
    @Qualifier("fakeData")
    private StudentDao studentDao;

    public Collection<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    public Student removeStudentById(int id) {
        return studentDao.removeStudentById(id);

    }

    public void updateStudent(Student s) {
        Student ss = studentDao.getStudentById(s.getId());
        ss.setCourse(s.getCourse());
        ss.setName(s.getName());
        studentDao.updateStudentById(s.getId(), ss);
    }
}
