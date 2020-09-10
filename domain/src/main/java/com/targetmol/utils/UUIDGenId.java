package com.targetmol.utils;

import tk.mybatis.mapper.genid.GenId;

public class UUIDGenId implements GenId<Integer> {
    @Override
    public Integer genId(String s ,String s1){
        return NumberUtils.getUUIDInOrderId();
    }

}
