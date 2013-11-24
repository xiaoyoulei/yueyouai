import logging
import logging.config
import sys 
import os 
filePath = os.path.split(os.path.realpath(__file__))[0] 
#confFile = filePath+"/../config/logging.conf"
#logging.config.fileConfig(confFile)

m_logFile = filePath+"/../log/yueyouai.log"
m_logErrorFile = filePath+"/../log/yueyouai.log.wf"
m_maxLogSize = 100*1024*1024 #Bytes 100M
m_when = "H" # "S" "M" "H" "D" "W" "midnight"
m_interval = 1
m_backupCount = 240
m_format = '[%(asctime)s][%(levelname)s] %(filename)s:[%(funcName)s]:%(lineno)d (%(name)s) : [%(message)s]'

# CRITICAL > ERROR > WARNING > INFO > DEBUG > NOTSET
'''
#a file
logging.basicConfig(level=logging.DEBUG,
		format='[%(asctime)s][%(levelname)s] %(filename)s:[%(funcName)s]:%(lineno)d (%(name)s) : [%(message)s]',
		filename=m_logFile,
		filemode='a');
'''
#max file by size
from logging.handlers import RotatingFileHandler
rtHandler = RotatingFileHandler(m_logErrorFile, mode='a', maxBytes= m_maxLogSize,backupCount = m_backupCount);
formatter = logging.Formatter(m_format)
rtHandler.setFormatter(formatter)
rtHandler.setLevel(logging.ERROR);
logging.getLogger('').addHandler(rtHandler)

#max time
from logging.handlers import TimedRotatingFileHandler
trHandler = TimedRotatingFileHandler(m_logFile, when = m_when, interval = m_interval, backupCount = m_backupCount);
formatter = logging.Formatter(m_format)
trHandler.setFormatter(formatter) 
#trHandler.setLevel(logging.DEBUG);
logging.getLogger('').addHandler(trHandler)  

# **** add console start ****
console = logging.StreamHandler();
#console.setLevel(logging.DEBUG);
# set a format which is simpler for console use
formatter = logging.Formatter('[%(asctime)s]%(filename)s:[%(funcName)s]:%(lineno)-4d : %(levelname)-8s [%(message)s]');
# tell the handler to use this format
console.setFormatter(formatter);
logging.getLogger('').addHandler(console);
# **** add console end ****

#create logger
logger = logging.getLogger("YYA")
logger.setLevel(logging.DEBUG);

'''
def testLog():
	logger.debug("debug message")
	logger.info("info message")
	logger.warn("warn message")
	logger.error("error message")
	logger.critical("critical message")
import time
while 1:
	testLog()
	time.sleep(1)

logHello = logging.getLogger("hello")
logHello.info("Hello world!")
'''

