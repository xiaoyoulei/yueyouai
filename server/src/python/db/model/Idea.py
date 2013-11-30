import os
import sys 
filePath = os.path.split(os.path.realpath(__file__))[0] 
sys.path.append(filePath+"/../") 
sys.path.append(filePath+"/../../common")
import commonUtils
import returnCode
import mysql
import log
class Idea:
	iid = None 
	title = None
	abstract = None
	content = None
	userLove = None
	userHate = None
	adminWeight = None
	rank = None
	status = None
	uid = None
	releaseTime = None
	
	def __init__(self,iid=None,title=None,abstract=None,content=None,userLove=None,userHate=None,adminWeight=None,rank=None,status=None,uid=None,releaseTime=None):
		self.iid = iid
		self.title = title
		self.abstract = abstract
		self.content = content
		self.userLove = userLove
		self.userHate = userHate
		self.adminWeight = adminWeight
		self.rank = rank
		self.status = status
		self.uid = uid
		self.releaseTime = releaseTime
	# ----------- function saveMysqlStr start ----
	def saveMysqlStr(self):
		mysqlStr = "INSERT INTO TblIdea(";
		#title
		mysqlKeys = "title"
		mysqlValues = mysql.get_mysql_value_string(self.title)
		#abstract
		mysqlKeys += ",abstract"
		mysqlValues += ","+ mysql.get_mysql_value_string(self.abstract)
		#content
		mysqlKeys += ",content"
		mysqlValues += "," + mysql.get_mysql_value_string(self.content)
		#userLove
		if (not self.userLove is None) :
			mysqlKeys += ",userLove"
			mysqlValues += "," + str(self.userLove)
		#userHate
		if (not self.userHate is None) :
			msqlKeys += ",userHate"
			mysqlValues += "," + str(self.uerHate)
		#adminWeight
		if (not self.adminWeight is None):
			mysqlKeys += ",adminWeight"
			mysqlValues += "," + str(self.adminWeight)
		#rank
		if (not self.rank is None):
			mysqlKeys += ",rank"
			mysqlValues += "," + str(self.rank)
		#status
		if (not self.status is None):
			mysqlKeys += ",status"
			mysqlValues += "," + str(self.status)
		#uid
		if (not self.uid is None):
			mysqlKeys += ",uid"
			mysqlValues +="," + str(self.uid)
		#releaseTime
		if (not self.releaseTime is None):
			mysqlKeys +=",release"
			mysqlValues += "," + mysql.get_mysql_value_string(self.releaseTime)
		#msyqStr end
		mysqlStr += mysqlKeys + ") VALUES(" + mysqlValues + ")";
		return  mysqlStr
	# ----------- function saveMysqlStr end ----
	# ----------- function selfCheck start ----
	def selfCheck(self):
		if (not self.title is None) or ( len(self.title) == 0 ):
			log.logger.info(" title is empty")
			return returnCode.IdeaValue.INVALID_PARAM
		if (not self.abstract is None) or ( len(self.abstract) == 0):
			log.logger.info(" abstract is empty") 
			return returnCode.IdeaValue.INVALID_PARAM 
		if (not self.content is None) or ( len(self.content) === 0):
			log.logger.info(" content is empty") 
			return returnCode.IdeaValue.INVALID_PARAM
		return returnCode.IdeaValue.OK

	# ----------- function selfCheck end ----
	
