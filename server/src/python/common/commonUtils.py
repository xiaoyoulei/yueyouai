import time
ISOTIMEFORMAT='%Y-%m-%d %X'

def get_now_time():                                                                                                            
	return time.strftime(ISOTIMEFORMAT, time.localtime())  
def convert_int_to_string_time(second):
	return time.strftime(ISOTIMEFORMAT, time.localtime(float(second)))
