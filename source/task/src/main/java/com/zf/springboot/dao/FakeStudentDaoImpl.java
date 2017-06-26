package com.zf.springboot.dao;

import com.zf.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhife on 2017/6/18.
 */
@Repository
@Qualifier("fakeData")
public class FakeStudentDaoImpl implements StudentDao {
    private static Map<Integer, Student> studentMap;

    static {
        studentMap = new HashMap<Integer, Student>() {
            {
                put(1, new Student(1, "zf", "math"));
                put(2, new Student(2, "vv", "english"));
            }
        };
    }

    @Override
    public Collection<Student> getAllStudents() {
        return this.studentMap.values();
    }

    @Override
    public Student getStudentById(int id) {
        return this.studentMap.get(id);
    }

    @Override
    public Student removeStudentById(int id) {
        return this.studentMap.remove(id);
    }

    @Override
    public void updateStudentById(int id, Student ss) {
        this.studentMap.put(id, ss);
    }
}
