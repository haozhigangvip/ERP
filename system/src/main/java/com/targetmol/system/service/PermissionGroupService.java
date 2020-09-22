package com.targetmol.system.service;

import com.targetmol.common.emums.ExceptionEumn;
import com.targetmol.common.exception.ErpExcetpion;
import com.targetmol.domain.system.Permission;
import com.targetmol.domain.system.PermissionGroup;
import com.targetmol.domain.system.PermissionGroupItem;
import com.targetmol.domain.system.ext.PermissionGroupUser;
import com.targetmol.system.dao.PermissionGroupDao;
import com.targetmol.system.dao.PermissionGroupItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionGroupService {
    @Autowired
    private PermissionGroupDao permissionGroupDao;
    @Autowired
    private PermissionGroupItemDao permissionGroupItemDao;
    
    //添加权限组
    public void addPermissionGroup(PermissionGroup permissionGroup) {
        //检查参数
        checkPermissionGroup(permissionGroup);
        if(permissionGroupDao.insertSelective(permissionGroup)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }

    //修改权限组
    public void savePermissionGroup(PermissionGroup permissionGroup) {
        //检查参数
        checkPermissionGroup(permissionGroup);
        if(permissionGroupDao.updateByPrimaryKeySelective(permissionGroup)!=1){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_SAVE);
        }
    }


    //删除权限组
    public void delPermissionGroup(Integer id) {
        if(id==null){
            throw new ErpExcetpion(ExceptionEumn.GROUPID_IS_NULL);
        }
        //判断ID是否存在
        PermissionGroup permissionGroup=findById(id);
        if(findById(id)==null){
            throw  new ErpExcetpion(ExceptionEumn.GROUP_IS_NOT_FOUND);
        }
        //判断该组下面是否有子组
        List<PermissionGroup>lst=findByPid(id);
        if(lst!=null || lst.size()>0){
            throw new ErpExcetpion(ExceptionEumn.FIND_SUB_GROUP);
        }
        if(permissionGroupDao.delete(permissionGroup)<0){
            throw new ErpExcetpion(ExceptionEumn.FAIIL_TO_DELETE);
        }

    }


    //根据ID查询下属组
    public List<PermissionGroup> findByPid(Integer id){
        if(id!=null){
            PermissionGroup permissionGroup=new PermissionGroup();
            permissionGroup.setPid(id);
            return (permissionGroupDao.select(permissionGroup));
        }
        return null;
    }


    //查询所有权限组
    public List<PermissionGroup> findAll() {

        List<PermissionGroup> data=permissionGroupDao.selectAll();
        List<PermissionGroup> lst=new ArrayList<>();
        for (PermissionGroup item:data){
            if(item.getPid()==null){
                lst.add(item);
            }
        }
        for(PermissionGroup item:lst){
            item.setPermissionGroupList(getChilde(item.getId(),data));
        }
        return lst;
    }


    //嵌套子查询
    private List<PermissionGroup>getChilde(Integer id,List<PermissionGroup> rootList){
        //子菜单
        List<PermissionGroup> childList=new ArrayList<>();
        if(rootList==null||rootList.size()<=0){
            return null;
        }
        for(PermissionGroup item:rootList){
            if(item!=null&&item.getPid()!=null &&item.getPid().equals(id)){
                childList.add(item);
            }
        }
        for(PermissionGroup item:childList){
            item.setPermissionGroupList(getChilde(item.getId(),rootList));
        }
        if(childList.size()==0){
            return null;
        }
        return childList;
    }

    //根据组ID查询绑定的用户
    public List<PermissionGroupUser> findAllByGid(Integer gid){
       
        if(gid!=null){
            PermissionGroupItem item=new PermissionGroupItem();
            item.setGid(gid);
            List<PermissionGroupItem> lst=permissionGroupItemDao.select(item);

        }
        return null;
    }

    //查询当前用户ID下的所有子用户ID
    public List<PermissionGroupUser> findAllSubUidByUid(Integer uid){
        if(uid!=null){
            //获取改用户所属组
            List<PermissionGroupItem> uids =permissionGroupItemDao.findByUid(uid);
            List<PermissionGroupItem> new_uids=new ArrayList<>();
            //只诗选最上层或同层该用户，下层该用户剔除
            if(uids!=null && uids.size()>=1){
                Integer pid_0=99;
                for (PermissionGroupItem item:uids) {
                    if(item.getPid()<=pid_0){
                        new_uids.add(item);
                        pid_0=item.getPid();
                    }
                }

            }
        List<PermissionGroupUser> data=new ArrayList<>();
        for (PermissionGroupItem item:new_uids) {
                //根据用户对应的GID遍历子组
                List<PermissionGroupUser> glist=permissionGroupItemDao.findSubUserByPid(item.getGid());
                List<PermissionGroupUser>result=getSubList(glist,item.getGid());
                if(result!=null && result.size()>0){
                    data.addAll(result);
                }

                //获取用户
            }

        return data;

        }
        return null;
    }

    private List<PermissionGroupUser> getSubList(List<PermissionGroupUser> lst,Integer pid){
        if(lst==null||lst.size()<=0){
            return null;
        }
        List<PermissionGroupUser> result=new ArrayList<>();
        result.addAll(lst);
        for (PermissionGroupUser item:lst){
            List<PermissionGroupUser> sublist=permissionGroupItemDao.findSubUserByPid(item.getGroupId());
            List<PermissionGroupUser> rt=getSubList(sublist,item.getGroupId());
            if(rt!=null&& rt.size()>0){
                result.addAll(rt);
            }

        }
        if(lst==null||lst.size()<=0){
            return null;
        }
        return result;
    }




    //检查参数
    private void checkPermissionGroup(PermissionGroup permissionGroup){
        //检查组名是否为空
        if(StringUtil.isEmpty(permissionGroup.getGroupname())){
            throw new ErpExcetpion(ExceptionEumn.GROUPNAME_IS_NULL);
        }
        //检查组名是否存在
        Example example=new Example(PermissionGroup.class);
        Example.Criteria criteria1=example.createCriteria();
        criteria1.andEqualTo("groupname",permissionGroup.getGroupname());
       if(permissionGroup.getId()!=null){
            criteria1.andNotEqualTo("id",permissionGroup.getId());
        }
        example.and(criteria1);
       if(permissionGroupDao.selectByExample(example)!=null){
           throw new ErpExcetpion(ExceptionEumn.GROUPNAME_ALREADY_EXISTS);
       }
       //检查PID是否存在
        List<PermissionGroup> lst=findByPid(permissionGroup.getPid());
        if(permissionGroup.getPid()!=null && (lst==null||lst.size()<=0)){
            throw  new ErpExcetpion(ExceptionEumn.GROUP_PID_IS_NOT_FOUND);
        }

    }

    //根据ID查询组
    public PermissionGroup findById(Integer id){
        if(id!=null){
            return permissionGroupDao.selectByPrimaryKey(id);
        }
        return null;
    }

    //绑定用户
    public void bindUsers(Integer gid, Integer[] uids) {
        if(gid==null || uids==null||uids.length<=0){
            throw new ErpExcetpion(ExceptionEumn.OBJECT_VALUE_ERROR);
        }

        PermissionGroup permissionGroup=findById(gid);
        if(permissionGroup==null){
            throw  new ErpExcetpion(ExceptionEumn.GROUP_IS_NOT_FOUND);
        }

        PermissionGroupItem permissionGroupItem=new PermissionGroupItem();
        permissionGroupItem.setGid(permissionGroup.getId());

        //清空绑定表
        if(permissionGroupItemDao.delete(permissionGroupItem)<0){
            throw new ErpExcetpion(ExceptionEumn.BIND_GROUP_FAILD);
        }

        //绑定表
        for (Integer uid:uids) {
            //检查用户id是否存在


            //保存绑定吧
            PermissionGroupItem newData=new PermissionGroupItem();
            newData.setGid(permissionGroup.getId());
            newData.setPid(permissionGroup.getPid()==null?0:permissionGroup.getPid());
            newData.setUid(uid);
            if(permissionGroupItemDao.insert(newData)!=1){
                throw new ErpExcetpion(ExceptionEumn.BIND_GROUP_FAILD);
            }

        }


    }
}
