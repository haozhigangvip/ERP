package com.targetmol.domain.system.ext;

import java.util.List;

/**
 * Created by admin on 2018/2/7.
 */

public class CategoryNode extends Category {

    List<CategoryNode> children;

    public List<CategoryNode> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryNode> children) {
        this.children = children;
    }
}
