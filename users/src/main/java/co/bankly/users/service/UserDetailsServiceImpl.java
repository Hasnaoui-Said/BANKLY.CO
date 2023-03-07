package co.bankly.users.service;

import co.bankly.users.Repository.UserDao;
import co.bankly.users.exception.BadRequestException;
import co.bankly.users.models.entity.User;
import co.bankly.users.rest.required.facade.WalletRestRequired;
import co.bankly.users.util.UtilString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    WalletRestRequired walletRestRequired;

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsernameOrEmail(username);
    }

    // fetch user by username or email and list of authorities , check password before apply to successfulAuthentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = this.findByUsername(username).orElseThrow(()->{
            System.out.println(String.format("User with username %s was not found", username));
            throw new UsernameNotFoundException("Bad credentials");
        });
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
        return userDetails;
    }

    public User save(User user) {
        if (userDao.existsByUsername(user.getUsername()))
            throw new BadRequestException("Username is already token!!");
        if (userDao.existsByEmail(user.getEmail()))
            throw new BadRequestException("Email is already token!!");
        user.setUuid(UUID.randomUUID());
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setPassword(UtilString.passwordEncoder(user.getPassword()));
        return userDao.save(user);
    }

    public List<User> findAll() {
        return this.userDao.findAll();
    }

    public User findByUuid(UUID uuid) {
        return this.userDao.findByUuid(uuid).orElse(null);
    }

    public User findByEmail(String email) {
        return this.userDao.findByEmail(email);
    }

    @Transactional
    public int deleteByUuid(UUID uuid1) {
        return this.userDao.deleteByUuid(uuid1);
    }

    @Transactional
    public int deleteByEmail(String email) {
        return this.userDao.deleteByEmail(email);
    }
}
