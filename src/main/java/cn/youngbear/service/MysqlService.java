package cn.youngbear.service;

import cn.youngbear.mapper.AuthorMapper;
import cn.youngbear.mapper.PicPopularPermanentMapper;
import cn.youngbear.mapper.PicTagMapper;
import cn.youngbear.mapper.TagMapper;
import cn.youngbear.pojo.Author;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.pojo.PicTag;
import cn.youngbear.pojo.Tag;
import cn.youngbear.utils.util.MybatisSessionPool;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MysqlService {
    SqlSession sqlSession = MybatisSessionPool.getSession();
    private AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
    private PicPopularPermanentMapper picPopularPermanentMapper = sqlSession.getMapper(PicPopularPermanentMapper.class);
    private PicTagMapper picTagMapper = sqlSession.getMapper(PicTagMapper.class);
    private TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
    public void insertAuthor(Author author){
        Author queryAuthor =authorMapper.selectByAuthName(author.getAuthorName());
        if(queryAuthor!=null){
            authorMapper.updateByPrimaryKey(author);
        }else{
            authorMapper.insertSelective(author);
        }
    }
    public void insertPicPopularPermanent(PicPopularPermanent picPopularPermanent){
        PicPopularPermanent queryPicPopularPermanent =picPopularPermanentMapper.selectByPrimaryKey(String.valueOf(picPopularPermanent.getPicId()));
        if(queryPicPopularPermanent!=null){
            picPopularPermanentMapper.updateByPrimaryKey(picPopularPermanent);
        }else{
            picPopularPermanentMapper.insertSelective(picPopularPermanent);
        }
    }

    public void insertPicTag(PicTag picTag){
        List<Tag> tagList = picTag.getTagList();
        String picId = picTag.getPicId();
        for (Tag tag : tagList) {
            Long tagId = getTagId(tag);
            if(tagId==null){
                tagMapper.insert(tag);
            }else{
                tag.setTagId(tagId);
            }
        }
        picTagMapper.insertSelective(picTag);
    }

    /**
     * 先根据中文标签查tagId,如果没有中文标签根据日文查。 如果查不到返回NULl
     * @param tag
     * @return
     */
    public Long getTagId(Tag tag){
        Long queryTagId;
        if(tag.getTagNameZh()!=null){
            queryTagId=tagMapper.selectTagIdByName(new Tag().setTagNameZh(tag.getTagNameZh()));
        }else{
            queryTagId=tagMapper.selectTagIdByName(new Tag().setTagName(tag.getTagName()));
        }
       return queryTagId;
    }

}
