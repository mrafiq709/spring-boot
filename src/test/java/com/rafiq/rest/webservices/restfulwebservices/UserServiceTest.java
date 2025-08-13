package com.rafiq.rest.webservices.restfulwebservices;

import com.rafiq.rest.webservices.restfulwebservices.model.Location;
import com.rafiq.rest.webservices.restfulwebservices.model.Role;
import com.rafiq.rest.webservices.restfulwebservices.model.UserEntity;
import com.rafiq.rest.webservices.restfulwebservices.repository.LocationRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.RoleRepository;
import com.rafiq.rest.webservices.restfulwebservices.repository.UserRepository;
import com.rafiq.rest.webservices.restfulwebservices.services.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private LocationRepository locationRepository;

    void createUserShouldSaveUserWithEncodedPassword() {
        Role mockRole = new Role();
        mockRole.setRoleName("USER");
        when(roleRepository.findByRoleName("USER")).thenReturn(mockRole);
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> invocation.getArgument(0));
        UserEntity savedUser = userService.createUser("Alice", "Bob", "alice@gmail.com", "alice123", "secret", "USER", "Tokyo");
        assertThat(savedUser.getFirstName()).isEqualTo("Alice");
        assertThat(savedUser.getRole()).isEqualTo("USER");
        assertThat(savedUser.getLocation().getPlace()).isEqualTo("Tokyo");
        assertThat(savedUser.getPassword()).startsWith("$2");

        verify(userRepository).save(any(UserEntity.class));
        verify(locationRepository).save(any(Location.class));
        verify(roleRepository).findByRoleName("USER");
    }
}
