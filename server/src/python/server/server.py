import tornado.web , tornado.ioloop ,  sys , os
#the file path , not run path
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/../db")
sys.path.append(filePath+"/ ../config")
import utils , returnCode
import json  , User , myConfig


class UserLoginHandler(tornado.web.RequestHandler):
	
	def post(self):
		username = self.get_argument("username")
		psw = self.get_argument("psw")
		my_utils = utils.Utils()
		if not username or not psw:
			raise tornado.web.HTTPError(404)
		if my_utils.UserCheck(username , psw) == returnCode.UserLogin.LOGIN_OK:
			res = {}
			res["username"] = my_utils.GetUserName(username)
			self.set_cookie("yueyouai_token",my_utils.GenToken(res["username"] , psw))
			self.write(json.dumps(res))
		else:
			raise tornado.web.HTTPError(404)

class MainHandler(tornado.web.RequestHandler):
    def get(self):
		from_id = self.get_argument("from" , 0)
		num = self.get_argument("num" , 0)
		if num == 0 :
			raise tornado.web.HTTPError(404)
		else :
			my_utils = utils.Utils()
			res = my_utils.GetIdeaList(from_id , num)
			if res == None :
				raise tornado.web.HTTPError(404)
			else :
				self.set_header("Content-Type" , "text/json ")
				self.write(json.dumps(res))
		return 


class UserRegisterHandler(tornado.web.RequestHandler):
	def post(self):
		username = self.get_argument("username" , None)
		mail = self.get_argument("mail" , None)
		passwd = self.get_argument("psw" , None)
		sex = self.get_argument("sex" , None)
		birthday = self.get_argument("birthday" , None)
		prov = self.get_argument("prov" , None)
		city = self.get_argument("city" , None)
		my_utils = utils.Utils()
		if not username or not mail or not passwd :
			raise tornado.web.HttpError(404)
		ret = my_utils.register(username , mail , passwd , sex , birthday , prov , city)
		if ret == returnCode.RegisterValue.REGIST_SUCC :
			res = {}
			res["username"] = username
			res["mail"]  = mail 
			res["info"] = "success"
			self.set_header("Content-Type","json")
			self.write(json.dumps(res))
		elif ret == returnCode.RegisterValue.NAME_EXIST :
			res = {}
			res["info"] = "username exist"
			self.set_header("Content-Type","json")
			self.write(json.dumps(res))
		else :
			tornado.web.HttpError(404)


if __name__ == "__main__":
	application = tornado.web.Application(
			[
				(r"/login" , UserLoginHandler),
				(r"/reg", UserRegisterHandler) ,
				(r"/home", MainHandler) ,
				], cookie_secret=myConfig.yueYouAiConfig.SECURE_KEY["key"])
	application.listen(int(sys.argv[1]))
	tornado.ioloop.IOLoop.instance().start()


