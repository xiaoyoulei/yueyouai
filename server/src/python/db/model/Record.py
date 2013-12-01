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
 * File: Record.py
 * Create Date: 2013-11-30 15:22:07
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

class Record:
	rid = None
	title = None
	site = None
	time = None
	content = None
	userLove = None
	userHate = None
	adminWeight = None
	rank = None
	status = None
	uid = None
	releaseTime = None

	def __init__(self,rid=None,title=None,site=None,time=None,content=None,userLove=None,userHate=None,adminWeight=None,rank=None,status=None,uid=None,releaseTime=None):
		self.rid = rid
		self.title = title
		self.site = site
		if (time is None):
			self.time = commonUtils.get_now_time() 
		else:
			self.time = time
		self.content = content
		self.userLove = userLove
		self.userHate = userHate
		self.adminWeight = adminWeight
		self.rank = rank
		self.status = status
		self.uid = uid
		self.releaseTime = releaseTime
	# ----------function saveMysqlStr start -------
	def saveMysqlStr(self):
		mysqlStr = "INSERT INTO TblRecord(";
		#title
		mysqlKeys = "title"; 
		mysqlValues = mysql.get_mysql_value_string(self.title);
		#site
		mysqlKeys += ",site";
		mysqlValues += ","+ mysql.get_mysql_value_string(self.site);
		#time
		mysqlKeys += ",time";
		mysqlValues += ","+ mysql.get_mysql_value_string(self.time);
		#content
		mysqlKeys += ",content";
		mysqlValues += ","+ mysql.get_mysql_value_string(self.content);
		#userLove
		if (not self.userLove is None):
			mysqlKeys += ",userLove";
			mysqlValues += ","+ str(self.userLove);
		#userHate
		if (not self.userHate is None):
			mysqlKeys += ",userHate";
			mysqlValues += ","+ str(self.userHate);
		#adminWeight
		if (not self.adminWeight is None):
			mysqlKeys += ",adminWeight";
			mysqlValues += ","+ str(self.adminWeight);
		#rank
		if (not self.rank is None):
			mysqlKeys += ",rank";
			mysqlValues += ","+ str(self.rank);
		#status
		if (not self.status is None):
			mysqlKeys += ",status";
			mysqlValues += ","+ str(self.status);
		#uid
		mysqlKeys += ",uid";
		mysqlValues += ","+ str(self.uid);
		#releaseTime
		if (not self.releaseTime is None):
			mysqlKeys += ",releaseTime";
			mysqlValues += ","+ mysql.get_mysql_value_string(self.releaseTime);
		#msyqStr end
		mysqlStr += mysqlKeys + ") VALUES(" + mysqlValues + ")";
		return  mysqlStr
	# ----------function saveMysqlStr end -------
	# --------------function selfCheck start ----  
	def selfCheck(self):
		if (self.title is None) or ( len(self.title) == 0 ): 
			log.logger.debug(" title is empty") 
			return  returnCode.RecordValue.INVALID_PARAM 
		if (self.site is None) or ( len(self.site) == 0 ): 
			log.logger.debug(" site is empty") 
			return  returnCode.RecordValue.INVALID_PARAM 
		if (self.time is None) or ( len(self.time) == 0 ): 
			log.logger.debug(" time is empty") 
			return  returnCode.RecordValue.INVALID_PARAM 
		if (self.content is None) or ( len(self.content) == 0 ): 
			log.logger.debug(" content is empty") 
			return  returnCode.RecordValue.INVALID_PARAM 
		if (self.uid is None):
			log.logger.debug(" uid is empty") 
			return  returnCode.RecordValue.INVALID_PARAM 
		return returnCode.RecordValue.OK
	# --------------function selfCheck end  ---- 





# vim: set ts=4 sw=4: 

