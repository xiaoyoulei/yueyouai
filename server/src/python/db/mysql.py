# -*- coding: utf-8 -*-
import sys
import os
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/../config")
sys.path.append(filePath+"/../common")
import log
import myConfig
import MySQLdb
import returnCode
from DBUtils.PooledDB import PooledDB

m_s_user = myConfig.yueYouAiConfig.mysql["user"]
m_s_passwd = myConfig.yueYouAiConfig.mysql["pwd"]
m_s_host = myConfig.yueYouAiConfig.mysql["host"]
m_i_port = int(myConfig.yueYouAiConfig.mysql["port"])
m_s_db = myConfig.yueYouAiConfig.mysql["dbName"]
m_i_mincached = int(myConfig.yueYouAiConfig.mysqlPool["mincached"])
m_i_maxcached = int(myConfig.yueYouAiConfig.mysqlPool["maxcached"])
m_i_maxshared = int(myConfig.yueYouAiConfig.mysqlPool["maxshared"])
m_i_maxconnections = int(myConfig.yueYouAiConfig.mysqlPool["maxconnections"])

try:
	g_pool = PooledDB(MySQLdb, user = m_s_user, passwd = m_s_passwd, host = m_s_host , port = m_i_port ,db = m_s_db , mincached= m_i_mincached , maxcached = m_i_maxcached , maxshared = m_i_maxshared , maxconnections = m_i_maxconnections )
except Exception, e:
	msg = 'conn datasource Excepts!!!(%s).'%(str(e))
	raise Exception,msg


def get_mysql_value_string(content):
	return "'"+MySQLdb.escape_string(content)+"'"

def MysqlExecute(cursor,mysqlStr):
	log.logger.info(mysqlStr)
	return cursor.execute(mysqlStr)

def MysqlReturn(ret,msg,conn,cursor):
	cursor.close()
	conn.close()
	return returnCode.Return(ret,msg)

def testDB():
	conn = g_pool.connection()
	cursor = conn.cursor()
	rowcount = cursor.execute("show tables")
	result = cursor.fetchall()
	print rowcount
	print result
	print  cursor.description
	cursor.close()
	conn.close()

#testDB()
