#!/bin/sh

echo "====================================================================================="
echo "Merging $char_name..."
echo "====================================================================================="
queststatus_columns="mobcount1, mobcount2, mobcount3, mobcount4, itemcount1, itemcount2, itemcount3, itemcount4"
item_value_count=48


# wotlk
#queststatus_columns="mobcount1, mobcount2, mobcount3, mobcount4, itemcount1, itemcount2, itemcount3, itemcount4, itemcount5, itemcount6"

function query
{
    mysql -u$user -p$pass mysql -Nse "$1"
}

#
# Replace character
#
if [ "$enable_character_replace" == "1" ]
then
	echo "Replacing character..."

	dstGuid=`query "select guid from ${dst}.characters WHERE name = '$char_name'"`
	query "DELETE FROM ${dst}.characters WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_reputation WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_spell WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_skills WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_queststatus WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_pet WHERE owner = $dstGuid"
	query "DELETE FROM ${dst}.character_action WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_aura WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_battleground_data WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.character_homebind WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.mail WHERE receiver = $dstGuid"
	dstGuid=`query "SELECT 1 + MAX(guid) as guid FROM ${dst}.characters"`
	dstAccountId=`query "SELECT id FROM ${dst_realm}.account WHERE username = '$dstAccount'"`

	query "INSERT INTO ${dst}.characters (guid,account,actionBars,ammoId,at_login,cinematic,class,death_expire_time,deleteDate,deleteInfos_Account,\
		deleteInfos_Name,drunk,equipmentCache,exploredZones,extra_flags,gender,health,honor_highest_rank,honor_standing,is_logout_resting,level,leveltime,\
		logout_time,map,money,name,online,orientation,playerBytes,playerBytes2,playerFlags,position_x,position_y,position_z,power1,power2,power3,power4,power5,\
		race,resettalents_cost,resettalents_time,rest_bonus,stable_slots,stored_dishonorable_kills,stored_honor_rating,stored_honorable_kills,taxi_path,taximask,\
		totaltime,trans_o,trans_x,trans_y,trans_z,transguid,watchedFaction,xp,zone) SELECT $dstGuid,$dstAccountId,actionBars,ammoId,at_login,cinematic,class,\
		death_expire_time,deleteDate,deleteInfos_Account,deleteInfos_Name,drunk,equipmentCache,exploredZones,extra_flags,gender,health,0 as honor_highest_rank,\
		0 as honor_standing,is_logout_resting,level,leveltime,logout_time,map,money,name,online,orientation,playerBytes,playerBytes2,playerFlags,\
		position_x,position_y,position_z,power1,power2,power3,power4,power5,race,resettalents_cost,resettalents_time,rest_bonus,stable_slots,\
		0 as stored_dishonorable_kills,0 as stored_honor_rating,0 as stored_honorable_kills,taxi_path,taximask,totaltime,trans_o,trans_x,trans_y,trans_z,\
		transguid,watchedFaction,xp,zone FROM ${src}.characters WHERE name = '$char_name'"
				
	echo "$char_name created, guid = "
	echo $dstGuid
fi

srcGuid=`query "select guid from ${src}.characters WHERE name = '$char_name'"`
dstGuid=`query "select guid from ${dst}.characters WHERE name = '$char_name'"`

if [ "$enable_character_replace" == "1" ]
then
	query "DELETE FROM ${dst}.character_homebind WHERE guid = $dstGuid"
	query "INSERT INTO ${dst}.character_homebind SELECT $dstGuid as guid,map,position_x,position_y,position_z,zone FROM ${src}.character_homebind WHERE guid = $srcGuid"
fi

#
# Replace pets
#
if [ "$enable_pet_replace" == "1" ]
then
	echo "Replacing pets..."
	
	firstPetId=`query "SELECT 1 + MAX(id) FROM ${dst}.character_pet"`
	query "DELETE FROM ${dst}.character_pet WHERE owner = $dstGuid"
	query "INSERT INTO ${dst}.character_pet \
		(abdata,CreatedBySpell,curhappiness,curhealth,curmana,entry,exp,id,level,loyalty,loyaltypoints,modelid,name,owner,PetType,Reactstate, \
		renamed,resettalents_cost,resettalents_time,savetime,slot,teachspelldata,trainpoint) \
		SELECT abdata,CreatedBySpell,curhappiness,curhealth,curmana,entry,exp,$firstPetId + (@i:=@i+1) as id,level,0 as loyalty,0 as loyaltypoints,modelid,name,$dstGuid as owner,PetType,Reactstate, \
		renamed,resettalents_cost,resettalents_time,savetime,slot,0 as teachspelldata,0 as trainpoint FROM (SELECT @i:=-1) foo, ${src}.character_pet WHERE owner = $srcGuid"
fi

#
# Replace inventory
#
if [ "$enable_inventory" == "1" ]
then
	echo "Replacing inventory..."

	query "DELETE FROM ${dst}.character_inventory WHERE guid = $dstGuid"
	query "DELETE FROM ${dst}.item_instance WHERE owner_guid = $dstGuid"
	firstItemGuid=`query "SELECT 1 + MAX(guid) FROM (SELECT guid FROM ${dst}.item_instance UNION SELECT item as guid FROM ${dst}.character_inventory) q"`

	query "INSERT INTO ${dst}.item_instance \
		SELECT $firstItemGuid + (@i:=@i+1) as guid, $dstGuid as owner_guid, SUBSTRING_INDEX(inst.data, ' ', $item_value_count) FROM (SELECT @i:=-1) foo, ${src}.item_instance inst \
		INNER JOIN ${src}.character_inventory inv on item = inst.guid \
		INNER JOIN ${dst_world}.item_template t on t.entry = inv.item_template \
		WHERE owner_guid = $srcGuid order by inst.guid"
	
	query "INSERT INTO ${dst}.character_inventory \
		SELECT $dstGuid as guid, bag, slot, $firstItemGuid + (@i:=@i+1) as item, item_template FROM (SELECT @i:=-1) foo, ${src}.character_inventory \
		INNER JOIN ${dst_world}.item_template on entry = item_template WHERE guid = $srcGuid order by item"
fi

	
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

#
# Races
#
if [ "$enable_race_replace" == "1" ]
then
	echo "Replacing races..."

	query "UPDATE ${dst}.characters SET race = 1 WHERE race = 11"
fi
