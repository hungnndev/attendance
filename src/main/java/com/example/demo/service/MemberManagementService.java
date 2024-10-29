package com.example.demo.service;

import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.repository.MemberManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class MemberManagementService implements IMemberManagementService {
//
//    @Autowired
//    private Session session;

    @Autowired
    private MemberManagementRepository memberManagementRepository;

    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();
    }

    @Override
    public  Optional<User> findById(Long id) {
        return memberManagementRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return memberManagementRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        memberManagementRepository.deleteById(id);

    }

    @Override
    public Set<Position> getPositionByUser(Long userId) {
        if (userId!= null) {
            Optional<User> optionalUser = memberManagementRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                Set<Position> positions = foundUser.getPositions();
                log.info("Position of user {}: {}", foundUser.getUserName(), positions);
                return positions;
            }
        }
        return null;
    }
}