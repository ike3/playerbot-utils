package org.mangosbot.web;

import java.util.HashMap;
import java.util.Map;

import org.mangosbot.service.api.BotRepository;
import org.mangosbot.service.api.dto.LiveData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bot")
public class BotLiveDataController {

    @Autowired
    private BotRepository botService;

    @RequestMapping(value = "/live-data.json", method = RequestMethod.POST)
    public @ResponseBody Map<Long, LiveData> home(@RequestBody Long[] guids) {
        HashMap<Long, LiveData> map = new HashMap<Long, LiveData>();
        for (long guid : guids) {
            LiveData data = new LiveData();
            data.setState(Double.toString(Math.random()));
            map.put(guid, data);
        }
        return map;
    }
}
