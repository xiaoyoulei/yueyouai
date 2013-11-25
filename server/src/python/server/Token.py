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
	uid = -1
	pwd = ""
	expirationTime = int(time.time()) + 60*60
	renewTime = 10*60
	md5Str = ""
	randStr = ""
	aes = AES.new(myConfig.yueYouAiConfig.AES["key"], AES.MODE_ECB)
	def __init__(self,uid=-1,pwd="",afterTime=60*60,renewTime=10*60):
		self.uid = uid
		self.pwd = pwd
		self.expirationTime = int(time.time()) + afterTime
		self.renewTime = renewTime
		self.md5Str = self.md5()
	def md5(self):
		plain = str(self.uid) + self.pwd + str(self.expirationTime) + str(self.renewTime) + myConfig.yueYouAiConfig.AES["MD5"] 
		m = MD5.new()
		m.update(plain)
		return m.hexdigest()[-8:]

	#------function toString start ----
	def toString(self):
		self.md5Str = self.md5();
		arr = [];
		length = 2; #[]
		length += len(str(self.uid)); #uid
		arr.append(self.uid)
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
	def parseString(self,token):
		jsonStr = unpad(self.aes.decrypt(base64.b64decode(token)))
		arr = json.loads(jsonStr)
		if isinstance(arr,list) is False:
			return returnCode.Return(returnCode.InvalidParam,"json parse error")
		#print arr
		if (len(arr) != 5 ) and (len(arr) != 6):
			return returnCode.Return(returnCode.InvalidParam,"json load arr size not 5,6")
		self.uid = arr[0]
		self.pwd = arr[1]
		self.expirationTime = arr[2]
		self.renewTime = arr[3]
		self.md5Str = arr[4]
		if len(arr) == 6:
			self.randStr = arr[5]
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			return returnCode.Return(returnCode.InvalidParam,"md5 check error")
		return returnCode.Return(returnCode.OK,"OK")
	#------function parseString end   ------
	#------function checkValid start -------
	def checkValid(self):
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			return returnCode.Return(returnCode.InvalidParam,"md5 check error")
		if self.expirationTime < int(time.time()):
			return returnCode.Return(returnCode.InvalidParam,"expiration time out")
		return returnCode.Return(returnCode.OK,"OK")
	#------function checkValid end   -------
 	
	#------function getNextToken start -------
	def getNextToken(self,afterTime=60*60,renewTime=10*60):
		tempMd5Str = self.md5()
		if self.md5Str != tempMd5Str:
			return returnCode.Return(returnCode.InvalidParam,"md5 check error")
		if self.expirationTime + self.renewTime < int(time.time()):
			return returnCode.Return(returnCode.InvalidParam,"expiration + renew time out")
		token = Token(self.uid,self.pwd,afterTime,renewTime)
		return token.toString()
	#------function getNextToken end   -------
	#------function Debug start        -------
	def Debug(self):
		debugStr = "uid:["+str(self.uid)+"] pwd["+self.pwd+"] expirationTime[" ;
		debugStr += commonUtils.convert_int_to_string_time(self.expirationTime) ;
		debugStr += "] renewTime[" +str(self.renewTime) +"](s) md5Str[" + self.md5Str + "] randStr["+self.randStr+"]"
		print debugStr
	#------function Debug end          -------
	
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
