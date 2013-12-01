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
 * File: Comment.py
 * Create Date: 2013-12-01 04:07:40
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
class Comment:
	cid = None
	refType = None
	refId = None
	content = None
	uid = None
	releaseTime = None

	def __init__(self,cid=None,refType=None,refId=None,content=None,uid=None,releaseTime=None):
		self.cid = cid;
		self.refType = refType
		self.refId = refId
		self.content = content
		self.uid = uid
		if (not releaseTime is None) and (len(releaseTime) != 0):
			self.releaseTime = releaseTime 
		else:
			self.releaseTime = commonUtils.get_now_time()
		
	# ----------- function saveMysqlStr start ----
	def saveMysqlStr(self):
		mysqlStr = "INSERT INTO TblComment(";
		#refType
		mysqlKeys = "refType"
		mysqlValues = str(self.refType)
		#refId
		mysqlKeys += ",refId"
		mysqlValues += "," + str(self.refId)
		#content
		mysqlKeys += ",content"
		mysqlValues += ","+ mysql.get_mysql_value_string(self.content)
		#uid
		mysqlKeys += ",uid"
		mysqlValues += "," + str(self.uid)
		#releaseTime
		mysqlKeys += ",releaseTime"
		mysqlValues += ","+ mysql.get_mysql_value_string(self.releaseTime)
		#msyqStr end
		mysqlStr += mysqlKeys + ") VALUES(" + mysqlValues + ")";
		return  mysqlStr
	# ----------- function saveMysqlStr end ----
	# ----------- function selfCheck start ----
	def selfCheck(self):
		if (self.refType is None) :
			log.logger.debug(" refType is empty") 
			return returnCode.CommentValue.INVALID_PARAM
		if (self.refId is None) :
			log.logger.debug(" refId is empty") 
			return returnCode.CommentValue.INVALID_PARAM
		if (self.content is None) or ( len(self.content) == 0 ):
			log.logger.debug(" content is empty")
			return returnCode.CommentValue.INVALID_PARAM
		if (self.uid is None) :
			log.logger.debug(" uid is empty") 
			return returnCode.CommentValue.INVALID_PARAM
		if (self.releaseTime is None) or ( len(self.releaseTime) == 0):
			log.logger.debug(" releaseTime is empty") 
			return returnCode.CommentValue.INVALID_PARAM 
		return returnCode.CommentValue.OK

	# ----------- function selfCheck end ----
	








# vim: set ts=4 sw=4: 

