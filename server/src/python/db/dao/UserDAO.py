import os
import sys
filePath = os.path.split(os.path.realpath(__file__))[0]                                                                        
sys.path.append(filePath+"/../")                                                                                               
sys.path.append(filePath+"/../model")                                                                                               
import mysql
import User
#the function can not throw exception,if you do that ,the conn may be not set back
class UserDAO:
	def AddUser(self,user):
		conn = mysql.g_pool.connection()
		cursor = conn.cursor()
		if len(user.email) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE email=" + mysql.get_mysql_value_string(user.email)
			rowcount = cursor.execute(mysqlStr)
			if rowcount > 0 :
				print "dsjcdoscjiosdc"
		if len(user.phoneNum) > 0:
			mysqlStr = "SELECT * FROM TblUser WHERE phoneNum=" + mysql.get_mysql_value_string(user.phoneNum)
			rowcount = cursor.execute(mysqlStr)
			if rowcount > 0 :
				print "1111111111"
		#add user
		conn.begin()
		cursor.execute(user.saveMysqlStr())
		conn.commit()
		#conn.rollback()
		cursor.close()
		conn.close()

user = User.User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
userdao = UserDAO()
userdao.AddUser(user)
