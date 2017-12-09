package com.bxjy.demo.services;

import com.bxjy.demo.entity.LiveChat;
import com.bxjy.demo.entity.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by wsy on 12/8/2017.
 */
public interface LiveChatServices {

    void saveChatRecord(LiveChat liveChat);

    Result findAllLiveChat(Map<String, String> paramMap);

}
