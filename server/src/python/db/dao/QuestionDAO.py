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
 * File: QuestionDAO.py
 * Create Date: 2013-11-30 15:37:36
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
import Question
import returnCode
import json
import commonUtils

class QuestionDAO:
	# --------function AddQuestion start -----   
	def AddQuestion(self,question):
		ret = question.selfCheck() 
		if ret != returnCode.QuestionValue.OK:
			return ret
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		# add idea
		conn.begin()
		mysql.MysqlExecute(cursor,question.saveMysqlStr())
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.QuestionValue.OK

	# --------function AddQuestion start -----   
	# --------function AddHitNum start -----  
	def AddHitNum(self,qid,hitNum):
		mysqlStr = "UPDATE TblQuestion SET  hitNum = hitNum + "+str(hitNum) + " WHERE qid="+str(qid)
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.QuestionValue.OK
	# --------function AddHitNum end -----
	# --------function GetQuestionList start ----- 
	def GetQuestionList(self,mysqlStr):
		questionList = []
		conn = mysql.g_pool.connection()
		cursor = conn.cursor() 
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		#deal one
		results =cursor.fetchall()
		for res in results:
			questionDict = {}
			self.createQuestionByMsyqlResult(questionDict,res)
			questionList.append(questionDict)
		cursor.close()
		conn.close() 
		return questionList

	# --------function GetQuestionList end ----- 
	# --------function GetTopQuestionByTime start -----
	def GetTopQuestionByTime(self,topNum):
		mysqlStr = "SELECT qid,content,hitNum,status,releaseTime FROM TblQuestion WHERE status=0 ORDER BY releaseTime desc LIMIT "+ str(topNum)
		return self.GetQuestionList(mysqlStr)
	# --------function GetTopQuestionByTime end -----

	# --------function GetAfterQuestionById start -----
	def GetAfterQuestionById(self,qid,afterNum): 
		mysqlStr = "SELECT qid,content,hitNum,status,releaseTime FROM TblQuestion WHERE status=0 AND qid < " + str(qid) + " ORDER BY qid LIMIT " + str(afterNum) 
		return self.GetQuestionList(mysqlStr) 
	# --------function GetAfterQuestionById end -----
	# --------function GetAfterQuestionByUid start  -----
	def GetAfterQuestionByUid(self,uid,qid,afterNum):
		if qid == 0 :
			qid = commonUtils.MAX_MYSQL_UINT
		mysqlStr = "SELECT qid,content,hitNum,status,releaseTime FROM TblQuestion  WHERE status=0 AND uid= "+str(uid) + " AND qid < "+str(qid)+ " ORDER BY qid LIMIT " + str(afterNum)
		return self.GetQuestionList(mysqlStr)
	# --------function GetAfterQuestionByUid end  -----
	# --------function createQuestionByMsyqlResult start -----
	def createQuestionByMsyqlResult(self,questionDict,res):
		questionDict["type"] = commonUtils.ThingType.QuestionTpye
		ind = 0
		questionDict["qid"] = int(res[ind])
		ind +=1
		questionDict["content"] = res[ind]
		ind +=1
		questionDict["hitNum"] = int(res[ind])
		ind +=1 
		questionDict["status"] = int(res[ind]) 
		ind +=1
		if (not res[ind] is None):
			questionDict["releaseTime"] = str(res[ind])
		else:
			questionDict["releaseTime"] = res[ind]
		ind+=1
	# --------function createQuestionByMsyqlResult end -----
#---------------------------test -------------------
'''
question = Question.Question(content="question content")
questionDao = QuestionDAO()
questionDao.AddQuestion(question)
questionDao.AddHitNum(1,12)
questionList = questionDao.GetTopQuestionByTime(2)
print json.dumps(questionList)
questionList = questionDao.GetAfterQuestionById(1,2)
print json.dumps(questionList)
questionList = questionDao.GetAfterQuestionByUid(1,0,3)
print json.dumps(questionList)
'''

# vim: set ts=4 sw=4: 

