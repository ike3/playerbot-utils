#!/bin/sh
export user=root
export pass=root

export src=characters
export src_realm=wotlk_realm
export src_world=udb

export dst=op_characters
export dst_world=op_world
export dst_realm=zero_realm

export max_skill=375

export enable_character_replace=0
export enable_reputation=1
export enable_spells=1
export enable_skills=1
export enable_quests=1

chars="char1 char2 char3"
for c in $chars
do
	export char_name=$c
	./merge.sh
done
