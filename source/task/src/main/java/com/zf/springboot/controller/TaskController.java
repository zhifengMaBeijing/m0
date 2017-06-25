package com.zf.springboot.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhife on 2017/6/25.
 */
@RestController
@RequestMapping(value = "task")
public class TaskController {
    /**
     * create a new task
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONPObject create(String userId, String taskName, @RequestBody String data) {

        return null;
    }

    /**
     * save task data
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JSONPObject save(String userId, String taskName, @RequestBody String data) {

        return null;
    }

    /**
     * load task data
     * note: task id should be unique
     *
     * @return
     */
    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public JSONPObject open(String userId, String taskName) {

        return null;
    }

}
