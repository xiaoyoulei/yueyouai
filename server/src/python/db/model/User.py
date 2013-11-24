import os 
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../../common")
import commonUtils
import returnCode
import mysql
class User:
	uid = None # 
	nickName = "yueyouai_love"
	email = None
	phoneNum = None
	passWord = None
	status = None
	sex = None #unkown
	single = None #nunkown
	birthTime = None
	togetherTime = None
	marryTime = None
	auth = None
	registerTime = "0000-00-00"
	lastLoginTime = "0000-00-00"

	def __init__(self,uid=None,nickName="yueyouai_love",email=None,phoneNum=None,passWord=None,status=None,sex = None,single=None,birthTime=None,togetherTime=None,marryTime=None,auth = None):
		self.registerTime = commonUtils.get_now_time()
		self.lastLoginTime = self.registerTime
		self.uid = uid
		self.nickName = nickName
		self.email = email
		self.phoneNum = phoneNum
		self.passWord = passWord
		self.status = status
		self.sex = sex
		self.single = single
		self.birthTime = birthTime
		self.togetherTime = togetherTime
		self.marryTime = marryTime
		self.auth = auth
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
		#status
		if (not self.status is None):
			mysqlKes += ",status";
			mysqlValues += ","+str(self.status)
		#sex
		if (not self.sex is None) :
			mysqlKes += ",sex";
			mysqlValues += ","+str(self.sex);
		#single
		if (not self.single is None) :
			mysqlKes += ",single";
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
		if (not self.auth is None):
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
	# ------------- function updateMysqlStr start   -----
	def updateMysqlStr(self,uid=-1,phoneNum="",email=""):
		mysqlStr ="UPDATE TblUser SET ";
		#nickName
		if (not self.nickName is None) and (len(self.nickName) > 0):
			mysqlStr += "nickName = " + mysql.get_mysql_value_string(self.nickName);
		else:
			return None
		#email
		if (not self.email is None) and (len(self.email) > 0) :
			mysqlStr += ",email=" + mysql.get_mysql_value_string(self.email);
		#phoneNum
		if (not self.phoneNum is None) and (len(self.phoneNum) > 0) :
			mysqlStr += ",phoneNum="+mysql.get_mysql_value_string(self.phoneNum);
		#passWord
		if (not self.passWord is None) and (len(self.passWord) > 0) :
			mysqlStr += ",passWord="+mysql.get_mysql_value_string(self.passWord);
		#status
		if (not self.status is None):
			mysqlStr += ",status="+str(self.status)
		#sex
		if (not self.sex is None) :
			mysqlStr += ",sex="+str(self.sex);
		#single
		if (not self.single is None) :
			mysqlStr += ",single="+str(self.single);
		#birthTime
		if (not self.birthTime is None)  :
			mysqlStr += ",birthTime="+mysql.get_mysql_value_string(self.birthTime);
		#togetherTime
		if (not self.togetherTime is None)  :
			mysqlStr += ",togetherTime="+mysql.get_mysql_value_string(self.togetherTime);
		#marryTime
		if (not self.marryTime is None) :
			mysqlStr += ",marryTime="+mysql.get_mysql_value_string(self.marryTime);
	    #auth
		if (not self.auth is None):
			mysqlStr += ",auth="+str(self.auth);

		#myqlStrend
		if uid != -1: 
			mysqlStr += " WHERE uid="+str(uid);
		elif len(phoneNum) != 0:
			 mysqlStr += " WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr += " WHERE email=" + mysql.get_mysql_value_string(email)
		return mysqlStr
	# ------------- function updateMysqlStr end   -----


#user = User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
#user.saveMysqlStr()
