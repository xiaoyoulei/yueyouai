import os
import sys
filePath = os.path.split(os.path.realpath(__file__))[0]                                                                        
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../model")                                                                        
sys.path.append(filePath+"/../../common") 
import log
import mysql
import User
#the function can not throw exception,if you do that ,the conn may be not set back
class UserDAO:
	def AddUser(self,user):
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		if len(user.email) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE email=" + mysql.get_mysql_value_string(user.email)
			rowcount = mysql.MyExecute(cursor,mysqlStr)
			if rowcount > 0 :
				log.logger.warn("email:["+user.email+"] already exist!")
		if len(user.phoneNum) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(user.phoneNum)
			rowcount = mysql.MyExecute(cursor,mysqlStr)
			if rowcount > 0 :
				log.logger.warn("phoneNum:["+user.phoneNum+"] already exist!")
		#add user
		conn.begin()
		mysql.MyExecute(cursor,user.saveMysqlStr())
		conn.commit()
		#conn.rollback()
		cursor.close()
		conn.close()
'''
user = User.User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
userdao = UserDAO()
userdao.AddUser(user)
'''
