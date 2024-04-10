package com.tyut.msm.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.entity.Msm;
import com.tyut.msm.entity.query.MsmAdmin;
import com.tyut.msm.entity.query.MsmPost;
import com.tyut.msm.entity.query.MsmQuery;
import com.tyut.msm.entity.subject.OneSubject;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.service.InstituteService;
import com.tyut.msm.service.MsmService;
import com.tyut.msm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小航
 * @since 2022-01-16
 */
@RestController
@RequestMapping("/msm/")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private InstituteService instituteService;

    // 查询最新的8条留言
    @GetMapping("getMsm")
    public R getMsM() {
        List<Msm> msms = msmService.getMsM();
        return R.ok().data("msms", msms);
    }

    // 获取随机留言
    @GetMapping("getRandomMsm")
    public R getRandomMsm() {
        List<Msm> msms = msmService.getgetRandomMsm();
        return R.ok().data("msms", msms);
    }

    // 添加留言
//    @PostMapping("addMsm")
//    public R addMsm(@RequestBody Msm msm) {
//        if(StringUtils.isEmpty(msm.getName()) || msm.getName() == "") {
//            msm.setName("晨晨曦曦");
//        }
//        msmService.saveMsm(msm);
//        return R.ok();
//    }

    // 添加、更新留言
    @PostMapping("addMsm")
    public R addMsm(@RequestBody MsmAdmin msmAdmin) {
        Msm msm = new Msm();
        if(StringUtils.isEmpty(msmAdmin.getName()) || msmAdmin.getName() == "") {
            msm.setName("晨晨曦曦");
        }else {
            msm.setName(msm.getName());
        }
        msm.setContent(msmAdmin.getContent());
        msm.setStatus(msmAdmin.getStatus());
        msm.setPath(msmAdmin.getPath());
        // 学院名称不为空
        if(!StringUtils.isEmpty(msmAdmin.getIname())) {
            String iid = instituteService.getIdByName(msmAdmin.getIname());
            // 数据库不存在该学院
            if(StringUtils.isEmpty(iid)) {
                Institute institute = new Institute();
                institute.setIName(msmAdmin.getIname());
                institute.setICount(1);
                instituteService.save(institute);
            }
            msm.setIId(instituteService.getIdByName(msmAdmin.getIname()));
            instituteService.updateCount(msm.getIId());
        }
        // 班级名称不为空
        if(!StringUtils.isEmpty(msmAdmin.getCname())) {
            String cid = classInfoService.getIdByName(msmAdmin.getCname());
            // 该班级不存在
            // 数据库不存在该学院
            if(StringUtils.isEmpty(cid)) {
                System.out.println("不存在");
                ClassInfo classInfo = new ClassInfo();
                classInfo.setCName(msmAdmin.getCname());
                classInfo.setCCount(1);
                String iid = instituteService.getIdByName(msmAdmin.getIname());
                if(!StringUtils.isEmpty(iid)) {
                    classInfo.setIId(iid);
                }
                classInfoService.save(classInfo);
            }
            msm.setCId(classInfoService.getIdByName(msmAdmin.getCname()));
            classInfoService.updateCount(msm.getCId());
        }
        if(!StringUtils.isEmpty(msmAdmin.getId())) {
            msm.setId(msmAdmin.getId());
            msmService.updateById(msm);
        }else {
            msmService.saveMsm(msm);
        }
        return R.ok();
    }




    // 统计留言
    @GetMapping("countMsm")
    public R countMsm() {
        Integer countMsm = msmService.countMsm();
        return R.ok().data("countMsm", countMsm);
    }

    // 根据id删除留言
    @GetMapping("delMsgById/{id}")
    public R delMsgById(@PathVariable("id") String id) {
        msmService.delMsgById(id);
        return R.ok();
    }

    // 获取全部留言
    @GetMapping("getAllMsm")
    public R getAllMsm() {
        List<Msm> msmAll = msmService.list(null);
        return R.ok().data("msmAll", msmAll);
    }

    // 获取全部留言（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = msmService.getOneTwoSubject();
        return R.ok().data("list", list);
    }

    // 分页查询留言、多条件查询
    @PostMapping("/{page}/{limit}")
    public R getPageList(
            @PathVariable Long page, @PathVariable Long limit, @RequestBody MsmQuery msmQuery) {
        Page<Msm> pageParam = new Page<>(page, limit);
        msmService.pageQuery(pageParam, msmQuery);
        List<Msm> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    // 根据id查询留言
    @GetMapping("getMsgById/{id}")
    public R getMsgById(@PathVariable("id") String id) {
        Msm msm = msmService.getMsgById(id);
        // 封装对象
        MsmAdmin msmAdmin = new MsmAdmin();
        msmAdmin.setId(msm.getId());
        msmAdmin.setContent(msm.getContent());
        msmAdmin.setName(msm.getName());
        if(!StringUtils.isEmpty(msm.getCId())) {
            msmAdmin.setCname(classInfoService.getNameById(msm.getCId()));
        }
        if(!StringUtils.isEmpty(msm.getIId())) {
            msmAdmin.setIname(instituteService.getNameById(msm.getIId()));
        }
        msmAdmin.setIname(msm.getIId());
        msmAdmin.setStatus(msm.getStatus());
        //
        return R.ok().data("msm", msmAdmin);
    }


}

