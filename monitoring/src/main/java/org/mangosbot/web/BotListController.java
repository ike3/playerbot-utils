package org.mangosbot.web;

import org.mangosbot.service.api.BotRepository;
import org.mangosbot.service.api.dto.Bot;
import org.mangosbot.service.api.dto.BotSearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class BotListController {

    @Autowired
    private BotRepository botService;

    @RequestMapping(value = "/bot-list.json", method = RequestMethod.POST)
    public @ResponseBody Bot[] home(@RequestBody BotSearchQuery query) {
        return botService.search(query).toArray(new Bot[] {});
    }
}
