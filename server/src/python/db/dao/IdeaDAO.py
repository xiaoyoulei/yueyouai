'''
/***************************************************************************
 *
 * Copyright (c) 2013 yueyouai.com, Inc. All Rights Reserved
 *
 **************************************************************************/


/*
 * Author: Jeff,xiaoyoulei <zhuli102232@163.com,huxinjie800@sina.com>
 * 
 *
 * File: IdeaDAO.py
 * Create Date: 2013-11-30 10:52:29
 *
 */
'''

import os
import sys

filePath = os.path.split(os.path.realpath(__file__))[0]                                                                        
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../model")                                                                        
sys.path.append(filePath+"/../../common") 
import log
import mysql
import Idea
import returnCode

class IdeaDAO:
	# --------function AddIdea start -----
	def AddIdea(self,idea):
		ret = idea.selfCheck()
		if ret != returnCode.IdeaValue.OK:
			return ret
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		# add idea
		conn.begin()
		mysql.MysqlExecute(cursor,idea.saveMysqlStr())
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.IdeaValue.OK
	# --------function AddIdea end -----

	# --------function AddIdeaUserLove start -----
	def AddIdeaUserLove(self,iid,loveNum):
		mysqlStr = "UPDATE TblIdea SET userLove=userLove+"+str(loveNum)+" WHERE iid="+str(iid);
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.IdeaValue.OK
	# --------function AddIdeaUserLove end -----
	# --------function AddIdeaUserHate start -----
	def AddIdeaUserHate(self,iid,hateNum):
		mysqlStr = "UPDATE TblIdea SET userHate=userHate+"+str(hateNum)+" WHERE iid="+str(iid);
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.IdeaValue.OK
	# --------function AddIdeaUserHate end -----

#-----------------------------------test -----------------------
'''
idea = Idea.Idea(title="idea title",abstract = "idea abstract",content = "idea content")
ideaDao = IdeaDAO();
ideaDao.AddIdea(idea);
ideaDao.AddIdeaUserHate(3,12)
ideaDao.AddIdeaUserLove(1,11)
'''
# vim: set ts=4 sw=4: 

