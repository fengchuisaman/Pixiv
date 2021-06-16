package cn.youngbear.mapper;

import cn.youngbear.pojo.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Long tagId);

    Long insert(Tag record);

    Long insertSelective(Tag record);

    Long insertTags(List<Tag> list);

    Tag selectByPrimaryKey(Long tagId);

    Long selectTagIdByName(Tag tag);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    Long selectMaxTagId();
}