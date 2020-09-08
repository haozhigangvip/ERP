package com.targetmol.domain.system.ext;

import com.targetmol.domain.system.PermissionMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class XcMenuExt extends PermissionMenu {

    List<CategoryNode> children;
}
