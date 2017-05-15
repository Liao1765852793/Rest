package com.nullptr.rest.service.impl;

import com.nullptr.mapper.TbItemCatMapper;
import com.nullptr.pojo.TbItemCat;
import com.nullptr.pojo.TbItemCatExample;
import com.nullptr.rest.pojo.CatNode;
import com.nullptr.rest.pojo.ItemCatResult;
import com.nullptr.rest.service.ItemCatService;
import com.sun.source.tree.CompoundAssignmentTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nullptr on 2017/5/15.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * @return
     */
    @Override
    public ItemCatResult getItemCatList() {
        //需要递归调用
        List resulteList = getItemCatList(0L);
        ItemCatResult itemCatResult = new ItemCatResult();
        itemCatResult.setData(resulteList);
        return itemCatResult;
    }

    private List getItemCatList(Long paramId) {
        //根据paramId查询列表
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(paramId);
        //执行查询
        List<TbItemCat> tbItemCatList = itemCatMapper.selectByExample(tbItemCatExample);

        List resultList = new ArrayList();

        for (TbItemCat tbItemCat : tbItemCatList) {
            //如果是父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                ///products/1.html
                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                //如果当前节点是第一级节点
                if (tbItemCat.getParentId() == 0) {
                    //<a href='/products/1.html'>图书、音像、电子书刊</a>
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    //"n": "大 家 电"
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setItems(getItemCatList(tbItemCat.getId()));
                resultList.add(catNode);

            } else {
                ///products/8.html|音乐
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return resultList;
    }
}
