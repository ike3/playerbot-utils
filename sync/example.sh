#!/bin/sh
export user=root
export pass=root

export src=characters
export dst=op_characters
export src_world=udb
export dst_world=op_world

export max_skill=375

export enable_reputation=1
export enable_spells=1
export enable_skills=1
export enable_quests=1

chars="MyCharName"
for c in $chars
do
	export char_name=$c
	./merge.sh
done
