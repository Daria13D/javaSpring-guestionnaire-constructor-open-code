package ru.arharova.questionnarie_constructor.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

@Data
@MappedSuperclass
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private String username;
    private String password;

    @Transient
    private List<SimpleGrantedAuthority> authorities;

    @Transient
    private Boolean Active;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean Active) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.Active = Active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Active;
    }

    @Override
    public boolean isAccountNonLocked() {return Active;}

    @Override
    public boolean isCredentialsNonExpired() {
        return Active;
    }

    @Override
    public boolean isEnabled() {
        return Active;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}
