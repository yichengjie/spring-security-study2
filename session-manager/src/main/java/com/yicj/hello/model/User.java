package com.yicj.hello.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    @Setter
    @Getter
    private Long id ;
    @Getter
    private String username;
    @Getter
    private String password ;
    @Setter
    @Getter
    private String roles ;
    @Setter
    private boolean enable ;
    @Setter
    private List<? extends GrantedAuthority> authorities ;


    //这里需要重写equals和hashCode会话管理的时候会使用
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            return this.username.equals(((User)obj).getUsername()) ;
        }
        return false ;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode() ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
