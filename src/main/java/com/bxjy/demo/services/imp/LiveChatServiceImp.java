package com.bxjy.demo.services.imp;

import com.bxjy.demo.entity.LiveChat;
import com.bxjy.demo.entity.Result;
import com.bxjy.demo.repositpry.LiveChatRepository;
import com.bxjy.demo.services.LiveChatServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wsy on 12/8/2017.
 */
@Service
public class LiveChatServiceImp implements LiveChatServices {

    @Autowired
    LiveChatRepository liveChatRepository;

    private Logger logger  = LoggerFactory.getLogger(LiveChatServiceImp.class);

    @Override
    public void saveChatRecord(LiveChat liveChat) {
        liveChatRepository.saveLiveChat(liveChat);
    }

    @Override
    public Result findAllLiveChat(Map<String, String> paramMap) {
        Result result = new Result();
        try {
            List<LiveChat> allLiveChat = null;
            if (StringUtils.isEmpty(paramMap.get("maxid"))) {
                allLiveChat = liveChatRepository.findAllLiveChat();
                logger.info("查询全部信息返回记录数：" + allLiveChat.size());
            } else {
                allLiveChat = liveChatRepository.findAllLiveChat(paramMap);
                logger.info("查询增量信息返回记录数：" + allLiveChat.size());
            }
            result.setResultValue("_rows_", allLiveChat);
            result.setErrorCode(0);
        } catch (Exception e) {
            result.setErrorCode(-1);
            result.setMessage(e.getMessage());
            logger.info("参数"+ paramMap.toString());
            logger.error(e.getMessage());
        }
        return result;
    }
}
