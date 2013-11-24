import os
import sys
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/dao")
import UserDAO
sys.path.append(filePath+"/../common")
import returnCode
class Utils():
	userDao = UserDAO.UserDAO()
	
	def addUser(self,user):
		return self.userDao.AddUser(user)
		
	def userCheckByPhoneNum(self ,username , psw):
		ret = self.userDao.checkPassWord(phoneNum=username,passWord=psw)
		if ret.ret == returnCode.OK:
			self.userDao.updateLastTime(phoneNum=username)
			return True
		else:
			return False
	def userCheckByEmail(self ,username , psw):
		ret = self.userDao.checkPassWord(email=username,passWord=psw)
		if ret.ret == returnCode.OK:
			self.userDao.updateLastTime(email=username)
			return True
		else:
			return False
	#getUidByPhoneNum return None or id
	def getUidByPhoneNum(self ,username):
		return self.userDao.getUid(phoneNum=username)
	def getUidByEmail(self ,username):
		return self.userDao.getUid(email=username)
