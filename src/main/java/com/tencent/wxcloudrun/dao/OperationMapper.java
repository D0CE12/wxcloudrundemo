package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.OperationLog;
import com.tencent.wxcloudrun.model.Operation;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Mapper
public interface OperationMapper {

    @Select("select OID from operation where GID = #{gid} and UserID = #{userid} and Otype = #{otype}" )
    Integer selectOID(@Param("gid") int gid, @Param("userid") String userid, @Param("otype") int otype);

    @Update("UPDATE operation SET Otime = #{otime} WHERE OID = #{OID} and Otype = 0")
    void updateOtime(@Param("otime")LocalDateTime otime, @Param("OID") Integer oID);

    @Insert("insert into operation (GID,UserID,Otype,Otime) values (#{gid},#{userid},#{otype},#{otime})")
    void InsertOperation(@Param("gid") int gid, @Param("userid") String userid,@Param("otype") int otype,@Param("otime")LocalDateTime otime);

    @Delete("delete from operation where OID = #{OID} and Otype IN (1, 2)")
    void DeleteOperation(@Param("OID") Integer oID);

    @Insert("insert into operation (GID,UserID,Otype) values (#{gid},#{userid},#{otype})")
    void InsertNewOperation(@Param("gid") int gid, @Param("userid") String userid,@Param("otype") int otype);

    @Select("select t.oid, t1.Gdes, t1.Gprice, t.Otype , t12.i_url as iurl " +
            "from operation t , goods t1 , goods_image t12  " +
            "where t.GID =t1.GID " +
            "and t1.GID= t12.g_id " +
            "and t1.status = 1 " +
            "and t.Otype = 1 " +
            "and  UserID=#{userid}" )
    ArrayList<Operation> queryCollectByUid(@Param("userid") String userid);


    @Select("select t.oid, t1.Gdes, t1.Gprice, t.Otype , t12.i_url as iurl " +
            "from operation t , goods t1 , goods_image t12  " +
            "where t.GID =t1.GID " +
            "and t1.GID= t12.g_id " +
            "and t.Otype = 0 " +
            "and  UserID=#{userid}" )
    ArrayList<Operation> querybrowseByUid(@Param("userid") String userid);


}
