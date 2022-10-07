package com.security.demo.login.userDetail;

import com.security.demo.app.entity.User;
import com.security.demo.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Exception 을 Handling 할 수도 있지만 예제에서 생략
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));
        return new MyUserDetails(user);
    }
}
