import os
import sys 
import time
filePath = os.path.split(os.path.realpath(__file__))[0]    
sys.path.append(filePath+"/../db/model") 
sys.path.append(filePath+"/../db") 
import utils
import User
import Token
user = User.User(nickName="'zhuli''zhuli01'",phoneNum="18301956106",email="zhuli102233@163.com",passWord="woshimima");
uitl = utils.Utils()
ret = uitl.addUser(user);
print ret

ret = uitl.userCheckByPhoneNum("18301956106","1233")
print ret
ret = uitl.userCheckByEmail("zhuli102233@163.com","woshimima")
print ret
uid = uitl.getUidByPhoneNum("18301956106")
print uid
uid = uitl.getUidByEmail("zhuli102233@163.com")
print uid
tk = Token.Token(nickName="18301956106",pwd="woshimima")
tk.Debug()
tkstr = tk.toString()
print tkstr
tkstr="e+K5AT1bL5fHv6LiVcsn2b7Qm023diCtwE370m2N/P7MOq6EUkXTpfnSLWpmFGJnSrWMWtl0XfpCTVbVNg7X1A=="
Token1 = Token.Token();
ret = Token1.parseString(tkstr)
print ret
Token1.Debug()
