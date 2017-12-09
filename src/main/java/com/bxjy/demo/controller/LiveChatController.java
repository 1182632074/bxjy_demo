package com.bxjy.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bxjy.demo.entity.LiveChat;
import com.bxjy.demo.entity.Result;
import com.bxjy.demo.repositpry.LiveChatRepository;
import com.bxjy.demo.services.LiveChatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LiveChatController {

    @Autowired
    LiveChatRepository repository;

    @Autowired
    LiveChatServices services;

    /**
     * 查询全部聊天记录和新增的聊天记录，区别参数：maxid
     *
     * @param maxid
     * @return
     */
    @RequestMapping(value = "findAllLiveChat")
    @ResponseBody
    public String findAllLiveChat(String maxid) {
        String result = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        if (!StringUtils.isEmpty(maxid)) {
            paramMap.put("maxid", maxid);
        }
        Result result1 = services.findAllLiveChat(paramMap);
        result = JSONObject.toJSONString(result1);
        return result;
    }

    /**
     * 保存用户聊天记录
     *
     * @param liveChat
     * @return
     */
    @RequestMapping(value = "saveLiveChat")
    @ResponseBody
    public String saveLiveChat(@ModelAttribute LiveChat liveChat) {
        repository.saveLiveChat(liveChat);
        Result result = new Result();
        result.setErrorCode(0);
        return JSONObject.toJSONString(result);
    }

    /**
     * 用户撤回信息：根据发送时间判断是否允许撤回在前段校验
     *
     * @param liveChat
     * @return
     */
    @RequestMapping(value = "revertChat")
    @ResponseBody
    public String revertChat(@ModelAttribute LiveChat liveChat) {
        repository.delete(liveChat);
        Result result = new Result();
        result.setErrorCode(0);
        return JSONObject.toJSONString(result);
    }
}
