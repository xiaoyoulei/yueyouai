UnKnowError = -1
OK = 0
NoSuchObject = 1
AlreadyExist = 2
InvalidParam = 3
MysqlError = 4

class Return:
	ret = -1
	msg = ""
	def __init__(self,ret = -1,msg = ""):
		self.ret = ret
		self.msg = msg
