package com.shop.projectlion.domain.item.repository.custom;

import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.web.adminitem.dto.ItemImageDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final EntityManager em;

    @Override
    public Page<MainItemDto> findItemInfoAllCustom(String searchName, Pageable pageable) {

        String query = "select new com.shop.projectlion.web.main.dto.MainItemDto(i.id,i.name,i.detail,im.name, i.price) " +
                        "from ItemImage im " +
                        "join im.item i " +
                        "where im.isRep = true and i.sellStatus='SELL' ";

        String countQuery = "select count(i) from Item i where i.sellStatus='SELL' ";
        String countSubQueryForSearch = "and (i.name like :search or i.detail like :search)";

        Long count;
        List<MainItemDto> resultList;

        int offset = Long.valueOf(pageable.getOffset()).intValue();

        if(searchName == null){
            resultList = em.createQuery(query, MainItemDto.class)
                            .setFirstResult(offset)
                            .setMaxResults(BaseConst.SET_PAGE_ITEM_MAX_COUNT)
                            .getResultList();

            count = em.createQuery(countQuery, Long.class).getSingleResult();

        }else{
            countQuery += countSubQueryForSearch;
            query += countSubQueryForSearch;

            resultList = em.createQuery(query, MainItemDto.class)
                            .setFirstResult((int) pageable.getOffset())
                            .setMaxResults(BaseConst.SET_PAGE_ITEM_MAX_COUNT)
                            .setParameter("search","%"+searchName+"%")
                            .getResultList();


            count = em.createQuery(countQuery, Long.class)
                    .setParameter("search","%"+searchName+"%")
                    .getSingleResult();
        }

        return new PageImpl<>(resultList,pageable,count);
    }
}
