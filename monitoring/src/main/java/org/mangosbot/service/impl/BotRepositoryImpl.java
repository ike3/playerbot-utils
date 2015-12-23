package org.mangosbot.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfno.jdbc.SqlBuilder;
import org.mangosbot.service.api.BotRepository;
import org.mangosbot.service.api.dto.Bot;
import org.mangosbot.service.api.dto.BotSearchQuery;
import org.mangosbot.service.api.dto.Faction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public class BotRepositoryImpl extends NamedParameterJdbcDaoSupport implements BotRepository {

    @Override
    @Transactional(readOnly = true)
    public List<Bot> search(BotSearchQuery query) {
        SqlBuilder sqlBuilder = new SqlBuilder()
            .SELECT("c.guid,c.name,c.class,c.race,c.gender,c.level")
            .FROM("characters c")
            .JOIN("ai_playerbot_random_bots rb on rb.bot = c.guid")
            .WHERE("rb.event = 'online'")
            .WHERE("c.online = 1")
            .ORDER_BY("c.name");

        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isEmpty(query.getName())) {
            sqlBuilder.WHERE("c.name like ?");
            params.add(query.getName() + "%");
        }
        if (!StringUtils.isEmpty(query.getFaction())) {
            sqlBuilder.WHERE("c.race in (" + StringUtils.join(Faction.valueOf(query.getFaction()).getRaces(), ',') + ")");
        }

        return getJdbcTemplate().query(sqlBuilder.SQL(), params.toArray(),
                new RowMapper<Bot>() {
                    @Override
                    public Bot mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        Bot bot = new Bot();
                        bot.setGuid(rs.getLong("guid"));
                        bot.setName(rs.getString("name"));
                        bot.setCls(rs.getLong("class"));
                        bot.setRace(rs.getLong("race"));
                        bot.setGender(rs.getLong("gender"));
                        bot.setLevel(rs.getLong("level"));
                        return bot;
                    }
                });
    }

}
