#!/bin/sh

echo "Merging $char_name..."
queststatus_columns="mobcount1, mobcount2, mobcount3, mobcount4, itemcount1, itemcount2, itemcount3, itemcount4"


# wotlk
#queststatus_columns="mobcount1, mobcount2, mobcount3, mobcount4, itemcount1, itemcount2, itemcount3, itemcount4, itemcount5, itemcount6"

function query
{
    mysql -u$user -p$pass mysql -Nse "$1"
}

srcGuid=`query "select guid from ${src}.characters where name = '$char_name'"`
dstGuid=`query "select guid from ${dst}.characters where name = '$char_name'"`

#
# Reputation
#
if [ "$enable_reputation" == "1" ]
then
	echo "Merging reputation..."

	todel=`query "SELECT faction FROM ${src}.character_reputation src WHERE standing >= \
		(SELECT standing FROM ${dst}.character_reputation dst WHERE dst.guid = $dstGuid AND dst.faction = src.faction UNION SELECT 0 LIMIT 1) \
		AND guid = $srcGuid"`

	IFS="
	"
	for id in $todel
	do
		query "DELETE FROM ${dst}.character_reputation WHERE guid = $dstGuid AND faction = $id"
	done

	query "INSERT INTO ${dst}.character_reputation \
		SELECT $dstGuid as guid, faction, standing, flags FROM ${src}.character_reputation src \
		WHERE standing >= (SELECT standing FROM ${dst}.character_reputation dst WHERE dst.guid = $dstGuid AND dst.faction = src.faction UNION SELECT 0 LIMIT 1) and guid = $srcGuid;"

fi
	
#
# Spells
#
if [ "$enable_spells" == "1" ]
then
	echo "Merging spells..."

	query "INSERT INTO ${dst}.character_spell \
		SELECT distinct $dstGuid as guid, spell, active, disabled FROM ${src}.character_spell \
		INNER JOIN ${dst}.ai_playerbot_spells ON id = spell \
		WHERE ($dstGuid, spell, active, disabled) NOT IN (SELECT guid, spell, active, disabled FROM ${dst}.character_spell WHERE guid = $dstGuid) \
		AND disabled = 0 AND guid = $srcGuid AND level <= (SELECT level FROM ${dst}.characters WHERE guid = $dstGuid)"
fi

#
# Skills
#
if [ "$enable_skills" == "1" ]
then
	echo "Merging skills..."

	todel=`query "SELECT skill FROM ${src}.character_skills src WHERE value >= \
		(SELECT value FROM ${dst}.character_skills dst WHERE dst.guid = $dstGuid AND dst.skill = src.skill UNION SELECT 0 LIMIT 1) and guid = $srcGuid"`

	IFS="
	"
	for id in $todel
	do
		query "DELETE FROM ${dst}.character_skills WHERE guid = $dstGuid AND skill = $id"
	done

	query "INSERT INTO ${dst}.character_skills \
		SELECT $dstGuid as guid, skill, IF (value > $max_skill, $max_skill, value) as value, IF (max > $max_skill, $max_skill, max) as max FROM ${src}.character_skills src \
		WHERE value >= (SELECT value FROM ${dst}.character_skills dst WHERE dst.guid = $dstGuid AND dst.skill = src.skill UNION SELECT 0 LIMIT 1) and guid = $srcGuid"
fi

#
# Quests
#
if [ "$enable_quests" == "1" ]
then
	echo "Merging quests..."

	todel=`query "SELECT quest FROM ${src}.character_queststatus src \
	INNER JOIN $dst_world.quest_template on entry = quest \
	WHERE status = 1 AND NOT EXISTS (SELECT quest FROM ${dst}.character_queststatus dst WHERE dst.guid = $dstGuid AND dst.quest = src.quest AND dst.status = 1) and guid = $srcGuid;"`

	IFS="
	"
	for id in $todel
	do
		query "DELETE FROM ${dst}.character_queststatus WHERE guid = $dstGuid AND quest = $id"
	done

	query "INSERT INTO ${dst}.character_queststatus \
		SELECT $dstGuid as guid, quest, status, rewarded, explored, timer, $queststatus_columns \
		FROM ${src}.character_queststatus src \
		INNER JOIN ${dst_world}.quest_template on entry = quest \
		WHERE status = 1 AND NOT EXISTS (SELECT quest FROM ${dst}.character_queststatus dst WHERE dst.guid = $dstGuid AND dst.quest = src.quest AND dst.status = 1) and guid = $srcGuid;"
fi
