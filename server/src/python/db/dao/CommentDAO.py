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
 * File: CommentDAO.py
 * Create Date: 2013-12-01 04:21:01
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
import Comment
import returnCode
import json
import commonUtils
class CommentDAO:
	# --------function AddComment start -----
	def AddComment(self,comment):
		ret = comment.selfCheck()
		if ret != returnCode.CommentValue.OK:
			return ret
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		# add comment
		conn.begin()
		mysql.MysqlExecute(cursor,comment.saveMysqlStr())
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.CommentValue.OK
	# --------function AddComment end -----
	# --------function GetCommentList start -----
	def GetCommentList(self,refType,refId,cid,afterNum):
		if cid == 0 :
			cid = commonUtils.MAX_MYSQL_UINT
		mysqlStr = "SELECT c.cid,c.refType,c.refId,c.content,u.nickName,c.releaseTime FROM TblComment c INNER JOIN TblUser u ON c.uid=u.uid AND c.refType="+str(refType)+" AND c.refId="+str(refId)+" AND c.cid<"+str(cid)+" ORDER BY c.cid DESC LIMIT "+str(afterNum)	
		return self.GetCommentListImpl(mysqlStr) 
	# --------function GetCommentList end -----
	# --------function GetCommentListImpl start -----
	def GetCommentListImpl(self,mysqlStr):
		commentList = []
		conn = mysql.g_pool.connection()
		cursor = conn.cursor() 
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		#deal one
		results =cursor.fetchall()
		for res in results:
			commentDict = {}
			self.createCommentByMsyqlResult(commentDict,res)
			commentList.append(commentDict)
		cursor.close()
		conn.close() 
		return commentList
	# --------function GetCommentListImpl end -----
	# --------function createCommentByMsyqlResult start -----
	def createCommentByMsyqlResult(self,commentDict,res):
		ind = 0;
		commentDict["cid"] = int(res[ind])
		ind+=1
		commentDict["refType"] = int(res[ind])
		ind+=1
		commentDict["refId"] = int(res[ind])
		ind+=1
		commentDict["content"] = res[ind]
		ind+=1
		commentDict["nickName"] = res[ind]
		ind+=1
		commentDict["releaseTime"] = str(res[ind])
		ind+=1
	# --------function createCommentByMsyqlResult end -----

#-----------------------------------test -----------------------
'''
comment = Comment.Comment(refType=commonUtils.ThingType.IdeaType,refId = 3,content = "comment content",uid=1)
commentDao = CommentDAO();
commentDao.AddComment(comment);
commentList = commentDao.GetCommentList(commonUtils.ThingType.IdeaType,3,0,2)
print json.dumps(commentList)
'''
# vim: set ts=4 sw=4: 

