import os 
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../../common")
import commonUtils
import returnCode
import mysql
class User:
	uid = -1 # 
	nickName = "yueyouai_love"
	email = None
	phoneNum = None
	passWord = None
	sex = None #unkown
	single = None #nunkown
	birthTime = None
	togetherTime = None
	marryTime = None
	auth = 1
	registerTime = "0000-00-00"
	lastLoginTime = "0000-00-00"

	def __init__(self,nickName="yueyouai_love",email=None,phoneNum=None,passWord=None,sex = None,single=None,birthTime=None,togetherTime=None,marryTime=None):
		self.registerTime = commonUtils.get_now_time()
		self.lastLoginTime = self.registerTime
		self.nickName = nickName
		self.email = email
		self.phoneNum = phoneNum
		self.passWord = passWord
		self.sex = sex
		self.single = single
		self.birthTime = birthTime
		self.togetherTime = togetherTime
		self.marryTime = marryTime
	# ------------- function saveMysqlStr start -----
	def saveMysqlStr(self):
		mysqlStr ="INSERT INTO TblUser(";
		#nickName
		mysqlKes = "nickName";
		mysqlValues = mysql.get_mysql_value_string(self.nickName);
		#email
		if (not self.email is None) and (len(self.email) > 0) :
			mysqlKes += ",email";
			mysqlValues += ","+mysql.get_mysql_value_string(self.email);
		#phoneNum
		if (not self.phoneNum is None) and (len(self.phoneNum) > 0) :
			mysqlKes += ",phoneNum";
			mysqlValues += ","+mysql.get_mysql_value_string(self.phoneNum);
		#passWord
		if (not self.passWord is None) and (len(self.passWord) > 0) :
			mysqlKes += ",passWord";
			mysqlValues += ","+mysql.get_mysql_value_string(self.passWord);
		#sex
		mysqlKes += ",sex";
		if self.sex is None :
			mysqlValues += ",NULL";
		else:
			mysqlValues += ","+str(self.sex);
		#single
		mysqlKes += ",single";
		if self.single is None :
			mysqlValues += ",NULL";
		else:
			mysqlValues += ","+str(self.single);
		#birthTime
		if (not self.birthTime is None)  :
			mysqlKes += ",birthTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.birthTime);
		#togetherTime
		if (not self.togetherTime is None)  :
			mysqlKes += ",togetherTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.togetherTime);
		#marryTime
		if (not self.marryTime is None) :
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
	# ------------- function saveMysqlStr end -----
	
	# ------------- function selfCheck start -----
	def selfCheck(self):
		if (self.email is None ) and (self.phoneNum is None ):
			return returnCode.Return(returnCode.InvalidParam,"email and phoneNum all empty")
		if (self.email is None ):
			self.email = "";
		if (self.phoneNum is None ):
			self.phoneNum = ""
		if (len(self.email) == 0 ) and (len(self.phoneNum) == 0):
			return returnCode.Return(returnCode.InvalidParam,"email and phoneNum all empty")
		if (self.passWord is None )or (len(self.passWord) == 0):
			return returnCode.Return(returnCode.InvalidParam,"passWord empty")
		if (self.passWord is None ) or (len(self.nickName) == 0):
			return returnCode.Return(returnCode.InvalidParam,"nickName empty")
		return returnCode.Return(returnCode.OK,"OK")	
	# ------------- function selfCheck end   -----


#user = User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
#user.saveMysqlStr()
