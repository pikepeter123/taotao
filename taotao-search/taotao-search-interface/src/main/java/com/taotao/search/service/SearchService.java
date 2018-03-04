package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * Created by 31364 on 2018/3/4.
 * description:
 */
public interface SearchService {

    SearchResult search(String keyWord, Integer page, Integer rows) throws Exception;
}
