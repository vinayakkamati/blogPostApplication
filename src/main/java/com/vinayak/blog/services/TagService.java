package com.vinayak.blog.services;

import com.vinayak.blog.repositories.TagsRepository;
import com.vinayak.blog.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    boolean checkTagWithName(String tagName);

    Tag getTagByName(String Name);
    List<Tag> getAllTag();
}
