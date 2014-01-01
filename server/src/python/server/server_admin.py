import tornado.web , tornado.ioloop ,  sys , os
#the file path , not run path
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/../db")
sys.path.append(filePath+"/ ../config")
import utils , returnCode
import json  , User , myConfig


class ListIdeaHandler(tornado.web.RequestHandler):
    def get(self):
		template_path=filePath+"/../template/admin/"
		from_id = self.get_argument("from" , 0)
		num = self.get_argument("num" , 10)
		if num == 0 :
			raise tornado.web.HTTPError(404)
		else :
			my_utils = utils.Utils()
			res = my_utils.GetIdeaList(from_id , num)
			if res == None :
				raise tornado.web.HTTPError(404)
			else :
				self.set_header("Content-Type" , "text/html")
				items = res["data"]
				for item in items :
					list = []
					list = item["pic"].split(";;")
					item["pic"] = list
				self.render(template_path+"ideaList.html" ,ideas=res["data"])
		return 

	def post(self):


class SubmitIdeaHandler(tornado.web.RequestHandler):
	def get(self):
		template_path=filePath+"/../template/"
		self.render(template_path+"subidea.html")
		return 

	def post(self):
		title = self_get_argument("title")


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
#				(r"/login" , UserLoginHandler),
#				(r"/reg", UserRegisterHandler) ,
				(r"/admin", ListIdeaHandler) ,
				], cookie_secret=myConfig.yueYouAiConfig.SECURE_KEY["key"])
	application.listen(int(sys.argv[1]))
	tornado.ioloop.IOLoop.instance().start()


