package ru.bellintegrator.TestService.services;

import ru.bellintegrator.TestService.dto.ActionUser;
import ru.bellintegrator.TestService.dto.MessageDto;
import ru.bellintegrator.TestService.dto.UserSearchRequest;
import ru.bellintegrator.TestService.dto.AddUserRequest;
import ru.bellintegrator.TestService.mappers.UserMapper;
import ru.bellintegrator.TestService.messaging.MessagingService;
import ru.bellintegrator.TestService.models.User;
import ru.bellintegrator.TestService.repositories.UserRepository;
import ru.bellintegrator.TestService.dto.UserDto;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CurUserService implements UserService {
    private final UserRepository userRepo;
    private final MessagingService messagingService;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(UUID id) {
        return userRepo.findById(id).map(userMapper::entityToDto).orElseGet(() -> null);
    }

    @Override
    @Cacheable(cacheNames = "getAllUsers")
    public Page<UserDto> getAll(UserSearchRequest searchRequest, Pageable pageable) {
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (! searchRequest.getFirstname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("firstname")), "%" + searchRequest.getFirstname().toLowerCase() + "%"));
            }
            if (! searchRequest.getLastname().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + searchRequest.getLastname().toLowerCase() + "%"));
            }
            if (searchRequest.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), searchRequest.getAge()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return userRepo.findAll(spec, pageable).map(userMapper::entityToDto);
    }

    @Override
    public UserDto create(AddUserRequest request) {
        User newUser = new User(
                UUID.randomUUID(),
                request.firstname,
                request.lastname,
                request.age,
                false
        );
        messagingService.send(new MessageDto(ActionUser.CREATE, newUser));
        return  userMapper.entityToDto(newUser);
    }

    @Override
    public UserDto update(UUID id, User user) throws Exception {
        if (user.getId() != id) {
            throw new Exception("user.getId() != id");
        }
        messagingService.send(new MessageDto(ActionUser.UPDATE, user));
        return  userMapper.entityToDto(user);
    }

    @Override
    public void deleteById(UUID id) {
        User user = new User();
        user.setId(id);
        messagingService.send(new MessageDto(ActionUser.DELETE_BY_ID, user));
    }
}
