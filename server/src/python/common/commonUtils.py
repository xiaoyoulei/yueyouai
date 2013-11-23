import time

def get_now_time():                                                                                                            
	ISOTIMEFORMAT='%Y-%m-%d %X'                                                                                                
	return time.strftime(ISOTIMEFORMAT, time.localtime())   
