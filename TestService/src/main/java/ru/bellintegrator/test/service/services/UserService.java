package ru.bellintegrator.test.service.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.test.service.dto.ActionUser;
import ru.bellintegrator.test.service.dto.UserEventDto;
import ru.bellintegrator.test.service.dto.UserSearchDto;
import ru.bellintegrator.test.service.dto.CreateUserDto;
import ru.bellintegrator.test.service.mappers.UserMapper;
import ru.bellintegrator.test.service.models.User;
import ru.bellintegrator.test.service.repositories.UserRepository;
import ru.bellintegrator.test.service.dto.UserDto;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private KafkaTemplate<String, UserEventDto> mbTemplate;

    @PostConstruct
    @Profile("local")
    public void init() {
        repository.save(User.builder().firstname("John").lastname("Doe").age(25).build());
        repository.save(User.builder().firstname("Jane").lastname("Smith").age(30).build());
        repository.save(User.builder().firstname("Bob").lastname("Johnson").age(35).build());
        repository.save(User.builder().firstname("Alice").lastname("Williams").age(28).build());
        repository.save(User.builder().firstname("Charlie").lastname("Brown").age(40).build());
        repository.save(User.builder().firstname("Eva").lastname("Davis").age(22).build());
        repository.save(User.builder().firstname("Frank").lastname("Miller").age(33).build());
        repository.save(User.builder().firstname("Grace").lastname("Wilson").age(27).build());
        repository.save(User.builder().firstname("Henry").lastname("Moore").age(45).build());
        repository.save(User.builder().firstname("Ivy").lastname("Taylor").age(31).build());
    }

    @Transactional(readOnly = true)
    public UserDto getById(UUID id) {
        log.debug("UserService: getById, id: {}", id);
        return repository.findById(id).map(mapper::entityToDto).orElseGet(() -> null);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "getAllUsers")
    public Page<UserDto> getAll(UserSearchDto searchRequest, Pageable pageable) {
        log.debug("UserService: getAll, UserSearchDto: {}, page={}, size={}, sort={}",
                searchRequest, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchRequest.getFirstname() != null) {
                predicates.add(cb.like(cb.lower(root.get("firstname")), "%" + searchRequest.getFirstname().toLowerCase() + "%"));
            }
            if (searchRequest.getLastname() != null) {
                predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + searchRequest.getLastname().toLowerCase() + "%"));
            }
            if (searchRequest.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), searchRequest.getAge()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return repository.findAll(spec, pageable).map(mapper::entityToDto);
    }

    public UserDto create(CreateUserDto request) {
        log.debug("UserService: create, CreateUserDto: {}", request);
        var newUser = User.builder().firstname(request.firstname).lastname(request.lastname).age(request.age).build();
        sendMB(UserEventDto.builder().action(ActionUser.CREATE).user(newUser).build());
        return  mapper.entityToDto(newUser);
    }

    public UserDto update(UUID id, User user) throws Exception {
        log.debug("UserService: update, id: {}, user: {}", id, user);
        if (user.getId() != id) { throw new Exception("user.getId() != id"); }

        sendMB(new UserEventDto(ActionUser.UPDATE, user));
        return  mapper.entityToDto(user);
    }

    public void deleteById(UUID id) {
        log.debug("UserService: deleteById, id: {}", id);
        sendMB(new UserEventDto(ActionUser.DELETE_BY_ID, User.builder().id(id).build()));
    }

    @CacheEvict(cacheNames="getAllUsers", allEntries=true)
    public void upsertForMB(User user) {
        log.debug("UserService: upsertForMB, user: {}", user);
        repository.save(user);
    }

    @CacheEvict(cacheNames="getAllUsers", allEntries=true)
    public void deleteByIdForMB(UUID id) {
        log.debug("UserService: deleteByIdForMB, id: {}", id);
        repository.findById(id).ifPresent(user -> {
            user.setIsDeleted(true);
            repository.save(user);
        });
    }

    private void sendMB(UserEventDto message) { mbTemplate.send("user-topic", message); }
}
