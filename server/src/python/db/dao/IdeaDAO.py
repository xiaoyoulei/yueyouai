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
import json
import commonUtils
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
	# --------function GetIdeaList start -----
	def GetIdeaList(self,mysqlStr):
		ideaList = []
		conn = mysql.g_pool.connection()
		cursor = conn.cursor() 
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		#deal one
		results =cursor.fetchall()
		for res in results:
			ideaDict = {}
			self.createIdeaByMsyqlResult(ideaDict,res)
			ideaList.append(ideaDict)
		cursor.close()
		conn.close() 
		return ideaList

	def GetIdeaItem(self , idea_id):
		mysqlStr = "SELECT iid,title,abstract,content,imgUrl,userLove,userHate,status,uid,releaseTime FROM TblIdea  WHERE status=0 and iid = %d " % int(idea_id)
		return self.GetIdeaList(mysqlStr)
	# --------function GetIdeaList end -----
	# --------function GetTopIdeaByTime start -----
	def GetTopIdeaByTime(self,topNum):
		mysqlStr = "SELECT iid,title,abstract,content,imgUrl,userLove,userHate,status,uid,releaseTime FROM TblIdea  WHERE status=0 ORDER BY releaseTime DESC LIMIT " + str(topNum)
		return self.GetIdeaList(mysqlStr)
	# --------function GetTopIdeaByTime end -----
	# --------function GetAfterIdeaById start -----
	def GetAfterIdeaById(self,iid,afterNum):
		mysqlStr = "SELECT iid,title,abstract,content,imgUrl,userLove,userHate,status,uid,releaseTime FROM TblIdea  WHERE status=0 AND iid < "+str(iid)+ " ORDER BY iid DESC LIMIT " + str(afterNum)
		return self.GetIdeaList(mysqlStr)
	# --------function GetAfterIdeaById end  -----
	# --------function GetAfterIdeaByUid start  -----
	def GetAfterIdeaByUid(self,uid,iid,afterNum):
		if iid == 0 :
			iid = commonUtils.MAX_MYSQL_UINT
		mysqlStr = "SELECT iid,title,abstract,content,imgUrl,userLove,userHate,status,uid,releaseTime FROM TblIdea  WHERE status=0 AND uid= "+str(uid) + " AND iid < "+str(iid)+ " ORDER BY iid DESC LIMIT " + str(afterNum)
		return self.GetIdeaList(mysqlStr)
	# --------function GetAfterIdeaByUid end  -----
	# --------function createIdeaByMsyqlResult start -----
	def createIdeaByMsyqlResult(self,ideaDict,res):
		ideaDict["type"] = commonUtils.ThingType.IdeaType
		ind = 0
		ideaDict["iid"] = int(res[ind])
		ind +=1
		ideaDict["title"] = res[ind]
		ind +=1
		ideaDict["abstract"] = res[ind]
		ind +=1
		ideaDict["content"] = res[ind]
		ind +=1
		ideaDict["imgUrl"] = res[ind]
		ind +=1
		ideaDict["userLove"] = int(res[ind])
		ind +=1
		ideaDict["userHate"] = int(res[ind])
		ind +=1
		ideaDict["status"] = int(res[ind])
		ind +=1
		ideaDict["uid"] = int(res[ind])
		ind +=1
		if ( not res[ind] is None):
			ideaDict["releaseTime"] = str(res[ind])
		else:
			ideaDict["releaseTime"] = res[ind]
		ind +=1
	# --------function createIdeaByMsyqlResult end -----


#-----------------------------------test -----------------------
'''
idea = Idea.Idea(title="idea title",abstract = "idea abstract",content = "idea content")
ideaDao = IdeaDAO();
#ideaDao.AddIdea(idea);
ideaDao.AddIdeaUserHate(3,12)
ideaDao.AddIdeaUserLove(1,11)
ideaList = ideaDao.GetAfterIdeaById(2,2)
print json.dumps(ideaList)
ideaList = ideaDao.GetTopIdeaByTime(2)
print json.dumps(ideaList)
ideaList = ideaDao.GetAfterIdeaByUid(1,2,2)
print json.dumps(ideaList)
'''
# vim: set ts=4 sw=4: 

