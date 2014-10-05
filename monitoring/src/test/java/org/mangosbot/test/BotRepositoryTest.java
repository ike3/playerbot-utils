package org.mangosbot.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mangosbot.service.api.BotRepository;
import org.mangosbot.service.api.dto.Bot;
import org.mangosbot.service.api.dto.BotSearchQuery;
import org.mangosbot.service.api.dto.Faction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(locations = "classpath:/test-context.xml")
public class BotRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(BotRepositoryTest.class);

    @Autowired
    private BotRepository botRepository;

    BotSearchQuery query = new BotSearchQuery();

    @Test
    public void search() throws Exception {
        query.setFaction("");
        query.setName(null);
        List<Bot> bots = assertSearchOk(query);

        query.setName(bots.get(0).getName());
        assertSearchOk(query);

        query.setName(bots.get(0).getName().substring(1, 3));
        assertSearchOk(query);

        query.setFaction(Faction.Alliance.name());
        assertSearchOk(query);
    }

    private List<Bot> assertSearchOk(BotSearchQuery query) throws Exception {
        List<Bot> list = log(botRepository.search(query));
        Assert.assertFalse(list.isEmpty());
        return list;
    }

    private List<Bot> log(List<Bot> bots) throws Exception {
        String json = new ObjectMapper().writeValueAsString(bots);
        LOG.debug(json);
        return bots;
    }
}
