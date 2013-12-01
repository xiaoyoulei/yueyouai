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
 * File: RecordDAO.py
 * Create Date: 2013-12-01 03:00:51
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
import Record
import returnCode
import json
import commonUtils

class RecordDAO:
	# -----function AddRecord start -----------
	def AddRecord(self,record):
		ret = record.selfCheck()
		if ret != returnCode.RecordValue.OK:
			return ret
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		# add record
		conn.begin()
		mysql.MysqlExecute(cursor,record.saveMysqlStr())
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.RecordValue.OK
	# -----function AddRecord end -----------
	# --------function AddRecordUserLove start -----
	def AddRecordUserLove(self,rid,loveNum):
		mysqlStr = "UPDATE TblRecord SET userLove=userLove+"+str(loveNum)+" WHERE rid="+str(rid);
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.RecordValue.OK
	# --------function AddRecordUserLove end -----
	# --------function AddRecordUserHate start -----
	def AddRecordUserHate(self,rid,hateNum):
		mysqlStr = "UPDATE TblRecord SET userHate=userHate+"+str(hateNum)+" WHERE rid="+str(rid);
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.RecordValue.OK
	# --------function AddRecordUserHate end -----

	# --------function GetRecordList start -----
	def GetRecordList(self,mysqlStr):
		recordList = []
		conn = mysql.g_pool.connection()
		cursor = conn.cursor() 
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		#deal one
		results =cursor.fetchall()
		for res in results:
			recordDict = {}
			self.createRecordByMsyqlResult(recordDict,res)
			recordList.append(recordDict)
		cursor.close()
		conn.close() 
		return recordList

	# --------function GetRecordList end -----
	# --------function GetTopRecordByTime start -----
	def GetTopRecordByTime(self,topNum):
		mysqlStr = "SELECT rid,title,site,time,content,userLove,userHate,status,uid,releaseTime FROM TblRecord  WHERE status=0 ORDER BY releaseTime desc LIMIT " + str(topNum)
		return self.GetRecordList(mysqlStr)
	# --------function GetTopRecordByTime end -----
	# --------function GetAfterRecordById start -----
	def GetAfterRecordById(self,rid,afterNum):
		mysqlStr = "SELECT rid,title,site,time,content,userLove,userHate,status,uid,releaseTime FROM TblRecord  WHERE status=0 AND rid > "+str(rid)+ " ORDER BY rid LIMIT " + str(afterNum)
		return self.GetRecordList(mysqlStr)
	# --------function GetAfterRecordById end  -----
	# --------function createRecordByMsyqlResult start -----
	def createRecordByMsyqlResult(self,recordDict,res):
		recordDict["type"] = commonUtils.ThingType.RecordType
		ind = 0
		recordDict["rid"] = int(res[ind])
		ind +=1
		recordDict["title"] = res[ind]
		ind +=1
		recordDict["site"] = res[ind]
		ind +=1
		recordDict["time"] = str(res[ind])
		ind +=1
		recordDict["content"] = res[ind]
		ind +=1
		recordDict["userLove"] = int(res[ind])
		ind +=1
		recordDict["userHate"] = int(res[ind])
		ind +=1
		recordDict["status"] = int(res[ind])
		ind +=1
		recordDict["uid"] = int(res[ind])
		ind +=1
		if ( not res[ind] is None):
			recordDict["releaseTime"] = str(res[ind])
		else:
			recordDict["releaseTime"] = res[ind]
		ind +=1
	# --------function createRecordByMsyqlResult end -----
#-----------------------------------test -----------------------
'''
record = Record.Record(title="record title",site = "record site",time="2013-12-01",content = "record content",uid=2)
recordDao = RecordDAO();
#recordDao.AddRecord(record);
recordDao.AddRecordUserHate(2,12)
recordDao.AddRecordUserLove(1,11)
recordList = recordDao.GetAfterRecordById(1,2)
print json.dumps(recordList)
recordList = recordDao.GetTopRecordByTime(2)
print json.dumps(recordList)
'''




# vim: set ts=4 sw=4: 

