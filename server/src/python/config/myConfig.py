# -*- coding:utf-8 -*-
import ConfigParser
import os

class MyConfig:
	mysql = {}
	mysqlPool = {}
	def __init__(self):
		config = ConfigParser.ConfigParser()
		confFileName = os.path.split(os.path.realpath(__file__))[0]+"/yueyouai.ini"
		config.read(confFileName)
		self.mysql["user"] = config.get("mysql","user")
		self.mysql["pwd"] = config.get("mysql","pwd")
		self.mysql["host"] = config.get("mysql","host")
		self.mysql["port"] = config.get("mysql","port")
		self.mysql["dbName"] = config.get("mysql","dbName")
		self.mysqlPool["mincached"] = config.get("mysqlPool","mincached")
		self.mysqlPool["maxcached"] = config.get("mysqlPool","maxcached")
		self.mysqlPool["maxshared"] = config.get("mysqlPool","maxshared")
		self.mysqlPool["maxconnections"] = config.get("mysqlPool","maxconnections")
		
		#print self.mysql
#we can only have a global config object
yueYouAiConfig = MyConfig();

#print yueYouAiConfig.mysql
