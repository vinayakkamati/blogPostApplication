package com.vinayak.blog.services;

import com.vinayak.blog.models.Tag;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TagService {

    boolean checkTagWithName(String tagName);

    Tag getTagByName(String Name);

    List<Tag> getAllTag();
}
