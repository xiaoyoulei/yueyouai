import os
import sys  ,json
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/dao")
import UserDAO , IdeaDAO
sys.path.append(filePath+"/../common")
import returnCode ,User,Idea ,Token ,commonUtils
class Utils():

	userDao = UserDAO.UserDAO()

	ideaDao = IdeaDAO.IdeaDAO()

	def GetIdeaList(self , from_id , num):
		if from_id == 0 :
			ideaList = self.ideaDao.GetTopIdeaByTime(num)
		else:
			ideaList = self.ideaDao.GetAfterIdeaById(from_id ,num)

		ideas = []
		min_id = 0
		for idea in ideaList:
			if idea["status"] != 0 :
				continue 
			idea_tmp = {}
			idea_tmp["id"] = idea["iid"]
			if  min_id > idea_tmp["id"] or min_id == 0:
				min_id = idea_tmp["id"]
			idea_tmp["type"] = commonUtils.ThingType.IdeaType 
			idea_tmp["title"] = idea["title"]
			idea_tmp["desc"] = idea["content"]
			idea_tmp["abstract"] = idea["abstract"]
			idea_tmp["pic"] = idea["imgUrl"]
			idea_tmp["time"] = idea["releaseTime"]
			ideas.append(idea_tmp)
		res = {}
		res["nowid"] = min_id
		res["data"] = ideas
		return res

	
	def addUser(self,user):
		return self.userDao.AddUser(user)
		
	def GenToken(self , username , psw):
		return Token.Token(username,psw).toString()

	def GetToken(self , token_str):
		mytoken = Token.Token()
		if mytoken.checkToken(token_str) == returnCode.TokenValue.OK:
			return mytoken.nickName 
		else:
			return None
	def register(self , username , mail , passwd , sex , birthday , prov , city):
		if cmp(str(sex).lower() , "male") == 0:
			sex = 1
		elif cmp(str(sex).lower() , "female") == 0 :
			sex = 0
		else:
			sex = 2
		new_user = User.User(nickName = username , email = mail , passWord = passwd , sex = sex , birthTime = birthday )
		ret = self.addUser(new_user)
		if ret == returnCode.UserValue.OK:
			return returnCode.RegisterValue.REGIST_SUCC
		elif ret == returnCode.UserValue.ALREADY_EXIST:
			return returnCode.RegisterValue.NAME_EXIST
		else:
			return returnCode.RegisterValue.REGIST_FAIL

	def UserCheck(self , username , psw):
		if self.userCheckByPhoneNum(username , psw) == returnCode.UserValue.OK :
			return returnCode.UserLogin.LOGIN_OK
		if self.userCheckByEmail(username , psw) == returnCode.UserValue.OK:
			return returnCode.UserLogin.LOGIN_OK
		if self.userCheckByNickName(username , psw) == returnCode.UserValue.OK:
			return returnCode.UserLogin.LOGIN_OK
		return returnCode.UserLogin.LOGIN_FAIL 

	def GetUserName(self , username ):
		ret = self.getNickNameByNickName(username )
		if ret != None:
			return ret
		ret = self.getNickNameByPhoneNum(username )
		if ret != None:
			return ret
		ret = self.getNickNameByEmail(username )
		if ret != None:
			return ret

	def userCheckByPhoneNum(self ,username , psw):
		ret = self.userDao.checkPassWord(phoneNum=username,passWord=psw)
		if ret == returnCode.UserValue.OK:
			self.userDao.updateLastTime(phoneNum=username)
			return ret
		else:
			return ret
	def userCheckByNickName(self ,username , psw):
		ret = self.userDao.checkPassWord(nickName=username,passWord=psw)
		if ret == returnCode.UserValue.OK:
			self.userDao.updateLastTime(nickName=username)
			return ret
		else:
			return ret

	def userCheckByEmail(self ,username , psw):
		ret = self.userDao.checkPassWord(email=username,passWord=psw)
		if ret == returnCode.UserValue.OK:
			self.userDao.updateLastTime(email=username)
			return ret
		else:
			return ret
	#getNickNameByPhoneNum return None or id
	def getNickNameByNickName(self ,username):
		return self.userDao.getNickName(nickName=username)
	def getNickNameByPhoneNum(self ,username):
		return self.userDao.getNickName(phoneNum=username)
	def getNickNameByEmail(self ,username):
		return self.userDao.getNickName(email=username)
	#getUidByPhoneNum return None or id
	def getUidByPhoneNum(self ,username):
		return self.userDao.getUid(phoneNum=username)
	def getUidByEmail(self ,username):
		return self.userDao.getUid(email=username)
