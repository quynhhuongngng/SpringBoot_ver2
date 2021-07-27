package com.tutorial.fleetapp.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.fleetapp.repositories.CommentRepository;
import com.tutorial.fleetapp.models.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    // Return list of states
    public List<Comment> getComment() {
        return commentRepository.findAll();
    }

    // SAve new state
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    // get by id
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}