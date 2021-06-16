package cn.youngbear.mapper;
import cn.youngbear.pojo.Author;

public interface AuthorMapper {
    int deleteByPrimaryKey(String authorId);

    int insert(Author record);

    int insertSelective(Author record);

    Author selectByPrimaryKey(String authorId);

    Author selectByAuthName(String authorName);

    int updateByPrimaryKeySelective(Author record);

    int updateByPrimaryKey(Author record);
}