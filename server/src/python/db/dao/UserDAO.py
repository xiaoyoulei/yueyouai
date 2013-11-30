import os
import sys
filePath = os.path.split(os.path.realpath(__file__))[0]                                                                        
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../model")                                                                        
sys.path.append(filePath+"/../../common") 
import log
import mysql
import User
import returnCode
#the function can not throw exception,if you do that ,the conn may be not set back
class UserDAO:
	# ------ function AddUser start ------
	def AddUser(self,user):
		ret = user.selfCheck()
		if ret != returnCode.UserValue.OK:
			return ret
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		if len(user.nickName) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE nickName=" + mysql.get_mysql_value_string(user.nickName)
			rowcount = mysql.MysqlExecute(cursor,mysqlStr)
			if rowcount > 0 :
				msg = "nickName:["+user.nickName+"] already exist!"
				log.logger.warn(msg)
				return mysql.MysqlReturn(returnCode.UserValue.ALREADY_EXIST,conn,cursor)
		if len(user.email) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE email=" + mysql.get_mysql_value_string(user.email)
			rowcount = mysql.MysqlExecute(cursor,mysqlStr)
			if rowcount > 0 :
				msg = "email:["+user.email+"] already exist!"
				log.logger.warn(msg)
				return mysql.MysqlReturn(returnCode.UserValue.ALREADY_EXIST,conn,cursor)
		if len(user.phoneNum) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(user.phoneNum)
			rowcount = mysql.MysqlExecute(cursor,mysqlStr)
			if rowcount > 0 :
				msg = "phoneNum:["+user.phoneNum+"] already exist!"
				log.logger.warn(msg)
				return mysql.MysqlReturn(returnCode.UserValue.ALREADY_EXIST,conn,cursor)
		#add user
		conn.begin()
		mysql.MysqlExecute(cursor,user.saveMysqlStr())
		cursor.close()
		conn.commit()
		#conn.rollback()
		conn.close()
		return returnCode.UserValue.OK
	# ------ function AddUser end ------
	# ------ function checkPassWord start ------
	def checkPassWord(self,uid = -1,nickName="",phoneNum="",email="",passWord=""):
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		if uid != -1:
			mysqlStr = "SELECT passWord FROM TblUser WHERE uid="+str(uid)
		elif len(nickName) != 0:
			mysqlStr = "SELECT passWord FROM TblUser WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			mysqlStr = "SELECT passWord FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr = "SELECT passWord FROM TblUser WHERE email=" + mysql.get_mysql_value_string(email)
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		if rowcount < 1:
			msg = "user not exist!"
			return mysql.MysqlReturn(returnCode.UserValue.NO_SUCH_OBJECT,conn,cursor)
		res = cursor.fetchone()
		if res[0] != passWord :
			msg = "passWord error"
			return mysql.MysqlReturn(returnCode.UserValue.NO_SUCH_OBJECT,conn,cursor)
		cursor.close()
		conn.close()
		return returnCode.UserValue.OK
	# ------ function checkPassWord end    ------
	# ------ function updateLastTime start ------
	def updateLastTime(self,uid = -1,nickName="",phoneNum="",email=""):
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		if uid != -1:
			mysqlStr = "UPDATE TblUser SET lastLoginTime = NOW() WHERE uid="+str(uid)
		elif len(nickName) != 0:
			mysqlStr = "UPDATE TblUser SET lastLoginTime = NOW() WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			mysqlStr = "UPDATE TblUser SET lastLoginTime = NOW() WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr = "UPDATE TblUser SET lastLoginTime = NOW() WHERE email=" + mysql.get_mysql_value_string(email)
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
	# ------ function updateLastTime end   ------
	# ------ function getUser start ------
	def getUser(self,uid = -1,nickName="",phoneNum="",email=""):
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		if uid != -1:
			mysqlStr = "SELECT uid,nickName,email,phoneNum,sex,single,birthTime,togetherTime,marryTime,auth,registerTime,lastLoginTime FROM TblUser WHERE uid="+str(uid)
		elif len(nickName) != 0:
			mysqlStr = "SELECT uid,nickName,email,phoneNum,sex,single,birthTime,togetherTime,marryTime,auth,registerTime,lastLoginTime FROM TblUser WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			mysqlStr = "SELECT uid,nickName,email,phoneNum,sex,single,birthTime,togetherTime,marryTime,auth,registerTime,lastLoginTime FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr = "SELECT uid,nickName,email,phoneNum,sex,single,birthTime,togetherTime,marryTime,auth,registerTime,lastLoginTime FROM TblUser WHERE email=" + mysql.get_mysql_value_string(email)
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		if rowcount < 1:
			return None
		user = User.User()
		res = cursor.fetchone()
		user.uid = int(res[0])
		user.nickName = res[1]
		user.email = res[2]
		user.phoneNum = res[3]
		user.sex = res[4]
		user.single = res[5]
		user.birthTime = res[6]
		user.togetherTime = res[7]
		user.marryTime = res[8]
		user.auth = res[9]
		user.registerTime = res[10]
		user.lastLoginTime = res[11]
		cursor.close()
		conn.close()
		return user;
		
	# ------ function getUser end   ------
	# ------ function updateUser start ------
	def updateUser(self,user = User.User(),uid = -1,nickName="",phoneNum="",email=""):
		conn = mysql.g_pool.dedicated_connection()
		cursor = conn.cursor()
		mysqlStr = user.updateMysqlStr(uid = uid,nickName=nickName,phoneNum=phoneNum,email=email);
		if mysqlStr is None:
			msg = "convert to mysql error"
			return mysql.MysqlReturn(returnCode.UserValue.INVALID_PARAM,conn,cursor)	
		conn.begin()
		mysql.MysqlExecute(cursor,mysqlStr)
		cursor.close()
		conn.commit()
		conn.close()
		return returnCode.UserValue.OK
	# ------ function updateUser end   ------
	# ------ function getUid start ----------
	def getUid(self,nickName="",phoneNum="",email=""):
		if len(nickName) != 0:
			mysqlStr = "SELECT uid FROM TblUser WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			mysqlStr = "SELECT uid FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr = "SELECT uid FROM TblUser WHERE email=" +  mysql.get_mysql_value_string(email)
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		if rowcount < 1:
			cursor.close()
			conn.close()
			return None
		else:
			res = cursor.fetchone()
			cursor.close()
			conn.close()
			return int(res[0])
	# ------ function getUid end   ----------
	# ------ function getNickname start ----------
	def getNickName(self,nickName="",phoneNum="",email=""):
		if len(nickName) != 0:
			mysqlStr = "SELECT nickName FROM TblUser WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			mysqlStr = "SELECT nickName FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr = "SELECT nickName FROM TblUser WHERE email=" +  mysql.get_mysql_value_string(email)
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		rowcount = mysql.MysqlExecute(cursor,mysqlStr)
		if rowcount < 1:
			cursor.close()
			conn.close()
			return None
		else:
			res = cursor.fetchone()
			cursor.close()
			conn.close()
			return str(res[0])
	# ------ function getUid end   ----------
'''
user = User.User(nickName="'zhuli''zhuli01'",phoneNum="18301956106",email="zhuli102233@163.com",passWord="woshimima");
#user = User.User(nickName="'zhuli''zhuli01'",phoneNum="",email="",passWord="woshimima");
userdao = UserDAO()
ret = userdao.AddUser(user)
print ret
ret = userdao.checkPassWord(uid=1,passWord="woshimima")
userdao.updateLastTime(uid=1)
user1 = userdao.getUser(uid=1);
import pprint 
#pprint.pprint(data)
print pprint.pformat(user1.phoneNum)
user2 = User.User(nickName="'zhuli''zhuli01'",phoneNum="18301956105")
ret = userdao.updateUser(user2,uid=1);
user1 = userdao.getUser(uid=1);
print pprint.pformat(user1.phoneNum)
'''
'''
print ret.ret
print ret.msg
'''
