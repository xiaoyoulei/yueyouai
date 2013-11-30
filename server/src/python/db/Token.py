import os
import sys
import time
import json
import base64 
import types 
from Crypto.Hash import MD5
from Crypto.Cipher import AES
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/../config")
sys.path.append(filePath+"/../common")
import myConfig
import returnCode
import commonUtils
BS = AES.block_size
pad = lambda s: s + (BS - len(s) % BS) * chr(BS - len(s) % BS)
unpad = lambda s : s[0:-ord(s[-1])]

class Token:
	nickName = ""
	pwd = ""
	expirationTime = int(time.time()) + 60*60
	renewTime = 10*60
	md5Str = ""
	randStr = ""
	aes = AES.new(myConfig.yueYouAiConfig.AES["key"], AES.MODE_ECB)
	def __init__(self,nickName="",pwd="",afterTime=60*60,renewTime=10*60):
		self.nickName = nickName
		self.pwd = pwd
		self.expirationTime = int(time.time()) + afterTime
		self.renewTime = renewTime
		self.md5Str = self.md5()
	def md5(self):
		plain = self.nickName + self.pwd + str(self.expirationTime) + str(self.renewTime) + myConfig.yueYouAiConfig.AES["MD5"] 
		m = MD5.new()
		m.update(plain)
		return m.hexdigest()[-8:]

	#------function toString start ----
	def toString(self):
		self.md5Str = self.md5();
		arr = [];
		length = 2; #[]
		length += 2+len(self.nickName); #"nickName"
		arr.append(self.nickName)
		length += (4 + len(self.pwd)) #, "pwd"
		arr.append(self.pwd)
		length += (2 + len(str(self.expirationTime))) #, expirationTime
		arr.append(self.expirationTime)
		length += (2 + len(str(self.renewTime))) #, renewTime
		arr.append(self.renewTime)
		length += (4 + len(self.md5Str)) #, "md5Str"
		arr.append(self.md5Str)
		length %= 16
		length = 16 - length
		if length > 3:
			length = length -4;
			self.randStr = random_str(length)
			arr.append(self.randStr)
		#else:
		#	print "need "+str(length)+"space(s) key"
		jsonEncodeStr = json.dumps(arr)
		#print len(jsonEncodeStr),jsonEncodeStr
		#print arr
		ciph = self.aes.encrypt(pad(jsonEncodeStr))
		return base64.b64encode(ciph)
	#------function toString end -----------
	#------function parseString start ------
	def parseString(self,tokenStr):
		jsonStr = unpad(self.aes.decrypt(base64.b64decode(tokenStr)))
		arr = json.loads(jsonStr)
		if isinstance(arr,list) is False:
			log.logger.debug("json parse error")
			return returnCode.TokenValue.INVALID_PARAM
		#print arr
		if (len(arr) != 5 ) and (len(arr) != 6):
			log.logger.debug("json load arr size not 5,6")
			return returnCode.TokenValue.INVALID_PARAM
		self.nickName = arr[0]
		self.pwd = arr[1]
		self.expirationTime = arr[2]
		self.renewTime = arr[3]
		self.md5Str = arr[4]
		if len(arr) == 6:
			self.randStr = arr[5]
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			log.logger.debug("md5 check error")
			return returnCode.TokenValue.INVALID_PARAM
		return returnCode.TokenValue.OK
	#------function parseString end   ------
	#------function checkValid start -------
	def checkValid(self):
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			log.logger.debug("md5 check error")
			return returnCode.TokenValue.INVALID_PARAM
		if self.expirationTime < int(time.time()):
			log.logger.debug("expiration time out")
			return returnCode.TokenValue.TIME_OUT
		return returnCode.TokenValue.OK
	#------function checkValid end   -------
 	
	#------function getNextToken start -------
	def getNextToken(self,afterTime=60*60,renewTime=10*60):
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			log.logger.debug("md5 check error")
			return None
		if self.expirationTime + self.renewTime < int(time.time()):
			log.logger.debug("expiration + renew time out")
			return None
		token = Token(self.nickName,self.pwd,afterTime,renewTime)
		return token.toString()
	#------function getNextToken end   -------
	#------function Debug start        -------
	def Debug(self):
		debugStr = "nickName:["+self.nickName+"] pwd["+self.pwd+"] expirationTime[" ;
		debugStr += commonUtils.convert_int_to_string_time(self.expirationTime) ;
		debugStr += "] renewTime[" +str(self.renewTime) +"](s) md5Str[" + self.md5Str + "] randStr["+self.randStr+"]"
		print debugStr
	#------function Debug end          -------
	#------function checkToken start     -------
	def checkToken(self,tokenStr):
		ret = self.parseString(tokenStr)
		if ret != returnCode.TokenValue.OK:
			return  ret
		return self.checkValid()
	#------function checkoken end       -------
	
from random import Random
def random_str(randomlength=8):
	str = ''
	chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'
	length = len(chars) - 1
	random = Random()
	for i in range(randomlength):
		str+=chars[random.randint(0, length)]
	return str

#-----------------test ---------------------------
'''
token = Token(12345686,"asdsadasdqweqwd")
tokenStr = token.toString()
print tokenStr
token.Debug()
token1 = Token();
ret = token1.parseString(tokenStr)
print ret.msg
time.sleep(2)
tokenStr =  token.getNextToken(60,1);
print tokenStr
ret = token1.parseString(tokenStr)
print ret.msg
'''
