package com.bxjy.demo.repositpry;

import com.bxjy.demo.entity.LiveChat;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wsy on 12/8/2017.
 */
@Repository
public interface LiveChatRepository {
    public static final String THING_ALL_KEY = "\"thing_all\"";
    public static final String THING_ADD_KEY = "\"thing_add\"";
    public static final String DEMO_CACHE_NAME = "demo";

    /**
     * 保存用户聊天信息
     *
     * @param liveChat
     * @return
     */
    int saveLiveChat(LiveChat liveChat);

    /**
     * 获取增量
     *
     * @return
     */
    @Cacheable(value = DEMO_CACHE_NAME, key = THING_ADD_KEY)
    List<LiveChat> findAllLiveChat(Map<String, String> paramMap);

    /**
     * 查找全部
     *
     * @return
     */
    @Cacheable(value = DEMO_CACHE_NAME, key = THING_ALL_KEY)
    List<LiveChat> findAllLiveChat();

    /**
     * 撤回信息
     * @return
     */
    int delete(LiveChat liveChat);

}
