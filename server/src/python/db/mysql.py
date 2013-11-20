# -*- coding: utf-8 -*-
import sys
import os
filePath = os.path.split(os.path.realpath(__file__))[0]
sys.path.append(filePath+"/../config")
import myConfig
#import MySQLdb
class MyDB:
	def __init__(self):
		print myConfig.yueYouAiConfig.mysql
