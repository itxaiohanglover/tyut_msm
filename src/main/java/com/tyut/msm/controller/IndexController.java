package com.tyut.msm.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tyut.msm.entity.ClassInfo;
import com.tyut.msm.entity.Institute;
import com.tyut.msm.entity.Msm;
import com.tyut.msm.entity.query.MsmPost;
import com.tyut.msm.entity.query.MsmShow;
import com.tyut.msm.service.ClassInfoService;
import com.tyut.msm.service.InstituteService;
import com.tyut.msm.service.MsmService;
import com.tyut.msm.service.StaService;
import com.tyut.msm.utils.DateUtil;
import com.tyut.msm.utils.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xh
 * @Date 2022/1/21
 * 前端接口
 */
@RestController("/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private StaService staService;

    @Resource
    private MinioClient minioClient;

    @Value("${endpoint}")
    private String endpoint;
    @Value("${bucket}")
    private String bucket;


    @GetMapping("/msms")
    public R index(){
        // 增加访问量
        staService.addView();
        // 查询最新留言并已通过审核的
        List<Msm> msms = msmService.getAcMsM(6);
        List<MsmShow> msmShows = new ArrayList<MsmShow>();
        for(Msm msm : msms) {
            MsmShow msmShow = new MsmShow();
            msmShow.setName(msm.getName());
            msmShow.setContent(msm.getContent());
            if(!StringUtils.isEmpty(msm.getCId())) {
                msmShow.setCname(classInfoService.getNameById(msm.getCId()));
            }
            if(!StringUtils.isEmpty(msm.getIId())){
                msmShow.setIname(instituteService.getNameById(msm.getIId()));
            }
            msmShow.setDate(msm.getGmtCreate());
            msmShows.add(msmShow);
        }
        System.out.println(msmShows);
        return R.ok().data("msmShows", msmShows);
    }

    @GetMapping("/views")
    public R views(){
        // 增加访问量
        staService.addView();
        // 留言总数
        Integer countMsm = msmService.countMsm();
        // 浏览量查询
        Integer viewNum = staService.countViews(DateUtil.formatDate(new Date())).getViewNum();
        return R.ok().data("countMsm", countMsm).data("viewNum", viewNum);
    }



    @GetMapping("/getCollegeRank")
    public R getCollegeRank() {
        // 增加访问量
        staService.addView();
        // 学院排行榜
        List<Institute> iTopList = instituteService.getTop();
        return R.ok().data("collegeRank", iTopList);
    }

    @GetMapping("/getClassRank")
    public R getClassRank() {
        // 增加访问量
        staService.addView();
        // 班级排行榜
        List<ClassInfo> cTopList = classInfoService.getTop();
        return R.ok().data("classRank", cTopList);
    }

    @PostMapping("/postData")
    public R postData(@RequestBody MsmPost msmPost) {
        Msm msm = new Msm();
        // 学院名称不为空
        if(!StringUtils.isEmpty(msmPost.getIname())) {
            String iid = instituteService.getIdByName(msmPost.getIname());
            // 数据库不存在该学院
            if(StringUtils.isEmpty(iid)) {
                Institute institute = new Institute();
                institute.setIName(msmPost.getIname());
                institute.setICount(1);
                instituteService.save(institute);
            }
            msm.setIId(instituteService.getIdByName(msmPost.getIname()));
            instituteService.updateCount(msm.getIId());
        }
        // 班级名称不为空
        if(!StringUtils.isEmpty(msmPost.getCname())) {
            String cid = classInfoService.getIdByName(msmPost.getCname());
            // 该班级不存在
            // 数据库不存在该学院
            if(StringUtils.isEmpty(cid)) {
                System.out.println("不存在");
                ClassInfo classInfo = new ClassInfo();
                classInfo.setCName(msmPost.getCname());
                classInfo.setCCount(1);
                String iid = instituteService.getIdByName(msmPost.getIname());
                if(!StringUtils.isEmpty(iid)) {
                    classInfo.setIId(iid);
                }
                classInfoService.save(classInfo);
            }
            msm.setCId(classInfoService.getIdByName(msmPost.getCname()));
            classInfoService.updateCount(msm.getCId());
        }
        msm.setContent(msmPost.getContent());
        if(StringUtils.isEmpty(msmPost.getName()) || msmPost.getName() == "") {
            msm.setName("晨晨曦曦");
        }else {
            msm.setName(msmPost.getName());
        }
        msmService.save(msm);
        // 增加访问量和浏览量
        staService.addAll();
        return R.ok();
    }
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 上传
        String path = UUID.randomUUID().toString(); // 文件名，使用 UUID 随机
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket) // 存储桶
                .object(path) // 文件名
                .stream(file.getInputStream(), file.getSize(), -1) // 文件内容
                .contentType(file.getContentType()) // 文件类型
                .build());
        // 拼接路径
        System.out.println(String.format("%s/%s/%s", endpoint, bucket, path));
        return R.ok().data("file",String.format("%s/%s/%s", endpoint, bucket, path));
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete/")
    public R delete(@RequestParam("path") String path) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket) // 存储桶
                .object(path) // 文件名
                .build());
        return R.ok();
    }

}
