package com.vinayak.blog.services.serviceImplementation;

import com.vinayak.blog.repositories.PostTagRepository;
import com.vinayak.blog.services.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagServiceImpl implements PostTagService {

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public void deleteTagByPostId(int id) {
        postTagRepository.deleteTagByPostId(id);
    }
}