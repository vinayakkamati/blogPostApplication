package com.vinayak.blog.services;

import org.springframework.stereotype.Service;

@Service
public interface PostTagService {
    void deleteTagByPostId(int id);
}
