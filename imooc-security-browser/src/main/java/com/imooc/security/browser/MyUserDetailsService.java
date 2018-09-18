package com.imooc.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 实现UserDetailsService接口，来自定义用户认证逻辑
 * @author Jiangxb
 * 
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

	/**
	 * 这里不操作数据库
	 * 	不管你是用Spring Data JPA 还是 MyBatis连接数据库。
	 * 你可以通过@Autowired将你的dao对象注入进来。
	 * 你就可以在loadUserByUsername()方法中根据用户名去数据里面查出用户信息
	 */
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		logger.info("登录用户名 ： " + s);
		// 第一步：根据用户名查找用户信息
		/**
		 * org.springframework.security.core.userdetails.User.User(String username, String password, Collection<? extends GrantedAuthority> authorities)
		 * 
		 * AuthorityUtils.commaSeparatedStringToAuthorityList("admin")
		 * 	这个工具方法，是将字符串转换为需要的对象(GrantedAuthority)
		 */
		/**
		 * User(用户名, 密码, 权限(集合))。已经实现了UserDetails接口
		 * 写完再次启动，运行http://localhost:8080/user
		 * 		此时用户名可以随便输入，但是密码必须为123456；否则会提示错误
		 */
//		return new User(s, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		// 第二步：根据查找到的用户信息判断用户是否被冻结
		/**
		 * User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
		 * 		  用户名		密码		是否被禁用  	是否过期				是否已过期的用户的凭证	用户是否被锁定			权限
		 * 跟上面比，多了四个 布尔值参数
		 * 
		 * 说明：用户可以自定义返回的类，不一定要用spring提供的User。你可以使用自己的实体类，但是必须要实现UserDetails接口；
		 * 		然后把用户是否过期、是否冻结等逻辑写在自己的实现类的实现方法里面。
		 * 		
		 * 案例：基于数据库自定义UserDetailsService实现Spring security认证
		 * 		https://blog.csdn.net/neweastsun/article/details/79360724
		 * 此时再登录，会提示——Reason: 用户帐号已被锁定
		 */
		//return new User(s, "123456", true, true, true, false, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		//第三步：加密与解密;注入PasswordEncoder
		/**
		 * 说明： 这一步应该是在注册的时候做的，现在只是模拟。
		 * 	真正做项目时：这个password只需要从数据库里面取出即可
		 * 
		 * 测试：没有将下面“123456”加密时，运行时输入123456，会报错。因为你在配置里面已经配置了passwordEncoder
		 * 将“123456”改成password，再进行测试,就可以通过
		 * 
		 * 利用passwordEncoder.encode()加的密，每次都不一样，但是用户都可以登录；因为它密码里面有”盐值“，虽然
		 * 	保存到数据库里面的跟你此时此刻输入的不一样，但是SpringSecurity会拿你保存的密码的”盐值“进行反推，从而匹配。
		 * 	这样做的好处：如所有人的密码都一样(123456),但是经过encode后都不一样。
		 */
		String password = passwordEncoder.encode("123456");
		logger.info("数据库密码是：" + password);
		return new User(s, password, true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}

}
