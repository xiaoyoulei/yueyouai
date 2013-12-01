import time
ISOTIMEFORMAT='%Y-%m-%d %X'
MAX_MYSQL_UINT = 4294967295 
def get_now_time():                                                                                                            
	return time.strftime(ISOTIMEFORMAT, time.localtime())  
def convert_int_to_string_time(second):
	return time.strftime(ISOTIMEFORMAT, time.localtime(float(second)))

class ThingType():
	IdeaType = 1
	RecordType = 2
	CommentType = 3
	QuestionTpye = 4
