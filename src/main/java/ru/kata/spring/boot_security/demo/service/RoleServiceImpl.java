package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Set<Role> findRolesByIds(String[] ids) {
        Set<Integer> integersIds = Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toSet());
        Set<Role> result = new HashSet<>();
        for (Integer id : integersIds) {
            result.add(roleRepository.findById(id).get());
        }
        return result;
    }

}
