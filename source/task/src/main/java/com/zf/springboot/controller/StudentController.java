package com.zf.springboot.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zf.springboot.entity.Student;
import com.zf.springboot.service.StudentService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by zhife on 2017/6/18.
 */

@RestController
@RequestMapping(value = "students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Student removeStudentById(@PathVariable("id") int id) {
        return studentService.removeStudentById(id);
    }

    //note the sender should send json formatted string
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateStudent(@RequestBody Student s) {
        studentService.updateStudent(s);
    }

    @RequestMapping(value = "/check", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String check(@RequestBody String s) {
        return s;
    }

    @RequestMapping(value = "/check2", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String check2(@RequestBody JSONPObject s) {
        return s.toString();
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String displayHeader(@RequestHeader("Content-Type") String content_type,
                                @RequestHeader("host") String host) {
        return String.format("host=%s, content-type=%s", host,content_type);
    }

    @PostMapping(value="post")
    public String post(@RequestBody String body){return body;}

}

