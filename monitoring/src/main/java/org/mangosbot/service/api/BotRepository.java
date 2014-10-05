package org.mangosbot.service.api;

import java.util.List;

import org.mangosbot.service.api.dto.Bot;
import org.mangosbot.service.api.dto.BotSearchQuery;

public interface BotRepository {
    List<Bot> search(BotSearchQuery query);
}
