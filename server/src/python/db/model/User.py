import os 
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
import mysql
class User:
	nickName = "yueyouai_love"
	email = ""
	phoneNum = ""
	passWord = ""
	sex = 3 #unkown
	single = 3 #unkown
	birthTime = ""
	togetherTime = ""
	marryTime = ""
	auth = 1
	registerTime = "0000-00-00"
	lastLoginTime = "0000-00-00"
	def __init__(self,nickName,email,phoneNum,passWord):
		self.registerTime = mysql.get_now_time()
		self.lastLoginTime = self.registerTime
		self.nickName = nickName
		self.email = email
		self.phoneNum = phoneNum
		self.passWord = passWord
	
	def saveMysqlStr(self):
		mysqlStr ="INSERT INTO TblUser(";
		#nickName
		mysqlKes = "nickName";
		mysqlValues = mysql.get_mysql_value_string(self.nickName);
		#email
		if len(self.email) > 0 :
			mysqlKes += ",email";
			mysqlValues += ","+mysql.get_mysql_value_string(self.email);
		#phoneNum
		if len(self.phoneNum) > 0 :
			mysqlKes += ",phoneNum";
			mysqlValues += ","+mysql.get_mysql_value_string(self.phoneNum);
		#passWord
		if len(self.passWord) > 0 :
			mysqlKes += ",passWord";
			mysqlValues += ","+mysql.get_mysql_value_string(self.passWord);
		#sex
		mysqlKes += ",sex";
		if self.sex == 3 :
			mysqlValues += ",NULL";
		else:
			mysqlValues += ","+str(self.sex);
		#single
		mysqlKes += ",single";
		if self.single == 3 :
			mysqlValues += ",NULL";
		else:
			mysqlValues += ","+str(self.single);
		#birthTime
		if len(self.birthTime) > 0 :
			mysqlKes += ",birthTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.birthTime);
		#togetherTime
		if len(self.togetherTime) > 0 :
			mysqlKes += ",togetherTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.togetherTime);
		#marryTime
		if len(self.marryTime) > 0 :
			mysqlKes += ",marryTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.marryTime);
	    #auth
		mysqlKes += ",auth";
		mysqlValues += ","+str(self.auth);
		#registerTime
		mysqlKes += ",registerTime";
		mysqlValues += ","+mysql.get_mysql_value_string(self.registerTime);
		#lastLoginTime
		mysqlKes += ",lastLoginTime";
		mysqlValues += ","+mysql.get_mysql_value_string(self.lastLoginTime);

		#myqlStrend
		mysqlStr += mysqlKes+") VALUES("+mysqlValues+")";
		
		return mysqlStr

#user = User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
#user.saveMysqlStr()
