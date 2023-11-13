package com.gopang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class CustumUserDetails implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private final Long id;

    @Column(name = "email", length = 50, unique = true)
    private final String email;

    @Column(name = "password", length = 100)
    private final String password;

    @Column
    @JoinColumn(name = "authority")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Authority authority;

    public CustumUserDetails(Long id, String email, String password,Authority authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
