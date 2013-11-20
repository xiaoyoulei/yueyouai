import tornado.web , tornado.ioloop ,  sys
sys.path.append("./db")
import utils


class UserLoginHandler(tornado.web.RequestHandler):
	def get(self):
		self.write('<html><body><form action="/login" method="post">'
				'username:<input type="text" name="username">'
				'</p>psw<input type="text" name="psw">'
				'</p><input type="submit" value="login in">'
				'</form></body></html>')
	
	def post(self):
		username = self.get_argument("username")
		psw = self.get_argument("psw")
		my_utils = utils.Utils()
		if not username or not psw:
			raise tornado.web.HTTPError(404)
		if my_utils.UserCheck(username , psw) == True :
			self.set_cookie("xiaoenai_token",my_utils.gen_token(username , psw))

if __name__ == "__main__":
	application = tornado.web.Application(
			[
				(r"/login" , UserLoginHandler),
				])
	application.listen(sys.argv[1])
	tornado.ioloop.IOLoop.instance().start()


