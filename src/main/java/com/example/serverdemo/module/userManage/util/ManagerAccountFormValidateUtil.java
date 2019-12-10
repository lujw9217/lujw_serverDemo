package com.example.serverdemo.module.userManage.util;

import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.util.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	用户form表单校验
 */
public class ManagerAccountFormValidateUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ManagerAccountFormValidateUtil.class);
	
	/**
	 * @description   : 验证 用户名
	 * @method_name   : validateAccount
	 * @param         : [account]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:55
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public static void validateAccount(String account) throws TopException {
		//参数空判断
		if(BaseUtil.isIncludeEmpty(account)){
			logger.error("管理系统参数验证","用户名为空！");
			throw new TopException("管理系统参数验证","用户名为空！");
		}
		
		//特殊字符
		String REG_SPECIAL = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		//全是数字
		String REG_NUM = "^\\d+$";
		//匹配以_开头或者结尾
		String REG_UNDERLINE = "^(^\\_[a-zA-Z0-9_\u4e00-\u9fa5]+)||([a-zA-Z0-9_\u4e00-\u9fa5]+\\_$)$";
		if(!BaseUtil.strReg(account, REG_SPECIAL)){
			logger.error("账号不能包含特殊字符！");
			throw new TopException("账号验证","账号不能包含特殊字符！");
		}else if(BaseUtil.strReg(account, REG_NUM)){
			logger.error("账号不能全是数字！");
			throw new TopException("账号验证","账号不能全是数字！");

		}else if(BaseUtil.strReg(account, REG_UNDERLINE)){
			logger.error("账号不能以下划线开头或者结尾！");
			throw new TopException("账号验证","账号不能以下划线开头或者结尾！");
		}
	}
	
	/**
	 * @description   : 校验密码
	 * @method_name   : validatePassWord
	 * @param         : [passWord]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:56
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public static void validatePassWord(String passWord) throws TopException{
		//参数空判断
		if(BaseUtil.isIncludeEmpty(passWord)){
			logger.error("管理系统参数验证","密码为空！");
			throw new TopException("管理系统参数验证","密码为空！");
		}
		
		//验证密码长度
		String REG_PASSWORD = "^[\\S]{8,16}$";
		if(!BaseUtil.strReg(passWord, REG_PASSWORD)){
			logger.error("密码不合法！");
			throw new TopException("管理系统参数验证","密码不合法！");
		}

	}

/**
 * @description   : 判断字符串string1中是否包含另一个字符串string2
 * @method_name   : stringContains
 * @param         : [string1, string2]
 * @return        : int
 * @throws        :
 * @date          : 2019/12/10 17:57
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static int stringContains(String string1,String string2){
    	int a=string1.indexOf(string2);
    	if(a>=0){
    		return 0;
    	}
    	return 1;
    }
    
    
    /**
     * @description   : 判断字符串string是否含有三个相同的字符（如ahsahsmhl）
     * @method_name   : StringContinu
     * @param         : [string]
     * @return        : int
     * @throws        :
     * @date          : 2019/12/10 17:57
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static int StringContinu(String string){
		int result = 1;
		int lena = string.length();
		for (int i = 0; i < lena - 2; i++) {
			String c = string.substring(i, i + 3);
			int t = string.indexOf(c, i + 3);
			if (t >=0) {
				result = 0;
				break;
			}
		}
		return result;
	}

   /**
    * @description   : 校验字符串是否全为字母或数字,且长度在8-16位
    * @method_name   : allLetterOrNum
    * @param         : [str]
    * @return        : boolean
    * @throws        :
    * @date          : 2019/12/10 17:57
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static boolean allLetterOrNum(String str){
    	String REG_LETTER = "^[a-zA-Z]+$";
    	String REG_LETTERU = "^[a-z]+$";
    	String REG_LETTERL = "^[A-Z]+$";
    	String REG_NUM = "^[0-9]+$";
    	String REG_LENGTH = "^[\\S]{8,16}$";
    	if(BaseUtil.strReg(str, REG_LETTER) ||
			BaseUtil.strReg(str, REG_LETTERU)||
			BaseUtil.strReg(str, REG_LETTERL)||
			BaseUtil.strReg(str, REG_NUM)){
    		return false;
    	}
    	if(!BaseUtil.strReg(str, REG_LENGTH)){
    		return false;
    	}
		return true;
    }

   /**
    * @description   : 检测字符串是否包含三个相同字符
    * @method_name   : containsThreeConsecutiveIdentical
    * @param         : [str]
    * @return        : boolean
    * @throws        :
    * @date          : 2019/12/10 17:58
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static boolean containsThreeConsecutiveIdentical(String str) {
		int length = str.length();
		for (int i = 0; i < length - 2;) {
			char c1 = str.charAt(i);
			char c2 = str.charAt(i + 1);
			char c3 = str.charAt(i + 2);
			if (c3 != c2)
				i += 2;
			else if (c1 != c2)
				i += 1;
			else {
				return true;
			}
		}
		return false;
	}
    
    /**
     * @description   : 检测字符串是否包含三个连续字符
     * @method_name   : containsThreeConsecutiveValue
     * @param         : [str]
     * @return        : boolean
     * @throws        :
     * @date          : 2019/12/10 17:58
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean containsThreeConsecutiveValue(String str) {
		int length = str.length();
		for (int i = 0; i < length - 2;) {
			char c2 = str.charAt(i + 1);

			if ((Character.isDigit(c2)) || (Character.isLowerCase(c2))
					|| (Character.isUpperCase(c2))) {
				char c1 = str.charAt(i);
				char c3 = str.charAt(i + 2);
				if ((c2 * '\2' == c1 + c3)
						&& (((c2 == c1 + '\1') || (c2 == c3 + '\1')))) {
					return true;
				}
				i += 1;
			} else {
				i += 2;
			}
		}
		return false;
	}

    /**
     * @description   : 弱密码校验
     * @method_name   : checkWeakPassword
     * @param         : [account, password]
     * @return        : boolean
     * @throws        :
     * @date          : 2019/12/10 17:58
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
	public static boolean checkWeakPassword(String account,String password){
		// 判断密码中是否含有用户名
		Integer containName = stringContains(password, account);
		// 判断密码中是否含有三个字符重复的子字符串（asd asd kuiy）
		Integer contnuStr = StringContinu(password);
		//判断三个相同字符
		boolean result= containsThreeConsecutiveIdentical(password);
		//判断三个连续字符
		boolean threeSame= containsThreeConsecutiveValue(password);
		if(result || threeSame){
			return false;
		}
		//判断是否全为数字或字母		
		boolean letter = allLetterOrNum(password);
		if(!letter){
			return false;
		}
		return true;
	}
    
}
