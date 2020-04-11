package com.yicj.hello.service;

import com.yicj.hello.mapper.UserMapper;
import com.yicj.hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库尝试读取该用户
        User user = userMapper.findByUserName(username);
        //用户名不存在，跑出异常
        if (user == null){
            throw new UsernameNotFoundException("用户不存在") ;
        }
        //将数据库形式的roles解析为UserDetails的权限集
        //AuthorityUtils.commaSeparatedStringToAuthorityList
        // 是spring提供的,改方法用于将逗号隔开的权限集字符串切割成可用权限对象列表
        //当然也可以自己实现，如用分号来隔开等，参考generateAuthorities
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
        user.setAuthorities(authorities);
        return user;
    }

    //自行实现权限的转换
    private List<GrantedAuthority> generateAuthorities(String roles){
        List<GrantedAuthority> authorities = new ArrayList<>() ;
        String [] roleArray = roles.split(";") ;
        if (roles !=null && !"".equals(roles)){
            for (String role: roleArray){
                authorities.add(new SimpleGrantedAuthority(role)) ;
            }
        }
        return authorities ;
    }
}
