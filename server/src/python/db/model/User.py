import os 
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../../common")
import commonUtils
import returnCode
import mysql
import log
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
		mysqlKeys = "nickName";
		mysqlValues = mysql.get_mysql_value_string(self.nickName);
		#email
		if (not self.email is None) and (len(self.email) > 0) :
			mysqlKeys += ",email";
			mysqlValues += ","+mysql.get_mysql_value_string(self.email);
		#phoneNum
		if (not self.phoneNum is None) and (len(self.phoneNum) > 0) :
			mysqlKeys += ",phoneNum";
			mysqlValues += ","+mysql.get_mysql_value_string(self.phoneNum);
		#passWord
		if (not self.passWord is None) and (len(self.passWord) > 0) :
			mysqlKeys += ",passWord";
			mysqlValues += ","+mysql.get_mysql_value_string(self.passWord);
		#status
		if (not self.status is None):
			mysqlKeys += ",status";
			mysqlValues += ","+str(self.status)
		#sex
		if (not self.sex is None) :
			mysqlKeys += ",sex";
			mysqlValues += ","+str(self.sex);
		#single
		if (not self.single is None) :
			mysqlKeys += ",single";
			mysqlValues += ","+str(self.single);
		#birthTime
		if (not self.birthTime is None)  :
			mysqlKeys += ",birthTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.birthTime);
		#togetherTime
		if (not self.togetherTime is None)  :
			mysqlKeys += ",togetherTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.togetherTime);
		#marryTime
		if (not self.marryTime is None) :
			mysqlKeys += ",marryTime";
			mysqlValues += ","+mysql.get_mysql_value_string(self.marryTime);
	    #auth
		if (not self.auth is None):
			mysqlKeys += ",auth";
			mysqlValues += ","+str(self.auth);
		#registerTime
		mysqlKeys += ",registerTime";
		mysqlValues += ","+mysql.get_mysql_value_string(self.registerTime);
		#lastLoginTime
		mysqlKeys += ",lastLoginTime";
		mysqlValues += ","+mysql.get_mysql_value_string(self.lastLoginTime);

		#myqlStrend
		mysqlStr += mysqlKeys+") VALUES("+mysqlValues+")";
		
		return mysqlStr
	# ------------- function saveMysqlStr end -----
	
	# ------------- function selfCheck start -----
	def selfCheck(self):
		if (self.nickName is None):
			log.logger.info(" nickName is empty")
			return returnCode.UserValue.INVALID_PARAM
		if (self.email is None ):
			self.email = "";
		if (self.phoneNum is None ):
			self.phoneNum = ""
		if (len(self.nickName) == 0 ):
			log.logger.info(" nickName is empty")
			return returnCode.UserValue.INVALID_PARAM
		if (self.passWord is None )or (len(self.passWord) == 0):
			log.logger.info(" passWord empty")
			return returnCode.UserValue.INVALID_PARAM
		if (self.passWord is None ) or (len(self.nickName) == 0):
			log.logger.info("nickName empty")
			return returnCode.UserValue.INVALID_PARAM
		return returnCode.UserValue.OK
	# ------------- function selfCheck end   -----
	# ------------- function updateMysqlStr start   -----
	def updateMysqlStr(self,uid=-1,nickName="",phoneNum="",email=""):
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
		elif len(nickName) != 0:
			mysqlStr += " WHERE nickName=" + mysql.get_mysql_value_string(nickName)
		elif len(phoneNum) != 0:
			 mysqlStr += " WHERE phoneNum=" + mysql.get_mysql_value_string(phoneNum)
		else:
			mysqlStr += " WHERE email=" + mysql.get_mysql_value_string(email)
		return mysqlStr
	# ------------- function updateMysqlStr end   -----


#user = User("'zhuli''zhuli01'","zhuli102232@163.com","18301956105","woshimima");
#user.saveMysqlStr()
