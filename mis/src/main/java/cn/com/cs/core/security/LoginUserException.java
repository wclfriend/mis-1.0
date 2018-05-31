package cn.com.cs.core.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 登录spring security异常
 *  
 *
 */
public class LoginUserException extends AuthenticationException{
	private static final long serialVersionUID = 213146456576767L;

	public LoginUserException(String msg) {
		super(msg);
	}
	
    public LoginUserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public LoginUserException(String msg, Object extraInformation) {
        super(msg);
    }
}
