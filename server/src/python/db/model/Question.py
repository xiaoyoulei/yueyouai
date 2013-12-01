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
 * File: Question.py
 * Create Date: 2013-11-30 15:21:40
 *
 */
'''
import os
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../../common")
import commonUtils
import returnCode
import mysql
import log

class Question:
	qid = None
	content = None
	hitNum = None
	adminWeight = None
	rank = None
	status = None
	uid = None
	releaseTime = None

	def __init__(self,qid=None,content=None,hitNum=None,adminWeight=None,rank=None,status=None,uid=None,releaseTime=None):
		self.qid = qid
		self.content = content
		self.hitNum = hitNum
		self.adminWeight = adminWeight
		self.rank = rank
		self.status = status
		self.uid = uid
		self.releaseTime = releaseTime
	
	# --------------function saveMysqlStr start ---------
	def saveMysqlStr(self): 
		mysqlStr = "INSERT INTO TblQuestion(";
		#content
		mysqlKeys = "content";
		mysqlValues = mysql.get_mysql_value_string(self.content)
		if (not self.hitNum is None):
			mysqlKeys += ",hitNum";
			mysqlValues += "," + str(self.hitNum)
		if (not self.adminWeight is None):
			mysqlKeys += ",adminWeight";
			mysqlValues += "," + str(self.adminWeight)
		if (not self.rank is None):
			mysqlKeys += ",rank"
			mysqlValues += "," + str(self.rank)
		if (not self.status is None):
			mysqlKeys += ",status"
			mysqlValues += "," + str(self.status)
		mysqlKeys += ",uid"
		mysqlValues += "," + str(self.uid)
		if (not self.releaseTime is None):
			mysqlKeys += ",releaseTime"
			mysqlValues += "," + mysql.get_mysql_value_string(self.releaseTime)
		#msyqStr end
		mysqlStr += mysqlKeys + ") VALUES(" + mysqlValues + ")";
		return  mysqlStr
	# --------------function saveMysqlStr end ---------
 
	# --------------function selfCheck start ----  
	def selfCheck(self):
		if (self.content is None) or ( len(self.content) == 0 ): 
			log.logger.debug(" content is empty") 
			return  returnCode.QuestionValue.INVALID_PARAM 
		if (self.uid is None):
			log.logger.debug(" uid is empty")
			return  returnCode.QuestionValue.INVALID_PARAM
		return returnCode.QuestionValue.OK
	# --------------function selfCheck end ----  

# vim: set ts=4 sw=4: 

