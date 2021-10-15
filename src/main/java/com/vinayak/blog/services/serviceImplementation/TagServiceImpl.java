package com.vinayak.blog.services.serviceImplementation;

import com.vinayak.blog.models.Tag;
import com.vinayak.blog.repositories.TagsRepository;
import com.vinayak.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public boolean checkTagWithName(String tagName) {
        Tag tag = tagsRepository.findTagWithName(tagName);
        if (tag != null) {
            return true;
        }
        return false;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagsRepository.findTagWithName(name);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagsRepository.findAll();
    }
}